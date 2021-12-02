package exopandora.worldhandler.util;

import javax.annotation.Nullable;

public class Util
{
	@Nullable
	public static String serializeBounds(@Nullable Number minBound, @Nullable Number maxBound)
	{
		boolean min = minBound != null;
		boolean max = maxBound != null;
		
		if(min && max && minBound.equals(maxBound))
		{
			return minBound.toString();
		}
		
		if(min || max)
		{
			StringBuilder builder = new StringBuilder();
			
			if(min)
			{
				builder.append(minBound);
			}
			
			builder.append("..");
			
			if(max)
			{
				builder.append(maxBound);
			}
			
			return builder.toString();
		}
		
		return null;
	}
}
