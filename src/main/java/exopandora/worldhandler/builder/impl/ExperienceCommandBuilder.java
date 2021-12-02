package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.PrimitiveArgument;
import exopandora.worldhandler.builder.argument.TargetArgument;

public class ExperienceCommandBuilder extends CommandBuilder
{
	private final TargetArgument targets = Arguments.target();
	private final PrimitiveArgument<Integer> amount = Arguments.intArg();
	
	private final CommandNodeLiteral root = CommandNode.literal("experience")
			.then(CommandNode.literal("add")
					.then(CommandNode.argument("targets", this.targets)
							.then(CommandNode.argument("amount", this.amount)
									.label(Label.ADD)
									.then(CommandNode.literal("points")
											.label(Label.ADD_POINTS))
									.then(CommandNode.literal("levels")
											.label(Label.ADD_LEVELS)))))
			.then(CommandNode.literal("set")
					.then(CommandNode.argument("targets", this.targets)
							.then(CommandNode.argument("amount", this.amount)
									.label(Label.SET)
									.then(CommandNode.literal("points")
											.label(Label.SET_POINTS))
									.then(CommandNode.literal("levels")
											.label(Label.SET_LEVELS)))))
			.then(CommandNode.literal("query")
					.then(CommandNode.argument("targets", this.targets)
							.then(CommandNode.literal("points")
									.label(Label.QUERY_POINTS))
							.then(CommandNode.literal("levels")
									.label(Label.QUERY_LEVELS))));
	
	public TargetArgument targets()
	{
		return this.targets;
	}
	
	public PrimitiveArgument<Integer> amount()
	{
		return this.amount;
	}
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
	
	public static enum Label
	{
		ADD,
		ADD_POINTS,
		ADD_LEVELS,
		SET,
		SET_POINTS,
		SET_LEVELS,
		QUERY_POINTS,
		QUERY_LEVELS;
	}
}
