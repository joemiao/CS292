import java.awt.Color;

//Ezra Bergstein
public class Wall extends Gizmo {
	
	public Wall(String shape, String trigger, String orientation, String action, String name, float reflection, int[] position,Color color)
	{
		super(name, orientation position, boundingBox, color);
		this.shape = "line"
		this.trigger = "hit";
		this.action = "none";
		this.reflection = 1.0
	}

}
