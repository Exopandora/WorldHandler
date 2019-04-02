package exopandora.worldhandler.format.text;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class FormattedString
{
	protected String text;
	
	protected boolean underlined;
	protected boolean bold;
	protected boolean italic;
	protected boolean strikethrough;
	protected boolean obfuscated;
	
	public String getText()
	{
		return this.text;
	}
	
	public void setText(String string)
	{
		this.text = string;
	}
	
	public boolean isUnderlined()
	{
		return this.underlined;
	}
	
	public void setUnderlined(boolean underlined)
	{
		this.underlined = underlined;
	}
	
	public boolean isBold()
	{
		return this.bold;
	}
	
	public void setBold(boolean bold)
	{
		this.bold = bold;
	}
	
	public boolean isItalic()
	{
		return this.italic;
	}
	
	public void setItalic(boolean italic)
	{
		this.italic = italic;
	}
	
	public boolean isStriked()
	{
		return this.strikethrough;
	}
	
	public void setStriked(boolean striked)
	{
		this.strikethrough = striked;
	}
	
	public boolean isObfuscated()
	{
		return this.obfuscated;
	}
	
	public void setObfuscated(boolean obfuscated)
	{
		this.obfuscated = obfuscated;
	}
	
	public static String getPreformattedString(String string)
	{
		if(string != null)
		{
			return string.replaceAll("\u0026", "\u00A7").replaceAll("\u00A7\u00A7", "\u0026");
		}
		
		return null;
	}
}
