package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.types.ArgumentType;

public class BuilderTime extends CommandBuilder
{
	public BuilderTime()
	{
		super();
	}
	
	public BuilderTime(EnumMode mode)
	{
		this.setMode(mode);
	}
	
	public BuilderTime(EnumMode mode, int value)
	{
		this(mode);
		this.setValue(value);
	}
	
	public void setMode(EnumMode mode)
	{
		this.setNode(0, mode.toString());
	}
	
	public void setValue(int value)
	{
		this.setNode(1, value);
	}
	
	@Override
	public String getCommandName()
	{
		return "time";
	}
	
	@Override
	public final CommandSyntax getSyntax()
	{
		CommandSyntax syntax = new CommandSyntax();
		
		syntax.addRequired("set|add|query", ArgumentType.STRING);
		syntax.addOptional("value", ArgumentType.INT);
		
		return syntax;
	}
	
	public static enum EnumMode
	{
		ADD,
		SET,
		QUERY;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
}
