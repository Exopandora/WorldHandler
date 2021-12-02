package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;

public class LiteralCommandBuilder extends CommandBuilder
{
	private final CommandNodeLiteral command;
	
	public LiteralCommandBuilder(String command)
	{
		this(command, Label.ROOT);
	}
	
	public LiteralCommandBuilder(String command, Object label)
	{
		this.command = CommandNode.literal(command)
				.label(label);
	}
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.command;
	}
	
	public static enum Label
	{
		ROOT;
	}
}
