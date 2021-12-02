package exopandora.worldhandler.util;

import java.util.function.Function;

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
	
	@Nullable
	public static <T extends Enum<T>> T find(String key, T[] values, Function<T, String> mapper)
	{
		for(T value : values)
		{
			if(mapper.apply(value).equals(key))
			{
				return value;
			}
		}
		
		return null;
	}
}
