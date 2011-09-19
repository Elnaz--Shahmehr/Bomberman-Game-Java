import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Toolkit;
import java.net.URL;

/**
 * Preloading images for game.
 */
public class Images {
	
	
	Image softImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
	Image aiImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
	Image playerImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
	Image bombImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
	Image solidImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
	Image explosionImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
	Image emptyImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
	
	// Multiplayers
	
	Image player1 = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
	Image player2 = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
	Image player3 = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
	Image player4 = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
	Image player5 = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
	
	Images(){
		String filePath = "file:/D:/Workspace/BoomClient/bin/";
		try{
		Toolkit tk = Toolkit.getDefaultToolkit();
		softImage = tk.getImage(new URL(filePath + "test.png"));
		aiImage = tk.getImage(new URL(filePath + "ai_bigger.png"));
		playerImage = tk.getImage(new URL(filePath + "playergreen.png"));
		bombImage = tk.getImage(new URL(filePath + "bomb_orange2.png"));
		solidImage = tk.getImage(new URL(filePath + "test2.png"));
		explosionImage = tk.getImage(new URL(filePath + "expl.png"));
		emptyImage = tk.getImage(new URL(filePath + "empty.png"));
		// Multiplayers
		
		player1 = tk.getImage(new URL(filePath + "player1.png"));
		player2 = tk.getImage(new URL(filePath + "player2.png"));
		player3 = tk.getImage(new URL(filePath + "player3.png"));
		player4 = tk.getImage(new URL(filePath + "player4.png"));
		player5 = tk.getImage(new URL(filePath + "player5.png"));
		
		} catch(Exception e){
			System.out.println("Could not load images in Images class");
		}
	}
}
