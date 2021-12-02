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
					.label(Label.VERSION));
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
	
	public static enum Label
	{
		HELP,
		DISPLAY,
		VERSION;
	}
}
