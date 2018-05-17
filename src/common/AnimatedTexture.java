package common;

import java.util.ArrayList;

public class AnimatedTexture extends Texture
{
	private ArrayList<Frame> m_lFrames;
	
	protected float m_fTimer = 0.0f;
	
	protected int m_iIterator = 0;
	
	public AnimatedTexture()
	{
		m_lFrames = new ArrayList<Frame>();
		m_iScale = 1;
	}
	
	public AnimatedTexture(ArrayList<Frame> list)
	{
		m_lFrames = list;
		m_Sprite = m_lFrames.get(0).getSprite();
		m_iScale = m_Sprite.getHeight() / EXPECTED_PIXEL_RES;
	}
	
	public AnimatedTexture(ArrayList<Frame> list, float scale)
	{
		m_lFrames = list;
		m_Sprite = m_lFrames.get(0).getSprite();
		m_iScale = scale;
	}
	
	// Methods
	public void Update(double dDiff)
	{
		if (m_fTimer < dDiff)
		{
			m_Sprite = m_lFrames.get(m_iIterator).getSprite();
			m_fTimer = m_lFrames.get(m_iIterator).getDuration();
			
			if (++m_iIterator == m_lFrames.size())
				m_iIterator = 0;
		}
		else
			m_fTimer -= dDiff;
	}
}
