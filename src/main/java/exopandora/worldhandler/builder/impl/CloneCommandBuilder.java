package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.BlockPosArgument;
import exopandora.worldhandler.builder.argument.BlockPredicateArgument;

public class CloneCommandBuilder extends CommandBuilder
{
	private final BlockPosArgument begin = Arguments.blockPos();
	private final BlockPosArgument end = Arguments.blockPos();
	private final BlockPosArgument destination = Arguments.blockPos();
	private final BlockPredicateArgument filter = Arguments.blockPredicate();
	
	private final CommandNodeLiteral root = CommandNode.literal("clone")
			.then(CommandNode.argument("begin", this.begin)
					.then(CommandNode.argument("end", this.end)
							.then(CommandNode.argument("destination", this.destination)
									.label(Label.CLONE)
									.then(CommandNode.literal("replace")
											.label(Label.REPLACE)
											.then(CommandNode.literal("force")
													.label(Label.REPLACE_FORCE))
											.then(CommandNode.literal("move")
													.label(Label.REPLACE_MOVE))
											.then(CommandNode.literal("normal")
													.label(Label.REPLACE_NORMAL)))
									.then(CommandNode.literal("masked")
											.label(Label.MASKED)
											.then(CommandNode.literal("force")
													.label(Label.MASKED_FORCE))
											.then(CommandNode.literal("move")
													.label(Label.MASKED_MOVE))
											.then(CommandNode.literal("normal")
													.label(Label.MASKED_NORMAL)))
									.then(CommandNode.literal("filtered")
											.then(CommandNode.argument("filter", this.filter)
													.label(Label.FILTERED)
													.then(CommandNode.literal("force")
															.label(Label.FILTERED_FORCE))
													.then(CommandNode.literal("move")
															.label(Label.FILTERED_MOVE))
													.then(CommandNode.literal("normal")
															.label(Label.FILTERED_NORMAL)))))));
	
	public BlockPosArgument begin()
	{
		return this.begin;
	}
	
	public BlockPosArgument end()
	{
		return this.end;
	}
	
	public BlockPosArgument destination()
	{
		return this.destination;
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
		CLONE,
		REPLACE,
		REPLACE_FORCE,
		REPLACE_MOVE,
		REPLACE_NORMAL,
		MASKED,
		MASKED_FORCE,
		MASKED_MOVE,
		MASKED_NORMAL,
		FILTERED,
		FILTERED_FORCE,
		FILTERED_MOVE,
		FILTERED_NORMAL;
	}
}
