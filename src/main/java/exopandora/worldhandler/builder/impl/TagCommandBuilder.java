package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.PrimitiveArgument;
import exopandora.worldhandler.builder.argument.TargetArgument;

public class TagCommandBuilder extends CommandBuilder
{
	private final TargetArgument targets = Arguments.target();
	private final PrimitiveArgument<String> name = Arguments.word();
	
	private final CommandNodeLiteral root = CommandNode.literal("tag")
			.then(CommandNode.argument("targets", this.targets)
					.then(CommandNode.literal("add")
							.then(CommandNode.argument("name", this.name)
									.label(Label.ADD)))
					.then(CommandNode.literal("remove")
							.then(CommandNode.argument("name", this.name)
									.label(Label.REMOVE)))
					.then(CommandNode.literal("list")
							.label(Label.LIST)));
	
	public TargetArgument targets()
	{
		return this.targets;
	}
	
	public PrimitiveArgument<String> name()
	{
		return this.name;
	}
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
	
	public static enum Label
	{
		ADD,
		REMOVE,
		LIST;
	}
}
