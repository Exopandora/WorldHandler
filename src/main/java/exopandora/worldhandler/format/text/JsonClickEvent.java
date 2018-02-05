package exopandora.worldhandler.format.text;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class JsonClickEvent
{
	private String action;
	private String value;
	
	public JsonClickEvent()
	{
		this(null, null);
	}
	
	public JsonClickEvent(String action, String value)
	{
		this.action = action;
		this.value = value;
	}
	
	public String getAction()
	{
		return this.action;
	}
	
	public void setAction(String action)
	{
		this.action = action;
	}
	
	public String getValue()
	{
		return this.value;
	}
	
	public void setValue(String value)
	{
		this.value = value;
	}
}
