package Components;

import java.awt.Image;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import javax.imageio.ImageIO;
import Algorithms.Path;
import Coords.GAMEcoords;
import Coords.LatLonAlt;
import Coords.MyCoords;

/**
 * This class represents a moving robot.
 * @author 318696150
 *
 */
public class Packman {
	private char Type = 'P';
	private int ID;
	private LatLonAlt Location;
	private double Speed;
	private double Radius;
	private long InitTime;
	private double Orientation;
	private Image img;
	private Path path = null;
	
	
////////////////////getters and setters////////////
public Image getImage() {
return img;
}

public void setImage(String filename) {
try {
img = ImageIO.read(new File(filename));
} catch (IOException e) {
}
}

public char getType() {
return Type;
}

public void setType(char ch) {
Type = ch;
}

public int getID() {
return ID;
}

public void setID(int id) {
ID = id;
}

public LatLonAlt getLocation() {
return Location;
}

public void setLocation(LatLonAlt lla) {
Location = new LatLonAlt(lla);
}

public double getSpeed() {
return Speed;
}

public void setSpeed(double speed) {
Speed = speed;
}

public double getRadius() {
return Radius;
}

public void setRadius(double radius) {
Radius = radius;
}

public double getOrientation() {
return Orientation;
}

public void setOrientation(double degree) {
Orientation = degree;
}

public boolean addPathLink(Fruit f) {
return path.addFruit(f);
}

public Fruit removePathLink(int index) {
return path.remove(index);
}

public boolean removePathLink(Fruit f) {
return path.remove(f);
}

public Path getPath() {
return new Path(path);
}

/**
 * sets the orientation according to a given degree.
 * 
 * @param lla
 */
public void setOrientation(LatLonAlt lla) {
	Orientation = Location.azimuth_elevation_dist(lla)[0];
}

public String toString() {
	return (Type + ": " + ID + ", Location: " + Location + ", Speed: " + Speed + ", Radius: " + Radius);
}

/**
 * @param p2
 * @return time
 */
public double DistanceSpeedTime(LatLonAlt p2) {
	if (p2 == null) {//calculates the time it would take the packman to move
		//from given coordinate to the location of the last fruit on the list 
		return -1;
	}
	MyCoords c = new MyCoords();
	double distance;
	if (path.getPath().isEmpty()) {
		distance = c.distance3d(Location, p2);
		return (distance / Speed) * 60 * 60;
	}
	distance = c.distance3d(getPath().getPath().get(getPath().getPath().size()).getLocation(), p2);
	return (distance / Speed) * 60 * 60;
}

public double DistanceSpeedTime(LatLonAlt p1, LatLonAlt p2) {
	if (p2 == null || p1 == null) {
		return -1;
	}
	MyCoords c = new MyCoords();
	double distance = c.distance3d(p1, p2);
	return (distance / Speed) * 60 * 60; //the time (s) altered by the packman's speed 
	//and the fruit's distance
}


/**
 * initiate a path according to a given one.
 * 
 * @param p
 */
public void initPath(Path p) {
	path = new Path(p);
}

/**
 * @return time
 * 
 */
public double getPathRunTime() {
	double time = 0;
	Iterator<Fruit> it = path.getPath().iterator();
	LatLonAlt ll1 = new LatLonAlt(Location);
	LatLonAlt ll2;
	while (it.hasNext()) {
		ll2 = new LatLonAlt(it.next().getLocation());
		time += DistanceSpeedTime(ll1, ll2);
		ll1 = new LatLonAlt(ll2);
	}
	return time; //the time it would take the packman to complete the path.
}


	/**
	 * This is a constructor with LatLonAlt
	 * 
	 * @param id
	 * @param location
	 * @param speed
	 * @param radius
	 */
	public Packman(int id, LatLonAlt location, double speed, double radius) {
		ID = id;
		Location = new LatLonAlt(location);
		Speed = speed;
		Radius = radius;
		InitTime = new Date().getTime();

	}

	/**
	 * This is a Constructor from a packman object
	 * 
	 * @param p
	 */
	public Packman(Packman p) {
		Type = p.getType();
		ID = p.getID();
		Location = new LatLonAlt(p.getLocation());
		Speed = p.getSpeed();
		Radius = p.getRadius();
		InitTime = new Date().getTime();
		img = p.getImage();
	}

	/**
	 *This is a Constructor from a string
	 * 
	 * @param str
	 */
	public Packman(String str) {
		String[] fields = str.split(",");
		Type = fields[0].charAt(0);
		ID = Integer.parseInt(fields[1]);
		Location = new LatLonAlt(fields[2] + "," + fields[3] + "," + fields[4]);
		Speed = Double.parseDouble(fields[5]);
		Radius = Double.parseDouble(fields[6]);
		InitTime = new Date().getTime();
	}
	
	/**
	 * initiate an empty path.
	 */
	public void initPath() {
		path = new Path();
	}

	public Packman(GAMEcoords pacmansGPS, int x, int y, double d, double e) { //
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param distance
	 */
	public void moving(double distance) {

		double d = distance * Speed / 1000.0; // calculate the lat, lon values from the current distance
		double[] ll1 = { Location.lat(), Location.lon(), Location.alt() };  
		double[] ans = GAMEcoords.offsetLatLonAzmDist(ll1, getOrientation(), d); // calculate the lat, lon values 
		//from the curraet location in the orientation direction
		Location = new LatLonAlt(ans[0], ans[1], Location.alt());
		
		//updates the location of the packmam
	}


	


	
}
