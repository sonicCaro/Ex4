package Coords;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import Geom.Point3D;

/**
 * Description: the class represents a picture of a map and its fields are an
 * image, gps points of top left and bottom right corners of the image and the
 * bounds of the image. the class also turns gps coordinates into x,y pixels,
 * and back, and calculates the 2d distance between two points.
 * 
 * @author Yarden and Caroline
 *
 */
public class Map {

	private Image img = null;
	private LatLonAlt TopLeft = new LatLonAlt(32.103813, 35.207357, 660.0);
	private LatLonAlt BottomRight = null;
	private double disX = 955.5;
	private double disY = 421.0;

	/**
	 * Description: the constructor receives a file name for the image and the top
	 * left and bottom right gps coordinates for the image's corners. the
	 * constructor treats the top left corner as the (0,0) and by using the method
	 * "vector3D" from MyCoords class we are given the x,y values in meters and thus
	 * the bounds of the image.
	 * 
	 * @param pathName
	 * @param top_left
	 * @param bottom_right
	 */
	public Map(String pathName, LatLonAlt top_left, LatLonAlt bottom_right, double distx, double disty) {

		TopLeft = top_left;
		BottomRight = bottom_right;
		disX = distx;
		disY = disty;
		try {
			img = ImageIO.read(new File(pathName));
		} catch (IOException e) {

		}
	}

	/**
	 * Description: the constructor uses its default coordinates for topleft and
	 * bottomright.
	 * 
	 * @param pathName
	 */
	public Map() {
		BottomRight = new LatLonAlt(TopLeft.tanstale(new Point3D(disX, disY, 0)));
		try {
			img = ImageIO.read(new File("img/Ariel1.png"));
		} catch (IOException e) {

		}
	}

	/**
	 * Description: the method receives a gps point and returns it as x,y values of
	 * the given image. altitude will remain 0.
	 * 
	 * @param coord
	 * 
	 */
	public Point3D CoordsToPixels(LatLonAlt coord) {
		double w = img.getWidth(null);
		double h = img.getHeight(null);
		Point3D vec = TopLeft.vector3D(coord);
		double dw = vec.x() * 2.0 / disX;
		double dh = vec.y() * 2.0 / disY;
		double x = w / 2 + (w / 2.0 * dw);
		double y = h / 2 - (h / 2.0 * dh);
		return new Point3D(x, y, coord.z());
	}

	/**
	 * Description: the method receives a pixel(x,y) and turns it into gps. altitude
	 * will remain 0.
	 * 
	 * @param pxl
	 */
	public LatLonAlt PixelsToCoords(Point3D pxl) {
		double w = img.getWidth(null);
		double h = img.getHeight(null);

		double dw = pxl.x() - w / 2.0;
		double dh = h / 2.0 - pxl.y();
		double xn = dw * 2.0 / w;
		double yn = dh * 2.0 / h;
		double x = xn * disX / 2.0;
		double y = yn * disY / 2.0;
		Point3D v = new Point3D(x, y, pxl.z());
		return TopLeft.tanstale(v);
	}

	/**
	 * Description: the method calculates the distance between two given pixels.
	 * 
	 * @param pxl1
	 * @param pxl2
	 */
	public double PixelDistance(Point3D pxl1, Point3D pxl2) {
		double result = Math.sqrt(Math.pow(pxl2.x() - pxl1.x(), 2) + Math.pow(pxl2.y() - pxl1.y(), 2));
		return result;
	}

	/**
	 * Description: the method calculate the coordinate between source(gps0) and
	 * destination(gps1) where the source advances a given distance in meters. the
	 * method uses Haversine formula to find the distance between source to
	 * destination, and uses it to find the ratio to alter the source.
	 * 
	 * see also:
	 * https://stackoverflow.com/questions/3073281/how-to-move-a-marker-100-meters-with-coordinates
	 * 
	 * @param gps0
	 * @param gps1
	 * @param distance
	 * @return new coordinate
	 */
	public LatLonAlt AdvanceDistance(LatLonAlt gps0, LatLonAlt gps1, double distance) {

		double radius = 6371000; // radius of earth in meters
		double latDist = gps0.x() - gps1.x();
		double lngDist = gps0.y() - gps1.y();

		latDist = Math.toRadians(latDist);
		lngDist = Math.toRadians(lngDist);
		latDist = Math.sin(latDist);
		lngDist = Math.sin(lngDist);

		double cosLat1 = Math.cos(Math.toRadians(gps0.x()));
		double cosLat2 = Math.cos(Math.toRadians(gps1.x()));
		double a = (latDist / 2) * (latDist / 2) + cosLat1 * cosLat2 * (lngDist / 2) * (lngDist / 2);

		if (a < 0) {
			a = -1 * a;
		}

		double ratio = distance / (radius * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));
		return new LatLonAlt(gps0.x() + ((gps1.x() - gps0.x()) * ratio), gps0.y() + ((gps1.y() - gps0.y()) * ratio), 0);
	}

	//////////////// getters and setters///////////////////

	public Image getImage() {
		return img;
	}

	public LatLonAlt PixelsToCoords(LatLonAlt location) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object gps2Pixel(GAMEcoords pacmansGPS) {
		// TODO Auto-generated method stub
		return null;
	}
}
