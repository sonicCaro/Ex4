package GIS;

import Geom.Geom_element;
import Geom.Point3D;

/**
 * Description: GPS_Point class implements the GIS_element interface. the class
 * represents a line from a csv file and contains the fields presented in the
 * csv excel in addition to one more field GPS_data.
 * 
 * @author Yarden, Caroline
 *
 */
public class GPS_Point implements GIS_element {

	private String MAC;
	private String SSID;
	private String AuthMode;
	private String FirstSeen;
	private double Chanel;
	private double RSSI;
	private double CurrentLatitude;
	private double CurrentLongitude;
	private double AltitudeMeters;
	private double AccuracyMeters;
	private String Type;

	private GPS_data data;

	/**
	 * Description: empty constructor
	 */
	public GPS_Point() {
		setMAC(null);
		setSSID(null);
		setAuthMode(null);
		FirstSeen = null;
		Chanel = 0;
		RSSI = 0;
		CurrentLatitude = 0;
		CurrentLongitude = 0;
		AltitudeMeters = 0;
		AccuracyMeters = 0;
		Type = null;
		data = new GPS_data();
	}

	/**
	 * Description: method inherited from Geom-element interface the method create a
	 * 3D point from the relevant fields
	 * 
	 * @return Point3D
	 */
	public Geom_element getGeom() {
		Point3D g = new Point3D(CurrentLatitude, CurrentLongitude, AltitudeMeters);
		return g;
	}

	@Override
	public void translate(Point3D vec) {

	}

	public Meta_data getData() {
		return data;
	}

	////////////////////////////////////////////////////////////////////////////
	// ---------------------getters and setters--------------------------------//

	public String getFirstSeen() {
		return FirstSeen;
	}

	public void setFirstSeen(String firstSeen) {
		FirstSeen = firstSeen;

	}

	public double getChanel() {
		return Chanel;
	}

	public void setChanel(double chanel) {
		Chanel = chanel;

	}

	public double getRSSI() {
		return RSSI;
	}

	public void setRSSI(double rSSI) {
		RSSI = rSSI;

	}

	public double getCurrentLatitude() {
		return CurrentLatitude;
	}

	public void setCurrentLatitude(double currentLatitude) {
		CurrentLatitude = currentLatitude;

	}

	public double getCurrentLongitude() {
		return CurrentLongitude;
	}

	public void setCurrentLongitude(double currentLongitude) {
		CurrentLongitude = currentLongitude;

	}

	public double getAltitudeMeters() {
		return AltitudeMeters;
	}

	public void setAltitudeMeters(double altitudeMeters) {
		AltitudeMeters = altitudeMeters;

	}

	public double getAccuracyMeters() {
		return AccuracyMeters;
	}

	public void setAccuracyMeters(double accuracyMeters) {
		AccuracyMeters = accuracyMeters;

	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;

	}

	public String getMAC() {
		return MAC;
	}

	public void setMAC(String mAC) {
		MAC = mAC;

	}

	public String getSSID() {
		return SSID;
	}

	public void setSSID(String sSID) {
		SSID = sSID;

	}

	public String getAuthMode() {
		return AuthMode;
	}

	public void setAuthMode(String authMode) {
		AuthMode = authMode;

	}

	public GPS_data get_Meta_data() {
		return data;
	}

	public void set_Meta_data(String colour) {
		this.data.set_colour(colour);
	}

	public String toString() {
		return "MAC: " + MAC + " GEOM: " + this.getGeom() + " Colour: " + data.get_colour();
	}

}
