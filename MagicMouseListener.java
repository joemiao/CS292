package staffui;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MagicMouseListener extends MouseAdapter
  implements MouseListener {
	
	public MagicMouseListener(){
	  //do something
	}
	
	@Override public void mouseClicked(MouseEvent e) {
       //do something
    }

    /**
     * MouseMotionListener interface
     * Override this method to act on mouse drag events. 
     * @param e Detected MouseEvent
     */ 
    public void mouseDragged(MouseEvent e) {
    }

    /**
     * MouseMotionListener interface
     * Override this method to act on mouse move events. 
     * @param e Detected MouseEvent
     */ 
    public void mouseMoved(MouseEvent e) {
    }

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
