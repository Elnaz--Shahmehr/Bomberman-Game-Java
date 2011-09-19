import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
/**
 * Main class of the game. Initializes, controls and paints.
 */
public class Client extends JApplet implements KeyListener, Constants {

	private LoginPanel loginPanel;
	private ProgressPanel progressPanel;
	protected static int difficulty;
	protected static MainPanel mainPanel;
	private SaveAndLoad sl = new SaveAndLoad();
	protected static String username;
	private static String password;
	protected static boolean loaded = false;
	private boolean hasAI = false;
	private Highscore highscore;
	protected static AIPlayer ai1;
	protected static AIPlayer ai2;
	protected static AIPlayer ai3;
	static boolean running;
	protected static long startTime;
	
	private int repaintCount = 0;
	static SoundEffect sound = new SoundEffect();
// Map variables
	public static int X = 0, Y = 0;
	static char tempValue = 0;

	Image tempImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);

	BufferedImage OSC = new BufferedImage(500, 500, BufferedImage.TYPE_3BYTE_BGR);
//	Graphics2D OSG;
	Graphics2D OSG = (Graphics2D)OSC.getGraphics();
	protected static boolean paintMapFlag = false;
	public static Images img;
	
	// Multiplayer variables
	
	public static boolean multiplayer = false;
	public static char currentPlayer = 0;
	
	public void init() {
		System.out.println(getCodeBase());
		progressPanel = new ProgressPanel();
		setSize(480, 480);
		getContentPane().add(progressPanel);
		setVisible(true);
		increaseProgressTo(10);
		sound.soundFile1 = getAudioClip(getDocumentBase(),
				"BombDestroyWalls.wav");
		sound.soundFile2 = getAudioClip(getDocumentBase(), "AiDies.wav");
		sound.soundFile3 = getAudioClip(getDocumentBase(), "eatWall.wav");
		loginPanel = new LoginPanel();
		initializeComponent();
		increaseProgressTo(40);
		img = new Images();
		mainPanel = new MainPanel();
		mainScreenListeners();
		increaseProgressTo(60);
		new Map();
		new SQL();
		increaseProgressTo(100);
		highscore = new Highscore();
		getContentPane().add(highscore);
		highScoreButton();
		getContentPane().removeAll();
		getContentPane().add(loginPanel);
		validate();
	}

	public void start() {
	}

	public void stop() {
	}

	/**
	 * Initializes the action listeners for Login Panel.
	 */
	private void initializeComponent() {
		loginPanel.jBLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("login button pressed");
				jBLogin_actionPerformed();
			}
		});
		loginPanel.jBNewUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jBNewUser_actionPerformed();
			}
		});
	}


	/**
	 * Updates the user status and closes the database connection.
	 */
	public void destroy() {
		try {
			if(username != null){
			Statement st = SQL.connection.createStatement();
			st.executeUpdate("UPDATE player SET status = 0 WHERE username = '" + username + "'");
			st.close();
			}
			SQL.connection.close();
		}
		catch (Exception e) {
		}
	}

	/**
	 * When the Login button is pushed, compares the username
	 * and password content with database and shows result.
	 */
	private void jBLogin_actionPerformed() {
		loginPanel.jLabelPassConf.setVisible(false);
		String query = "";
		boolean found = false;
		showStatus("login pressed...");
		// connnnnection code :::
		Statement st = null;
		ResultSet rs = null;
		int i = 0;
		username = loginPanel.getName();
		password = loginPanel.getPassword();
		System.out.println(username + " " + password);
		try {
			st = SQL.connection.createStatement();
			rs = st.executeQuery("SELECT * FROM player WHERE username = \""
					+ username + "\"");
			while (rs.next()) {
				i++;
			}
		} catch (SQLException se) {
			showStatus("We got an exception while executing our query: that probably means our SQL is invalid");
			System.exit(1);
		}
		if (i != 0) {
			try {
				query = "SELECT * FROM player WHERE username = \"" + username
						+ "\" and password=\"" + password + "\"";
				rs = st.executeQuery(query);
				found = rs.next();
			} catch (SQLException d) {
				System.err.println("an Sql error has occured");
				System.exit(1);
			}
			if (found) {
				try{
				if (rs.getInt(3) == 0){
					
				st.executeUpdate("UPDATE player SET status = 1 WHERE username = '" + username + "'");
				getContentPane().removeAll();
				getContentPane().add(mainPanel);
				validate();
				}
				else{
				username = null;
				loginPanel.jLabelPassConf.setText("User already online.");
				loginPanel.jLabelPassConf.setVisible(true);
				}
				}
				catch (Exception e){
				e.printStackTrace();
				}
			} 
				else {
				// showStatus("Invalid password");
				loginPanel.jLabelPassConf.setText("Invalid password");
				loginPanel.jLabelPassConf.setVisible(true);
			}
		} else {
			// showStatus("user doesn't exsist");
			loginPanel.jLabelPassConf.setText("User doesn't exsist");
			loginPanel.jLabelPassConf.setVisible(true);
		}
	}

	/**
	 * When New User button is pushed, a new user is added to the database.
	 */
	private void jBNewUser_actionPerformed() {
		loginPanel.jLabelPassConf.setVisible(false);
		showStatus("New user pressed...");
		Statement st = null;
		int rs = 0;
		username = loginPanel.getName();
		password = loginPanel.getPassword();
		if (username.trim().length() == 0)
			username = null;
		if (password.trim().length() == 0)
			password = null;
		if (username != null && password != null) {
			try {
				st = SQL.connection.createStatement();
				rs = st
						.executeUpdate("INSERT INTO player (Username,Password) VALUES (\""
								+ username.trim()
								+ "\", \""
								+ password.trim()
								+ "\")");
				if (rs == 1) {
					showStatus("User " + username + " added.");
					loginPanel.jLabelPassConf.setText("User " + username
							+ " added.");
				}
			} catch (SQLException se) {
				// showStatus("User already exists");
				loginPanel.jLabelPassConf.setText("User already exists");
				loginPanel.jLabelPassConf.setVisible(true);
			}
		}
	}

	/**
	 * It start controlling the multiplayer.
	 */
	public void multiplayer() {
		getContentPane().removeAll();
		validate();
		multiplayer = true;
		paintMapFlag = true;
//		highscore = new Highscore();
//		highScoreButton();
//		highscore.PlayerDED = false;
		tempValue = EMPTY;
		running = true;
		BombTimer.gameWon = false;
		requestFocus();
		new ClientToServer();
		validate();
		addKeyListener(this);
		repaint();
		ReceiveThread receive = new ReceiveThread(this);
		Thread t = new Thread(receive);
		t.start();
		
	}

	/**
	 * Initializes action listeners for Main Panel.
	 */
	public void mainScreenListeners() {
		// Register listeners for mainScreen panel
		mainPanel.logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showStatus("Logout Button Clicked");
				logOutButton_actionPerformed();
			}
		});

		mainPanel.multiplayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showStatus("Multiplayer Button Clicked");
				multiplayer();
			}
		});
		mainPanel.startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showStatus("Start Button Clicked");
				loaded = false;
				showMap();
			}
		});
		mainPanel.retrieveHighscore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				showStatus("Highscore Button Clicked");
				loaded = false;
				Highscore.mainHighscore = true;
				highscore = new Highscore();
				getContentPane().removeAll();
				highScoreButton();
				highscore.panel.revalidate();
				highscore.panel.setVisible(true);
				getContentPane().add(highscore.panel);
				validate();
				
			}
		});
		mainPanel.loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showStatus("Load Button Clicked");
				try {
					loaded = true;
					sl.loadGame();
				} catch (Exception d) {
					System.err.println("Not connected!");
				}
				if (loaded) {
					showMap();
				}
			}
		});
		mainPanel.easyRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showStatus("Easy Button Selected");
				difficulty = 1;
			}
		});
		mainPanel.mediumRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showStatus("Medium Button Selected");
				difficulty = 2;
			}
		});
		mainPanel.hardRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showStatus("Hard Button Selected");
				difficulty = 3;
			}
		});
	}

	/**
	 * Adds action listener for highscore button.
	 */
	public void highScoreButton() {
		highscore.mainMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showStatus("MainMenu Button Selected");
				mainMenuButton_actionPerformed(e);

			}
		});
	}
	
	/**
	 * Changes the status of the user and updates the panels.
	 */
	private void logOutButton_actionPerformed() {
		try{
		Statement st = SQL.connection.createStatement();
		st.executeUpdate("UPDATE player SET status = 0 WHERE username = '" + username + "'");
		st.close();
		getContentPane().removeAll();
		loginPanel = new LoginPanel();
		initializeComponent();
		mainPanel = new MainPanel();
		mainScreenListeners();
//		highscore = new Highscore();
		highScoreButton();
		getContentPane().add(loginPanel);
		validate();
		}
		catch(Exception d){
		d.printStackTrace();
		}
	}

	private void mainMenuButton_actionPerformed(ActionEvent e) {
		getContentPane().removeAll();
		mainPanel = new MainPanel();
		mainScreenListeners();
		getContentPane().add(mainPanel);
		validate();
	}

	/**
	 * Controls single player game mode.
	 */
	public void showMap() {
		//starts the time used in the highscore class
		startTime = System.currentTimeMillis();

		currentPlayer = PLAYER;
		BombLogic.aiNumber = 3;
		highscore.playerDead = false;
		tempValue = EMPTY;
		running = true;
		BombTimer.gameWon = false;
		getContentPane().removeAll();
		validate();
		paintMapFlag = true;
		addKeyListener(this);
		if(!hasAI){
		ai1 = new AIPlayer();
		ai2 = new AIPlayer();
		ai3 = new AIPlayer();
		}
		if (loaded) {
			ai1.disableAi();
			ai2.disableAi();
			ai3.disableAi();
				BombLogic.aiNumber = SaveAndLoad.AILeft;
				if (BombLogic.aiNumber == 1) {
					ai1 = new AIPlayer();
					ai1.currentP.setLocation(SaveAndLoad.temp.get(0),
							SaveAndLoad.temp.get(1));
				} else if (BombLogic.aiNumber == 2) {
					ai1 = new AIPlayer();
					ai2 = new AIPlayer();
					ai1.currentP.setLocation(SaveAndLoad.temp.get(0),
							SaveAndLoad.temp.get(1));
					ai2.currentP.setLocation(SaveAndLoad.temp.get(2),
							SaveAndLoad.temp.get(3));
				} else {
					ai1 = new AIPlayer();
					ai2 = new AIPlayer();
					ai3 = new AIPlayer();
					ai1.currentP.setLocation(SaveAndLoad.temp.get(0),
							SaveAndLoad.temp.get(1));
					ai2.currentP.setLocation(SaveAndLoad.temp.get(2),
							SaveAndLoad.temp.get(3));
					ai3.currentP.setLocation(SaveAndLoad.temp.get(4),
							SaveAndLoad.temp.get(5));
				}
				Map.setValue(1, 0, EMPTY);
		} else {
			new Map();
			Y = 0;
			X = 0;
			if(hasAI){
			ai1.disableAi();
			ai2.disableAi();
			ai3.disableAi();
			ai1 = new AIPlayer();
			ai2 = new AIPlayer();
			ai3 = new AIPlayer();
			}
		}
		Map.setValue(Y, X, PLAYER);
		hasAI = true;
		requestFocus();
		repaint();
	}

	/**
	 * Paints the map.
	 * @param g Graphics parameter.
	 */
	public void paint(Graphics g) {
		if (paintMapFlag) {
//			OSC.createGraphics();
			OSG.dispose();
			Graphics2D OSG = (Graphics2D)OSC.getGraphics();
			OSG.setBackground(getBackground());
			paintComponent(OSG); // Draw applet contents to OSC.
			g.drawImage(OSC, 0, 0, this); // Copy OSC to screen.
		}
	}
	/**
	 * Paints the off screen canvas.
	 * @param g Graphics parameter.
	 */
	public void paintComponent(Graphics g) {
		if (paintMapFlag) {
			int i = 0, j = 0;
//			g.setB(Color.orange);
			for (i = 0; i < 15; i++) {
				for (j = 0; j < 15; j++) {
					switch(Map.getValue(i, j)){
					case SOLID:
						tempImage = img.solidImage;
						break;
					case SOFT:
						tempImage = img.softImage;
						break;
					case EMPTY:
						tempImage = img.emptyImage;
						break;
					case BOMB:
						tempImage = img.bombImage;
						break;
					case PLAYER:
						tempImage = img.playerImage;
						break;
					case PLAYER1:
						tempImage = img.player1;
						break;
					case PLAYER2:
						tempImage = img.player2;
						break;
					case PLAYER3:
						tempImage = img.player3;
						break;
					case PLAYER4:
						tempImage = img.player4;
						break;
					case PLAYER5:
						tempImage = img.player5;
						break;
					case AI:
						tempImage = img.aiImage;
						break;
					case EXPLOSION:
						repaintCount++;
						if (repaintCount == 20) {
							Map.setValue(i, j, EMPTY);
							repaintCount = 0;
						}
						tempImage = img.explosionImage;
						break;
					}
					g.drawImage(tempImage, j * SIZE + DISTANCE, i * SIZE + DISTANCE, this);
				}
			}
			if (!multiplayer)
				isItOver();
			repaint();
		}
	}
	
	/**
	 * Checks if game is over.
	 */
	private void isItOver() {
		if (ai1.itsOver || ai2.itsOver || ai3.itsOver) {
			gameOver();
			return;
		}
	}
	
	/**
     * Tries to move the player to the wanted position in the array.
     * @param newX The new X coordinate that the player wants to move to.
     * @param newY The new Y coordinate that the player wants to move to.
     *
     */
	private void tryMove(int newX, int newY) {
		
		System.out.println("trying to move");
		// we shouldn't move out of board
		if (newX > 14 || newY > 14 || newX < 0 || newY < 0) {
			return;
		}
		char value = Map.getValue(newY, newX);
		// we can NOT move on 1(permanent wall) or 3(temporary wall)
		switch (value){
		case SOFT:
			return;
		case SOLID:
			return;
		case PLAYER1:
			return;
		case PLAYER2:
			return;
		case PLAYER3:
			return;
		case PLAYER4:
			return;
		case PLAYER5:
			return;
		case AI:
			gameOver();
			return;
		}
		Map.setValue(Y, X, tempValue); // set the previous status
		// System.out.println("second if");
		tempValue = value; // keep the status of the square we're moving to
		Map.setValue(newY, newX, currentPlayer);
		X = newX;
		Y = newY;
		SendThread send = new SendThread();
		Thread t = new Thread(send);
		t.start();
		repaint();
	}

	public void keyPressed(KeyEvent e) {
		System.out.println("key is pressed on client");
		int key = e.getKeyCode();
		// we test the new coordinate using newX and newY to see
		// if we can move to that square
		int newX = X;
		int newY = Y;
			switch (key) {
			case KeyEvent.VK_SPACE:
				// dropping bomb
				if (tempValue == EMPTY) {
					tempValue = BOMB;
					BombTimer bt = new BombTimer(this, X, Y);
				}
				break;
				// movement
			case KeyEvent.VK_LEFT:
				newX--;
				tryMove(newX, newY);
				break;
			case KeyEvent.VK_RIGHT:
				newX++;
				tryMove(newX, newY);
				break;
			case KeyEvent.VK_UP:
				newY--;
				tryMove(newX, newY);
				break;
			case KeyEvent.VK_DOWN:
				newY++;
				tryMove(newX, newY);
				break;
			}
	}

	/**
	 * Repaints in case of explosion of bomb.
	 */
	public void explode() {
		repaint();
	}

	/**
	 * Key listener for ESC key released acts as 'save game'.
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_ESCAPE && paintMapFlag == true && !multiplayer){
			ai1.disableAi();
			ai2.disableAi();
			ai3.disableAi();
			removeKeyListener(this);
			paintMapFlag = false;
			highscore.endtime();
			try {
				sl.saveGame();
			} catch (Exception d) {
				System.out.println("Unable to save!");
				d.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "Game saved!", "Saving...", 0);
			getContentPane().removeAll();
			mainPanel = new MainPanel();
			mainPanel.revalidate();
			mainPanel.setVisible(true);

			mainScreenListeners();
			getContentPane().add(mainPanel);
			validate();
		}
	}

	public void keyTyped(KeyEvent e) {
	}
	
	/**
	 * Manages applet when player dies.
	 */
	public void gameOver() {
		removeKeyListener(this);
		if (running) {
			if(!multiplayer){
				ai1.disableAi();
				ai2.disableAi();
				ai3.disableAi();
			}
			running = false;
			multiplayer = false;
			paintMapFlag = false;
			System.out.println("inside game over");
			highscore.mainHighscore = false;
			highscore = new Highscore();
			highscore.playerDead = true;
			highscore.endtime();
			JOptionPane.showMessageDialog(null, "Player died!", "Game Over", 0);
			getContentPane().removeAll();
			highscore.panel.revalidate();
			highscore.panel.setVisible(true);
			highScoreButton();
			getContentPane().add(highscore.panel);
			validate();
//			highscoreDisplayer(true);
		}
	}

	/**
	 * Manages applet when player wins.
	 */
	public void win() {
		removeKeyListener(this);
		if (running) {
			running = false;
			multiplayer = false;
			paintMapFlag = false;
			highscore.mainHighscore = false;
			highscore = new Highscore();
			highscore.playerDead = false;
			highscore.endtime();
			JOptionPane.showMessageDialog(null, "All enemies are dead!", "You Win", 0);
			getContentPane().removeAll();
			highscore.panel.revalidate();
			highscore.panel.setVisible(true);
			highScoreButton();
			getContentPane().add(highscore.panel);
			validate();

		}
	}

	/**
	 * Sets the progress of the progress bar in Progress Panel.
	 * @param temprogress The value of the desires progress.
	 */
	private void increaseProgressTo(int temprogress) {
		progressPanel.progress.setValue(temprogress);
	}

}
