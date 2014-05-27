import java.awt.Color;

//Ezra Bergstein
public class Bumper extends Gizmo {
	
	public Bumper(String shape, int orientation, String name, int[] position, Color color, int width, int height)
	{
		super(shape, orientation, name, position, boundingBox, color, width, height);
		this.trigger = "none";
		this.action = "none";
		this.reflection = 1.0
	}

}
