package exopandora.worldhandler.format.text;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class JsonSignLine extends FormattedString
{
	private String color;
	private JsonClickEvent clickEvent;
	
	public JsonSignLine()
	{
		
	}
	
	public JsonSignLine(ColoredString string)
	{
		this.text = super.getPreformattedString(string.getText());
		this.color = string.getColor().getFormat();
		this.bold = string.isBold();
		this.strikethrough = string.isStriked();
		this.underlined = string.isUnderlined();
		this.italic = string.isItalic();
		this.obfuscated = string.isObfuscated();
	}
	
	public JsonClickEvent getClickEvent()
	{
		return this.clickEvent;
	}
	
	public void setClickEvent(JsonClickEvent clickEvent)
	{
		this.clickEvent = clickEvent;
	}
	
	public String getColor()
	{
		return this.color;
	}
	
	public void setColor(String color)
	{
		this.color = color;
	}
}
