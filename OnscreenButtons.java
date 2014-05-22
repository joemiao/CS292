package staffui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class OnscreenButtons extends JFrame implements ActionListener {
	private JButton run = new JButton("Run");
	private JButton stop = new JButton("Quit");
	private JButton quit = new JButton("Quit");
	public OnscreenButtons() {
		/*add buttons to frame*/
	    run.addActionListener(this);
	    stop.addActionListener(this);
	    quit.addActionListener(this);
	}

	public void actionPerformed(ActionEvent evt) {
	    Object src = evt.getSource();
	    if (src == run) {
	    	System.out.print("start");
	    } else if (src == stop) {
	    	System.out.print("stop");
	    } else if (src == quit) {
	    	System.exit(0);
	    } 
	  }	
	
}
