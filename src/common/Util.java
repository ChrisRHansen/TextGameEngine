package common;

public class Util 
{
	public static final float PI = 3.14159f;
	public static final float TWO_PI = PI * 2.0f;
	public static final float RAD_TO_DEG = 180 / PI;
	public static final float DEG_TO_RAD = PI / 180;
	
	// Calculations
	
	public static double CalcDistanceD(MapPosition a, MapPosition b)
	{
		return Math.sqrt(Math.pow((b.x - a.x), 2) + Math.pow((b.y - a.y), 2));
	}
	
	public static float CalcDistanceF(MapPosition a, MapPosition b)
	{
		return (float)CalcDistanceD(a, b);
	}
	
	public static Vector2f GetVector2F(MapPosition a, MapPosition b)
	{
		return new Vector2f(b.x - a.x, b.y - a.y);
	}
	
	public static float GetDirectionDegF(MapPosition a, MapPosition b)
	{
		return (float)GetDirectionDeg(a, b);
	}
	
	public static float GetDirectionRadF(MapPosition a, MapPosition b)
	{
		return (float)GetDirectionRad(a, b);
	}
	
	public static double GetDirectionDeg(MapPosition a, MapPosition b)
	{
		if (b.x == a.x && b.y == a.y)
			return 0;
		
		return Math.atan2(b.x - a.x, b.y - a.y) * RAD_TO_DEG;
	}
	
	public static double GetDirectionRad(MapPosition a, MapPosition b)
	{
		if (b.x == a.x && b.y == a.y)
			return 0;
		
		return Math.atan2(b.x - a.x, b.y - a.y);
	}
}
