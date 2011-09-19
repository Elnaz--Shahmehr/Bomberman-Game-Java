import javax.swing.*;

@SuppressWarnings("serial")
/**
 * Creates a panel that holds a progress bar, which we use
 * to display the loading that takes place at the initiation of the 
 * applet.
 */
public class ProgressPanel extends JPanel {

	protected JProgressBar progress = new JProgressBar(0, 100);

	ProgressPanel(){
		this.setLayout(null);
		progress.setValue(20);
		progress.setStringPainted(true);
		progress.setBounds(100, 200, 300, 30);
		this.add(progress);
		this.setVisible(true);
	}
}