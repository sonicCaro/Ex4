package File_format;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import GIS.GPS_Point;
import GIS.GPS_layer;

/**
 * Description: csv2kml class is mainly a converting class. the class has an
 * empty constructor, and two methods converting csv files into kml while
 * constructing points and layers.
 * 
 * @author Yarden, Caroline
 *
 */
public class csv2kml extends GPS_layer {

	/**
	 * Description: empty constructor
	 */
	public csv2kml() {

	}

	/**
	 * Description: the method receives GPS_layer, csv file path and kml file path.
	 * the method follows the csv file path, reads from it and converts it into a
	 * kml file, located at the kml file path. the method also update the given
	 * layer according to the csv file.
	 * 
	 * @param layer
	 * @param csvFileName
	 * @param kmlFileName
	 * @throws IOException
	 * 
	 */
	public void convertFile(GPS_layer layer, String csvFileName, String kmlFileName) throws IOException {

		StringBuilder content = new StringBuilder();
		String kmlstart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n" + "<Document>\n";
		content.append(kmlstart);
		FileWriter fw = new FileWriter(kmlFileName);
		BufferedWriter bw = new BufferedWriter(fw);

		content = createDocument(layer, content, csvFileName);

		content.append("</Document>" + "</kml>\n");
		String csv = content.toString();
		bw.write(csv);
		bw.close();
		 
	}

	/**
	 * Description: the method receives a GPS_layer, StringBuilder and a csv file.
	 * the method reads from the given csv file and appends the kml-relevant
	 * information into the StringBuilder while creating GPS_Points accordingly and
	 * adding them to the given layer.
	 * 
	 * @param layer
	 * @param content
	 * @param csvFileName
	 * @return the StringBuilder appended information
	 * @throws IOException
	 */
	public StringBuilder createDocument(GPS_layer layer, StringBuilder content, String csvFileName) throws IOException {

		// Read csv file
		BufferedReader csvReader;
		csvReader = new BufferedReader(new FileReader(csvFileName));
		String[] s = null;

		// the second line in the CSV file is column/field names

		String curLine = csvReader.readLine();
		curLine = csvReader.readLine();

		try {

			while ((curLine = csvReader.readLine()) != null) {
				GPS_Point g = new GPS_Point();
				g.set_Meta_data(layer.get_data().get_colour());
				s = curLine.split(",");

				String kmlelement = "<Placemark>\n" + "<name>" + s[1] + "</name>\n" + "<styleUrl>"
						+ layer.get_data().get_colour() + "</styleUrl>\n" + "<description>" + "<![CDATA[BSSID: <b>"
						+ s[0] + "</b><br/>Capabilities: <b>" + s[2] + "</b><br/>Date: <b>" + s[3]
						+ "</b><br/>Chanel: <b>" + s[4] + "</b><br/>RSSI: <b>" + s[5] + "</b><br/>Accurecy: <b>" + s[9]
						+ "</b><br/>Type: <b>" + s[10] + "</b>]]>" + "</description>\n" + "<Point>\n" + "<coordinates>"
						+ s[7] + "," + s[6] + "," + s[8] + "</coordinates>\n" + "</Point>\n" + "<TimeStamp>\n"
						+ "<when>" + g.get_Meta_data().toTime() + "</when>\n" + "</TimeStamp>\n" + "</Placemark>\n";
				content.append(kmlelement);

				// creating gps point and adding it to the layer:
				g.setMAC(s[0]);
				g.setSSID(s[1]);
				g.setAuthMode(s[2]);
				g.setFirstSeen(s[3]);
				g.setChanel(Double.parseDouble(s[4]));
				g.setRSSI(Double.parseDouble(s[5]));
				g.setCurrentLatitude(Double.parseDouble(s[6]));
				g.setCurrentLongitude(Double.parseDouble(s[7]));
				g.setAltitudeMeters(Double.parseDouble(s[8]));
				g.setAccuracyMeters(Double.parseDouble(s[9]));
				g.setType(s[10]);
				layer.add(g);
			}
		}

		finally {
			csvReader.close();
		}
		return content;
	}

}
