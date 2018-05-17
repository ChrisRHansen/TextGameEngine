package common;

public class Main 
{
	private static Engine gameEngine;
	
	public static void main(String args[])
	{
		gameEngine = new Engine();
		gameEngine.CreateEngine(1600, 1000, "Text Engine", 4, 12);
		gameEngine.StartEngine();
	}
}
