package GIS;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import Geom.Point3D;

/**
 * Description: GPS_data class implements the Meta_data interface. the class has
 * 2 fields: millis which is the current time in milliseconds and colour, which
 * can describe the colour of both a GPS_Point and a GPS_layer.
 * 
 * @author Yarden, Caroline
 *
 */
public class GPS_data implements Meta_data {
	private long millis;
	private String colour;

	/**
	 * Description: empty constructor, where colour is set to default #red.
	 */
	public GPS_data() {
		this.setData();
		this.colour = "#red";
	}

	/**
	 * Description: constructor that receives and set a colour.
	 * 
	 * @param colour
	 */
	public GPS_data(String colour) {
		this.setData();
		this.colour = colour;
	}

	void setData() {
		millis = System.currentTimeMillis();

	}

	/**
	 * Description: the method returns the utc in milliseconds.
	 * 
	 * @return millis (long)
	 */
	public long getUTC() {
		return millis;
	}

	public Point3D get_Orientation() {
		// TODO Auto-generated method stub
		return null;
	}

	public String toTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		String utc = sdf.format(new Date(this.millis));
		return utc;
	}

	public String toString() {

		return "Date: " + this.toTime() + ", Pin-Colour: " + this.colour;
	}

	///////////////////////////////////////////////////////////

	public void set_colour(String colour) {
		this.colour = colour;
	}

	public String get_colour() {
		return this.colour;
	}
}
