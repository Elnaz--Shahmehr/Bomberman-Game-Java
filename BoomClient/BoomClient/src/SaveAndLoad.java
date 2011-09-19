import java.sql.*;
import java.util.*;
/**
 * The saving and loading of the game.
 */
public class SaveAndLoad implements Constants{

	private String save;//Used to store the info from the array
	protected static ArrayList<Integer> temp;//Used to store positions of remaining ai:s
	protected static int AILeft;

	/**
	 * Stores the current state of the map array in a string for later use when loading
	 * @throws SQLException
	 */
	public void saveGame() throws SQLException{
		Statement st = SQL.connection.createStatement();
		save = "";
		int i = 0, j = 0;
		for(i = 0; i < 15; i++){
			for(j = 0; j < 15; j++){
				save = save + Map.getValue(i, j);
			}
		}
		System.out.println(save);
		try{//Storing the info in the database
			String query = "UPDATE player SET Save = '" + save + "' WHERE username = '" + Client.username + "'";
			st.executeUpdate(query);
			System.out.println(Client.username);
		}catch(Exception e){
			System.err.println("A SQL error has occured");
			e.printStackTrace();
		}
		st.close();
	}

	/**
	 * Used to fill the map array with previously saved values read from a string.
	 * @throws SQLException
	 */
	public void loadGame() throws SQLException{
		new Map();
		temp = new ArrayList<Integer>();
		int i = 0, j = 0, c = 0;
		AILeft = 0;
		Statement st = SQL.connection.createStatement();
		ResultSet rs = null;
		try{
			String query = "SELECT * from player WHERE username = '" + Client.username + "'";
			rs = st.executeQuery(query);
			rs.first();
			save = rs.getString(5);
			if (save != null){
				for(i = 0; i < 15; i++){
					for(j = 0; j < 15; j++){ 
						char arrayValue = save.charAt(c);
						//Getting each character in the string as an array value
						if(arrayValue == BOMB){
							arrayValue = EMPTY;
						}
						if(arrayValue == PLAYER){
							Client.X = j;
							Client.Y = i;
						}
						Map.setValue(i, j, arrayValue);
						if(arrayValue == AI){
							AILeft++;
							temp.add(i);
							temp.add(j);
							Map.setValue(i, j, EMPTY);
							if(AILeft > 3){
								AILeft = 3;
							}
						}
						c++;
					}
				}
			}else{
				Client.mainPanel.loadInfo.setText("No save found.");
				System.err.println("You have no save!");
				Client.loaded = false;
			}
		}
		catch(Exception e){
			System.err.println("A SQL error has occured!");
			e.printStackTrace();
		}
		st.close();
	}
}