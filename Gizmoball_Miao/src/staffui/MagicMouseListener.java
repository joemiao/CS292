package staffui;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MagicMouseListener 
  implements MouseListener {
	
  public void mousePressed(MouseEvent e) {
	eventOutput("Mouse pressed (# of clicks: "
	            + e.getClickCount() + ")", e);
  }
	     
  public void mouseReleased(MouseEvent e) {
	eventOutput("Mouse released (# of clicks: "
	            + e.getClickCount() + ")", e);
  }
	     
  public void mouseEntered(MouseEvent e) {
	eventOutput("Mouse entered", e);
  }
	     
  public void mouseExited(MouseEvent e) {
	eventOutput("Mouse exited", e);
  }
	     
  public void mouseClicked(MouseEvent e) {
	eventOutput("Mouse clicked (# of clicks: "
	             + e.getClickCount() + ")", e);
  }
  
  public void 
}
