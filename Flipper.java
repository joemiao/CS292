//Ezra Bergstein
public class Flipper extends Gizmo{
	public Flipper(String shape, String trigger, String orientation, String action, String name, float reflection, int[] position)
	{
		super(name, orientation, position);
		this.shape = "rectangle"
		this.trigger = "hit";
		this.action = "rotate";
		this.reflection = 0.95
	}
	//placeholder
	public flip(){
		return;
	}

}
