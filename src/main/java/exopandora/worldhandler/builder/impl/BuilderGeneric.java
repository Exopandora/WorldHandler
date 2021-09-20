package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandString;
import exopandora.worldhandler.builder.ICommandBuilder;

public class BuilderGeneric implements ICommandBuilder
{
	private final String command;
	private final String[] arguments;
	
	public BuilderGeneric(String command, String... arguments)
	{
		this.command = command;
		this.arguments = arguments;
	}
	
	@Override
	public String toCommand()
	{
		return new CommandString(this.command, this.arguments).toString();
	}
	
	public String toActualCommand()
	{
		return this.toCommand();
	}
}
