import java.awt.Point;

/**
 * What class does.
 */
public class AIPlayer implements Runnable, Constants{

	Point currentP = new Point(0,0);
	Point nextP = new Point(0,0);
	int direction = 0;
	int prevDirection = 0;
	int x;
	int y;
	public Point mazeBlockPos = new Point(0,0);
	public int mazeBlockValue = 99;
	Thread engine = null;
	protected boolean aiDead;
	boolean itsOver = false;

	public AIPlayer(int x, int y){
		this.x = x*32;
		this.y = y*32;
	}

	public AIPlayer(){
		this.aiDead = false;
		if(!Client.loaded){
			placeAI();
		}
		engine = new Thread(this);
		engine.start();
	}

	/**
	 * What method does
	 */
	public void run(){
		System.out.println("in run 1 ");
		while (!this.aiDead){
			generateMove();
			/*Any square the ai has succesfully moved to turns 
				empty when the ai leaves.**/
			Map.setValue(mazeBlockPos.x, mazeBlockPos.y, EMPTY);
			Map.setValue(currentP.x, currentP.y, AI);
			try {
				//The threads sleep-time is modified with the difficulty
				if(Client.difficulty == 1){
					Thread.sleep(1000);
				}
				else if(Client.difficulty == 3){
					Thread.sleep(400);
				}
				else{
					Thread.sleep(700);
				}
			}
			catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("thread error in aiPlayer");
			}
		}		
	}
	/**
	 * Used to randomize AI placing on non-loaded games.
	 */
	public void placeAI(){
		boolean correctPoint = false;
		while(!correctPoint){
			currentP.setLocation(((Math.random()*12)),(Math.random()*12));
			if(currentP.x%2 == 0 && currentP.y%2 == 0  && currentP.x > 3 && currentP.y > 3
					&& Map.getValue(currentP.x, currentP.y) == EMPTY ){
				correctPoint = true;
			}
		}
		Map.setValue(currentP.x, currentP.y, AI);
	}

	public Point getPossition(){
		return currentP;
	}	
	
	/**
	 * Used to remove remaining AI values from the array.
	 */
	public void disableAi(){
		Client.sound.soundFile2.play(); 
		this.aiDead = true;
		Map.setValue(this.currentP.x, this.currentP.y, EMPTY);
	}
	
	/**
	 * This method is used to make the AI move. 
	 */
	public void generateMove(){
		boolean allowMove = false;
		prevDirection = direction;
		//c		the random directions are
		//c		1 is up
		//c		2 is right
		//c		3 is down
		//c		4 is left
		//c		going clockwise from 12
		while (!allowMove){
			direction = (int)(Math.random()*4+1);
			if(Math.abs(direction-prevDirection)==2){
				allowMove = false;
			} else{
				allowMove = checkMove(direction);
			}
		}
		if(Math.abs(direction-prevDirection) == 2){
			System.out.println("problem");
		}
		currentP.x = nextP.x;
		currentP.y = nextP.y;
	}
	
	/**
	 * This method checks if the place the AI 
	 * wants to move to is an allowed position
	 * @param directions What is directions
	 * @return True if..
	 */
	public boolean checkMove(int directions){
		int tempX = 0, tempY = 0;
		switch(directions) {
		case 1:
			if(currentP.x == 0){
				return false;
			}
			tempX = currentP.x-1;
			tempY = currentP.y;
			break;
		case 2:
			if(currentP.y == 14){
				return false;
			}
			tempX = currentP.x;
			tempY = currentP.y+1;
			break;
		case 3:
			if(currentP.x == 14){
				return false;
			}
			tempX = currentP.x+1;
			tempY = currentP.y;
			break;
		case 4:
			if(currentP.y == 0){
				return false;
			}
			tempX = currentP.x;
			tempY = currentP.y-1;
			break;
		}
		if (engine.isInterrupted()) {
			System.out.println("First thread has been interrupted.");
		}
		
		if(Map.getValue(tempX, tempY) == SOLID){
			return false;
		}else{
			if(Map.getValue(tempX, tempY) == PLAYER){
				itsOver = true;
			}
			if(Map.getValue(tempX, tempY) == SOFT && Client.paintMapFlag)
				Client.sound.soundFile3.play(); 

			nextP.setLocation(tempX, tempY);
			mazeBlockPos.setLocation(currentP.x, currentP.y);
			return true;
		}
	}
}



