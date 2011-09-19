import java.io.IOException;

/**
 *This class is used to receive information from the server
 */
public class ReceiveThread implements Runnable, Constants{

	Client client;

	ReceiveThread(Client client){
		this.client = client;
	}
	/**
	 *This method interprets the info received from the server.
	 */
	public void run() {
		try{
			String line = ClientToServer.in.readLine();

			while (line.indexOf(Client.currentPlayer) != -1){
				if(line.charAt(225) == '1'){
					System.out.println("one of the clients won");
					client.win();
					return;
				}

				Map.mapString = line;
				line = ClientToServer.in.readLine();
				System.out.println("String received on client: " + Map.mapString);	
			}

			System.out.println("Client ReceiveThread terminated");
			BombTimer.gameWon = true;
			SendThread send = new SendThread(line.substring(0, 225) + (((line.charAt(225))-49)));
			Thread t = new Thread(send);
			t.start();
			client.gameOver();

		} catch (IOException e){
			System.out.println("Game ended");
			System.exit(1);
		}
	}


}