package common;

import java.util.ArrayList;

public class MovGenHead
{
	public MapPosition head;
	
	public ArrayList<MapPosition> path;
	
	public float a;
	public float h;
	
	public MovGenHead(MapPosition headLocation, float a, float h)
	{
		this.head = headLocation;
		this.a = a;
		this.h = h;
		path = new ArrayList<MapPosition>(10);
	}
	
	// Methods
	
	public void UpdatePath(ArrayList<MapPosition> otherPath, MapPosition oldHead)
	{
		for (MapPosition pos : otherPath)
			path.add(pos);
		path.add(oldHead);
	}
}