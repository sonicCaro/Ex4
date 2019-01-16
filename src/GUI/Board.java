package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Algorithms.AutoNode;
import Algorithms.AutoPlayer;
import Algorithms.Play;
import Components.Blockers;
import Components.Fruit;
import Components.Game;
import Components.Packman;
import Coords.LatLonAlt;
import Coords.Map;
import Geom.Point3D;

/**
 *This class implements : KeyLister and MouseListener.
 *It represents the Game Board where the game takes place.
 *Its fields are:
 *1)JLabel that in charge of displaying the grade.
 *2)The game file 
 *3)The array of the id's (player's id's)
 *4)map object for coordinates alteration and display
 *5)update- that is an object its in charge for updating the game score and location
 *6)boolean variable to start Automatic mode.  
 * @author 318696150
 *
 */


public class Board extends JPanel implements KeyListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JLabel grade;
	private String GameFile = null;
	private long[] playerId;
	private Map map = null;
	private Play update = null;
	private boolean auto = false;
	private double deg = -1;

	
	
	/**
	 * empty constructor
	 * it displays the background image of the game.
	 * 
	 * @throws FileNotFoundException
	 */
	public Board() throws FileNotFoundException {
		initBoard();
	}
	
	
	

	/**
	 * This is a constructor that receives a string from the GameFile for a route. 
	 * it does two things :
	 * 1)takes the players' id numbers
	 * 2)sets up the board's parameters.
	 * 
	 * @param GameFile
	 * @throws FileNotFoundException
	 */
	public Board(String GameFile) throws FileNotFoundException {
		this.GameFile = GameFile;
		SetIDPane();
		initBoard();
	}
	

	/**
	 *displays the games pieces
	 * 
	 * @param g
	 */
	private void drawGame(Graphics g) {

		Game game = update.getGame();
		Point3D p1;
		grade.setText(update.getStatistics());

		for (Blockers blc : game.getblock()) {

			p1 = map.CoordsToPixels(blc.getBounds().get(0));
			Point3D p2 = map.CoordsToPixels(blc.getBounds().get(1));
			int w = Math.abs(p2.ix() - p1.ix());
			int h = Math.abs(p2.iy() - p1.iy());
			g.setColor(Color.BLACK);
			g.fillRect(p2.ix() - w, p2.iy(), w, h);
		}

		for (Fruit fr : game.getFruits()) {
			p1 = map.CoordsToPixels(fr.getLocation());
			g.drawImage(fr.getImage(), p1.ix(), p1.iy(), 30, 30,null);
		}
		for (Packman p : game.getPackmans()) {
			p1 = map.CoordsToPixels(p.getLocation());
			g.drawImage(p.getImage(), p1.ix(), p1.iy(), 30, 30, null);
		}
		for (Packman gh : game.getGhosts()) {
			p1 = map.CoordsToPixels(gh.getLocation());
			g.drawImage(gh.getImage(), p1.ix(), p1.iy(), 50, 50, null);
		}
		if (game.getPlayer() != null) {
			p1 = map.CoordsToPixels(game.getPlayer().getLocation());
			g.drawImage(game.getPlayer().getImage(), p1.ix(), p1.iy(), 30, 30, null);
		}
	}
	

	/**
	 * This method creates 3 JOption panels 
	 * for the players to enter their ID's.
	 */
	private void SetIDPane() {
		ArrayList<String> ans = new ArrayList<String>();
		ans.add(JOptionPane.showInputDialog(this, "Enter the 1st ID", null));
		ans.add(JOptionPane.showInputDialog(this, "Enter the 2nd ID", null));
		while (ans.remove("")) {
		}
		;
		playerId = new long[ans.size()]; 
		for (int i = 0; i < ans.size(); i++) { //collects the players ID
			playerId[i] = Integer.parseInt(ans.get(i));
		}
	}

	
	/**
	 * This method adds a MouseListener and a KeyListener to the JPanel.
	 * @throws FileNotFoundException
	 */
	private void initBoard() throws FileNotFoundException {
		map = new Map(); 
		setPreferredSize(new Dimension(map.getImage().getWidth(this),
				map.getImage().getHeight(this))); //displays the map
		
		addMouseListener(this);
		addKeyListener(this);
		
		setFocusable(true);
		this.requestFocusInWindow();
		this.setFocusTraversalKeysEnabled(false);

		grade = new JLabel(); //displays the grade's of the players 
		grade.setLocation(0, 630);
		grade.setForeground(Color.ORANGE);
		this.add(grade);

		repaint();

		if (GameFile != null) { //if there is no Game file (file is empty)
			update = new Play(GameFile);//create a new "update" object
			if (playerId.length == 3) { //use the file route to create it 
				update.setIDs(playerId[0], playerId[1], playerId[2]);
			} else if (playerId.length == 2) {  //sets the ID for it according to the file route
				update.setIDs(playerId[0], playerId[1]);
			} else if (playerId.length == 1) {
				update.setIDs(playerId[0]);
			}
			repaint();
		}
	}
	
	

	/**
	 * A mouseClicked method that uses the initial location received by MouseEvent
	 * 
	 */
	
	@Override
	public void mouseClicked(MouseEvent e) {
		LatLonAlt p = map.PixelsToCoords(new Point3D(e.getX(), e.getY(), 0.0));
		update.setInitLocation(p.lat(), p.lon()); //the initial location of each player
		repaint();
		
		update.start();
		if (auto) {
			robot();
		} else {
			(new Thread() { //creates a Thread
				public void run() { animate();}}).start(); //animates the game
		}
	}
	
	
/**
 * this method alters the degree by each key press 
 * 
 */
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode(); //gets the key code
		if (code == KeyEvent.VK_UP) { //compares it to find if it : up/down/left/right
			deg = 0; //updates the degree based on the result of the comparing
		}
		if (code == KeyEvent.VK_DOWN) {
			deg = 180;
		}
		if (code == KeyEvent.VK_LEFT) {
			deg = 270;
		}
		if (code == KeyEvent.VK_RIGHT) {
			deg = 90;
		}
	}

	/**
	 * This method is in charge of the movement 
	 */
	private void animate() {
		while (update.isRunning()) { //calls the update method to update there location
			if (deg != -1) {
				update.rotate(deg); //to make the player move forward
				repaint();
			}
			try {
				Thread.sleep(60); //it uses the thread so it can repaint in the right time 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		update.stop();
	}

	
	/**
	*robot method is used for automatic mode
	*/
	private void robot() {
		while (update.isRunning()) {
			if (update.getGame().getblock().isEmpty()) {

				eatClosestFruit();
			} else {
				getBetterAim();
			}
		}
		update.stop();
		auto = false;
	}

	/**
	 * When there is zero blocks the robot calls this method to operate.
	 */
	private void eatClosestFruit() {
		int ind = update.getClosestFruit(update.getGame().getPlayer()); //finds the nearest fruit to the player
		Fruit f = update.getGame().getFruits().get(ind); 
		while (update.getGame().getFruits().contains(f)) { //while the fruit is on the board 
			double degree = update.getGame().getPlayer().getLocation().azimuth_elevation_dist(f.getLocation())[0];
			update.rotate(degree); //rotate the player until it eats to the fruit 
			repaint();

			try {
				Thread.sleep(60); //to let the repaint catch up we use a sleep thread
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * When the game board contains some blocks , robot calls this method to operate.
	 * 
	 */
	public void getBetterAim() {
		AutoPlayer pa = new AutoPlayer(update.getGame().getPlayer()
				, update.getGame().getFruits(),
				update.getGame().getblock());  //autoPlayer is created 
		ArrayList<AutoNode> path = new ArrayList<AutoNode>(); 
		path = pa.createPath(); //create's the same path to the fruits 

		while (!path.isEmpty()) { //until the player finishes his path 

			double degree = update.getGame().getPlayer().getLocation()
					.azimuth_elevation_dist(path.get(0).getLocation())[0];
			double dist = update.getGame().getPlayer().getRadius() + update.getGame().getPlayer().getSpeed() / 10;

			while (update.getGame().getPlayer().getLocation().GPS_distance(path.get(0).getLocation()) >= dist) {
				update.rotate(degree); //rotate the player until he finishes his path
				repaint();

				try {
					Thread.sleep(60); ////to let the repaint catch up we use a sleep thread
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			path.remove(0);
		}
	}
	

	/**
	 * sets automatic mode on for the current game.
	 */
	public void AutomaticOn() {
		auto = true;
	}

	
	/**
	 *displays the game 
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(map.getImage(), 0, 0, null);

		if (update != null) {
			drawGame(g);
		}
	}


	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {

		// TODO Auto-generated method stub
	}
}
