package common;

public class MapTile 
{
	private boolean m_bPathable;
	private boolean m_bBlocksLOS;
	private Texture m_Texture;
	
	// Constructors
	
	public MapTile()
	{
		
	}
	
	public MapTile(boolean pathable, boolean blocksLOS, Texture texture)
	{
		m_bPathable = pathable;
		m_bBlocksLOS = blocksLOS;
		m_Texture = texture;
	}
	
	// Methods
	
	// Getters
	
	public Texture getTexture()
	{
		return m_Texture;
	}
	
	public Sprite getSprite()
	{
		return m_Texture.getSprite();
	}
	
	public boolean isPathable()
	{
		return m_bPathable;
	}
	
	public boolean isNotPathable()
	{
		return !m_bPathable;
	}
	
	public boolean isLOSBlocker()
	{
		return m_bBlocksLOS;
	}
	
	// Setters
}
