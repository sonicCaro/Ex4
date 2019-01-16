package Components;

import java.io.BufferedReader;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import Coords.LatLonAlt;



/**
 * This is the game class that consists of many fields such as:
 * Packman ,Packman Array List , Fruit Array list , Blockers Array List .....
 * @author 318696150 
 */
public class Game {
	private Packman _player = null;
	private ArrayList<Packman> _packmans = null;
	private ArrayList<Packman> _ghosts = null;
	private ArrayList<Fruit> _fruits = null;
	private ArrayList<Blockers> _block = null;
	private long InitTime;

	
	/////////////// getters and setters///////////////
	public Packman getPlayer() {
		return _player;
	}

	public void setPlayer(Packman pl) {
		if (pl != null) {
			_player = new Packman(pl);
		}
	}

	public ArrayList<Packman> getGhosts() {
		return _ghosts;
	}

	public void setGhosts(ArrayList<Packman> ghosts) {
		if (!ghosts.isEmpty()) {
			_ghosts = ghosts;
		}
	}

	public ArrayList<Blockers> getblock() {
		return _block;
	}

	public void setblock(ArrayList<Blockers>  block) {
		if (!block.isEmpty()) {
			_block = block;
		}
	}
	/**
	 *This constructor does many things :
	 *1)receives a csv file as parameter
	 *2)reads the csv file
	 *3)constructs a PackmenSet from the cvs file 
	 *4)constructs a FruitSet from the csv file 
	 * 
	 * @param csvFile
	 * @throws FileNotFoundException
	 */
	public Game(String csvFile) throws FileNotFoundException {

		
		_packmans = new ArrayList<Packman>();
		_ghosts = new ArrayList<Packman>();
		_fruits = new ArrayList<Fruit>();
		_block = new ArrayList<Blockers>(); // creates empty array-lists 

		BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
		String currLine;

		try {
			
			currLine = csvReader.readLine(); //// reads from the csv file

			while ((currLine = csvReader.readLine()) != null) {

				if (currLine.charAt(0) == 'P') { 
					Packman p = new Packman(currLine);
					p.setImage("img/pacman.jpg");
					_packmans.add(p);
				} else if (currLine.charAt(0) == 'F') {
					Fruit f = new Fruit(currLine);
					_fruits.add(f);
				} else if (currLine.charAt(0) == 'G') {
					Packman g = new Packman(currLine);
					g.setImage("img/ghost.jpg");
					_ghosts.add(g);
				} else if (currLine.charAt(0) == 'B') {
					Blockers blc = new Blockers(currLine);
					_block.add(blc);
				} else if (currLine.charAt(0) == 'M') {
					_player = new Packman(currLine);
					_player.setImage("img/player.png");
				}
			}

			csvReader.close();

		} catch (IOException e) {

		}

	}

	/**
	 *  empty constructor
	 * @param arrayList2 
	 * @param arrayList 
	 */
	public Game(ArrayList<Packman> arrayList, ArrayList<Fruit> arrayList2) { //
		_packmans = new ArrayList<Packman>();
		_ghosts = new ArrayList<Packman>();
		_fruits = new ArrayList<Fruit>();
		_block = new ArrayList<Blockers>();
		InitTime = new Date().getTime();
	}
	
	
	/**
	 * copy constructor 
	 * @param game
	 */

	public Game(Game game) {
		_packmans = new ArrayList<Packman>();
		_ghosts = new ArrayList<Packman>();
		_fruits = new ArrayList<Fruit>();
		_block = new ArrayList<Blockers>();
		InitTime = new Date().getTime();

		setPackmans(game.getPackmans());
		setFruits(game.getFruits());
		setGhosts(game.getGhosts());
		setblock(game.getblock());
		setPlayer(game.getPlayer());
	}

	/**
	 *  This method converts object Game to csvFile
	 * 
	 * @param csvFile
	 */
	public void GameToCSV(String csvFile) {

		try {
			PrintWriter print= new PrintWriter(new File(csvFile));
			StringBuilder strB = new StringBuilder();
			strB.append("Type" + ','+ "ID" + 
			',' + "Latitude" + 
			',' + "Longtitude" + 
			',' + "Altitute" + 
			',' + "Velocity/Weight" +
		    ','	+ "Radius" +
			',' + _packmans.size() +
			',' + _fruits.size() + 
			',' + _block.size());
			strB.append('\n');

			Iterator<Packman> PacItr= _packmans.iterator();
			Packman p;
			while (PacItr.hasNext()) {
				p = PacItr.next();
				strB.append('P' + ','
						+ Integer.toString(p.getID()) + 
						',' + Double.toString(p.getLocation().x()) + 
						','+ Double.toString(p.getLocation().y()) + 
						',' + Double.toString(p.getLocation().z()) + 
						','+ Double.toString(p.getSpeed()) + 
						',' + Double.toString(p.getRadius()));
				strB.append('\n');
			}

			Iterator<Packman> GhItr = _ghosts.iterator();
			Packman g;
			while (GhItr.hasNext()) {
				g = GhItr.next();
				strB.append('G' + ',' + Integer.toString(g.getID()) + 
						',' + Double.toString(g.getLocation().x()) +
						','+ Double.toString(g.getLocation().y()) + 
						',' + Double.toString(g.getLocation().z()) + 
						','+ Double.toString(g.getSpeed()) + 
						',' + Double.toString(g.getRadius()));
				strB.append('\n');
			}

			Iterator<Fruit> FruItr = _fruits.iterator();
			Fruit f;
			while (FruItr.hasNext()) {
				f = FruItr.next();
				strB.append('F' + ',' + Integer.toString(f.getID()) + 
						','+ Double.toString(f.getLocation().x()) + 
						','	+ Double.toString(f.getLocation().y()) + 
						',' + Double.toString(f.getLocation().z()) + 
						',' + Double.toString(f.getWeight()));
				strB.append('\n');
			}

			Iterator<Blockers> BlcItr= _block.iterator();
			Blockers blc;
			ArrayList<LatLonAlt> bounds = new ArrayList<LatLonAlt>();
			while (BlcItr.hasNext()) {
				blc = BlcItr.next();
				bounds = blc.getBounds();
				strB.append('B' + ',' + Integer.toString(blc.getID()) + ',' + Double.toString(bounds.get(0).x()) + ','
						+ Double.toString(bounds.get(0).y()) + ',' + Double.toString(bounds.get(0).z()) + ','
						+ Double.toString(bounds.get(1).x()) + ',' + Double.toString(bounds.get(1).y()) + ','
						+ Double.toString(bounds.get(1).z()) + ',' + Double.toString(blc.getWeight()));
				strB.append('\n');
			}
			print.write(strB.toString());
			print.close();
		} catch (IOException e) {

		}

	}
	
	

	public int FruitIndex(Fruit f) {
		Iterator<Fruit> it = _fruits.iterator();
		int counter = 0;
		Fruit fr;
		while (it.hasNext()) {
			fr = it.next();
			if (fr.getID() == f.getID() && fr.getLocation() == f.getLocation()) {
				break;
			}
			counter++;
		}
		return counter;
	}
	
	
///////////////boolean's/////////////////////
	public boolean addPackman(Packman p) {
		return _packmans.add(p);
	}

	public boolean addGhost(Packman g) {
		return _ghosts.add(g);
	}

	public boolean addFruit(Fruit f) {
		return _fruits.add(f);
	}

	public boolean addObstacle(Blockers blc) {
		return _block.add(blc);
	}
	
	
///////////////////Iterators//////////////////////
	public Iterator<Packman> PacItr() {
		Iterator<Packman> it = _packmans.iterator();
		return it;
	}

	public Iterator<Packman> GhItr() {
		Iterator<Packman> it = _ghosts.iterator();
		return it;
	}

	public Iterator<Fruit> FruItr() {
		Iterator<Fruit> it = _fruits.iterator();
		return it;
	}

	public Iterator<Blockers> BlcItr() {
		Iterator<Blockers> it = _block.iterator();
		return it;
	}
	
	


	/**
	 *This method that returns an ArrayList of Fruit objects
	 * 
	 * @return _fruits 
	 */
	public ArrayList<Fruit> getFruits() {
		return _fruits;
	}
	

	/**
	 * This method that sets Fruits field with a given ArrayList.
	 * 
	 * @param _fruits
	 */
	public void setFruits(ArrayList<Fruit> fruits) {
		if (!fruits.isEmpty()) {
			_fruits = fruits;
		}
	}
	
	
	/**
	 * This method returns Packman array list
	 * 
	 * @return _packmans
	 */
	public ArrayList<Packman> getPackmans() {
		return _packmans;
	}
	
	

	/**
	 * This method sets Packmans field with a given ArrayList.
	 * 
	 * @param packmans
	 */
	public void setPackmans(ArrayList<Packman> packmans) {
		if (!packmans.isEmpty()) {
			_packmans = packmans;
		}
	}
	

	
	
	
	/**
	 * To String function
	 * @return a string consists of pacman/fruit/ghost/block.
	 */
		public String toString() {
			StringBuilder strB = new StringBuilder();
			Iterator<Packman> pItr = _packmans.iterator();
			while (pItr.hasNext()) {
				strB.append(pItr.next().toString());
				strB.append('\n');
			}
			Iterator<Packman> gItr = _ghosts.iterator();
			while (gItr.hasNext()) {
				strB.append(gItr.next().toString());
				strB.append('\n');
			}
			Iterator<Fruit> fItr = _fruits.iterator();
			while (fItr.hasNext()) {
				strB.append(fItr.next().toString());
				strB.append('\n');
			}
			Iterator<Blockers> bItr = _block.iterator();
			while (bItr.hasNext()) {
				strB.append(bItr.next().toString());
				strB.append('\n');
			}
			if (_player != null) {
				strB.append(_player.toString());
			}
			return strB.toString();
		}


	
}
