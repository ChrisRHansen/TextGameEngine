package common;

import enums.CreatureType;
import enums.UnitType;

public class Creature extends Unit
{
	protected MovementGenerator m_MovementGenerator;
	
	protected CreatureType m_CreatureType = CreatureType.UNDEFINED;
	
	protected String m_sName;
	protected String m_sTitle;
	
	
	
	// Constructors
	
	public Creature()
	{
		m_UnitType = UnitType.CREATURE;
	}
	
	public Creature(float x, float y, float height, float health, CreatureType type, Texture texture)
	{
		m_MovementGenerator = new MovementGenerator();
		m_UnitType = UnitType.CREATURE;
		
		setPosition(new MapPosition(x, y));
		m_fDrawHeight = height;
		m_Texture = texture;
		m_CreatureType = type;
		m_fHealth = health;
	}
	
	public Creature(MapPosition loc, float height, float health, CreatureType type, Texture texture)
	{
		m_MovementGenerator = new MovementGenerator();
		m_UnitType = UnitType.CREATURE;

		setPosition(loc);
		m_fDrawHeight = height;
		m_Texture = texture;
		m_CreatureType = type;
		m_fHealth = health;
	}
	
	// Methods
	
	public void Update(double dDiff)
	{
		super.Update(dDiff);
		
		// Update movement
		if (m_MovementGenerator.isMoving())
		{
			//MapPosition moveto = m_MovementGenerator.GenerateMovementToLocation(getPosition(), Engine.getGame().player.getPosition());
			MapPosition moveto = m_MovementGenerator.GenerateMovementToLocationWithinDist(getPosition(), Engine.getGame().player.getPosition(), 1f);
			
			if (moveto != null)
			{
				Vector2f vec = Util.GetVector2F(getPosition(), moveto);
				m_Position.x += vec.x * m_fMoveSpeed * dDiff;
				m_Position.y += vec.y * m_fMoveSpeed * dDiff;
			}
		}
	}
	
	// Getters
	
	public MovementGenerator getMovementGenerator()
	{
		return m_MovementGenerator;
	}
	
	public String getName()
	{
		return m_sName;
	}
	
	public String getTitle()
	{
		return m_sTitle;
	}
	
	// Setters
	
	public void setMovementGenerator(MovementGenerator movementGenerator)
	{
		m_MovementGenerator = movementGenerator;
	}
	
	public void setName(String name)
	{
		m_sName = name;
	}
	
	public void setTitle(String title)
	{
		m_sTitle = title;
	}
}
