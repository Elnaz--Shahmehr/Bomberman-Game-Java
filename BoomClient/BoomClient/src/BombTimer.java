import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * Timer for bomb
 * calculates milliseconds for the bomb to explode
 */
public class BombTimer implements Constants{

	protected static boolean gameWon = false;
	private Timer timer;
	private int ontopBomb;

	public BombTimer(final Client client, int x, int y){
		final int bombX = x;
		final int bombY = y;
		ActionListener explosion = new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				BombTimer.this.timer.stop();
				ontopBomb = Map.getValue(bombY, bombX);
				Map.setValue(bombY, bombX, EXPLOSION);
				if (BombLogic.destroyBomb(bombX, bombY)){
					//					Map.setValue(bombY, bombX, EXPLOSION);
					client.gameOver();
					return;
				}else if(gameWon == true){
					//					Map.setValue(bombY, bombX, EXPLOSION);
					client.win();
					return;
				} else if(ontopBomb == Client.currentPlayer){
					//					Map.setValue(bombY, bombX, EXPLOSION);
					client.gameOver();
					return;
				} else if(ontopBomb == AI){
					//					Map.setValue(bombY, bombX, EXPLOSION);
					BombLogic.aiNumber--;

					if(bombX == Client.ai1.currentP.y && bombY == Client.ai1.currentP.x){
						Client.ai1.disableAi();
					}else if(bombX == Client.ai2.currentP.y && bombY == Client.ai2.currentP.x){
						Client.ai2.disableAi();
					}else{
						Client.ai3.disableAi();
					}

					if(BombLogic.aiNumber == 0){
						client.win();
						return;
					}
				} else {
					// calls explode method of the game for graphical effect
					client.explode();
					return;
				}
			}
		};
		timer = new Timer(1000, explosion);
		timer.start();
	}
}
