package common;

import java.util.Comparator;

public class MovGenHeadComparator implements Comparator<MovGenHead>
{
	public int compare(MovGenHead obj1, MovGenHead obj2) 
	{
		return (int)((obj1.a + obj1.h) - (obj2.a + obj2.h));
	}
}