package common;

public class Frame 
{
	private float m_fDuration;
	
	private Sprite m_Sprite;
	
	public Frame()
	{
		
	}
	
	public Frame(Sprite sprite, float duration)
	{
		m_Sprite = sprite;
		m_fDuration = duration;
	}
	
	// Methods

	// (S)Getters
	public Sprite getSprite() 
	{
		return m_Sprite;
	}

	public void setSprite(Sprite sprite) 
	{
		this.m_Sprite = sprite;
	}

	public float getDuration() 
	{
		return m_fDuration;
	}

	public void setDuration(float duration) 
	{
		this.m_fDuration = duration;
	}
	
	
}
