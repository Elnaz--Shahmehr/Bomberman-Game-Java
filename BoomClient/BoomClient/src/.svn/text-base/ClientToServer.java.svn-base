import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * ClientToServer class contains connection settings for socket from client to server
 * receives lines of string which come from server to client.
 */
public class ClientToServer {

	static Socket socketIn = null;
	static Socket socketOut = null;
	static int sendTimes = 0;
	static PrintWriter out = null;
	static BufferedReader in = null;

	ClientToServer(){
		try{//"79.99.2.203"
			socketIn = new Socket("79.99.2.203", 4000);
			in = new BufferedReader(new InputStreamReader(socketIn.getInputStream()));
			String line = in.readLine();
			System.out.println("I am player " + line);
			setCurrentPlayerInfo(line.charAt(0));
			Client.mainPanel.multiplayerInfo.setText("Please wait..");
			Client.mainPanel.multiplayerInfo.setVisible(true);
			socketOut = new Socket("79.99.2.203", 5000);
			out = new PrintWriter(socketOut.getOutputStream(), true);
			line = in.readLine();
			Map.mapString = line;
		} catch(UnknownHostException e) {
			System.out.println("Unknown host");
			System.exit(1);
		} catch(IOException e) {
			System.out.println("No I/O");
			System.exit(1);
		}
	}

	/**
	 * Sets the player's positions on the map.
	 * @param value is the value of the player. It is being set to a certain position on the map.
	 */
	private void setCurrentPlayerInfo(char value) {
		Client.currentPlayer = value;
		switch (Client.currentPlayer) {
		case  'a':
			Client.X = 0;
			Client.Y = 0;
			break;
		case  'b':
			Client.X = 14;
			Client.Y = 14;
			break;
		case  'c':
			Client.X = 14;
			Client.Y = 0;
			break;
		case  'd':
			Client.X = 0;
			Client.Y = 14;
			break;
		case  'e':
			Client.X = 7;
			Client.Y = 7;
			break;
		}
	}
}
