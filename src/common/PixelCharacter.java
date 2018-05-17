package common;

import java.awt.Color;

public class PixelCharacter 
{
	private char character;
	private Color color;
	
	public PixelCharacter()
	{
		this.character = ' ';
		this.color = Color.BLACK;
	}
	
	public PixelCharacter(char character, Color color)
	{
		this.character = character;
		this.color = color;
	}
	
	public char getCharacter() 
	{
		return character;
	}
	
	public void setCharacter(char character) 
	{
		this.character = character;
	}
	
	public Color getColor() 
	{
		return color;
	}
	
	public void setColor(Color color) 
	{
		this.color = color;
	}
}
