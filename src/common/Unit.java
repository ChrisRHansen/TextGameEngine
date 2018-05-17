package common;

import enums.DamageClass;
import enums.UnitType;

public class Unit extends GameObject
{
	UnitType m_UnitType = UnitType.UNDEFINED;
	
	protected float m_fMaxHealth = 1f;
	protected float m_fHealth;
	protected float m_fMoveSpeed = 0.5f;
	
	protected boolean m_bIsAlive;
	
	// Constructors
	public Unit()
	{
		
	}
	
	public Unit(float x, float y, Texture texture)
	{
		setPositionX(x);
		setPositionY(y);
		m_Texture = texture;
	}
	
	public Unit(float x, float y, float height, float health, UnitType type, Texture texture)
	{
		setPositionX(x);
		setPositionY(y);
		m_fDrawHeight = height;
		m_Texture = texture;
		m_UnitType = type;
		m_fMaxHealth = health;
		m_fHealth = health;
	}
	
	// Methods
	
	public void Update(double dDiff)
	{
		super.Update(dDiff);
	}
	
	public void ReceiveDamage(float damage, DamageClass dmgC)
	{
		
	}
	
	// (S)Getters
	
	public float getHealth()
	{
		return m_fHealth;
	}
	
	public float getHealthPercentage()
	{
		return m_fHealth / m_fMaxHealth;
	}
	
	public boolean isAlive()
	{
		return m_bIsAlive;
	}
	
	public boolean isDead()
	{
		return !m_bIsAlive;
	}
}
