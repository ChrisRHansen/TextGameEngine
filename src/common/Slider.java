package common;

import java.util.ArrayList;

public class Slider extends GUIElement 
{
	private float m_fLow;
	private float m_fHigh;
	private float m_fValue;
	
	private ArrayList<Sprite> m_Sprites;
	
	// Constructors
	
	public Slider()
	{
		
	}
	
	public Slider(float low, float high, float init, ArrayList<Sprite> sprites, 
			float startX, float startY, float endX, float endY)
	{
		m_fLow = low;
		m_fHigh = high;
		m_fValue = init;
		
		m_Sprites = sprites;
		
		m_fStartDrawX = startX;
		m_fStartDrawY = startY;
		m_fEndDrawX = endX;
		m_fEndDrawY = endY;
		
		UpdateSpriteBasedOnValue();
	}
	
	// Methods
	
	public void UpdateSpriteBasedOnValue()
	{
		m_Sprite = m_Sprites.get((int)m_fValue);
	}
	
	// Getters
	
	
	
	// Setters
	
	public void setLow(float low)
	{
		m_fLow = low;
		
		setValue(m_fValue);
	}
	
	public void setHigh(float high)
	{
		m_fHigh = high;
		
		setValue(m_fValue);
	}
	
	public void setValue(float val)
	{
		m_fValue = Math.min(m_fHigh, Math.max(m_fLow, val));
	}
}
