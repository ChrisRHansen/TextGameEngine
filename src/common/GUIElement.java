package common;

public class GUIElement 
{
	protected Sprite m_Sprite;

	protected float m_fStartDrawX;
	protected float m_fStartDrawY;
	protected float m_fEndDrawX;
	protected float m_fEndDrawY;
	
	// Constructors
	
	public GUIElement()
	{
		
	}
	
	public GUIElement(Sprite sprite, float startX, float startY, float endX, float endY)
	{
		m_Sprite = sprite;
		m_fStartDrawX = startX;
		m_fStartDrawY = startY;
		m_fEndDrawX = endX;
		m_fEndDrawY = endY;
	}
	
	// Methods
	
	// Getters
	
	public Sprite getSprite()
	{
		return m_Sprite;
	}

	public float getStartDrawX() 
	{
		return m_fStartDrawX;
	}

	public float getStartDrawY() 
	{
		return m_fStartDrawY;
	}

	public float getEndDrawX() 
	{
		return m_fEndDrawX;
	}

	public float getEndDrawY() 
	{
		return m_fEndDrawY;
	}
	
	// Setters
}
