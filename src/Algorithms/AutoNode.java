package Algorithms;

import Coords.LatLonAlt;


/**
 *  This class represents vertices in the game 
 * The Automatic path maker is making its path to the fruits.
 *  The classes fields are :
 *  1)index
 *  2)location  
 *  3)variable
 * 
 * @author 318696150
 *
 */


public class AutoNode {
	private double index;
	private LatLonAlt Location;
	private int closeFruit = -1;

	
	/**
	 *This is a constructor with index and location
	 * @param index1
	 * @param locate
	 */
	public AutoNode(double index1, LatLonAlt locate) {
		index = index1;
		Location = new LatLonAlt(locate);
	}
	
	

	/**
	 *This is a copy constructor 
	 * 
	 * @param n
	 */
	public AutoNode(AutoNode n) {
		index = n.getIndex();
		closeFruit = n.getCloseFruit();
		Location = new LatLonAlt(n.getLocation());
	}

	
	public AutoNode(int id) {
	}
	
	/**getters and setters for our variable**/
	public void setCloseFruit(int c) {
		closeFruit = c;
	}
	
	/**getters and setters for Index**/
	public double getIndex() {
		return index;
	}
	
	public void setIndex(double index1) {
		index = index1;
	}

	/**getters and setters for location**/
	public LatLonAlt getLocation() {
		return Location;
	}

	public void setLocation(LatLonAlt lla) {
		Location = new LatLonAlt(lla);
	}
	

	public int getCloseFruit() {
		return closeFruit;
	}

	/**a string function**/
	public String toString() {
		return "Node:"  + index + ",Location:" + Location + ", Closest Fruit :"+ closeFruit;
	}
	
	}

