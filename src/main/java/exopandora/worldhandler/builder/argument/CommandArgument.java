package exopandora.worldhandler.builder.argument;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.ICommandBuilder;

public class CommandArgument implements IArgument
{
	private ICommandBuilder command;
	private Object label;
	
	public void set(ICommandBuilder command, Object label)
	{
		this.command = command;
		this.label = label;
	}
	
	public void set(Object label)
	{
		this.label = label;
	}
	
	@Nullable
	public ICommandBuilder getCommand()
	{
		return this.command;
	}
	
	@Nullable
	public Object getLabel()
	{
		return this.label;
	}
	
	@Override
	@Nullable
	public String serialize()
	{
		if(this.command == null)
		{
			return null;
		}
		
		return this.command.toCommand(this.label, false).substring(1);
	}
	
	@Override
	public boolean isDefault()
	{
		return false;
	}
}
