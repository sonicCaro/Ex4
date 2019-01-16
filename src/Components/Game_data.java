package Components;

import GIS.Meta_data;
import Geom.Point3D;

public class Game_data implements Meta_data{

	private long UTC;
	private Point3D Location;
	private int ID;
	private char Type;
	
	/**
	 *
	 * constructor
	 * @param utc
	 * @param location
	 */
	public Game_data(int id,Point3D location, char c){
		UTC=System.currentTimeMillis();
		Location=location;
		setID(id);
		setType(c);
	}
	
	/**
	 * 
	 * @returns universal time clock, when the object was first made.
	 */
	public long getUTC() {
		return UTC;
	}
	
	/**
	 * Description:
	 * returns the gps coordinates of the object
	 */
	public Point3D get_Orientation() {
		return Location;
	}
	
	
	
	//////////////getters and setters///////////////////
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public char getType() {
		return Type;
	}

	public void setType(char type) {
		Type = type;
	}
	
	public void setLocation(Point3D p){
		Location=p;
	}
	
}
