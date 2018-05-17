package common;

import java.util.ArrayList;

import enums.GameObjectType;

public class Bullet extends GameObject
{
	private float m_fDirection;
	private float m_fVelocity;
	private float m_fDuration;
	
	private ArrayList<GameObject> m_lHitList;
	
	public Bullet()
	{
		m_GameObjectType = GameObjectType.BULLET;
	}
	
	public Bullet(float x, float y, float dir, float speed, Texture texture)
	{
		m_GameObjectType = GameObjectType.BULLET;
		m_fDrawHeight = 0;
		m_fDuration = 5f;

		m_Position = new MapPosition(x, y);
		m_fDirection = dir;
		m_fVelocity = speed;
		m_Texture = texture;
	}
	
	public Bullet(float x, float y, float dir, float speed, float height, Texture texture)
	{
		m_GameObjectType = GameObjectType.BULLET;
		m_fDuration = 5f;
		m_fCollisionRadius = 0.25f;

		m_Position = new MapPosition(x, y);
		m_fDirection = dir;
		m_fVelocity = speed;
		m_fDrawHeight = height;
		m_Texture = texture;
	}
	
	// Functions
	
	public void Update(double dDiff)
	{
		if (m_fDuration < dDiff)
		{
			m_bMarkedForDeletion = true;
			return;
		}
		else
			m_fDuration -= dDiff;
		
		setPositionX( getPositionX() + (float)(Math.sin(m_fDirection) * m_fVelocity * dDiff) );
		setPositionY( getPositionY() + (float)(Math.cos(m_fDirection) * m_fVelocity * dDiff) );
		
		Engine.getGame().worldState.DetectBulletCollision(this);
	}
}