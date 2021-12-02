package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.ItemArgument;
import exopandora.worldhandler.builder.argument.PrimitiveArgument;
import exopandora.worldhandler.builder.argument.TargetArgument;

public class GiveCommandBuilder extends CommandBuilder
{
	private final TargetArgument targets = Arguments.target();
	private final ItemArgument item = Arguments.item();
	private final PrimitiveArgument<Integer> count = Arguments.intArg();
	
	private final CommandNodeLiteral root = CommandNode.literal("give")
			.then(CommandNode.argument("targets", this.targets)
					.then(CommandNode.argument("item", this.item)
							.label(Label.GIVE)
							.then(CommandNode.argument("count", this.count)
									.label(Label.GIVE_COUNT))));
	
	public TargetArgument targets()
	{
		return this.targets;
	}
	
	public ItemArgument item()
	{
		return this.item;
	}
	
	public PrimitiveArgument<Integer> count()
	{
		return this.count;
	}
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
	
	public enum Label
	{
		GIVE,
		GIVE_COUNT;
	}
}
