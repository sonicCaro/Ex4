package Components;

import java.util.ArrayList;

import Coords.LatLonAlt;

/**
 * This class represents an obstacle on the game field. the object
 * field's are: 
 * 1)the location 
 * 2)ID
 * 3)weight.
 * 
 * 
 * @author 318696150
 *
 */
public class Blockers {

	private LatLonAlt TopRight = null;
	private LatLonAlt BottomLeft = null;
	private int id;
	private double weight;

	/**
	 * This is a Constructor with id and two LatLonAlt. weight is set to 1 by
	 * default.
	 * 
	 * @param id
	 * @param lla1
	 * @param lla2
	 */
	public Blockers(int id, LatLonAlt lla1, LatLonAlt lla2) {
		TopRight = new LatLonAlt(Math.max(lla1.x(), lla2.x()), Math.max(lla1.y(), lla2.y()),
				Math.max(lla1.z(), lla2.z()));
		BottomLeft = new LatLonAlt(Math.min(lla1.x(), lla2.x()), Math.min(lla1.y(), lla2.y()),
				Math.min(lla1.z(), lla2.z()));
		this.id = id;
		weight = 1.0;
	}

	/**
	 * This is a Constructor from a string
	 * 
	 * @param str
	 */
	public Blockers(String str) {
		String[] fields = str.split(",");
		id = Integer.parseInt(fields[1]);
		LatLonAlt lla1 = new LatLonAlt(fields[2] + "," + fields[3] + "," + fields[4]);
		LatLonAlt lla2 = new LatLonAlt(fields[5] + "," + fields[6] + "," + fields[7]);
		TopRight = new LatLonAlt(Math.max(lla1.x(), lla2.x()), Math.max(lla1.y(), lla2.y()),
				Math.max(lla1.z(), lla2.z()));
		BottomLeft = new LatLonAlt(Math.min(lla1.x(), lla2.x()), Math.min(lla1.y(), lla2.y()),
				Math.min(lla1.z(), lla2.z()));
		weight = Double.parseDouble(fields[8]);
	}

	/**
	 * This is a Constructor from another obstacle object
	 * 
	 * @param blc
	 */
	public Blockers(Blockers blc) {
		TopRight = new LatLonAlt(blc.TopRight);
		BottomLeft = new LatLonAlt(blc.BottomLeft);
		id = blc.id;
		weight = blc.weight;
	}

	
	
	/**
	 * @returns an arraylist where the first element is the bottom left
	 * LatLonAlt andthe second is top right LatLonAlt.
	 * 
	 * @return
	 */
	public ArrayList<LatLonAlt> getBounds() {
		ArrayList<LatLonAlt> bounds = new ArrayList<LatLonAlt>();
		bounds.add(BottomLeft);
		bounds.add(TopRight);
		return bounds;
	}

	public double getWeight() {
		return weight;
	}

	public int getID() {
		return id;
	}

	public String toString() {
		return ("B: " + id + " Location:" + BottomLeft + ", " + TopRight + ", weight:" + weight);
	}
	
	
	
	/**
	 * This method determines whether a given Location is inside an
	 * obstacle(and therefore a violation of the rules)or not.
	 * 
	 * @param lla
	 * @return
	 */
	public boolean inBounds(LatLonAlt lla) {
		if (lla.lat() >= BottomLeft.lat() && lla.lon() >= BottomLeft.lon() && lla.lat() <= TopRight.lat()
				&& lla.lon() <= TopRight.lon()) {
			return true;
		}
		return false;
	}

	
}
