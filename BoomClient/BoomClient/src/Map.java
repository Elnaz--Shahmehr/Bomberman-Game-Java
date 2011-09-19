/**
 * Map class contains the map, which is being generated when new instance is created.
 */
public class Map implements Constants{

	static String mapString = "";

	Map(){
		mapString = "";
		for(int i=0; i<15; i++) {
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
		setValue(0, 1, EMPTY);
		setValue(1, 0, EMPTY);
	}

	/**
	 * Retrieves certain value from the mapString.
	 * @param column represents column in array in which we need to get the value.
	 * @param row represents row in array in which we need to get the value.
	 * @return
	 */
	public static char getValue(int column, int row){
		return mapString.charAt((column)+(row * 15));
	}

	/**
	 * Replaces the value in the string treating a string as an array.
	 * @param column represents column in array in which we need to set the value
	 * @param row represents row in array in which we need to get the value
	 * @param value is the value, which need to be set in to mapString.
	 */
	public static void setValue(int column, int row, char value){
		mapString = mapString.substring(0,column+(row * 15))
			+ value + mapString.substring(column + 1+(row * 15));
	}

	/**
	 * Prints out mapString.
	 */
	public static void printMaze(){
		System.out.println("in Map on Client: " + mapString);
	}
}
