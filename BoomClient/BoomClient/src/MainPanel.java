import java.awt.Color;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
/**
 * Create MainPanel with single/multi player choice, difficulty, 
 * loading a saved game, and log out buttons.
 */
@SuppressWarnings("serial")
public class MainPanel extends JPanel{

//FIELDS
	JButton logOutButton = new JButton("Log Out");
	JRadioButton easyRadio = new JRadioButton("Easy");
	JRadioButton mediumRadio = new JRadioButton("Medium", true);
	JRadioButton hardRadio = new JRadioButton("Hard");
	JButton startButton = new JButton("START!");
	JButton loadButton = new JButton("LOAD!");
	JButton retrieveHighscore = new JButton("Highscore");
	JButton multiplayer = new JButton("Join");
	JLabel multiplayerInfo = new JLabel();
	protected JLabel loadInfo;

//CONSTRUCTORS
	MainPanel(){
		Client.difficulty = 2;
		JLabel single = new JLabel("Single player");
		JLabel multi = new JLabel("Multi player");
		loadInfo = new JLabel("");
		// Group the radio buttons, so only one can be selected
		ButtonGroup group = new ButtonGroup();
		easyRadio.setBackground(Color.orange);
		mediumRadio.setBackground(Color.orange);
		hardRadio.setBackground(Color.orange);
		group.add(easyRadio);
		group.add(mediumRadio);
		group.add(hardRadio);
		
		this.setBackground(Color.orange);
		
		this.setLayout(null);
		logOutButton.setBounds(180, 10, 120, 30);
		easyRadio.setBounds(100, 100, 80, 30);
		mediumRadio.setBounds(100, 130, 80, 30);
		hardRadio.setBounds(100, 160, 80, 30);
		startButton.setBounds(180, 250, 120, 60);
		loadButton.setBounds(180, 350, 120, 30);
		retrieveHighscore.setBounds(180, 400, 120, 30);
		multiplayer.setBounds(315, 100, 100, 30);
		single.setBounds(90, 50, 120, 30);
		multi.setBounds(330, 50, 120, 30);
		loadInfo.setBounds(100, 220, 120, 30);
		multiplayerInfo.setBounds(315, 150, 120, 30);
		multiplayerInfo.setVisible(false);
		this.add(logOutButton);
		loadInfo.setBounds(200, 220, 120, 30);
		this.add(multiplayerInfo);
		this.add(easyRadio);
		this.add(mediumRadio);
		this.add(hardRadio);
		this.add(startButton);
		this.add(loadButton);
		this.add(retrieveHighscore);
		this.add(multiplayer);
		this.add(loadInfo);
		this.add(single);
		this.add(multi);
		this.setVisible(true);
	}
}
