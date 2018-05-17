package common;

import java.util.ArrayList;

import enums.GameObjectType;

public class WorldState 
{
	private ArrayList<GameObject> m_lAllWorldObjects;
	
	private int iMapWidth 		= 16;
	private int iMapHeight 		= 25;
	
	private float fMaxDrawDistance 	= 25.0f;
	private float fSightDepth		= Math.max(iMapWidth, iMapHeight);
	
	private String[] mapS;
	private MapTile[][] map;
	
	//private Engine engine;
	
	// Constructors
	
	public WorldState(Engine engine)
	{
		m_lAllWorldObjects = new ArrayList<GameObject>();
		
		mapS = new String[]
			 {"################"
			 ,"#.....#..#.....#"
			 ,"#.....#..#.....#"
			 ,"#..............#"
			 ,"#.....#..#.....#"
			 ,"#.....#..#.....#"
			 ,"#######..#######"
			 ,"#.....#..#.....#"
			 ,"#.....#..#.....#"
			 ,"#.....#..#.....#"
			 ,"#..............#"
			 ,"#.....#..#.....#"
			 ,"#.....#..#.....#"
			 ,"#######..#######"
			 ,"#.....#..#.....#"
			 ,"#.....#..#.....#"
			 ,"#.....#..#.....#"
			 ,"#..............#"
			 ,"#.....#..#.....#"
			 ,"#.....#..#.....#"
			 ,"#######..#######"
			 ,"#..............#"
			 ,"#..............#"
			 ,"#..............#"
			 ,"################"};
		
		map = new MapTile[mapS.length][mapS[0].length()];

		Sprite wallSprite = engine.loadSprite("gray_wall");
		Texture wallTex = new Texture(wallSprite);
		
		for (int i = 0; i < mapS.length; i++)
			for (int j = 0; j < mapS[0].length(); j++)
			{
				if (mapS[i].charAt(j) == '#')
				{
					map[i][j] = new MapTile(false, true, wallTex);
				}
				else
					map[i][j] = new MapTile(true, false, wallTex);
			}
	}
	
	// Methods

	public void RegisterGameObject(GameObject obj) 
	{
		m_lAllWorldObjects.add(obj);
	}
	
	public void DetectBulletCollision(Bullet bullet)
	{
		if (MapTileAt(bullet.getPositionX(), bullet.getPositionY()).isNotPathable())
		{
			bullet.markForDeletion();
			return;
		}
		
		for (GameObject obj : m_lAllWorldObjects)
		{
			if (obj != bullet && obj.getGameObjectType() != GameObjectType.BULLET && obj.DetectCollision(bullet))
			{
				obj.markForDeletion();
				bullet.markForDeletion();
				return;
			}
		}
	}
	
	public MapTile MapTileAt(int x, int y)
	{
		return map[y][x];
	}
	
	public MapTile MapTileAt(float x, float y)
	{
		return MapTileAt((int)x, (int)y);
	}
	
	// Getters

	public ArrayList<GameObject> getWorldObjects() 
	{
		return m_lAllWorldObjects;
	}

	public int getMapWidth() 
	{
		return iMapWidth;
	}

	public int getMapHeight() 
	{
		return iMapHeight;
	}

	public float getMaxDrawDistance() 
	{
		return fMaxDrawDistance;
	}

	public float getSightDepth() 
	{
		return fSightDepth;
	}
	
	// Setters

	public void setMaxDrawDistance(float maxDrawDistance) 
	{
		fMaxDrawDistance = maxDrawDistance;
	}

	public void setSightDepth(float sightDepth) 
	{
		fSightDepth = sightDepth;
	}
}
