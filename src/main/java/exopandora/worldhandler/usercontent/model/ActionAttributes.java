package exopandora.worldhandler.usercontent.model;

import com.google.gson.annotations.SerializedName;

public class ActionAttributes
{
	@SerializedName("function")
	private String function;
	
	@SerializedName("command")
	private String command;
	
	@SerializedName("argument")
	private String argument;
	
	@SerializedName("value")
	private String value;
	
	public ActionAttributes(String function, String command, String argument, String value)
	{
		this.function = function;
		this.command = command;
		this.argument = argument;
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
	
	public String getCommand()
	{
		return this.command;
	}
	
	public void setCommand(String command)
	{
		this.command = command;
	}
	
	public String getArgument()
	{
		return this.argument;
	}
	
	public void setArgument(String argument)
	{
		this.argument = argument;
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
