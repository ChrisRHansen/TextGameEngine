package common;

public class Texture 
{
	protected static final float EXPECTED_PIXEL_RES = 32.0f;
	
	protected Sprite m_Sprite;

	protected float m_iScale;
	
	public Texture()
	{
		m_iScale = 1;
	}
	
	public Texture(Sprite sprite)
	{
		m_Sprite = sprite;
		m_iScale = sprite.getHeight() / EXPECTED_PIXEL_RES;
	}
	
	public Texture(Sprite sprite, float scale)
	{
		m_Sprite = sprite;
		m_iScale = scale;
	}
	
	// Methods
	public void Update(double dDiff) {}
	
	// (G)Setters
	public Sprite getSprite()
	{
		return m_Sprite;
	}
	
	public float getScale()
	{
		return m_iScale;
	}
	
	public void setSprite(Sprite sprite)
	{
		m_Sprite = sprite;
	}
}
