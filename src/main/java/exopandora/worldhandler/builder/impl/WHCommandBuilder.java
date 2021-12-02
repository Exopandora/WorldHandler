package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.BlockPredicateArgument;
import exopandora.worldhandler.builder.argument.BlockStateArgument;

public class WHCommandBuilder extends CommandBuilder
{
	private final BlockStateArgument block = Arguments.blockState();
	private final BlockPredicateArgument filter = Arguments.blockPredicate();
	
	private final CommandNodeLiteral root = CommandNode.literal("wh")
			.then(CommandNode.literal("clone")
					.label(Label.CLONE)
					.then(CommandNode.literal("filtered")
							.then(CommandNode.argument("filter", this.filter)
									.label(Label.CLONE_FILTERED)))
					.then(CommandNode.literal("masked")
							.label(Label.CLONE_MASKED))
					.then(CommandNode.literal("replace")
							.label(Label.CLONE_REPLACE)))
			.then(CommandNode.literal("fill")
					.then(CommandNode.argument("block", this.block)
							.label(Label.FILL)))
			.then(CommandNode.literal("pos1")
					.label(Label.POS1))
			.then(CommandNode.literal("pos2")
					.label(Label.POS2))
			.then(CommandNode.literal("replace")
					.then(CommandNode.argument("block", this.block)
							.then(CommandNode.argument("filter", this.filter)
									.label(Label.REPLACE))));
	
	public BlockStateArgument block()
	{
		return this.block;
	}
	
	public BlockPredicateArgument filter()
	{
		return this.filter;
	}
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
	
	public static enum Label
	{
		CLONE,
		CLONE_FILTERED,
		CLONE_MASKED,
		CLONE_REPLACE,
		FILL,
		POS1,
		POS2,
		REPLACE;
	}
}
