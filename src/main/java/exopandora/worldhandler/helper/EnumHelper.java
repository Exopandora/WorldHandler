package exopandora.worldhandler.helper;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EnumHelper
{
	public static <T extends Enum<T>> T valueOf(Class<T> klass, String name)
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
