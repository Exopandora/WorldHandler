package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.BlockPosArgument;
import exopandora.worldhandler.builder.argument.BlockPredicateArgument;
import exopandora.worldhandler.builder.argument.BlockStateArgument;

public class FillCommandBuilder extends CommandBuilder
{
	private final BlockPosArgument from = Arguments.blockPos();
	private final BlockPosArgument to = Arguments.blockPos();
	private final BlockStateArgument block = Arguments.blockState();
	private final BlockPredicateArgument filter = Arguments.blockPredicate();
	
	private final CommandNodeLiteral root = CommandNode.literal("fill")
			.then(CommandNode.argument("from", this.from)
					.then(CommandNode.argument("to", this.to)
								.then(CommandNode.argument("block", this.block)
										.label(Label.FILL)
										.then(CommandNode.literal("destroy")
												.label(Label.DESTROY))
										.then(CommandNode.literal("hollow")
												.label(Label.HOLLOW))
										.then(CommandNode.literal("keep")
												.label(Label.KEEP))
										.then(CommandNode.literal("outline")
												.label(Label.OUTLINE))
										.then(CommandNode.literal("replace")
												.then(CommandNode.argument("filter", this.filter)
														.label(Label.REPLACE))))));
	
	public BlockPosArgument from()
	{
		return this.from;
	}
	
	public BlockPosArgument to()
	{
		return this.to;
	}
	
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
	
	public enum Label
	{
		FILL,
		DESTROY,
		HOLLOW,
		KEEP,
		OUTLINE,
		REPLACE;
	}
}
