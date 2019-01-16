package Algorithms;

import Coords.GeoBox;
import Coords.LatLonAlt;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import Components.Blockers;
import Components.Fruit;
import Components.Game;
import Components.Packman;

public class Play {

	private Game _game;
	private ArrayList<Long> _ids;
	private Date _start;
	private int _status;
	private int _steps;
	private int _gameID;
	private String _res;
	private int _wrongLocation;
	private int _ghostKills;
	private double _lastGhostKill;
	private double _score;
	private double _time;
	private double _maxTime;
	private Blockers Bounds;
	private boolean isRunning = false;
	private String _gameFile = "unknown.123";
	public static final double DT = 100.0D;
	public static final double MAX_TIME = 100000.0D;
	public static final int INIT = 0;
	public static final int RUN = 1;
	public static final int PUASE = 2;
	public static final int DONE = 3;
	public static final LatLonAlt MIN = new LatLonAlt(32.101898D, 35.202369D, 0.0D);
	public static final LatLonAlt MAX = new LatLonAlt(32.105728D, 35.212416D, 0.0D);
	
	private void sendFinalReport() {
		String jdbcUrl = "jdbc:mysql://ariel-oop.xyz:3306/oop";
		String jdbcUser = "boaz";
		String jdbcPassword = "9125";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
			Statement statement = connection.createStatement();

			String id = _ids.get(0) + ",0,0";
			if (_ids.size() == 2) {
				id = _ids.get(0) + "," + _ids.get(1) + ",0";
			}
			if (_ids.size() == 3) {
				id = _ids.get(0) + "," + _ids.get(1) + "," + _ids.get(2);
			}
			String insertSQL = "INSERT INTO logs (FirstID, SecondID, ThirdID, LogTime,Point, SomeDouble)\r\nVALUES ("
					+ id + ", CURRENT_TIMESTAMP," + _score + "," + _gameID + ");";
			statement.executeUpdate(insertSQL);
			statement.close();
			connection.close();
		} catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Play(String g) throws FileNotFoundException {
		_game = new Game(g);
		_status = 0;
		_gameFile = g;
		init();
	}

	public String getStatistics() {
		Date end = new Date();
		double time_left = _maxTime - _time;
		String ans = "Play Report:" + end + " ,total time:" + _time + " ,score:" + _score + ", Time left:" + time_left
				+ ", kill by ghosts:" + _ghostKills + ", out of box:" + _wrongLocation;
		return ans;
	}

	public String getBoundingBox() {
		return Bounds.toString();
	}

	public void stop() {
		isRunning = false;
		if (_status == 1) {

			_time = _maxTime;
			checkDone();
		}
	}

	/**
	 *This method :
	 *1)receives lat and lot for the initial location of the
	 * player.
	 *2)creates a player and assign it the given location only if
	 * the location is valid.
	 * 
	 * @param lat
	 * @param lon
	 * @return the initial location
	 */
	public boolean setInitLocation(double lat, double lon) {
		boolean ans = false;
		if (_status != 1) {
			LatLonAlt ll = new LatLonAlt(lat, lon, 0.0D);
			if (isValid(ll)) {
				Packman pl = new Packman(0, ll, 20, 1);
				pl.setType('M');
				pl.setImage("img/player.png");
				_game.setPlayer(pl);
				ans = true;
			}
		}
		return ans;
	}

	public void setIDs(long id1) {
		_ids = new ArrayList<Long>();
		_ids.add(Long.valueOf(id1));
	}

	public void setIDs(long id1, long id2) {
		_ids = new ArrayList<Long>();
		_ids.add(Long.valueOf(id1));
		_ids.add(Long.valueOf(id2));
	}

	public void setIDs(long id1, long id2, long id3) {
		_ids = new ArrayList<Long>();
		_ids.add(Long.valueOf(id1));
		_ids.add(Long.valueOf(id2));
		_ids.add(Long.valueOf(id3));
	}

	public Game getGame() {
		return new Game(_game);
	}

	/**
	 * @return the value of a boolean variant to determine whether the
	 * game still playing or not.
	 * 
	 */
	public boolean isRunning() {
		return isRunning;
	}

	private void init() {
		_score = 0.0D;
		_steps = 0;
		_time = 0.0D;
		_wrongLocation = 0;
		_lastGhostKill = 0.0D;
		_ghostKills = 0;
		_res = "";
		Bounds = new Blockers(-1, MIN, MAX);
		if ((_ids == null) || (_ids.size() == 0)) {
			_ids = new ArrayList<Long>();
			_ids.add(Long.valueOf(123456789L));
		}
		_gameID = _gameFile.hashCode();
	}

	public void start() {
		start(100000.0D);
	}

	public void start(double time_out_ms) {
		isRunning = true;
		_maxTime = time_out_ms;
		_start = new Date();
		_status = 1;
		init();
		_game.getPlayer().setSpeed(20.0);
	}

	public boolean rotate(double ang) {
		boolean ans = false;
		boolean done = checkDone();
		if (_status == 1) {
			Packman p = _game.getPlayer();
			p.setOrientation(ang);
			ans = move();
		}
		return ans;
	}

	private boolean isValid(LatLonAlt p) {
		boolean ans = true;
		if (!Bounds.inBounds(p)) {
			ans = false;
		} else {
			for (int i = 0; (ans) && (i < _game.getblock().size()); i++) {
				Blockers b = _game.getblock().get(i);
				if (b.inBounds(p))
					ans = false;
			}
		}
		return ans;
	}

	private void run() {
		Packman p = _game.getPlayer();
		for (int i = 0; i < _game.getGhosts().size(); i++) {
			Packman g = _game.getGhosts().get(i);
			g.setOrientation(p.getLocation());
			g.moving(100.0D);
		}

		boolean cont = true;
		for (int i = 0; (cont) && (i < _game.getPackmans().size()); i++) {
			Packman pk = _game.getPackmans().get(i);
			int find = getClosestFruit(pk);
			Fruit cl = _game.getFruits().get(find);
			pk.setOrientation(cl.getLocation());
			if (pk.getLocation().GPS_distance(cl.getLocation()) < pk.getRadius()) {
				_game.getFruits().remove(find);
				if (_game.getFruits().size() == 0)
					cont = false;
			}
			pk.moving(100.0D);
		}
	}

	private void test() {
		Packman p = _game.getPlayer();
		double dist = p.getRadius() + p.getSpeed() / 10;
		for (int i = 0; i < _game.getFruits().size(); i++) {
			Fruit f = _game.getFruits().get(i);
			if (p.getLocation().GPS_distance(f.getLocation()) < dist) {
				_score += f.getWeight();
				_game.getFruits().remove(i);
			}
		}
	}

	private void test1() {
		Packman p = _game.getPlayer();
		double dist = p.getRadius() + p.getSpeed() * 100.0D / 1000.0D;
		for (int i = 0; i < _game.getPackmans().size(); i++) {
			Packman pk = _game.getPackmans().get(i);
			if (p.getLocation().GPS_distance(pk.getLocation()) < dist) {
				_score += pk.getRadius();
				_game.getPackmans().remove(i);
			}
		}
	}

	private void test2() {
		Packman p = _game.getPlayer();
		for (int i = 0; (_time - _lastGhostKill > 3000.0D) && (i < _game.getGhosts().size()); i++) {
			Packman pk = _game.getGhosts().get(i);
			double dist = pk.getRadius() + pk.getSpeed() * 100.0 / 1000.0;
			if (p.getLocation().GPS_distance(pk.getLocation()) < dist) {
				_score -= 20.0;
				_ghostKills += 1;
				_lastGhostKill = _time;
			}
		}
	}

	public int getClosestFruit(Packman p) {
		int ans = -1;
		ArrayList<Fruit> ff = _game.getFruits();
		if (ff.size() > 0) {
			ans = 0;
			double min_d = p.getLocation().GPS_distance(ff.get(ans).getLocation());
			for (int i = 1; i < ff.size(); i++) {
				double d = p.getLocation().GPS_distance((ff.get(i).getLocation()));
				if (d < min_d) {
					min_d = d;
					ans = i;
				}
			}
		}
		return ans;
	}

	private boolean move() {
		boolean ans = false;
		if (_status == 1) {
			Packman p = _game.getPlayer();
			LatLonAlt pv = new LatLonAlt(p.getLocation());
			p.moving(100.0D);
			test();
			test1();
			test2();
			ans = true;
			if (!isValid(p.getLocation())) {
				_score -= 1.0D;
				_wrongLocation += 1;
				p.setLocation(pv);
			}
			_steps += 1;
			_time += 100.0D;
			if ((_status == 1) && (!checkDone())) {
				run();
			}
		}
		return ans;
	}

	private boolean checkDone() {
		boolean ans = false;
		if ((_status == 1) && ((_game.getFruits().size() == 0) || (_time >= _maxTime))) {
			isRunning = false;
			_status = 3;
			double remain_time = _maxTime - _time;
			_score += remain_time / 1000.0D;
			sendFinalReport();
			ans = true;

			_res = toString();
		}
		return ans;
	}


}
