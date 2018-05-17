package common;

import enums.GameObjectType;

public class GameObject 
{
	protected MapPosition m_Position;
	
	protected Texture m_Texture;
	
	protected GameObjectType m_GameObjectType;
	protected float m_fDrawHeight;
	protected float m_fCollisionRadius;
	
	protected boolean m_bMarkedForDeletion = false;
	
	public GameObject()
	{
		m_GameObjectType = GameObjectType.UNDEFINED;
	}
	
	public GameObject(float x, float y, Texture texture)
	{
		m_GameObjectType = GameObjectType.UNDEFINED;
		
		m_Position = new MapPosition(x, y);
		m_Texture = texture;
	}
	
	public GameObject(float x, float y, float height, GameObjectType type, Texture texture)
	{
		m_fCollisionRadius = 0.25f;

		m_Position = new MapPosition(x, y);
		m_fDrawHeight = height;
		m_GameObjectType = type;
		m_Texture = texture;
	}
	
	// Methods
	
	public void Update(double dDiff)
	{
		m_Texture.Update(dDiff);
	}
	
	public boolean DetectCollision(GameObject obj)
	{
		float objX = obj.getPositionX();
		float objY = obj.getPositionY();
		float objRad = obj.getCollisionRadius();
		
		if (m_Position.x + m_fCollisionRadius < objX - objRad
				|| m_Position.x - m_fCollisionRadius > objX + objRad
				|| m_Position.y + m_fCollisionRadius < objY - objRad
				|| m_Position.y - m_fCollisionRadius > objY + objRad)
			return false;
		
		return true;
	}
	
	// Getters
	
	public MapPosition getPosition()
	{
		return m_Position;
	}
	
	public float getPositionX() 
	{
		return m_Position.x;
	}

	public float getPositionY() 
	{
		return m_Position.y;
	}
	
	public float getCollisionRadius()
	{
		return m_fCollisionRadius;
	}
	
	public float getDrawHeight()
	{
		return m_fDrawHeight;
	}
	
	public boolean isMarkedForDeletion()
	{
		return m_bMarkedForDeletion;
	}

	public Texture getTexture() 
	{
		return m_Texture;
	}
	
	public GameObjectType getGameObjectType()
	{
		return m_GameObjectType;
	}
	
	// Setters
	
	public void setPosition(MapPosition newLoc)
	{
		this.m_Position = newLoc;
	}

	public void setPositionX(float positionX) 
	{
		this.m_Position.x = positionX;
	}

	public void setPositionY(float positionY) 
	{
		this.m_Position.y = positionY;
	}
	
	public void markForDeletion()
	{
		this.m_bMarkedForDeletion = true;
	}

	public void setTexture(Texture texture) 
	{
		this.m_Texture = texture;
	}
}
