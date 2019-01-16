/**
 * 
 */
package Coords;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Geom.Point3D;

/**
 * @author 318696150
 *
 */
public class MyCoordsTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link Coords.MyCoords#add(Geom.Point3D, Geom.Point3D)}.
	 */
	@Test
	public final void testAdd() {
		MyCoords c = new MyCoords();
		Point3D gps0 = new Point3D(32.1057528, 35.202108333333335, 0);
		Point3D gps1 = new Point3D(32.106352, 35.205225, 650);
		// Point3D vector=new Point3D(c.vector3D(gps0, gps1));
		gps1 = c.vector3D(gps0, gps1);
		System.out.println(gps1);
		double[] a0 = { 32.1057528, 35.202108333333335, 0 };
		double[] a1 = { 32.106352, 35.205225, 650 };
		double[] a = GAMEcoords.flatWorldDist(a0, a1);
		System.out.println(a[0] + " " + a[1] + " " + a[2]);
		assertEquals("addition failed", 32.106352, gps1.x(), 0.5);
		assertEquals("addition failed", 35.205225, gps1.y(), 0.5);
		assertEquals("addition failed", 650, gps1.z(), 0.5);

	}

	/**
	 * Test method for
	 * {@link Coords.MyCoords#distance3d(Geom.Point3D, Geom.Point3D)}.
	 */
	@Test
	public final void testDistance3d() {
		Point3D gps0 = new Point3D(32.103315, 35.209039, 670);
		Point3D gps1 = new Point3D(32.106352, 35.205225, 650);
		MyCoords c = new MyCoords();
		assertEquals("distance failed", 493.0523318, c.distance3d(gps0, gps1), 0.5);
	}

	/**
	 * Test method for {@link Coords.MyCoords#vector3D(Geom.Point3D, Geom.Point3D)}.
	 */
	@Test
	public final void testVector3D() {
		MyCoords c = new MyCoords();
		Point3D gps0 = new Point3D(32.1057528, 35.202108333333335, 0);
		Point3D gps1 = new Point3D(32.10194444, 35.21222222, 0);
		Point3D vector = new Point3D(c.vector3D(gps0, gps1));
		System.out.println(vector);
		// MyCoords c=new MyCoords();
		// Point3D gps0=new Point3D(32.103315, 35.209039, 670);
		// Point3D gps1=new Point3D(32.106352, 35.205225, 650);
		// Point3D vector=new Point3D(c.vector3D(gps0, gps1));

		assertEquals("vector failed", 337.6989921, vector.x(), 0.5);
		assertEquals("vector failed", -359.2492069, vector.y(), 0.5);
		assertEquals("vector failed", -20, vector.z(), 0.5);
	}

	/**
	 * Test method for
	 * {@link Coords.MyCoords#azimuth_elevation_dist(Geom.Point3D, Geom.Point3D)}.
	 */
	@Test
	public final void testAzimuth_elevation_dist() {
		MyCoords c = new MyCoords();
		Point3D gps0 = new Point3D(32.103315, 35.209039, 670);
		Point3D gps1 = new Point3D(32.106352, 35.205225, 650);
		double[] arr = new double[3];
		arr = c.azimuth_elevation_dist(gps0, gps1);
	}

	/**
	 * Test method for {@link Coords.MyCoords#isValid_GPS_Point(Geom.Point3D)}.
	 */
	@Test
	public final void testIsValid_GPS_Point() {

	}

}
