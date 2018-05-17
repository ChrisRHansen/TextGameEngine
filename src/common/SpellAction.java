package common;

import enums.DamageClass;
import enums.SpellEffect;

public class SpellAction 
{
	private SpellEffect m_SpellEffect;
	private DamageClass m_DamageClass;
	
	private float m_fValueLow;
	private float m_fValueHigh;
	
	// Constructors
	
	public SpellAction()
	{
		
	}
	
	// Methods
	
	// Getters
	
	public SpellEffect getSpellEffect()
	{
		return m_SpellEffect;
	}
	
	public DamageClass getDamageClass()
	{
		return m_DamageClass;
	}

	public float getValueLow() 
	{
		return m_fValueLow;
	}
	
	public float getValueHigh() 
	{
		return m_fValueHigh;
	}
	
	// Setters
}
