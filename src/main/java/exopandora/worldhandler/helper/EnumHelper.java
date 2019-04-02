package exopandora.worldhandler.helper;

import javax.annotation.Nullable;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
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
