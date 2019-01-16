package Algorithms;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import File_format.csv2kml;
import GIS.GIS_layer;
import GIS.GIS_project;
import GIS.GPS_data;
import GIS.GPS_layer;
import GIS.Meta_data;

/**
 * Description: MultiCSV class implements GIS_project interface and is
 * represented by two fields: a Set of <GPS_layer> that unite multiple layers
 * under one project, each layer presented with a different colour (up to 5
 * colours- red, yellow, green, blue, pink). and a GPS_data containing the utc
 * of when the element was first initiated.
 * 
 * 
 * @author Yarden, Caroline
 *
 */
public class MultiCSV implements GIS_project {
	private HashSet<GPS_layer> project;
	private GPS_data data;

	public static void main(String[] args) throws IOException {
		MultiCSV m = new MultiCSV();
		m.createLayer("C:/Users/User/workspace/Ex2-4/csv", "C:/Users/User/workspace/Ex2-4/converted.kml");
	}

	/**
	 * Description: empty constructor, initiate GPS_data with no colour.
	 * 
	 */
	public MultiCSV() {
		project = new HashSet<GPS_layer>();
		data = new GPS_data("");
	}

	/**
	 * Description: the method receives a path for a folder allegedly containing csv
	 * files the method converts each file into a GPS_layer and adding them all into
	 * a set of layers. the method also creates a complete kml file of the project,
	 * where each layer is shown in a different colour.
	 * 
	 * @param FileNamePath
	 * @param kmlFileDest
	 * @throws IOException
	 */
	public void createLayer(String FileNamePath, String kmlFileDest) throws IOException {

		final File folder = new File(FileNamePath);
		List<String> filenames = listFilesForFolder(folder);

		StringBuilder content = new StringBuilder();
		String kmlstart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n" + "<Document>\n";
		kmlstart += "<Style id=" + '"' + "red" + '"'
				+ "><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/red-dot.png</href></Icon></IconStyle></Style>\n"
				+ "<Style id=" + '"' + "yellow" + '"'
				+ "><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/yellow-dot.png</href></Icon></IconStyle></Style>\n"
				+ "<Style id=" + '"' + "green" + '"'
				+ "><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/green-dot.png</href></Icon></IconStyle></Style>\n"
				+ "<Style id=" + '"' + "blue" + '"'
				+ "><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/blue-dot.png</href></Icon></IconStyle></Style>\n"
				+ "<Style id=" + '"' + "pink" + '"'
				+ "><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/pink-dot.png</href></Icon></IconStyle></Style>\n";
		content.append(kmlstart);

		String[] colours = { "#red", "#yellow", "#green", "#blue", "#pink" };
		int i = 0;

		FileWriter fw = new FileWriter(kmlFileDest);
		BufferedWriter bw = new BufferedWriter(fw);

		Iterator<String> it = filenames.iterator();
		while (it.hasNext()) {

			if (i > 4)
				i = 0;
			GPS_layer layer = new GPS_layer();
			layer.get_data().set_colour(colours[i]);
			csv2kml c = new csv2kml();

			content = c.createDocument(layer, content, FileNamePath + "/" + it.next());
			i++;
			project.add(layer);
		}
		content.append("</Document>" + "</kml>\n");
		String csv = content.toString();
		bw.write(csv);
		bw.close();

	}

	/**
	 * Description: the method receives a folder and recursively searches for csv
	 * files inside.
	 * 
	 * @param folder
	 * @return a list of Strings containing csv file names.
	 */
	public List<String> listFilesForFolder(final File folder) {
		List<String> filenames = new LinkedList<String>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				if (fileEntry.getName().contains(".csv"))
					filenames.add(fileEntry.getName());
			}
		}
		return filenames;
	}

	public Meta_data get_Meta_data() {
		return data;
	}

	public boolean add(GIS_layer e) {
		project.add((GPS_layer) e);
		return true;
	}

	public String toString() {
		String s = "";
		Iterator<GPS_layer> it = project.iterator();
		while (it.hasNext()) {
			s += "Layer-> " + it.next().toString() + "\n";
		}
		return s;
	}

	//////////////////////////////////////
	@Override
	public boolean addAll(Collection<? extends GIS_layer> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<GIS_layer> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
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
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

}
