import java.io.*;
/**
 * Contains BufferedReader which constantly reads incoming data from clients and updates the map.
 * 
 */
/**
 * What class does.
 */
class ServerRead implements Runnable {

	BufferedReader in;

	ServerRead(BufferedReader in){
		this.in = in;
	}

	/**
	 * What method does.
	 */
	public void run(){
		try{
			String line = in.readLine();;
			while(!line.isEmpty()){
				System.out.println("this came from client: \n" + line);
				ServerMap.mapString = line;
				System.out.println("sending the mapString from server to all clients");
				for(ClientWorker tempCW : Server.clientList){
					tempCW.out.println(ServerMap.mapString);
					System.out.println(ServerMap.mapString);
				}
				line = in.readLine();
			}
			System.out.println("Reading thread stopped");
		} catch (IOException e) {
			System.out.println("Read failed");
		}
	}
}
