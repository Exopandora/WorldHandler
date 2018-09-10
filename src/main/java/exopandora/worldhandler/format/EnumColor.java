package exopandora.worldhandler.format;

import javax.annotation.Nullable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Deprecated
@SideOnly(Side.CLIENT)
public enum EnumColor
{
	DEFAULT("reset", "r"),
	YELLOW("yellow", "e"),
	GOLD("gold", "6"),
	DARK_RED("dark_red", "4"),
	RED("red", "c"),
	LIGHT_PURPLE("light_purple", "d"),
	DARK_PURPLE("dark_purple", "5"),
	BLUE("blue", "9"),
	DARK_BLUE("dark_blue", "1"),
	DARK_AQUA("dark_aqua", "3"),
	AQUA("aqua", "b"),
	GREEN("green", "a"),
	DARK_GREEN("dark_green", "2"),
	BLACK("black", "0"),
	DARK_GRAY("dark_gray", "8"),
	GRAY("gray", "7"),
	WHITE("white", "f"),
	
	OBFUSCATED("obfuscated", "k"),
	BOLD("bold", "l"),
	STRIKETHROUGH("strikethrough", "m"),
	UNDERLINED("underlined", "n"),
	ITALIC("italic", "o");
	
	private String format;
	private String prefix;
	
	private EnumColor(String format, String prefix)
	{
		this.format = format;
		this.prefix = prefix;
	}
	
	public String getFormat()
	{
		return this.format;
	}
	
	public String getPrefix()
	{
		return this.prefix;
	}
	
	@Override
	public String toString()
	{
		return "\u00A7" + this.prefix;
	}
	
	@Nullable
	public static EnumColor getColorFromId(int id)
	{
		if(id >= 0 && id < EnumColor.values().length)
		{
			return EnumColor.values()[id];
		}
		
		return null;
	}
}
