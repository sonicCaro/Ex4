package Algorithms;

import java.util.ArrayList;

import Components.Blockers;
import Components.Fruit;
import Components.Packman;
import Coords.GAMEcoords;
import Coords.LatLonAlt;

/**
 * this class represents all the obstacles vertices and fruits as
 * Node elements, 
 * the class can generate a path from the
 * player's location to the games fruits without points lost by Obstacles in the
 * process.
 *the classes fields are an obstacles list, fruits list, vertices list
 * containing all the fruits and obstacles and the currant location of the
 * player.
 * 
 * @author 318696150
 *
 */

public class AutoPlayer {
	private ArrayList<Blockers> blockers;
	private ArrayList<AutoNode> vertex = null;
	private ArrayList<Fruit> fruits = null;
	private Packman pac;

	/**
	 * The constructor calls for two methods:
	 * 1)Turn every relevant point on the field into Node objects .
	 * 2)gives each node the information that it needs to build a path.
	 *
	 * @param paci
	 * @param fruit1
	 * @param block1
	 */
	public AutoPlayer(Packman paci, ArrayList<Fruit> fruit1, ArrayList<Blockers> block1) {
		blockers = new ArrayList<Blockers>();
		blockers.addAll(block1);
		fruits = new ArrayList<Fruit>();
		fruits.addAll(fruit1);
		vertex = new ArrayList<AutoNode>();
		pac = paci;
		LoadVertex();
		GenerateVertex();
	}

	/**
	 *this method :
	 *1)finds all 4 vertices of the obstacles 
	 *2)creates a node, for each of them, that is slightly altered to a safe
	 * direction, so that the player can use them without point loss. 
	 * 3)turns all the fruits into Node objects and uses their exact location.
	 */
	
	private void LoadVertex() {
		LatLonAlt lla1, lla2;
		AutoNode n1, n2, n3, n4;

		for (Blockers blc : blockers) {
			lla1 = new LatLonAlt(blc.getBounds().get(0));
			lla2 = new LatLonAlt(blc.getBounds().get(1));
			double[] ll1 = { lla1.lat(), lla1.lon(), lla1.alt() };
			double[] ll2 = { lla2.lat(), lla2.lon(), lla2.alt() };
			double[] a1 = GAMEcoords.offsetLatLonAzmDist(ll1, 225, 5);
			double a2 = GAMEcoords.offsetLatLonAzmDis + 0.2 ;
			//LatLonAlt a2 = new LatLonAlt( a2[0] , a2[1], lla2.z());// top right

			double[] ll3 = { lla2.lat(), lla1.lon(), lla1.alt() };
			double[] ll4 = { lla1.lat(), lla2.lon(), lla2.alt() };
			double[] a3 = GAMEcoords.offsetLatLonAzmDist(ll3, 315, 5);
			double[] a4 = GAMEcoords.offst(ll2, 45, 5);

			n1 = new AutoNode(blc.getID() + 0.1, new LatLonAlt(a1[0], a1[1], lla1.z()));// bottom left
			//n2 = new Node(ob.getID())setLatLonAzmDist(ll4, 135, 5);

			n3 = new AutoNode(blc.getID() + 0.3, new LatLonAlt(a3[0], a3[1], lla1.z()));// top left
			n4 = new AutoNode(blc.getID() + 0.4, new LatLonAlt(a4[0], a4[1], lla2.z()));// bottom right
			vertex.add(n1);
			//vertex.add(n2);
			vertex.add(n3);
			vertex.add(n4);
		}
		for (Fruit f : fruits) {
			lla1 = new LatLonAlt(f.getLocation());
			n1 = new AutoNode(f.getID(), lla1);
			n1.setCloseFruit(0);
			vertex.add(n1);
		}
	}

	private void setLatLonAzmDist(double[] ll4, int i, int j) {
	}

	/**
	 * this method:
	 * 1)assign each Node a special number that indicate how
	 * many vertexes away is it from a fruit. 
	 * 2)fruits are obviously assigned 0. 
	 * 3)any Node that has a direct line to a fruit(with no obstacles in the middle) is
	 * assigned 1 etc.
	 */
	private void GenerateVertex() {
		for (Fruit f : fruits) {
			for (AutoNode n1 : vertex) {
				if (obstacleFree(f.getLocation(), n1.getLocation()) && n1.getCloseFruit() == -1) {
					n1.setCloseFruit(1);
				}
			}
		}
		for (int i = 0; i < 10; i++) {// runs only up to 10 because thats the maximus needed to map the important
										// nodes. anything not mapped by then is irrelevant.
			for (AutoNode n2 : vertex) {
				if (n2.getCloseFruit() == -1) {
					for (AutoNode n3 : vertex) {
						if (obstacleFree(n2.getLocation(), n3.getLocation()) && n2 != n3 && n3.getCloseFruit() != -1) {
							if (n2.getCloseFruit() == -1 || (n3.getCloseFruit() + 1) < n2.getCloseFruit()) {
								n2.setCloseFruit(n3.getCloseFruit() + 1);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * this method:
	 * 1)is called each time for a fruit to be eaten.
	 * 2) searches for nodes that are linked directly to the player 
	 * 3)sends the player closest to a method that create a path.
	 * 
	 * @return path
	 */
	public ArrayList<AutoNode> createPath() {
		ArrayList<AutoNode> direct = new ArrayList<AutoNode>();
		int counter = 0;

		while (direct.isEmpty()) {
			for (AutoNode n : vertex) {
				if (obstacleFree(pac.getLocation(), n.getLocation()) && n.getCloseFruit() == counter) {
					direct.add(n);
				}
			}
			counter++;
		}

		AutoNode closest = direct.get(0);
		for (AutoNode n2 : direct) {
			if (closest.getLocation().GPS_distance(pac.getLocation()) > n2.getLocation()
					.GPS_distance(pac.getLocation())) {
				closest = new AutoNode(n2);
			}
		}
		ArrayList<AutoNode> path = new ArrayList<AutoNode>();
		path = GeneratePath(closest);
		return path;
	}

	/**
	 * This method:
	 * 1) receives the closest node to the player that is also the closest to a fruit. 
	 * 2)while path is yet to contain a fruit, it would find a
	 * node with direct line to closest and that is closer to a fruit and add it to
	 * a path 
	 * 3)repeats the process until a fruit is inside the path.
	 * 
	 * @param closest
	 * @return path
	 */
	private ArrayList<AutoNode> GeneratePath(AutoNode closest) {
		ArrayList<AutoNode> path = new ArrayList<AutoNode>();
		path.add(closest);
		while (path.get(path.size() - 1).getCloseFruit() != 0) {
			for (AutoNode n1 : vertex) {
				if (obstacleFree(closest.getLocation(), n1.getLocation())
						&& (n1.getCloseFruit() == closest.getCloseFruit() - 1)) {
					path.add(n1);
					closest = new AutoNode(n1);
				}
			}
		}
		return path;
	}

	/**
	 * This is a test :
	 *  given LatLonAlt whether its inside a block bounds.
	 * 
	 * @param param
	 * @return
	 */
	private boolean Valid(LatLonAlt param) {
		for (Blockers blc : blockers) {
			if (blc.inBounds(param)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 	This method :
	 *  given two LatLonAlt elements and test to see
	 * whether a player can move from one to the other without any points lost in
	 * the process.
	 * 
	 * @param lla1
	 * @param lla2
	 * @return true if the way is obstacle free and false otherwise
	 */
	private boolean obstacleFree(LatLonAlt lla1, LatLonAlt lla2) {
		Packman p = new Packman(-1, lla1, 20, 1);
		p.setOrientation(lla2);

		double distance = p.getRadius() + p.getSpeed() / 10;
		while (Valid(p.getLocation())) {
			if (p.getLocation().GPS_distance(lla2) < distance) {
				return true;
			}
			p.moving(100);
		}
		return false;
	}
}
