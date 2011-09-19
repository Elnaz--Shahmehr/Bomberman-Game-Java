/**
 * Send thread sends data between server and client.
 */
public class SendThread implements Runnable {

	String output = "";

	SendThread(){
		output = Map.mapString;
	}

	SendThread(String a){
		output = a;
	}

	/**
	 * Here data is being sent.
	 */
	public void run() {
		//Send array to server
		System.out.println("converting client's map to string from client "
				+  output.length() + " " + output);
		ClientToServer.out.println(output);
	}
}
