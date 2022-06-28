package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;

public class WorldHandlerCommandBuilder extends CommandBuilder
{
	private final CommandNodeLiteral root = CommandNode.literal("worldhandler")
			.then(CommandNode.literal("help")
					.label(Label.HELP))
			.then(CommandNode.literal("display")
					.label(Label.DISPLAY))
			.then(CommandNode.literal("version")
					.label(Label.VERSION))
			.then(CommandNode.literal("allow_commands")
					.label(Label.ALLOW_COMMANDS));
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
	
	public static enum Label
	{
		HELP,
		DISPLAY,
		VERSION,
		ALLOW_COMMANDS;
	}
}
