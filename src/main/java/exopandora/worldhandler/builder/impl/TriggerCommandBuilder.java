package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.PrimitiveArgument;

public class TriggerCommandBuilder extends CommandBuilder
{
	private final PrimitiveArgument<String> objective = Arguments.word();
	private final PrimitiveArgument<Integer> value = Arguments.intArg();
	
	private final CommandNodeLiteral root = CommandNode.literal("trigger")
			.then(CommandNode.argument("objective", this.objective)
					.then(CommandNode.literal("add")
							.then(CommandNode.argument("value", this.value)
									.label(Label.ADD)))
					.then(CommandNode.literal("set")
							.then(CommandNode.argument("value", this.value)
									.label(Label.SET))));
	
	public PrimitiveArgument<String> objective()
	{
		return this.objective;
	}
	
	public PrimitiveArgument<Integer> value()
	{
		return this.value;
	}
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
	
	public static enum Label
	{
		ADD,
		SET;
	}
}
