
/**
 * Generates map on the server which will be later sent to the clients.
 */

public class ServerMap implements Constants{

	static String mapString = "";
	
	ServerMap(){
		for ( int i=0; i<15; i++) {
			for ( int j=0; j<15; j++) { 
				if((i+1)%2 == 0 && (j+1)%2 == 0){
					mapString = mapString + SOLID;
				} else {
					if(Math.random()<0.5){
						mapString = mapString + SOFT;
					} else {
						mapString = mapString + EMPTY;
					}
				}
			}
		}
		mapString = mapString + 5;
		StringBuilder a = new StringBuilder(mapString);
		a.setCharAt(1, EMPTY);
		a.setCharAt(15, EMPTY);
		a.setCharAt(0, PLAYER1);
		a.setCharAt(209, EMPTY);
		a.setCharAt(223, EMPTY);
		a.setCharAt(224, PLAYER2);
		a.setCharAt(195, EMPTY);
		a.setCharAt(211, EMPTY);
		a.setCharAt(210, PLAYER3);
		a.setCharAt(13, EMPTY);
		a.setCharAt(29, EMPTY);
		a.setCharAt(14, PLAYER4);
		a.setCharAt(97, EMPTY);
		a.setCharAt(98, EMPTY);
		a.setCharAt(113, PLAYER5);
		mapString = a.toString();
	}
}