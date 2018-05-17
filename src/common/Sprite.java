package common;

import java.awt.Color;
import java.io.Serializable;

public class Sprite implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int m_iWidth;
	private int m_iHeight;
	
	private char[][]  m_symbolGrid;
	private Color[][] m_colorGrid;
	
	public Sprite(int width, int height)
	{
		m_iWidth  = width;
		m_iHeight = height;
		
		m_symbolGrid = new char[m_iWidth][m_iHeight];
		m_colorGrid  = new Color[m_iWidth][m_iHeight];
		
		for (int i = 0; i < m_iWidth; i++)
			for (int j = 0; j < m_iHeight; j++)
			{
				m_symbolGrid[i][j] = '\0';
				m_colorGrid[i][j]  = Color.BLACK;
			}
	}
	
	// Methods
	
	// (S)Getters
	
	public int getWidth()
	{
		return m_iWidth;
	}
	
	public int getHeight()
	{
		return m_iHeight;
	}
	
	public void setSymbol(int x, int y, char symb)
	{
		if (x < 0 || x >= m_iWidth || y < 0 || y >= m_iHeight)
			return;
		
		m_symbolGrid[x][y] = symb;
	}
	
	public void setColor(int x, int y, Color color)
	{
		if (x < 0 || x >= m_iWidth || y < 0 || y >= m_iHeight)
			return;
		
		m_colorGrid[x][y] = color;
	}
	
	public void setSymbol(float x, float y, char symb)
	{
		if (x < 0 || x >= 1 || y < 0 || y >= 1)
			return;
		
		m_symbolGrid[(int)x * m_iWidth][(int)y * m_iHeight] = symb;
	}
	
	public void setColor(float x, float y, Color color)
	{
		if (x < 0 || x >= 1 || y < 0 || y >= 1)
			return;
		
		m_colorGrid[(int)x * m_iWidth][(int)y * m_iHeight] = color;
	}
	
	public char getSymbol(int x, int y)
	{
		if (x < 0 || x >= m_iWidth || y < 0 || y >= m_iHeight)
			return ' ';
	
		return m_symbolGrid[x][y];
	}
	
	public Color getColor(int x, int y)
	{
		if (x < 0 || x >= m_iWidth || y < 0 || y >= m_iHeight)
			return Color.BLACK;
	
		return m_colorGrid[x][y];
	}
	
	public char getSymbolSampled(float x, float y)
	{
		int sx = (int)(x * m_iWidth);
		int sy = (int)(y * m_iHeight);
		
		if (sx < 0 || sx >= m_iWidth || sy < 0 || sy >= m_iHeight)
			return ' ';
	
		return m_symbolGrid[sx][sy];
	}
	
	public Color getColorSampled(float x, float y)
	{
		int sx = (int)(x * m_iWidth);
		int sy = (int)(y * m_iHeight);
		
		if (sx < 0 || sx >= m_iWidth || sy < 0 || sy >= m_iHeight)
			return Color.BLACK;
	
		return m_colorGrid[sx][sy];
	}
}
