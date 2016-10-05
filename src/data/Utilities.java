package data;

import javax.management.InvalidAttributeValueException;

public class Utilities {
	
	public static int boolToInt(boolean bool)
	{
		int i;
		if(bool)
			i = 1;
		else
			i = 0;
		return i;
	}

	public static boolean intToBool(int i) throws InvalidAttributeValueException
	{
		Boolean bool;
		if (i == 1)
			bool = true;
		else if (i == 0)
			bool = false;
		else
			throw new InvalidAttributeValueException("Tried to Convert " + i +". Can only convert 1 or 0");
		return bool;
	}
}
