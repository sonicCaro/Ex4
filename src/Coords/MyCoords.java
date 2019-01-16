package Coords;

import Geom.Point3D;

/**
 * Description: MyCoords class implements the coords_converter interface. the
 * class performs various calculations with 3d points.
 * 
 * @author Yarden, Caroline
 *
 */
public class MyCoords implements coords_converter {

	/**
	 * Description: the method computes a new point which is the gps point
	 * transformed by a 3D vector (in meters)
	 * 
	 * @param gps
	 * @param local_vector_in_meter
	 * @return new computed Point3D
	 */
	public Point3D add(Point3D gps, Point3D local_vector_in_meter) {

		double radius = 6371000;
		double lon_norm = Math.cos((gps.x() * Math.PI) / 180);
		double lat = gps.x();
		double lon = gps.y();

		// to radian:
		lat = Math.toRadians(lat);
		lon = Math.toRadians(lon);

		// to meters:
		lat = Math.sin(lat) * radius;
		lon = Math.sin(lon) * radius * lon_norm;

		// in meters:
		lat = lat + local_vector_in_meter.x();
		lon = lon + local_vector_in_meter.y();

		// back to radians:
		lat = Math.asin(lat / radius);
		lon = Math.asin(lon / (radius * lon_norm));

		// back to degrees:
		lat = Math.toDegrees(lat);
		lon = Math.toDegrees(lon);

		return new Point3D(lat, lon, gps.z() + local_vector_in_meter.z());
	}

	/**
	 * Description: computes the 3D distance (in meters) between the two gps like
	 * points source
	 * :https://www.codeguru.com/cpp/cpp/algorithms/article.php/c5115/Geographic-Distance-and-Azimuth-Calculations.htm
	 */
	public double distance3d(Point3D gps0, Point3D gps1) {
		double radius = 6371000;
		double lat1 = Math.toRadians(gps0.x()), lat2 = Math.toRadians(gps1.x()), lon1 = Math.toRadians(gps0.y()),
				lon2 = Math.toRadians(gps1.y());
		double distance = Math.cos(90 - lat2) * Math.cos(90 - lat1)
				+ Math.sin(90 - lat2) * Math.sin(90 - lat1) * Math.cos(lon2 - lon1);
		distance = Math.acos(distance);

		return distance * radius;

	}

	/**
	 * Description: computes the 3D vector (in meters) between two gps like points
	 */
	public Point3D vector3D(Point3D gps0, Point3D gps1) {
		// Common values
		double radius = 6371000;
		double b = radius + gps1.x();
		double c = radius + gps0.x();
		double b2 = b * b;
		double c2 = c * c;
		double bc2 = 2 * b * c;

		// Longitudinal calculations
		double alpha = gps1.y() - gps0.y();
		// Conversion to radian
		alpha = alpha * Math.PI / 180;
		// Small-angle approximation
		double cos = Math.cos(alpha);
		// Use the law of cosines / Al Kashi theorem
		double x = Math.sqrt(b2 + c2 - bc2 * cos);
		// Repeat for latitudinal calculations
		alpha = gps1.x() - gps0.x();
		alpha = alpha * Math.PI / 180;
		cos = Math.cos(alpha);
		double y = Math.sqrt(b2 + c2 - bc2 * cos);
		// Obtain vertical difference, too
		double z = gps1.z() - gps0.z();
		return new Point3D(x, y, z);

	}

	/**
	 * Description: source:
	 * https://www.codeguru.com/cpp/cpp/algorithms/article.php/c5115/Geographic-Distance-and-Azimuth-Calculations.htm
	 * computes the polar representation of the 3D vector be gps0--gps1 this method
	 * return an azimuth (aka yaw), elevation (pitch), and distance
	 */
	public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) {

		double radius = 6371000;
		double[] arr = new double[3];
		double lat1 = Math.toRadians(gps0.x()), lat2 = Math.toRadians(gps1.x()), lon1 = Math.toRadians(gps0.y()),
				lon2 = Math.toRadians(gps1.y());
		double distance = distance3d(gps0, gps1);
		arr[2] = distance;

		// azimuth:
		double az = Math.asin((Math.sin(90 - lat2) * Math.sin(lon2 - lon1)) / Math.sin(distance / radius));
		arr[0] = Math.toDegrees(az);

		// elevation:
		double a = Math.cos(lon2 - lon1);
		double b = Math.cos(lat1);
		double ele = Math.atan((a * b - .1512) / (Math.sqrt(1 - (a * a) * (b * b))));
		arr[1] = Math.toDegrees(ele);

		System.out.println("aed:" + arr[0] + " " + arr[1] + " " + arr[2]);

		return arr;
	}

	/**
	 * Description: return true if this point is a valid lat, lon , lat coordinate:
	 * [-180,+180],[-90,+90],[-450, +inf]
	 * 
	 * @param p
	 * @return bool
	 */
	public boolean isValid_GPS_Point(Point3D p) {

		if ((p.x() >= -180) && (p.x() <= 180)) {
			if ((p.y() >= -90) && (p.y() <= 90)) {
				if ((p.z() >= -450) && (p.z() <= 450)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Description: creates an empty constructor to test in JUnit
	 */
	public MyCoords() {

	}

	public double distance3d(LatLonAlt p1, Point3D p2) {
		// TODO Auto-generated method stub
		return 0;
	}

	public double distance3d(LatLonAlt location, LatLonAlt p2) {
		// TODO Auto-generated method stub
		return 0;
	}

}
