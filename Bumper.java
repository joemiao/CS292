//Ezra Bergstein
public class Bumper extends Gizmo {
	
	public Bumper(String shape, String trigger, String orientation, String action, String name, float reflection, int[] position)
	{
		super(shape, orientation, name, position);
		this.trigger = "none";
		this.action = "none";
		this.reflection = 1.0
	}

}
