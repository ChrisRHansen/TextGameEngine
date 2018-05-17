package common;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import enums.CreatureType;
import enums.GameObjectType;

public class Game implements Runnable
{	
	private int iScreenWidth;
	private int iScreenHeight;
	private int iFrameRateCap = 60;
	
	private long lPrevTime;
	private long lCurrTime;
	private long fFrameRateDelay = (long)(1000.0f / iFrameRateCap);
	
	private boolean bGamePaused = false;
	
	public Player player;
	
	private Color ceilingColor = Color.DARK_GRAY;
	private Color floorColor = new Color(32, 32, 32);
	
	private Engine engine;
	
	private Sprite fireballSprite;
	private Sprite skeleSprite;
	private Texture fireballTex;
	private Texture skeleTex;
	
	/*private*/public WorldState worldState;
	ArrayList<GUIElement> guiElements;
	Slider healthpool;
	
	public Game(Engine engine)
	{
		this.engine = engine;
		worldState = new WorldState(engine);
		guiElements = new ArrayList<GUIElement>(10);
		
		//Sprite gui = engine.loadSprite("GUI");
		//guiElements.add(new GUIElement(gui, 0.6f, 0.0f, 1.0f, 1.0f));
		
		ArrayList<Sprite> sliderSprites = new ArrayList(10);
		sliderSprites.add(engine.loadSprite("hp_00"));
		sliderSprites.add(engine.loadSprite("hp_10"));
		sliderSprites.add(engine.loadSprite("hp_20"));
		sliderSprites.add(engine.loadSprite("hp_30"));
		sliderSprites.add(engine.loadSprite("hp_40"));
		sliderSprites.add(engine.loadSprite("hp_50"));
		sliderSprites.add(engine.loadSprite("hp_60"));
		sliderSprites.add(engine.loadSprite("hp_70"));
		sliderSprites.add(engine.loadSprite("hp_80"));
		sliderSprites.add(engine.loadSprite("hp_90"));
		sliderSprites.add(engine.loadSprite("hp_full"));
		
		healthpool = new Slider(0f, 10f, 10f, 
				sliderSprites, 0.0f, 0.8f, 0.15f, 1.0f);
		guiElements.add(healthpool);
		
		fireballSprite = engine.loadSprite("fireball");
		//lightPostSprite = engine.loadSprite("light_post");
		
		fireballTex = new Texture(fireballSprite);
		
		skeleSprite = engine.loadSprite("skeleton");
		skeleTex = new Texture(skeleSprite, 0.75f);
		
		Sprite fire1 = engine.loadSprite("fire1");
		Sprite fire2 = engine.loadSprite("fire2");
		Sprite fire3 = engine.loadSprite("fire3");
		Sprite fire4 = engine.loadSprite("fire4");
		Sprite fire5 = engine.loadSprite("fire5");
		
		ArrayList<Frame> frames = new ArrayList<Frame>();
		frames.add(new Frame(fire1, 0.05f));
		frames.add(new Frame(fire2, 0.05f));
		frames.add(new Frame(fire3, 0.05f));
		frames.add(new Frame(fire4, 0.05f));
		frames.add(new Frame(fire5, 0.05f));
		
		AnimatedTexture tx = new AnimatedTexture(frames);
		
		worldState.RegisterGameObject(new Creature(8.0f, 14.0f, -50, 10, CreatureType.UNDEAD, tx));
		worldState.RegisterGameObject(new Creature(8.0f, 15.0f, 0, 10, CreatureType.UNDEAD, tx));
		worldState.RegisterGameObject(new Creature(8.0f, 16.0f, 0, 10, CreatureType.UNDEAD, tx));
		Creature c = new Creature(2.0f, 16.0f, 0, 10, CreatureType.UNDEAD, skeleTex);
		c.m_fMoveSpeed = 2.75f;
		worldState.RegisterGameObject(c);
		worldState.RegisterGameObject(new Creature(8.0f, 18.0f, 0, 10, CreatureType.UNDEAD, skeleTex));
		worldState.RegisterGameObject(new Creature(8.0f, 19.0f, 0, 10, CreatureType.UNDEAD, skeleTex));
		worldState.RegisterGameObject(new Creature(8.0f, 20.0f, 0, 10, CreatureType.UNDEAD, skeleTex));
		
		c.getMovementGenerator().setIsMoving(true);
		player = new Player(8.0f, 23.0f, 100f);
		worldState.RegisterGameObject(player);
		
		lPrevTime = System.currentTimeMillis();
	}
	
	public void run() 
	{
		while (true)
		{
			lCurrTime = System.currentTimeMillis();
			double timeDiffMilliSeconds = lCurrTime - lPrevTime;
			double timeDiffSeconds = timeDiffMilliSeconds / 1000.0;
			lPrevTime = lCurrTime;
			
			HandleInput(timeDiffSeconds);
			UpdateGameObjects(timeDiffSeconds);
			ProcessScreen(timeDiffSeconds);
			
			engine.UpdateScreen();
		}
	}
	
	public void HandleInput(double dDiff)
	{
		if (engine.IsMoveBackward())
		{
			
			player.setPositionX( (float)(player.getPositionX() - Math.sin(player.getAngle()) * player.getMoveSpeed() * dDiff) );
			player.setPositionY( (float)(player.getPositionY() - Math.cos(player.getAngle()) * player.getMoveSpeed() * dDiff) );
			
			if (worldState.MapTileAt(player.getPositionX(), player.getPositionY()).isNotPathable())
			{
				player.setPositionX( (float)(player.getPositionX() + Math.sin(player.getAngle()) * player.getMoveSpeed() * dDiff) );
				player.setPositionY( (float)(player.getPositionY() + Math.cos(player.getAngle()) * player.getMoveSpeed() * dDiff) );
			}
		}
		else if (engine.IsMoveForward())
		{
			player.setPositionX( (float)(player.getPositionX() + Math.sin(player.getAngle()) * player.getMoveSpeed() * dDiff) );
			player.setPositionY( (float)(player.getPositionY() + Math.cos(player.getAngle()) * player.getMoveSpeed() * dDiff) );
			
			if (worldState.MapTileAt(player.getPositionX(), player.getPositionY()).isNotPathable())
			{
				player.setPositionX( (float)(player.getPositionX() - Math.sin(player.getAngle()) * player.getMoveSpeed() * dDiff) );
				player.setPositionY( (float)(player.getPositionY() - Math.cos(player.getAngle()) * player.getMoveSpeed() * dDiff) );
			}
		}
		
		if (engine.IsMoveLeft())
		{
			player.setPositionX( (float)(player.getPositionX() - Math.cos(player.getAngle()) * player.getMoveSpeed() * dDiff) );
			player.setPositionY( (float)(player.getPositionY() + Math.sin(player.getAngle()) * player.getMoveSpeed() * dDiff) );
			
			if (worldState.MapTileAt(player.getPositionX(), player.getPositionY()).isNotPathable())
			{
				player.setPositionX( (float)(player.getPositionX() + Math.cos(player.getAngle()) * player.getMoveSpeed() * dDiff) );
				player.setPositionY( (float)(player.getPositionY() - Math.sin(player.getAngle()) * player.getMoveSpeed() * dDiff) );
			}
		}
		else if (engine.IsMoveRight())
		{
			player.setPositionX( (float)(player.getPositionX() + Math.cos(player.getAngle()) * player.getMoveSpeed() * dDiff) );
			player.setPositionY( (float)(player.getPositionY() - Math.sin(player.getAngle()) * player.getMoveSpeed() * dDiff) );
			
			if (worldState.MapTileAt(player.getPositionX(), player.getPositionY()).isNotPathable())
			{
				player.setPositionX( (float)(player.getPositionX() - Math.cos(player.getAngle()) * player.getMoveSpeed() * dDiff) );
				player.setPositionY( (float)(player.getPositionY() + Math.sin(player.getAngle()) * player.getMoveSpeed() * dDiff) );
			}
		}
		
		if (engine.IsTurnLeft())
		{
			player.setAngle( (float)(player.getAngle() - player.getTurnRate() * dDiff) );
		}
		else if (engine.IsTurnRight())
		{
			player.setAngle( (float)(player.getAngle() + player.getTurnRate() * dDiff) );
		}
		
		if (engine.IsFire())
		{
			GameObject bullet = new Bullet(player.getPositionX(), player.getPositionY(), player.getAngle(), 15.0f, iScreenHeight / 2.0f, fireballTex);
			worldState.RegisterGameObject(bullet);
		}
	}
	
	public void UpdateGameObjects(double dDiff)
	{
		for (Iterator<GameObject> itr = worldState.getWorldObjects().iterator(); itr.hasNext();)
		{
			GameObject obj = itr.next();
			obj.Update(dDiff);
			
			if (obj.isMarkedForDeletion())
				itr.remove();
		}
	}
	
	public void ProcessScreen(double dDiff)
	{
		DrawWorldEnvironment();
		DrawWorldObjects();
		DrawGUIElements();
		
		// Display Stats
        engine.DrawStringFore(0, 0, String.format("X=%3.2f, Y=%3.2f, A=%3.2f, FPS = %4.2f ", player.getPositionX(), player.getPositionY(), player.getAngle(), 1.0f / dDiff), Color.GREEN);
        
        // Cap framerate
		try {
			long sleepFor = fFrameRateDelay - (long)dDiff;
        
			if (sleepFor > 0)
				Thread.sleep(sleepFor);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void DrawWorldEnvironment()
	{
		for (int x = 0; x < iScreenWidth; x++)
		{
			// Calculate projected ray angle into world space
			float rayAngle = (player.getAngle() - player.getFOV() / 2.0f) + ((float)x / (float)iScreenWidth) * player.getFOV();
			
			float stepSize = 0.01f;
			float distanceToWall = 0.0f;
			boolean hitWall = false;
			
			float eyeX = (float)Math.sin(rayAngle); // Unit vector for ray in player space
            float eyeY = (float)Math.cos(rayAngle);
            float sampleX = 0.0f;
            
            Sprite wallSprite = null;
            
            while (!hitWall && distanceToWall < worldState.getSightDepth())
            {
            	distanceToWall += stepSize;

                float testX = player.getPositionX() + eyeX * distanceToWall;
                float testY = player.getPositionY() + eyeY * distanceToWall;

                if (testX < 0 || testX >= worldState.getMapWidth() || testY < 0 || testY >= worldState.getMapHeight())
                {
                    hitWall = true;
                    distanceToWall = worldState.getSightDepth();
                }
                else
                {
                    // Test if we hit a wall block
                    if (worldState.MapTileAt(testX, testY).isNotPathable())
                    {
                        hitWall = true;
                        wallSprite = worldState.MapTileAt(testX, testY).getSprite();
                        
                        // Determine where ray has hit wall
                        float blockMidX = (int)testX + 0.5f;
                        float blockMidY = (int)testY + 0.5f;
                        
                        float testAngle = (float)Math.atan2(testY - blockMidY, testX - blockMidX);
                        
                        if (testAngle >= -Util.PI * 0.25f && testAngle < Util.PI * 0.25f)
                        	sampleX = testY - (int)testY;
                        else if (testAngle >= Util.PI * 0.25f && testAngle < Util.PI * 0.75f)
                        	sampleX = testX - (int)testX;
                        else if (testAngle < -Util.PI * 0.25f && testAngle >= -Util.PI * 0.75f)
                        	sampleX = testX - (int)testX;
                        else if (testAngle >= Util.PI * 0.75f || testAngle < -Util.PI * 0.75f)
                        	sampleX = testY - (int)testY;
                    }
                }
            }
            
            // Calculate distance to ceiling and floor
            int ceiling = (int)((float)(iScreenHeight / 2.0f) - iScreenHeight / ((float)distanceToWall));
            int floor = iScreenHeight - ceiling;

            for (int y = 0; y < iScreenHeight; y++)
            {
                if (y <= ceiling) // Ceiling
                    engine.DrawBack(x, y, ' ', ceilingColor);
                else if (y > ceiling && y <= floor) // Wall
                {
                	if (distanceToWall < worldState.getMaxDrawDistance())
                	{
	                	float sampleY = ((float)y - (float)ceiling) / ((float)floor - ceiling);
	                    engine.DrawBack(x, y, wallSprite.getSymbolSampled(sampleX, sampleY), wallSprite.getColorSampled(sampleX, sampleY));
                	}
                	else // too far
                		engine.DrawBack(x, y, ' ', Color.BLACK);
                }
                else // Ground
                    engine.DrawBack(x, y, (char)0x2588, floorColor);

                // Update depth buffer
                engine.UpdateDeathBuffer(x, y, distanceToWall);
            }
		}
	}
	
	private void DrawWorldObjects()
	{
		// Update & Draw Objects
		// TODO: rework this to not directly modify world objects list
		for (GameObject obj : worldState.getWorldObjects())
		{
			// Can object be seen?
			float vecX = obj.getPositionX() - player.getPositionX();
			float vecY = obj.getPositionY() - player.getPositionY();
			float dist = (float)Math.sqrt(vecX * vecX + vecY * vecY);
			
			// Determine if inside player's FOV
			float eyeX = (float)Math.sin(player.getAngle());
			float eyeY = (float)Math.cos(player.getAngle());
			float objAngle = (float)(Math.atan2(eyeY, eyeX) - Math.atan2(vecY, vecX));
			
			if (objAngle < -Util.PI)
				objAngle += Util.TWO_PI;
			else if (objAngle > Util.PI)
				objAngle -= Util.TWO_PI;
			
			boolean inFOV = Math.abs(objAngle) < player.getFOV() / 2.0f;
			
			if (inFOV && dist >= 0.5f && dist < worldState.getSightDepth())
			{
				float objCeiling = (float)(iScreenHeight / 2.0f) - ((iScreenHeight / dist) * obj.getTexture().getScale());
				float objFloor = iScreenHeight - objCeiling;

				float drawHeight = (((iScreenHeight / 2.0f) + (iScreenHeight / obj.getTexture().getSprite().getHeight())) - obj.getDrawHeight()) / dist;
				objCeiling += drawHeight;
				objFloor += drawHeight;
				
				float objHeight = objFloor - objCeiling;
				float objAspectRatio = (float)obj.getTexture().getSprite().getHeight() / (float)obj.getTexture().getSprite().getWidth();
				float objWidth = objHeight / objAspectRatio;
				
				float middleOfObj = (0.5f * (objAngle / (player.getFOV() / 2.0f)) + 0.5f) * (float)iScreenWidth;
				
				for (float lx = 0; lx < objWidth; lx++)
					for (float ly = 0; ly < objHeight; ly++)
					{
						float sampleX = lx / objWidth;
						float sampleY = ly / objHeight;
						
						int objColumn = (int)(middleOfObj + lx - (objWidth / 2.0f));
						int objRow	  = (int)(objCeiling + ly);
						
						if (objColumn >= 0 && objColumn < iScreenWidth && objRow >= 0 && objRow < iScreenHeight
								&& obj.getTexture().getSprite().getSymbolSampled(sampleX, sampleY) != '\0'
								&& engine.GetDepthAt(objColumn, objRow) >= dist)
						{
							engine.DrawBack(objColumn, objRow, 
									obj.getTexture().getSprite().getSymbolSampled(sampleX, sampleY), 
									obj.getTexture().getSprite().getColorSampled(sampleX, sampleY));
							
							engine.UpdateDeathBuffer(objColumn, objRow, dist);
						}
					}
			}
		}
	}
	
	private void DrawGUIElements()
	{
        /*// Display Map
        for (int nx = 0; nx < worldState.getMapWidth(); nx++)
            for (int ny = 0; ny < worldState.getMapWidth(); ny++)
                engine.DrawFore(nx, ny + 1, worldState.SampleMap(ny * worldState.getMapWidth() + (worldState.getMapWidth() - nx - 1)), Color.YELLOW);
        
        engine.DrawFore(worldState.getMapWidth() - player.getPositionX(), player.getPositionY() + 1, 'P', Color.GREEN);
        */
		healthpool.setValue( player.getHealthPercentage() * 10 );
		healthpool.UpdateSpriteBasedOnValue();
		
		for (GUIElement element : guiElements)
		{
			int startX = (int)(iScreenWidth * element.getStartDrawX());
			int endX   = (int)(iScreenWidth * element.getEndDrawX());
			int startY = (int)(iScreenHeight * element.getStartDrawY());
			int endY   = (int)(iScreenHeight * element.getEndDrawY());
			
			for (int i = startX; i < endX; i++)
			{
				float sampleX = (i - startX) / (float)(endX - startX);
				
				for (int j = startY; j < endY; j++)
				{
					float sampleY = (j - startY) / (float)(endY - startY);
					
					if (element.getSprite().getSymbolSampled(sampleX, sampleY) != '\0')
						engine.DrawBack(i, j, element.getSprite().getSymbolSampled(sampleX, sampleY), 
								element.getSprite().getColorSampled(sampleX, sampleY));
				}
			}
		}
	}

	public int getScreenWidth() 
	{
		return iScreenWidth;
	}

	public void setScreenWidth(int iScreenWidth) 
	{
		this.iScreenWidth = iScreenWidth;
	}

	public int getScreenHeight() 
	{
		return iScreenHeight;
	}

	public void setScreenHeight(int iScreenHeight) 
	{
		this.iScreenHeight = iScreenHeight;
	}
}
