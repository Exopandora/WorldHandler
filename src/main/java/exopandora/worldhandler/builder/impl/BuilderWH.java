package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.types.ArgumentType;

public class BuilderWH extends CommandBuilder
{
	@Override
	public String getCommandName()
	{
		return "wh";
	}
	
	@Override
	public CommandSyntax getSyntax()
	{
		CommandSyntax syntax = new CommandSyntax();
		
		syntax.addRequired("pos1|pos2|fill|replace", ArgumentType.STRING);
		
		return syntax;
	}
}
