import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This is the server of Boom game
 * when it starts players may be connected and participate in multip-layer game
 * 
 */
class Server {

	static ArrayList<ClientWorker> clientList = new ArrayList<ClientWorker>();
	static ServerSocket server1;
	static ServerSocket server2;
	PrintWriter out = null;
	BufferedReader in = null;
	static int playerNumber = 97;
	Socket socketOut = null;
	Socket socketIn = null;

	/**
	 * Listenes to the incoming data on the sockets.
	 * @param player is player's value on the map.
	 */
	public void listenSocket(char player){
		try{
			socketOut = server1.accept();
			out = new PrintWriter(socketOut.getOutputStream(), true);
			out.println(player);
			System.out.println("Player " + player + " added on server");
			playerNumber++;
			socketIn = server2.accept();
			in = new BufferedReader(new InputStreamReader(socketIn.getInputStream()));
			ServerRead read = new ServerRead(in);
			Thread t = new Thread(read);
			t.start();
		} catch (IOException e) {
			System.out.println("Could not create ports");
			System.exit(-1);
		}
		ClientWorker tempCW = new ClientWorker(out);
		clientList.add(tempCW);
	}

	/**
	 * Method Closes the connection when browser is closed.
	 */
	protected void finalize(){
		try{
			server1.close();
			server2.close();
		} catch (IOException e) {
			System.out.println("Could not close socket");
			System.exit(-1);
		}
	}

	/**
	 * Starts the server
	 */
	public static void main(String[] args){
		new ServerMap();
		Server frame = new Server();
		System.out.println("Server map is generated");
		try {
			server1 = new ServerSocket(4000);
			server2 = new ServerSocket(5000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(playerNumber<102){
			frame.listenSocket((char) playerNumber);
		}
		for(ClientWorker tempCW : Server.clientList){
			tempCW.out.println(ServerMap.mapString);
		}
	}

}
