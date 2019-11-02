package exopandora.worldhandler.usercontent.model;

import com.google.gson.annotations.SerializedName;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class JsonText
{
	@SerializedName("text")
	private String text;
	
	@SerializedName("x")
	private int x;
	
	@SerializedName("y")
	private int y;
	
	@SerializedName("color")
	private int color;
	
	public JsonText(String text, int x, int y, int color)
	{
		this.text = text;
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public String getText()
	{
		return this.text;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public int getColor()
	{
		return this.color;
	}
	
	public void setColor(int color)
	{
		this.color = color;
	}
}
