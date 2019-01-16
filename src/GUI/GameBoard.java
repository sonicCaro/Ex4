
package GUI;

import java.awt.Dimension;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;

import javax.swing.*;

import Algorithms.Path;
import Algorithms.ShortestPathAlgo;
import Components.Fruit;
import Components.Game;
import Components.Packman;
import Coords.LatLonAlt;
import Coords.Map;
import Coords.MyCoords;
import Geom.Point3D;

/**
 * Description: class's fields:img, fruit, pacman are the images displayed on
 * the screen. B_Width, B_Height are the bounds of the background image. map,
 * game, set are the objects used to create the display.
 * 
 * this class displays the actual game according to the shortest path
 * algorithm(set).
 * 
 * @author Yarden and Caroline
 *
 */
public class GameBoard extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;
	private Map map;
	private Game game = null;
	private ShortestPathAlgo set = null;
	private Timer timer;

	/**
	 * Description: simple constructor that calls initBoard method to set the
	 * parameters.
	 * 
	 * @param g
	 */
	public GameBoard(Game g) {

		initBoard(g);
	}

	/**
	 * Description: the method loads the proper images to the fields and set the
	 * bounds of the component. if the user picked a complete game set the method
	 * calls forth the animation method else the program waits for the user to act.
	 * 
	 * @param g
	 */
	private void initBoard(Game g) {
		map = new Map();
		setPreferredSize(new Dimension(map.getImage().getWidth(null), map.getImage().getHeight(null)));
		game = g;
		if (game != null) {
			animate();
		}
	}

	/**
	 * Description: the method uses the shortestPathAlgo to animate the game. the
	 * method uses AdvanceDistance to set each packman exactly in its right location
	 * according to its speed, every second. the packman eats the fruit from the
	 * distance of its radius or less, each fruit eaten disappears from the screen.
	 * the method stops when all the fruits in each path were eaten.
	 */
	private void animate() {
		set = new ShortestPathAlgo(game);

		timer = new Timer(1, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int size = set.getPackmanPaths().size();
				Iterator<Packman> it = set.getPackmanPaths().iterator();
				Packman path;
				LatLonAlt point;
				MyCoords m = new MyCoords();
				if (size == 0) {
					timer.stop();
				} else {
					while (it.hasNext()) {
						path = it.next();
						if (path != null && !path.getPath().getPath().isEmpty()) {
							point = map.AdvanceDistance(path.getLocation(),
									path.getPath().getPath().get(0).getLocation(), path.getSpeed());
							if (m.distance3d(path.getLocation(), path.getPath().getPath().get(0).getLocation()) <= path
									.getRadius()) {
								path.setLocation(path.getPath().getPath().get(0).getLocation());
								path.getPath().remove(0);
							} else {
								path.setLocation(map.AdvanceDistance(path.getLocation(),
										path.getPath().getPath().get(0).getLocation(), path.getSpeed()));
							}
						} else {
							size--;
						}
						repaint();
					}
				}
			}
		});
		timer.start();
	}

	/**
	 * Description: the method loads a game piece to game field according to its
	 * parameter and then displays the pieces motionless.
	 * 
	 * @param piece
	 */
	public void LoadGamePiece(double[] piece) {
		if (piece[0] == 0) {
			game.addPackman(new Packman(0, new LatLonAlt(piece[1], piece[2], 0), 1.0, 1.0));
		} else {
			game.addFruit(new Fruit(0, new LatLonAlt(piece[1], piece[2], 0), 1.0));
		}
		// set=new ShortestPathAlgo(game);
		// animate();
		repaint();
	}

	/**
	 * Description: default method to display the game pieces.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(map.getImage(), 0, 0, null);
		if (!set.getPackmanPaths().isEmpty()) {
			drawSet(g);
		} else {
			drawGame(g);
		}

	}

	public void drawSet(Graphics g) {
		for (Packman p : set.getPackmanPaths()) {
			Point3D loc = map.CoordsToPixels(p.getLocation());
			g.drawImage(p.getImage(), loc.ix(), loc.iy(), 30, 30, null);

			for (Fruit f : p.getPath().getPath()) {
				loc = map.CoordsToPixels(f.getLocation());
				g.drawImage(f.getImage(), loc.ix(), loc.iy(), 30, 30, null);

			}
		}
	}

	public void drawGame(Graphics g) {
		for (Packman p : game.getPackmans()) {
			g.drawImage(p.getImage(), p.getLocation().ix(), p.getLocation().iy(), 30, 30, null);
		}
		for (Fruit f : game.getFruits()) {
			g.drawImage(f.getImage(), f.getLocation().ix(), f.getLocation().iy(), 30, 30, null);
		}
	}

	////////////// setters and getters/////////////////
	public Game getGame() {
		return game;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

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
