import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;


@SuppressWarnings("serial")
/**
 * Calculates the score for the player, and adds the information to the database.
 */
public class Highscore extends JPanel {
//FIELDS
	private long endTime;
	private long gameTime;
	boolean playerDead;
	private int score;
	private int bonusScore = 0;
	private boolean betterScore;
	private JTextArea textArea = new JTextArea();
	JButton mainMenuButton = new JButton("Main Menu");

	protected static boolean mainHighscore = true;
	JPanel panel = new JPanel();


//CONSTRUCTOR
	public Highscore() {
		displayHighscore(Client.username);
		textArea.setBounds(100, 100, 400, 300);
		mainMenuButton.setBounds(175, 350, 100, 30);
		panel.add(mainMenuButton);
		panel.add(textArea);
		panel.setBackground(Color.ORANGE);
		panel.setLayout(null);
		panel.setVisible(true);
	}



	/**
	 * Takes the username and the calculated score, and adds it to the database.
	 * @param username The name the user has entered.
	 * @param score The score that has been calculated for the user.
	 */
	public void sethighScore(final String username, final int score) {
		Statement st = null;
		ResultSet rs;
		int rs_ = 0;
		try {
			st = SQL.connection.createStatement();
			rs = st
			.executeQuery("SELECT Highscore FROM player WHERE username = \""
					+ username + "\"");
			rs.first();
			if (rs.getInt(1) < score) {
				st = SQL.connection.createStatement();
				rs_ = st.executeUpdate("UPDATE player SET Highscore = '"
						+ score + "' WHERE Username = '" + username + "'");
				betterScore = true;
			} else {
				betterScore = false;
			}
			if (rs_ == 1) {
				System.out.println("Score " + score + " added to player "
						+ username);
				st.close();
			}
		} catch (final SQLException se) {
			System.out.println("Something went wrong at update");
		}
	}

	/**
	 * Sends a query to the database regarding the highscore list. Descending the list 
	 * using the highscore in the database when sending the query
	 * and extracts the three tuples which has the highest integer value. It also goes            
	 * through the database and checks the username position and extracts it.
	 * @param username The name the user has entered. 
	 * @return The extracted highscore list from the database, with added Strings.
	 */
	public String[] getHighscoreList(final String username) {
		final String[] list = new String[4];
		ResultSet rs = null;
		Statement st = null;
		int counter = 1;
		int index = 0;
		try {
			st = SQL.connection.createStatement();
			rs = st
			.executeQuery("SELECT * FROM player ORDER BY Highscore DESC");
			while (rs.next()) {
				if (counter < 4) {
					list[index] = counter + ". " + rs.getString(1) + " "
					+ rs.getInt(4);
					System.out.println(list[index]);
				}
				if (rs.getString(1).equals(username) && betterScore == true) {
					list[3] = "\n"
						+ "You did better this time. This is your position"
						+ "\n" + counter + ". " + rs.getString(1) + " "
						+ rs.getInt(4);
					System.out.println(list[3]);
					System.out.println("inne i equals");
				} else if (rs.getString(1).equals(username)
						&& betterScore == false) {
					list[3] = "\n"
						+ "You did not do better this time. \nThis is your score for this game: "
						+ score
						+ "\nThis is your position in the highscore list."
						+ "\n" + counter + ". " + rs.getString(1) + " "
						+ rs.getInt(4);
					System.out.println(list[3]);
				}

				counter++;
				index++;
			}
			System.out.println();
			st.close();
		} catch (final SQLException se) {
			System.out.println("We got an exception while getting a result:  ");
			System.out.println(se);
		}
		return list;

	}


	/** 
	 * Calls the getHighscoreList method. Sends the username as a parameter.
	 * Gets the returned arraylist and adds it to the JTextArea
	 * @param username The name the user has entered.  
	 */
	public void displayHighscore(final String username) {
		
		final String[] list = getHighscoreList(username);
		String s = "";

		if (mainHighscore) {
			for (int i = 0; i < (list.length - 1); i++) {
				s = s + list[i] + "\n";
			}
		} else {
			for (int i = 0; i < list.length; i++) {
				s = s + list[i] + "\n";
			}
		}
		
		textArea.setText("PLACEMENT\n\n" + s);
		textArea.setEditable(false);
		textArea.setOpaque(false);
		textArea.setFont(new Font("Serif", Font.BOLD, 15));
		textArea.setForeground(Color.BLACK);

	}


	/**
	 * Ends the timing of the game.
	 * Calls the calculateScore method.
	 */
	public void endtime() {
		endTime = System.currentTimeMillis();
		calculateScore();
	}

	/** 
	 * Takes the difference from startTime and endTime, and uses this to calculate the
	 * score. 
	 * It calls the sethighScore method and sends the username and score as a parameter.
	 * It calls the displayHighscore method and sends the username as a parameter.
	 */
	public void calculateScore() {
		score = 0;
		bonusScore = 0;
		gameTime = endTime - Client.startTime;		

		switch  (Client.difficulty){
		case 1:
			if (playerDead == false) {
				score = (int) (60000 - gameTime) / 1000;
			}
			if(playerDead == true && BombLogic.aiNumber == 1){
				score = 20;
			}
			if(playerDead == true && BombLogic.aiNumber == 2){
				score = 10;
			}
			break;
		case 2:
			if (playerDead == false) {
				score = (int) (80000 - gameTime) / 1000;
			}
			if(playerDead == true && BombLogic.aiNumber == 1){
				score = 40;
			}
			if(playerDead == true && BombLogic.aiNumber == 2){
				score = 30;
			}
			// gives the player 5 min to finish opponent
			if (playerDead == false && 300 - ((int) (gameTime) / 1000) > 0) {
				bonusScore = 30;
			}
			break;
		case 3:
			if (playerDead == false) {
				score = (int) (180000 - gameTime) / 1000;
			}
			if(playerDead == true && BombLogic.aiNumber == 1){
				score = 60;
			}
			if(playerDead == true && BombLogic.aiNumber == 2){
				score = 50;
			}
			// gives the player 5 min to finish opponent
			if (playerDead == false && 300 - ((int) (gameTime) / 1000) > 0) {
				bonusScore = 40;
			}
			break;
		}
		score = score + bonusScore;


		if (score < 0) {
			score = 0;
		}

		sethighScore(Client.username, score);
		displayHighscore(Client.username);

	}
}
