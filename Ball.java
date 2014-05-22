//Ezra Bergstein
public class Ball {
	private String shape;
	private String name;
	private int[] position;
	private int[] vector;
	private int[[]] boundingBox;
	
	public Ball(String name, int[] position, int[] vector)
	{
		this.shape = "circle";
		this.name = name;
		this.position = position;
		this.vector = vector;
		this.boundingBox = [[position[0]-1, position[1]+1], [position[0]+1, position[1]+1], [position[0]+1, position[1]-1], position[0]-1, position[1]-1];
	}
	
	public String getShape()
	{
		return shape;
	}

	public String getName()
	{
		return name;
	}
	public int[] getPosition()
	{
		return position;
	}
	public updatePosition(int[] position)
	{
		this.position = position;
	}
	public int[] getVector()
	{
		return vector;
	}
	public updateVector(int[] vector)
	{
		this.vector = vector;
	}
	public int[[]] getBoundingBox()
	{
		return boundingBox;
	}
}
