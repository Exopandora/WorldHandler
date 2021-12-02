package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.TargetArgument;

public class TargetCommandBuilder extends CommandBuilder
{
	private final TargetArgument targets = Arguments.target();
	private final CommandNodeLiteral root;
	
	public TargetCommandBuilder(String root, Object label)
	{
		this.root = CommandNode.literal(root)
				.then(CommandNode.argument("targets", this.targets)
						.label(label));
	}
	
	public TargetArgument targets()
	{
		return this.targets;
	}
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
}
