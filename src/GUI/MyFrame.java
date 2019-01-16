package GUI;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import Components.Fruit;
import Components.Game;
import Components.Packman;
import Coords.Map;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import Coords.MyCoords;
import File_format.csv2kml;
import File_format.Path2KML;
import File_format.Json_101;
import Geom.Point3D;
import Algorithms.ShortestPathAlgo;
import Components.Fruit;
import Components.Game;
import Components.Game_data;
import Components.Blockers;
import Components.Packman;




/**
 *This class is essintial for the game.
 *Its fields are:
 *1)a default serialVersionUID.
 *2)a GameBoard where the actual game takes place.
 *3)an array, set to null, unless the user creates a new game piece.
 *it also has :
 *1)GameSets where complete game sets are stores and can be displayed. 
 *2)"New Game" where the user can create a new game set and save it.
 * 
 * @author 318696150
 */
 

public class MyFrame extends JFrame implements MouseListener {

	private static final long serialVersionUID = 1L;
	private GameBoard g; // ex 2-3
	private double[] piece = null;
	private Game game = null;
	private Board board; // ex 4

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					MyFrame frame = new MyFrame();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 *constructor that starts the gui functionality.
	 */
	public MyFrame() {

		initGameGUI();
	}

	

	/**
	 *The method where the JFrame assembles all its components which
	 * are 2 menu's:
	 * BoardGame(JPanel) action listeners to all the menu buttons and
	 * a mouse listener to the screen for when the user places a new game piece.
	 */
	 
	public void initGameGUI() {
		try {
			LoadPanel();
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		JMenuBar m = new JMenuBar();
		setJMenuBar(m);

		JMenu menu = new JMenu("Game Sets");
		JMenuItem m1 = new JMenuItem("Game Set 1");
		JMenuItem m2 = new JMenuItem("Game Set 2");
		JMenuItem m3 = new JMenuItem("Game Set 3");
		JMenuItem m4 = new JMenuItem("Game Set 4");
		JMenuItem m5 = new JMenuItem("Game Set 5");
		JMenuItem m6 = new JMenuItem("Game Set 6");

		m.add(menu);
		menu.add(m1);
		menu.add(m2);
		menu.add(m3);
		menu.add(m4);
		menu.add(m5);
		menu.add(m6);

		JMenu create = new JMenu("New Game");
		JMenuItem c1 = new JMenuItem("Create New Game");
		JMenuItem c2 = new JMenuItem("New Pacman");
		JMenuItem c3 = new JMenuItem("New Fruit");
		JMenuItem c4 = new JMenuItem("Save Game");
		JMenuItem c5 = new JMenuItem("Play Game");

		m.add(create);
		create.add(c1);
		create.add(c2);
		create.add(c3);
		create.add(c4);
		create.add(c5);

		JMenu play = new JMenu("Play");
		JMenuItem p1 = new JMenuItem("Load Game");
		JMenuItem p2 = new JMenuItem("Automatic Pilot");

		m.add(play);
		play.add(p1);
		play.add(p2);

		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		/////////////////// Game Set Animation Menu////////////////////
		m1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					loadBoard("csv/game_1543684662657.csv");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		m2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					loadBoard("csv/game_1543685769754.csv");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		m3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {

					loadBoard("csv/game_1543693822377.csv");

				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		m4.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					loadBoard("csv/game_1543693911932.csv");

				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		m5.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					loadBoard("csv/game_1543693911932_a.csv");

				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		m6.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					loadBoard("csv/game_1543693911932_b.csv");

				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		

		/////////////// New Game Menu////////////////////////

		c1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					loadBoard("");
					piece = new double[3];
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		c2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				piece[0] = 0;
			}
		});

		c3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				piece[0] = 1;
			}
		});

		c4.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				game = new Game(game);
				Map map = new Map();
				int i = 0;
				for (Packman p : g.getGame().getPackmans()) {
					game.addPackman(new Packman(i, map.PixelsToCoords(p.getLocation()), p.getSpeed(), p.getRadius()));
					i++;
				}
				i = 0;
				for (Fruit f : g.getGame().getFruits()) {
					game.addFruit(new Fruit(i, map.PixelsToCoords(f.getLocation()), f.getWeight()));
					i++;
				}
				game.GameToCSV("csv/game2csv.csv");
			}
		});

		c5.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (game != null) {
					try {
						loadBoard("csv/game2csv.csv");
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		//////////////////////// Play Menu//////////////////
		p1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new java.io.File("."));
				fileChooser.setDialogTitle("User/eclipse-workspace/Ex4_OOP/data/");
				int result = fileChooser.showOpenDialog(getParent());
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					try {
						LoadPanel(selectedFile);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		p2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				board.AutomaticOn();
			}
		});
	}

	/**
	 *
	 * 
	 * @throws FileNotFoundException
	 */
	 
	private void LoadPanel() throws FileNotFoundException {
		board = new Board();
		add(board);// creates an empty Board to display only the background of the game
		pack();

	}

	/**
	 *
	 * 
	 * @param selectedFile
	 * @throws FileNotFoundException
	 */
	 
	private void LoadPanel(File selectedFile) throws FileNotFoundException {
		remove(board);
		board = new Board(selectedFile.getPath());
		add(board);//Creates a new Board based on the game file selected by the user
		pack();
		setVisible(true);
	}

	/**
	 * A method called by initGameGUI 
	 * 
	 * @param str
	 * @throws FileNotFoundException
	 */
	 
	public void loadBoard(String str) throws FileNotFoundException {
		if (g != null) {
			this.remove(g);
		}
		Game game = null;
		if (str == "")
			game = new Game(game);
		else
			game = new Game(str);
		g = new GameBoard(game);
		add(g); //set the GameBoar parameters according to the button's menu, clicked.
		pack();
		g.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (piece != null) {
					piece[1] = e.getX();
					piece[2] = e.getY();

					g.LoadGamePiece(piece);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
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
		});
	}

	
	 
	public void paintComponents(Graphics g) {
		super.paintComponents(g); //A default method of Swing components to display the game pieces.
	}

	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

} 




