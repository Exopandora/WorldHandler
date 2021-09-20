package exopandora.worldhandler.util;

import javax.annotation.Nullable;

public class EnumHelper
{
	@Nullable
	public static <T extends Enum<T>> T valueOf(String name, Class<T> klass)
	{
		try
		{
			return Enum.valueOf(klass, name.toUpperCase());
		}
		catch(Exception e)
		{
			return null;
		}
	}
}
