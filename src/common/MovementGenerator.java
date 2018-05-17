package common;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class MovementGenerator 
{
	private MovGenHeadComparator comparator;
	
	private boolean m_bIsMoving = false;
	
	private float m_fWithinDist = 1f;
	private float m_fStepSize = 0.5f;
	
	// Constructors
	
	public MovementGenerator()
	{
		comparator = new MovGenHeadComparator();
	}
	
	// Methods
	
	public MapPosition GenerateMovementToLocation(MapPosition start, MapPosition goal)
	{
		MapPosition destination = Route_AStar(start, goal);
		
		return destination;
	}
	
	public MapPosition GenerateMovementToLocationWithinDist(MapPosition start, MapPosition goal, float dist)
	{
		m_fWithinDist = dist;
		MapPosition destination = Route_AStar(start, goal);
		
		return destination;
	}
	
	private MapPosition Route_AStar(MapPosition start, MapPosition goal)
	{
		comparator = new MovGenHeadComparator();
		PriorityQueue<MovGenHead> fringe = new PriorityQueue<MovGenHead>(100, comparator);
		ArrayList<MapPosition> visited = new ArrayList<MapPosition>(100);
		
		fringe.add(new MovGenHead(start, 1, GetHeuristicValue(start, goal)));
		
		//////////////////////////////////////////////////////////////////////
		while (!fringe.isEmpty())											//
		{																	//
			MovGenHead popped = fringe.poll();								//
			MapPosition head = popped.head;									//
																			//
			if (IsGoal(head, goal))											//
			{																//
				if (popped.path.size() > 1)									//
					return /*head*/popped.path.get(1);						//
				else														//
				{															//
					m_bIsMoving = false;									//
					return head;											//
				}															//
			}																//
																			//
			//////////////////////////////////////////////////////////////////
			boolean skip = false;											//
			for (MapPosition pos : visited)									//
				if (pos.x == head.x && pos.y == head.y)						//
				{															//
					skip = true;											//
					break;													//
				}															//
																			//
			if (skip)														//
				continue;													//
			//////////////////////////////////////////////////////////////////
			
			visited.add(head);
			
			MapPosition[] neighbors = GetNeighbors(head);
			
			for (MapPosition pos : neighbors)
			{
				if (!ApplyRules(pos))
					continue;
				
				MovGenHead newHead = new MovGenHead(pos, popped.a + 1, GetHeuristicValue(pos, goal)); 
				newHead.UpdatePath(popped.path, head);
				
				fringe.add(newHead);
			}
		}
		
		return null;
	}
	
	private boolean IsGoal(MapPosition test, MapPosition goal)
	{
		if (Math.abs(test.x - goal.x) <= m_fWithinDist && Math.abs(test.y - goal.y) <= m_fWithinDist)
			return true;
		
		return false;
	}
	
	// Return value false = failed rules
	private boolean ApplyRules(MapPosition pos)
	{
		if (Engine.getGame().worldState.MapTileAt(pos.x, pos.y).isNotPathable())
			return false;
		
		return true;
	}
	
	private MapPosition[] GetNeighbors(MapPosition pos)
	{
		MapPosition[] neighbors = new MapPosition[4];
		
		neighbors[0] = new MapPosition(pos.x, pos.y + m_fStepSize);
		neighbors[1] = new MapPosition(pos.x, pos.y - m_fStepSize);
		neighbors[2] = new MapPosition(pos.x + m_fStepSize, pos.y);
		neighbors[3] = new MapPosition(pos.x - m_fStepSize, pos.y);
		
		return neighbors;
	}
	
	private float GetHeuristicValue(MapPosition start, MapPosition end)
	{
		return Util.CalcDistanceF(start, end);
	}
	
	// Getters
	
	public boolean isMoving()
	{
		return m_bIsMoving;
	}
	
	// Getters
	
	public void setIsMoving(boolean isMoving)
	{
		m_bIsMoving = isMoving;
	}
}
