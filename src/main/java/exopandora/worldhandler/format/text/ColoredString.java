package exopandora.worldhandler.format.text;

import com.mojang.realmsclient.gui.ChatFormatting;

import exopandora.worldhandler.format.EnumColor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ColoredString extends FormattedString
{
	private EnumColor color = EnumColor.DEFAULT;
	private static final String EMPTY_STRING = "(\u00A7[a-f0-9k-or]?)*";
	
	public ColoredString(String string)
	{
		this.text = string;
	}
	
	public ColoredString()
	{
		this("");
	}
	
	public void setText(String string)
	{
		this.text = ChatFormatting.stripFormatting(string);
	}
	
	public EnumColor getColor()
	{
		return this.color;
	}
	
	public void setColor(EnumColor color)
	{
		this.color = color;
	}
	
	public void setColor(int color)
	{
		this.color = EnumColor.getColorFromId(color);
	}
	
	public boolean isSpecial()
	{
		if(this.text != null && !this.text.isEmpty())
		{
			return this.toString().contains("\u00A7");
		}
		
		return false;
	}
	
	private String getFormattedString(String string)
	{
		String result = string;
		
		if(result != null)
		{
			if(this.italic)
			{
				result = ChatFormatting.ITALIC + result;
			}
			
			if(this.underlined)
			{
				result = ChatFormatting.UNDERLINE + result;
			}
			
			if(this.strikethrough)
			{
				result = ChatFormatting.STRIKETHROUGH + result;
			}
			
			if(this.bold)
			{
				result = ChatFormatting.BOLD + result;
			}
			
			if(this.obfuscated)
			{
				result = ChatFormatting.OBFUSCATED + result;
			}
			
			if(this.color != null && !this.color.equals(EnumColor.DEFAULT))
			{
				result = this.color + result;
			}
		}
		
		return result;
	}
	
	public String getTextFieldString()
	{
		if(this.text != null)
		{
			return this.getFormattedString(super.getPreformattedString(this.text));
		}
		
		return null;
	}
	
	@Override
	public String toString()
	{
		if(this.text != null)
		{
			String result = super.getPreformattedString(this.text);
			
			if(!result.matches(EMPTY_STRING))
			{
				result = this.getFormattedString(result);
			}
			
			if(result.contains("\u00A7"))
			{
				result += ChatFormatting.RESET;
			}
			
			return result;
		}
		
		return null;
	}
}
