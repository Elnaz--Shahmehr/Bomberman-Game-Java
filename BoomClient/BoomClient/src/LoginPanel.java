

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
/**
 * Create LoginPanel with username and password fields &
 * add New User and Login buttons. 
 */
public class LoginPanel extends JPanel {
	
//FIELDS
	private JLabel jLabelName = new JLabel("Username:");
	private JLabel jLabelPass = new JLabel("Password:");
	private JTextField jtfName = new JTextField();
	private JPasswordField jpfPass = new JPasswordField();
	JButton jBLogin = new JButton("Login");
	JButton jBNewUser = new JButton("Add this user");
	JLabel jLabelPassConf = new JLabel();
	
//CONSTRUCTORS
	LoginPanel(){
		this.add(jLabelName);
		this.jLabelName.setFocusable(false);
		this.setBackground(Color.orange);
		this.setLayout(null); //use this code with caution, it can mess everything up
		jLabelName.setBounds(100, 75, 100, 25);
		jLabelPass.setBounds(100, 125, 100, 25);
		jtfName.setBounds(200, 75, 120, 25);
		jpfPass.setBounds(200, 125, 120, 25);
		jBLogin.setBounds(175, 225, 150, 50);
		jLabelPassConf.setBounds(100, 170, 120, 25);
		jBNewUser.setBounds(190, 300, 120, 30);
		this.add(jLabelName);
		this.add(jLabelPass);
		this.add(jtfName);
		this.add(jpfPass);
		this.add(jBLogin);
		this.add(jBNewUser);
		this.add(jLabelPassConf);
		this.jLabelPassConf.setVisible(false);
		this.setVisible(true);
	}

//METHODS
	
	/**
     * Returns the username user has entered.
     * @return String 
     */
	public String getName(){
		return this.jtfName.getText();
	}
	
	/**
     * Returns the password user has entered.
     * @return String 
     */
	public String getPassword(){
		return String.valueOf(this.jpfPass.getPassword());
	}
	
}
