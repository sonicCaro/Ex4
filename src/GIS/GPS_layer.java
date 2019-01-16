package GIS;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import File_format.csv2kml;

/**
 * Description: GPS_layer class implements GIS_layer interface, holds two
 * fields: a Set of <GPS_Point> creating a layer and GPS_data containing a utc
 * for when the layer was first initiated and one colour for all the points in
 * the layer.
 * 
 * @author Yarden, Caroline
 *
 */
public class GPS_layer implements GIS_layer {
	private HashSet<GPS_Point> layer;
	private GPS_data data;

	/**
	 * Description: empty constructor
	 */
	public GPS_layer() {
		layer = new HashSet<GPS_Point>();
		data = new GPS_data();
	}

	/**
	 * Description: a converting constructor receives csv filename and kml filename.
	 * the constructor builds a layer while converting the csv file into kml file
	 * 
	 * @param csvFileName
	 * @param kmlFileName
	 * @throws IOException
	 */
	public GPS_layer(String csvFileName, String kmlFileName) throws IOException {
		csv2kml c = new csv2kml();
		c.convertFile(this, csvFileName, kmlFileName);
	}

	public Meta_data get_Meta_data() {
		return this.data;
	}

	public HashSet<GPS_Point> get_layer() {
		return layer;
	}

	public GPS_data get_data() {
		return this.data;
	}

	public String toString() {
		String s = "";
		Iterator<GPS_Point> it = layer.iterator();
		while (it.hasNext()) {
			s += "Point-> " + it.next().toString() + "\n";
		}
		return s;
	}

	//////////////////////////////////////////////////////////

	@Override
	public boolean addAll(Collection<? extends GIS_element> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<GIS_element> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(GIS_element e) {
		layer.add((GPS_Point) e);
		return true;
	}

}
