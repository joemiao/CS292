import java.awt.Color;

//Ezra Bergstein
public class Flipper extends Gizmo{
	public Flipper(int orientation, String name, int[] position, Color color, int width, int height)
	{
		super(name, orientation, position, boundingBox, color, width, height);
		this.shape = "triangle"
		this.trigger = "hit";
		this.action = "rotate";
		this.reflection = 0.95
	}
	//placeholder
	public flip(){
		return;
	}

}
