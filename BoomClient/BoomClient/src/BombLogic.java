/**
 * Contains logic which is run when bomb explodes.
 * Checks what bomb explosion meets on it's way and destroys.
 */
public class BombLogic implements Constants {

	protected static int aiNumber;

	/**
	 * Deploys bomb.
	 * @param map The game map array.
	 * @param bombX The x corrdinate of the bomb in the array at deployment time
	 * @param bombY The y corrdinate of the bomb in the array at deployment time
	 * @return True if bomb is deployed.
	 */
	public static boolean destroyBomb(int bombX,int bombY){
		int possibleDestroyRange = 0;
		int startPoint = bombX+1;
		int endPoint = 0;
		Client.sound.soundFile1.play();
		//destroy on right side
		possibleDestroyRange = 15 - startPoint;
		if ( possibleDestroyRange > RANGE ){
			possibleDestroyRange = RANGE;
		}
		// for loop check what the explosion of the bomb meets on it's way
		for(int i = startPoint; i < startPoint + possibleDestroyRange; i++){
			if(Map.getValue(bombY, i) == SOLID){
				break;
			}else if (Map.getValue(bombY, i) == EMPTY){
				Map.setValue(bombY, i, EXPLOSION);
				continue;
			}else if(Map.getValue(bombY, i) == PLAYER){
				Map.setValue(bombY, i, EXPLOSION);
				//GAME OVER SHOULD BE CALLED
				//PLAYER HAS DIED
				return true;
			}else if(Map.getValue(bombY, i) == AI){
				Map.setValue(bombY, i, EXPLOSION);
				aiNumber--;

				if(i == Client.ai1.currentP.y && bombY == Client.ai1.currentP.x){
					Client.ai1.disableAi();
				}else if(i == Client.ai2.currentP.y && bombY == Client.ai2.currentP.x){
					Client.ai2.disableAi();
				}else{
					Client.ai3.disableAi();
				}

				if(aiNumber == 0){
					BombTimer.gameWon = true;
				}
			}
			Map.setValue(bombY, i, EXPLOSION); //destroys the wall
		}
		// destroy on left side
		possibleDestroyRange = bombX;
		if (possibleDestroyRange > RANGE){
			possibleDestroyRange = RANGE;
		}

		startPoint = bombX-1;
		endPoint = startPoint - possibleDestroyRange;
		if (endPoint<-1){
			endPoint = -1;
		}

		if (startPoint <0){
			startPoint =0;
		}

		for(int i=startPoint ; i>endPoint ; i--){
			if(Map.getValue(bombY, i) == SOLID){
				break;
			} else if (Map.getValue(bombY, i) == EMPTY){
				Map.setValue(bombY, i, EXPLOSION);
				continue;
			}else if(Map.getValue(bombY, i) == PLAYER){
				Map.setValue(bombY, i, EXPLOSION);
				//GAME OVER SHOULD BE CALLED
				//PLAYER HAS DIED
				return true;
			}else if(Map.getValue(bombY, i) == AI){
				Map.setValue(bombY, i, EXPLOSION);
				aiNumber--;
				if(i == Client.ai1.currentP.y && bombY == Client.ai1.currentP.x){
					Client.ai1.disableAi();
				}else if(i == Client.ai2.currentP.y && bombY == Client.ai2.currentP.x){
					Client.ai2.disableAi();
				}else{
					Client.ai3.disableAi();
				}

				if(aiNumber == 0){
					BombTimer.gameWon = true;
				}
			}
			Map.setValue(bombY, i, EXPLOSION);
		}

		// destroy up
		possibleDestroyRange = bombY;
		if (possibleDestroyRange > RANGE ) possibleDestroyRange = RANGE;
		startPoint = bombY-1;
		endPoint = startPoint - possibleDestroyRange;
		if (endPoint<-1) endPoint = -1;
		if (startPoint <0) startPoint =0;
		for(int i=startPoint ; i>endPoint ; i--){
			if(Map.getValue(i, bombX) == SOLID){
				break;
			} else if (Map.getValue(i, bombX) == EMPTY){
				Map.setValue(i, bombX, EXPLOSION);
				continue;
			}else if(Map.getValue(i, bombX) == PLAYER){
				Map.setValue(i, bombX, EXPLOSION);
				//GAME OVER SHOULD BE CALLED
				//PLAYER HAS DIED
				return true;
			}else if(Map.getValue(i, bombX) == AI){
				Map.setValue(i, bombX, EXPLOSION);
				aiNumber--;
				if(bombX == Client.ai1.currentP.y && i == Client.ai1.currentP.x){
					Client.ai1.disableAi();
				}else if(bombX == Client.ai2.currentP.y && i == Client.ai2.currentP.x){
					Client.ai2.disableAi();
				}else{
					Client.ai3.disableAi();
				}

				if(aiNumber == 0){
					BombTimer.gameWon = true;
				}
			}
			Map.setValue(i, bombX, EXPLOSION);
		}
		//destroy down
		startPoint = bombY+1;
		possibleDestroyRange = 15 - startPoint;
		if (possibleDestroyRange > RANGE ) possibleDestroyRange = RANGE;
		endPoint =startPoint + possibleDestroyRange;
		if (endPoint>  224 ) {
			endPoint = 224;
		}
		for(int i=startPoint ; i<endPoint ; i++){
			if (i<15){
				if(Map.getValue(i, bombX) == SOLID){
					break;
				} else if (Map.getValue(i, bombX) == EMPTY){
					Map.setValue(i, bombX, EXPLOSION);
					continue;
				}else if(Map.getValue(i, bombX) == PLAYER){
					Map.setValue(i, bombX, EXPLOSION);
					//GAME OVER SHOULD BE CALLED
					//PLAYER HAS DIED
					return true;
				}else if(Map.getValue(i, bombX) == AI){
					Map.setValue(i, bombX, EXPLOSION);
					aiNumber--;
					if(bombX == Client.ai1.currentP.y && i == Client.ai1.currentP.x){
						Client.ai1.disableAi();
					}else if(bombX == Client.ai2.currentP.y && i == Client.ai2.currentP.x){
						Client.ai2.disableAi();
					}else{
						Client.ai3.disableAi();
					}

					if(aiNumber == 0){
						BombTimer.gameWon = true;
					}
				}
				Map.setValue(i, bombX, EXPLOSION);
			}
		}
		return false;
	}
}

