package File_format;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Algorithms.Path;
import Components.Fruit;

public class Path2KML {

	public Path2KML(ArrayList<Path> LP) {
		StringBuilder strB = new StringBuilder();
		strB.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n");
		strB.append("<kml>" + "\n");
		strB.append("<Document>" + "\n");
		for (Path p : LP) {
			strB.append(createPath(p));
		}
		strB.append("</Document>" + "\n");
		strB.append("</kml>");
		try {
			PrintWriter print = new PrintWriter(new File("converted/KMLFile" + System.currentTimeMillis() + ".kml"));
			print.write(strB.toString());
			print.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public StringBuilder createPath(Path path) {
		StringBuilder strB = new StringBuilder(); // string builder is mutable
		// when u call append(..) it alters the internal char array
		// rather than creating a new string object
		DateFormat dt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		StringBuilder date = new StringBuilder();

		for (Fruit f : path.getPath()) {

			strB.append("<Placemark>" + "\n");
			strB.append("<name>" + f.getID() + " </name>" + "\n");
			strB.append(" <TimeStamp>" + "\n");
			date.append(dt.format(Calendar.getInstance().getTime()));
			date.insert(10, "T");
			date.append("Z");
			strB.append("<when>" + date.toString() + "</when>" + "\n");
			strB.append("</TimeStamp>" + "\n");
			strB.append("<Point>" + "\n");
			strB.append("<coordinates>" + f.getLocation().x() + "," + f.getLocation().y() + "," + f.getLocation().z()
					+ "</coordinates>");
			strB.append("</Point>" + "\n");
			strB.append("</Placemark>" + "\n");
		}
		return strB;
	}

}
