package exopandora.worldhandler.builder.impl;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.types.ArgumentType;

public class BuilderPlayer extends CommandBuilder
{
	private final String command;
	
	public BuilderPlayer(String command)
	{
		this.command = command;
	}
	
	public void setPlayer(String player)
	{
		this.setNode(0, player);
	}
	
	@Nullable
	public String getPlayer()
	{
		return this.getNodeAsString(0);
	}
	
	@Override
	public String getCommandName()
	{
		return this.command;
	}
	
	@Override
	public final CommandSyntax getSyntax()
	{
		CommandSyntax syntax = new CommandSyntax();
		
		syntax.addRequired("player", ArgumentType.STRING);
		
		return syntax;
	}
}
