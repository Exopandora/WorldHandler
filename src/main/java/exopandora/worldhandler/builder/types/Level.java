package exopandora.worldhandler.builder.types;

import javax.annotation.Nullable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Level
{
	private int level;
	
	public Level()
	{
		this(0);
	}
	
	public Level(int level)
	{
		this.level = level;
	}
	
	public int getLevel()
	{
		return this.level;
	}
	
	public void setLevel(int level)
	{
		this.level = level;
	}
	
	@Nullable
	public static Level valueOf(String value)
	{
		if(value != null)
		{
			if(value.matches("[-]?[0-9]+[Ll]?"))
			{
				return new Level(Integer.valueOf(value.replaceAll("[lL]$", "")));
			}
		}
		
		return null;
	}
	
	@Override
	public String toString()
	{
		return this.level + "L";
	}
}