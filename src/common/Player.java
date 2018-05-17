package common;

import enums.UnitType;

public class Player extends Unit
{
	private float m_fAngle		= 3.0f;
	private float m_fMoveSpeed 	= 2.5f;
	private float m_fTurnRate 	= 2.2f;
	private float m_fFOV		= Util.PI / 4.0f;
	
	// Constructors
	
	public Player()
	{
		m_UnitType = UnitType.PLAYER;
	}
	
	public Player(float posX, float posY, float health)
	{
		m_UnitType = UnitType.PLAYER;
		m_Texture = new Texture();
		
		m_Position = new MapPosition(posX, posY);
		m_fMaxHealth = health;
		m_fHealth = health;
	}
	
	// Methods
	
	public void Update(double dDiff)
	{
		super.Update(dDiff);
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
	
	public float getAngle()
	{
		return m_fAngle;
	}
	
	public float getTurnRate()
	{
		return m_fTurnRate;
	}
	
	public float getMoveSpeed()
	{
		return m_fMoveSpeed;
	}
	
	public float getFOV()
	{
		return m_fFOV;
	}
	
	// Setters
	
	public void setPositionX(float x)
	{
		m_Position.x = x;
	}
	
	public void setPositionY(float y)
	{
		m_Position.y = y;
	}
	
	public void setAngle(float a)
	{
		m_fAngle = a;
	}
}
