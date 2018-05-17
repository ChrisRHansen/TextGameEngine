package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import common.PixelCharacter;

public class DrawingRegion extends JPanel
{
	private PixelCharacter[][] backScreen;
	private int iBackPixelWidth;
	private int iBackPixelHeight;
	private int iOffsetX;
	private int iOffsetY;
	
	private PixelCharacter[][] foreScreen;
	private int iForePixelWidth;
	private int iForePixelHeight;

	private Font backFont;
	private Font foreFont;
			
	public DrawingRegion()
	{
		setOpaque(true);
		setBackground(Color.BLACK);
	}
	
	public void repaint(PixelCharacter[][] bscreen, PixelCharacter[][] fscreen)
	{
		this.backScreen = bscreen;
		this.foreScreen = fscreen;
		super.repaint();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if (backScreen == null || foreScreen == null)
			return;
		
		// Draw background
		g.setFont(backFont);
		
		for (int i = 0; i < backScreen.length; i++)
			for (int j = 0; j < backScreen[i].length; j++)
			{
				g.setColor(backScreen[i][j].getColor());
				g.drawString(""+backScreen[i][j].getCharacter(), iOffsetX + (i * iBackPixelWidth), iOffsetY + (j * iBackPixelHeight));
			}
		
		// Draw foreground
		g.setFont(foreFont);
		
		for (int i = 0; i < foreScreen.length; i++)
			for (int j = 0; j < foreScreen[i].length; j++)
				if (foreScreen[i][j].getCharacter() != '\0')
				{
					g.setColor(foreScreen[i][j].getColor());
					g.drawString(""+foreScreen[i][j].getCharacter(), iOffsetX + (i * iForePixelWidth), iOffsetY + (j * iForePixelHeight));
				}
	}
	
	public void setBackFont(Font font, int width, int height)
	{
		this.backFont = font;
		this.iBackPixelWidth = width;
		this.iBackPixelHeight = height;
	}
	
	public void setForeFont(Font font, int width, int height)
	{
		this.foreFont = font;
		this.iForePixelWidth = width;
		this.iForePixelHeight = height;
	}
	
	public void setOffset(int x, int y)
	{
		iOffsetX = x;
		iOffsetY = y / 2;
	}
}
