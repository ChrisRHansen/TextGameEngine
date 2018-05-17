package common;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import views.DrawingRegion;
import views.MainWindow;

public class Engine
{
	public int 	iWindowWidth;
	public int 	iWindowHeight;
	public int 	iBackLayerFontSize;
	public int 	iForeLayerFontSize;
	public int 	iBackPixelWidth;
	public int 	iBackPixelHeight;
	public int 	iForePixelWidth;
	public int 	iForePixelHeight;
	
	public String stTitle; 
	
	private final String SPRITE_PATH = System.getProperty("user.dir") + "\\src\\resources\\sprites\\";
	private static Game	game;

	private PixelCharacter[][] 	backScreen;
	private PixelCharacter[][] 	foreScreen;
	private float[][] 			fDepthBuffer;
	
	private MainWindow 		window;
	private DrawingRegion	drawRegion;
	private Thread 			thread;
	
	private ArrayList<InputAction> alKeysDown;
	
	private enum InputAction {
		MOVE_FWD
		, MOVE_BCK
		, STRAF_LEFT
		, STRAF_RIGHT
		, TURN_LEFT
		, TURN_RIGHT
		, FIRE_PRIMARY
		, INVENTORY
		
		// Actions released
		
		, MOVE_FWD_R
		, MOVE_BCK_R
		, STRAF_LEFT_R
		, STRAF_RIGHT_R
		, TURN_LEFT_R
		, TURN_RIGHT_R
		, FIRE_PRIMARY_R
		, INVENTORY_R
	}
	
	public void CreateEngine(int width, int height, String title, int backLayerFontSize, int foreLayerFontSize)
	{
		iWindowWidth = width;
		iWindowHeight = height;
		iBackLayerFontSize = backLayerFontSize;
		iForeLayerFontSize = foreLayerFontSize;
		stTitle = title;
		
        alKeysDown = new ArrayList<InputAction>();
		
		window = new MainWindow();
		window.CreateWindow(iWindowWidth, iWindowHeight, stTitle, iBackLayerFontSize, this);
		drawRegion = window.GetDrawingRegion();

		// Setup backScreen info
        Font font = new Font("Courier", Font.PLAIN, backLayerFontSize);
        FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
        
        int iFontPixelWidth  = (int)font.getStringBounds(""+(char)0x2588, frc).getWidth();
        int iFontPixelHeight = (int)font.getStringBounds(""+(char)0x2588, frc).getHeight();
        window.SetBackFont(font, iFontPixelWidth, iFontPixelHeight);
		
		int offsetX 			= (int)(drawRegion.getLocationOnScreen().getX() - window.getLocationOnScreen().getX());
		int offsetY 			= (int)(drawRegion.getLocationOnScreen().getY() - window.getLocationOnScreen().getY());
		int windowWidthDiff 	= window.getWidth() - drawRegion.getWidth();
		int windowHeightDiff 	= window.getHeight() - drawRegion.getHeight();
		int iScreenWidth 		= (iWindowWidth - windowWidthDiff) / iFontPixelWidth;
		int iScreenHeight 		= (iWindowHeight - windowHeightDiff) / iFontPixelHeight;
		
		drawRegion.setOffset(offsetX, offsetY);
		backScreen = new PixelCharacter[iScreenWidth][iScreenHeight];
		fDepthBuffer = new float[iScreenWidth][iScreenHeight];
		
		for (int i = 0; i < backScreen.length; i++)
			for (int j = 0; j < backScreen[i].length; j++)
				backScreen[i][j] = new PixelCharacter();
		
		// Setup foreScreen info
		font = new Font("Courier", Font.PLAIN, foreLayerFontSize);
        
        int iFontPixelWidth2  = (int)font.getStringBounds(""+(char)0x2588, frc).getWidth();
        int iFontPixelHeight2 = (int)font.getStringBounds(""+(char)0x2588, frc).getHeight();
        window.SetForeFont(font, iFontPixelWidth2, iFontPixelHeight2);
		
		int iScreenWidth2 	= (iWindowWidth - windowWidthDiff) / iFontPixelWidth2;
		int iScreenHeight2 	= (iWindowHeight - windowHeightDiff) / iFontPixelHeight2;
		
		foreScreen = new PixelCharacter[iScreenWidth2][iScreenHeight2];
		
		for (int i = 0; i < foreScreen.length; i++)
			for (int j = 0; j < foreScreen[i].length; j++)
				foreScreen[i][j] = new PixelCharacter();
		
		game = new Game(this);
		game.setScreenWidth(iScreenWidth);
		game.setScreenHeight(iScreenHeight);
		
        InitInput();
		
		//System.out.println(String.format("Background Screen:\n\t(%d, %d) + (%d, %d): (%d, %d)\nForeground Screen:\n\t(%d, %d) : (%d, %d)", 
		//		iFontPixelWidth, iFontPixelHeight, iScreenWidth, iScreenHeight, offsetX, offsetY,
		//		iFontPixelWidth2, iFontPixelHeight2, iScreenWidth2, iScreenHeight2));
	}
	
	public void StartEngine()
	{
		// Game Loop
		thread = new Thread(game);
		thread.start();
	}
	
	public void StopEngine()
	{
		thread.interrupt();
		window.dispose();
		System.exit(0);
	}
	
	public void UpdateScreen()
	{
		drawRegion.repaint(backScreen, foreScreen);
	}
	
	// Background drawing
	public void DrawBack(int x, int y, char symb, Color color)
	{
		backScreen[x][y].setCharacter(symb);
		backScreen[x][y].setColor(color);
	}
	
	public void DrawBack(int x, int y, PixelCharacter pixChar)
	{
		DrawBack(x, y, pixChar.getCharacter(), pixChar.getColor());
	}
	
	public void DrawBack(float x, float y, PixelCharacter pixChar)
	{
		DrawBack((int)x, (int)y, pixChar.getCharacter(), pixChar.getColor());
	}
	
	public void DrawBack(float x, float y, char symb, Color color)
	{
		DrawBack((int)x, (int)y, symb, color);
	}
	
	public void DrawStringBack(int x, int y, String text, Color color)
	{
		for (int i = 0; i < text.length() && y + i < iWindowWidth; i++)
		{
			backScreen[x + i][y].setCharacter(text.charAt(i));
			backScreen[x + i][y].setColor(color);
		}
	}

	// Foreground drawing
	public void DrawFore(int x, int y, char symb, Color color)
	{
		foreScreen[x][y].setCharacter(symb);
		foreScreen[x][y].setColor(color);
	}
	
	public void DrawFore(int x, int y, PixelCharacter pixChar)
	{
		DrawFore(x, y, pixChar.getCharacter(), pixChar.getColor());
	}
	
	public void DrawFore(float x, float y, PixelCharacter pixChar)
	{
		DrawFore((int)x, (int)y, pixChar.getCharacter(), pixChar.getColor());
	}
	
	public void DrawFore(float x, float y, char symb, Color color)
	{
		DrawFore((int)x, (int)y, symb, color);
	}
	
	public void DrawStringFore(int x, int y, String text, Color color)
	{
		for (int i = 0; i < text.length() && y + i < iWindowWidth; i++)
		{
			foreScreen[x + i][y].setCharacter(text.charAt(i));
			foreScreen[x + i][y].setColor(color);
		}
	}
	
	// Depth Buffer
	
	public void UpdateDeathBuffer(int x, int y, float depth)
	{
		fDepthBuffer[x][y] = depth;
	}
	
	public float GetDepthAt(int x, int y)
	{
		if (x >= 0 && x < fDepthBuffer.length)
			return fDepthBuffer[x][y];
		
		return 0;
	}
	
	public float GetDepthAt(float x, float y)
	{
		if (x >= 0 && x < fDepthBuffer.length)
			return fDepthBuffer[(int)x][(int)y];
		
		return 0;
	}
	
	public void GameUpdate()
	{
		
	}
	
	// Input	
	public boolean IsMoveForward()
	{
		return alKeysDown.contains(InputAction.MOVE_FWD);
	}

	public boolean IsMoveBackward()
	{
		return alKeysDown.contains(InputAction.MOVE_BCK);
	}

	public boolean IsMoveLeft()
	{
		return alKeysDown.contains(InputAction.STRAF_LEFT);
	}

	public boolean IsMoveRight()
	{
		return alKeysDown.contains(InputAction.STRAF_RIGHT);
	}

	public boolean IsTurnLeft()
	{
		return alKeysDown.contains(InputAction.TURN_LEFT);
	}

	public boolean IsTurnRight()
	{
		return alKeysDown.contains(InputAction.TURN_RIGHT);
	}

	public boolean IsFire()
	{
		return alKeysDown.contains(InputAction.FIRE_PRIMARY);
	}

	public boolean IsInventory()
	{
		return alKeysDown.contains(InputAction.INVENTORY);
	}
	
	private void InitInput()
	{
		InputMap im = window.GetInputMap();
		ActionMap am = window.GetActionMap();
		
		// Keys pressed down
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), InputAction.MOVE_FWD);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), InputAction.MOVE_BCK);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0, false),	InputAction.STRAF_LEFT);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0, false),	InputAction.STRAF_RIGHT);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false),	InputAction.TURN_LEFT);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false),	InputAction.TURN_RIGHT);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false),	InputAction.FIRE_PRIMARY);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0, false),	InputAction.INVENTORY);
		
		am.put(InputAction.MOVE_FWD, 		new InputActionHandler(InputAction.MOVE_FWD));
		am.put(InputAction.MOVE_BCK, 		new InputActionHandler(InputAction.MOVE_BCK));
		am.put(InputAction.STRAF_LEFT, 		new InputActionHandler(InputAction.STRAF_LEFT));
		am.put(InputAction.STRAF_RIGHT, 	new InputActionHandler(InputAction.STRAF_RIGHT));
		am.put(InputAction.TURN_LEFT, 		new InputActionHandler(InputAction.TURN_LEFT));
		am.put(InputAction.TURN_RIGHT, 		new InputActionHandler(InputAction.TURN_RIGHT));
		am.put(InputAction.FIRE_PRIMARY, 	new InputActionHandler(InputAction.FIRE_PRIMARY));
		am.put(InputAction.INVENTORY, 		new InputActionHandler(InputAction.INVENTORY));
		
		// Keys released
		
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), InputAction.MOVE_FWD_R);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), InputAction.MOVE_BCK_R);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0, true), InputAction.STRAF_LEFT_R);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0, true), InputAction.STRAF_RIGHT_R);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), InputAction.TURN_LEFT_R);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), InputAction.TURN_RIGHT_R);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), InputAction.FIRE_PRIMARY_R);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0, true), InputAction.INVENTORY_R);
		
		am.put(InputAction.MOVE_FWD_R, 		new InputActionHandler(InputAction.MOVE_FWD_R));
		am.put(InputAction.MOVE_BCK_R, 		new InputActionHandler(InputAction.MOVE_BCK_R));
		am.put(InputAction.STRAF_LEFT_R,	new InputActionHandler(InputAction.STRAF_LEFT_R));
		am.put(InputAction.STRAF_RIGHT_R, 	new InputActionHandler(InputAction.STRAF_RIGHT_R));
		am.put(InputAction.TURN_LEFT_R, 	new InputActionHandler(InputAction.TURN_LEFT_R));
		am.put(InputAction.TURN_RIGHT_R, 	new InputActionHandler(InputAction.TURN_RIGHT_R));
		am.put(InputAction.FIRE_PRIMARY_R, 	new InputActionHandler(InputAction.FIRE_PRIMARY_R));
		am.put(InputAction.INVENTORY_R, 	new InputActionHandler(InputAction.INVENTORY_R));
	}
    
    public Sprite loadSprite(String spriteName)
	{
		FileInputStream streamIn = null;
	    ObjectInputStream objectinputstream = null;
	    Sprite sprite = null;
	    
		try 
		{
			if (!spriteName.endsWith(".ser"))
				spriteName += ".ser";
			
			streamIn = new FileInputStream(SPRITE_PATH + spriteName);
		    objectinputstream = new ObjectInputStream(streamIn);
		    sprite = (Sprite) objectinputstream.readObject();
		} catch (Exception ex) 
		{
			System.out.println("Failed to load sprite \"" + spriteName + "\".");
			ex.printStackTrace();
		} finally 
		{
			if (streamIn != null) 
			{
				try 
				{
					streamIn.close();
				} catch (IOException e) 
				{
					e.printStackTrace();
				}
			}

			if (objectinputstream != null) 
			{
				try 
				{
					objectinputstream.close();
				} catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
		
		return sprite;
	}
    
    public static Game getGame()
    {
    	return game;
    }
	
	private class InputActionHandler extends AbstractAction 
	{
		InputAction action;
		int flags;
		
		/* 0000 0001 : 1 = 
		 * 0000 0010 : 2 = 
		 * 
		 */

		InputActionHandler(InputAction action) 
        {
            this.action = action;
            this.flags = 0;
        }
		
		InputActionHandler(InputAction action, int flags) 
        {
            this.action = action;
            this.flags = flags;
        }

        public void actionPerformed(ActionEvent e) 
        {
        	switch (action)
        	{
        		case MOVE_FWD:
        		case MOVE_BCK:
        		case STRAF_LEFT:
        		case STRAF_RIGHT:
        		case TURN_LEFT:
        		case TURN_RIGHT:
        		case FIRE_PRIMARY:
        		case INVENTORY:
    	        	if (alKeysDown.contains(action))
    	        		return;
    	        	else
    	        	{
    	        		alKeysDown.add(action);
    	        	}
        			break;
        		case MOVE_FWD_R:
        			alKeysDown.remove(InputAction.MOVE_FWD);
        			break;
        		case MOVE_BCK_R:
        			alKeysDown.remove(InputAction.MOVE_BCK);
        			break;
        		case STRAF_LEFT_R:
        			alKeysDown.remove(InputAction.STRAF_LEFT);
        			break;
        		case STRAF_RIGHT_R:
        			alKeysDown.remove(InputAction.STRAF_RIGHT);
        			break;
        		case TURN_LEFT_R:
        			alKeysDown.remove(InputAction.TURN_LEFT);
        			break;
        		case TURN_RIGHT_R:
        			alKeysDown.remove(InputAction.TURN_RIGHT);
        			break;
        		case FIRE_PRIMARY_R:
        			alKeysDown.remove(InputAction.FIRE_PRIMARY);
        			break;
        		case INVENTORY_R:
        			alKeysDown.remove(InputAction.INVENTORY);
        			break;
        	};
        }
    }
}
