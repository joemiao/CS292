import java.awt.Color;

//Ezra Bergstein
public class Wall extends Gizmo {
	
	public Wall(String shape, String trigger, int orientation, String action, String name, float reflection, int[] position, Color color, int width, int height)
	{
		super(name, orientation position, boundingBox, color, width, height);
		this.shape = "line"
		this.trigger = "hit";
		this.action = "none";
		this.reflection = 1.0
	}

}
