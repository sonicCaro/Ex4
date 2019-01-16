/**
 * 
 */
package Algorithms;

import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import GIS.Game;
import Components.Game_data;
import Algorithms.ShortestPathAlgo;


/**
 * @author 318696150
 *
 */
public class ShortestPathTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link Algorithms.ShortestPathAlgo#ShortestPathAlgo(GIS.Game)}.
	 */
	@Test
	public final void testShortestPathAlgo() {
		try {
			Components.Game g=new Components.Game("csv/game_1543693822377.csv");
			System.out.println(g.toString());
			ShortestPathAlgo p=new ShortestPathAlgo(g);
			System.out.println(p.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link Algorithms.ShortestPathAlgo#getPackmanPaths()}.
	 */
	@Test
	public final void testGetPackmanPaths() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link Algorithms.ShortestPathAlgo#setPackmanPaths(java.util.ArrayList)}.
	 */
	@Test
	public final void testSetPackmanPaths() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link Algorithms.ShortestPathAlgo#getFruitSet()}.
	 */
	@Test
	
	public final void testGetFruitSet() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link Algorithms.ShortestPathAlgo#setFruitSet(java.util.ArrayList)}.
	 */
	@Test
	public final void testSetFruitSet() {
		fail("Not yet implemented"); // TODO
	}

}
