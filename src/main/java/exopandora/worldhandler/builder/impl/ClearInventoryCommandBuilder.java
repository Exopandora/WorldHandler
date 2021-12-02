package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.ItemPredicateArgument;
import exopandora.worldhandler.builder.argument.PrimitiveArgument;
import exopandora.worldhandler.builder.argument.TargetArgument;

public class ClearInventoryCommandBuilder extends CommandBuilder
{
	private final TargetArgument targets = Arguments.target();
	private final ItemPredicateArgument item = Arguments.itemPredicate();
	private final PrimitiveArgument<Integer> maxCount = Arguments.intArg();
	
	private final CommandNodeLiteral root = CommandNode.literal("clear")
			.label(Label.CLEAR)
			.then(CommandNode.argument("targets", this.targets)
					.label(Label.CLEAR_TARGETS)
					.then(CommandNode.argument("item", this.item)
							.label(Label.CLEAR_TARGETS_ITEM)
							.then(CommandNode.argument("maxCount", this.maxCount)
									.label(Label.CLEAR_TARGETS_IMTE_MAXCOUNT))));
	
	public TargetArgument targets()
	{
		return this.targets;
	}
	
	public ItemPredicateArgument item()
	{
		return this.item;
	}
	
	public PrimitiveArgument<Integer> maxCount()
	{
		return this.maxCount;
	}
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
	
	public static enum Label
	{
		CLEAR,
		CLEAR_TARGETS,
		CLEAR_TARGETS_ITEM,
		CLEAR_TARGETS_IMTE_MAXCOUNT;
	}
}
