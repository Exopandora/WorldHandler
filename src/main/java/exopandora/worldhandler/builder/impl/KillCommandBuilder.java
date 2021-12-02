package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.TargetArgument;

public class KillCommandBuilder extends CommandBuilder
{
	private final TargetArgument targets = Arguments.target();
	
	private final CommandNodeLiteral root = CommandNode.literal("kill")
			.label(Label.KILL)
			.then(CommandNode.argument("targets", this.targets)
					.label(Label.KILL_TARGETS));
	
	public TargetArgument targets()
	{
		return this.targets;
	}
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
	
	public static enum Label
	{
		KILL,
		KILL_TARGETS;
	}
}