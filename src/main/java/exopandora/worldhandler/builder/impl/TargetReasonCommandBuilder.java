package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.PrimitiveArgument;
import exopandora.worldhandler.builder.argument.TargetArgument;

public class TargetReasonCommandBuilder<T> extends CommandBuilder
{
	private final TargetArgument targets = Arguments.target();
	private final PrimitiveArgument<String> reason = Arguments.greedyString();
	
	private final CommandNodeLiteral root;
	
	public TargetReasonCommandBuilder(String root, T noReason, T reason)
	{
		this.root = CommandNode.literal(root)
				.then(CommandNode.argument("targets", this.targets)
						.label(noReason)
						.then(CommandNode.argument("reason", this.reason)
								.label(reason)));
	}
	
	public TargetArgument targets()
	{
		return this.targets;
	}
	
	public PrimitiveArgument<String> reason()
	{
		return this.reason;
	}
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
}
