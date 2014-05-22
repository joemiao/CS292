//Ezra Bergstein

import java.awt.Color;

public abstract class Gizmo {
	private String shape;
	//what triggers an action
	private String orientation;
	//direction that the gizmo is facing
	private String trigger;
	//what happens when the trigger is set off
	private String action;
	private String name;
	//coefficient of reflection
	private float reflection;
	private int[] position;
	private int[[]] boundingBox;
	private Color color;
	//initialization
	public Gizmo(String shape, String trigger, String orientation, String action, String name, float reflection, int[] position, Color color)
	{
		this.shape = shape
		this.trigger = trigger
		this.action = action
		this.name = name
		this.reflection = reflection
		this.position = position
		this.color = color
		this.boundingBox = [[position[0]-1, position[1]+1], [position[0]+1, position[1]+1], [position[0]+1, position[1]-1], position[0]-1, position[1]-1];
	}

	public String getShape()
	{
		return shape;
	}
	public String getTrigger()
	{
		return trigger;
	}
	public String getOrientation()
	{
		return orientation;
	}
	public String getAction()
	{
		return action;
	}
	public String getName()
	{
		return name;
	}
	public float getCoefficient()
	{
		return reflection;
	}
	public int[] getPosition()
	{
		return position;
	}
	public int[[]] getBoundingBox()
	{
		return boundingBox;
	}
}

