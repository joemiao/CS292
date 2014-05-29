//Ezra Bergstein

import java.awt.Color;

public abstract class Gizmo {
	private String shape;
	//0 = northwest, left flipper, 1 = northeast, right flipper, 2 = southeast, 3 = southwest, directions that the hypotenuse faces
	//direction that the gizmo is facing
	private int orientation;
	//what triggers an action
	private String trigger;
	//what happens when the trigger is set off
	private String action;
	//name of the Gizmo
	private String name;
	//coefficient of reflection
	private float reflection;
	private int[] position;
	private int[[]] boundingBox;
	private Color color;
	private int width;
	private int height;
	//initialization
	public Gizmo(String shape, String trigger, int orientation, String action, String name, float reflection, int[] position, Color color, int width, int height)
	{
		this.shape = shape
		this.trigger = trigger
		this.action = action
		this.name = name
		this.reflection = reflection
		this.position = position
		this.color = color
		this.width = width
		this.height = height
		//bounding box temp value
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
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
	
}

