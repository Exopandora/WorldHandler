package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.types.ArgumentType;

public class BuilderWorldHandler extends CommandBuilder
{
	@Override
	public String getCommandName()
	{
		return "worldhandler";
	}
	
	@Override
	public final CommandSyntax getSyntax()
	{
		CommandSyntax syntax = new CommandSyntax();
		
		syntax.addRequired("help|display|version", ArgumentType.STRING);
		
		return syntax;
	}
}
