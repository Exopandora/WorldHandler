package exopandora.worldhandler.format.text;

import exopandora.worldhandler.format.EnumColor;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * XXX To be replaced with ITextComponent
 */
@Deprecated
@OnlyIn(Dist.CLIENT)
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
		this.text = TextFormatting.getTextWithoutFormattingCodes(string);
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
				result = TextFormatting.ITALIC + result;
			}
			
			if(this.underlined)
			{
				result = TextFormatting.UNDERLINE + result;
			}
			
			if(this.strikethrough)
			{
				result = TextFormatting.STRIKETHROUGH + result;
			}
			
			if(this.bold)
			{
				result = TextFormatting.BOLD + result;
			}
			
			if(this.obfuscated)
			{
				result = TextFormatting.OBFUSCATED + result;
			}
			
			if(this.color != null && !this.color.equals(EnumColor.DEFAULT))
			{
				result = this.color + result;
			}
		}
		
		return result;
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
				result += TextFormatting.RESET;
			}
			
			return result;
		}
		
		return null;
	}
	
	public String textFormatter(String string, Integer integer)
	{
		return this.getFormattedString(string);
	}
}
