package Algorithms;

import java.util.ArrayList;

import java.util.Iterator;

import Components.Fruit;
import Components.Game;
import Components.Packman;

/**
 * Description: the class calculates the best route for each packman to eat,
 * considering the number of fruits in a game. the class has only one field
 * which is an arraylist of paths, that updates as the algorithm is built. the
 * class consider the number of fruits when creating the paths, for a game with
 * less than 21 fruits the algorithm uses a more accurate method, but for a
 * large number of fruits it switches to a simpler method, otherwise the program
 * is overwhelmed.
 * 
 * 
 * @author Yarden and Caroline
 *
 */
public class ShortestPathAlgo {

	private ArrayList<Packman> PackmanPaths = null;

	/**
	 * Description: the constuctor builds itself from a game object.
	 * 
	 * @param g
	 */
	public ShortestPathAlgo(Game g) {

		// initiate the fields:
		PackmanPaths = new ArrayList<Packman>();
		ArrayList<Fruit> FruitSet = new ArrayList<Fruit>();

		// going over Game object and creating an empty path for each packman.
		Iterator<Packman> it_p = g.PacItr();
		Packman p;
		while (it_p.hasNext()) {
			p = new Packman(it_p.next());
			p.initPath();
			PackmanPaths.add(p);
		}

		// running over Game Object and creating a deep copy for a fruit list
		Iterator<Fruit> it_f = g.FruItr();
		Fruit f;
		while (it_f.hasNext()) {
			f = new Fruit(it_f.next());
			FruitSet.add(f);
		}

		// for a large amount of game pieces the program is overwhelmed, therefore we
		// have separate cases
		if (FruitSet.size() < 21) {
			SmartPathCalculator(FruitSet);
		} else {
			SimplePathCalculator(FruitSet);
		}

	}


	


	/**
	 * Description: the method runs over all the packman objects in the game and
	 * assign to them the closest fruit in the game, then it searches for the
	 * packman closest to its fruit and adds it to it's path, deletes the fruit from
	 * the main fruits list clears everything and starts from the top. the method
	 * stops when the main fruit list is finally empty.
	 * 
	 * @param FruitSet
	 */
	public void SmartPathCalculator(ArrayList<Fruit> FruitSet) {
		// creating an ArrayList containing the closest fruit to each Packman
		// accordingly, before adding them to their paths.
		ArrayList<Fruit> Closest = new ArrayList<Fruit>();

		while (!FruitSet.isEmpty()) {
			Iterator<Packman> it_path = PackmanPaths.iterator();
			Packman p;
			while (it_path.hasNext()) {
				p = it_path.next();
				Iterator<Fruit> it_fruit = FruitSet.iterator();
				Closest.add(new Fruit(-1, null, 0));
				Fruit f;

				while (it_fruit.hasNext()) {
					f = it_fruit.next();

					double time1 = p.DistanceSpeedTime(f.getLocation());
					double time2 = p.DistanceSpeedTime(Closest.get(Closest.size() - 1).getLocation());

					if ((Closest.get(Closest.size() - 1).getLocation() == null) || (time1 < time2)) {

						if (Closest.indexOf(f) == -1) {
							Closest.remove(Closest.size() - 1);
							Closest.add(f);

						} else {
							double runningtime1 = PackmanPaths.get(Closest.indexOf(f)).getPathRunTime()
									+ PackmanPaths.get(Closest.indexOf(f)).DistanceSpeedTime(f.getLocation());
							double runningtime2 = p.getPathRunTime() + p.DistanceSpeedTime(f.getLocation());
							if (runningtime2 < runningtime1) {
								Closest.remove(Closest.size() - 1);
								Closest.add(f);
								Closest.get(Closest.indexOf(f)).setWeight(0);
							}
						}
					}
				}
			}

			Iterator<Packman> it_p1 = PackmanPaths.iterator();
			Iterator<Fruit> it_f1 = Closest.iterator();
			Packman p1;
			Fruit f1;
			while (it_p1.hasNext() && it_f1.hasNext()) {
				p1 = it_p1.next();
				f1 = it_f1.next();
				if (f1.getWeight() != 0) {
					p1.addPathLink(f1);
					FruitSet.remove(f1);
				}
			}
			Closest.clear();
		}
	}

	/**
	 * Description: the method goes over all the packman objects, look for the
	 * closest fruit to them and assign in to their path, and then deletes it from
	 * the main fruit list.
	 * 
	 * @param FruitSet
	 */
	public void SimplePathCalculator(ArrayList<Fruit> FruitSet) {
		while (!FruitSet.isEmpty()) {

			Iterator<Packman> it_path = PackmanPaths.iterator();
			Packman p;
			while (it_path.hasNext()) {
				p = it_path.next();
				Iterator<Fruit> it_fruit = FruitSet.iterator();
				Fruit f;
				Fruit closest = new Fruit(-1, null, 0);
				while (it_fruit.hasNext()) {
					f = it_fruit.next();
					if (closest.getLocation() == null
							|| p.DistanceSpeedTime(f.getLocation()) < p.DistanceSpeedTime(closest.getLocation())) {
						closest = f;
					}
				}
				if (closest.getLocation() != null) {
					p.addPathLink(closest);
				}
				FruitSet.remove(closest);
			}
		}

	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Packman Pathes");
		sb.append('\n');
		Iterator<Packman> it = PackmanPaths.iterator();
		while (it.hasNext()) {
			sb.append(it.next().toString());
			sb.append('\n');
		}
		return sb.toString();
	}

	////////////////// getters and setters////////////////////
	public ArrayList<Packman> getPackmanPaths() {
		return PackmanPaths;
	}

	public void setPackmanPaths(ArrayList<Packman> packmanPaths) {
		PackmanPaths = packmanPaths;
	}

}
