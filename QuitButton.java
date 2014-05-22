package staffui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class QuitButton 
  implements ActionListener 
{
	private String shape;
	private int[] position;
	private int x;
	private int y;
	private int width;
	private int height;
	private JButton quitButton;
	
	public QuitButton(int x, int y, int w, int h){
		quitButton = new JButton("Quit");
		this.shape = "Rectangle";
	    quitButton.setBounds(x, y, w, h);
	}
	
	public String getShape()
	{
		return shape;
	}

	public int[] getPosition()
	{
		position[0] = x;
		position[1] = y;
		return position;
	}
	
	public void actionPerformed(ActionEvent e){
		quitButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent event) {
	    		System.exit(0);
	    	}
	    });
	}
	
	
}
