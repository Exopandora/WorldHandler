package exopandora.worldhandler.usercontent.model;

import com.google.gson.annotations.SerializedName;

public class ActionAttributes
{
	@SerializedName("function")
	private String function;
	
	@SerializedName("command")
	private int command;
	
	@SerializedName("index")
	private int index;
	
	@SerializedName("value")
	private String value;
	
	public ActionAttributes(String function, int command, int index, String value)
	{
		this.function = function;
		this.command = command;
		this.index = index;
		this.value = value;
	}
	
	public String getFunction()
	{
		return this.function;
	}
	
	public void setFunction(String function)
	{
		this.function = function;
	}
	
	public int getCommand()
	{
		return this.command;
	}
	
	public void setCommand(int command)
	{
		this.command = command;
	}
	
	public int getIndex()
	{
		return this.index;
	}
	
	public void setIndex(int index)
	{
		this.index = index;
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
