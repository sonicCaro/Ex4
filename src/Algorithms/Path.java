package Algorithms;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import Components.Fruit;
import Components.Packman;
import Coords.LatLonAlt;
import Coords.MyCoords;
import Geom.Point3D;

/**
 * Description: the class represents the path of a pacman. the class's fields
 * are a pacman object, a time variable which updates itself after every fruit
 * the pacman eats according to its speed and the fruit's distance. the class
 * also contains arraylist of fruits in the order they were meant to be eaten.
 * 
 * @author Yarden and Caroline
 *
 */
public class Path {

	private ArrayList<Fruit> PathArray = null;
	private long initTime;

	/**
	 * Description: constructor which receives a a parameter the initial packman
	 * object, creates a list which the packman location as the first object and
	 * sets the time to 0 as no fruits were eaten yet.
	 * 
	 * @param StartingPoint
	 */
	public Path() {

		PathArray = new ArrayList<Fruit>();
		initTime = new Date().getTime();
	}

	public Path(Fruit f) {
		PathArray = new ArrayList<Fruit>();
		PathArray.add(f);
		initTime = new Date().getTime();
	}

	public Path(Path p) {
		initTime = new Date().getTime();
		PathArray = new ArrayList<Fruit>();
		for (Fruit f : p.getPath()) {
			PathArray.add(f);
		}
	}

	/**
	 * Description: the method calculates the time it would take the packman to move
	 * from given coordinate to the location of the last fruit on the list (also the
	 * last stop of the packman before advancing).
	 * 
	 * @param p2
	 * @return the time in seconds altered by the packman's speed and the fruit's
	 *         distance
	 */
	public double DistanceSpeedTime(Packman head, Point3D p2) {
		if (p2 == null) {
			return -1;
		}
		LatLonAlt p1 = PathArray.get(PathArray.size() - 1).getLocation(); // p1 kan point3d
		MyCoords c = new MyCoords();
		double distance = c.distance3d(p1, p2);
		return (distance / head.getSpeed()) * 60 * 60;

	}

	/**
	 * Description: the method returns the length of the list.
	 * 
	 * @return the number of fruits that were eaten
	 */
	public int FruitsEaten() {
		return PathArray.size() - 1;
	}

	/**
	 * Description: the method is given a fruit object. appends it to the end of the
	 * list and set the time according to the speed, time and fruit's location
	 * 
	 * @param f
	 * @return
	 */
	public boolean addFruit(Fruit f) {
		return PathArray.add(f);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Path:");
		Iterator<Fruit> it = PathArray.iterator();
		while (it.hasNext()) {
			sb.append("F: " + it.next().toString());
		}
		return sb.toString();
	}

	//////////////////// getters and setters//////////////////////

	public ArrayList<Fruit> getPath() {
		return PathArray;
	}

	public long getInitTime() {
		return initTime;
	}

	public Fruit remove(int index) {
		return PathArray.remove(index);
	}

	public boolean remove(Fruit f) {
		return PathArray.remove(f);
	}

}
