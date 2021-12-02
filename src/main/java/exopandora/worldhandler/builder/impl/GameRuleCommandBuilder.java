package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.PrimitiveArgument;

public class GameRuleCommandBuilder extends CommandBuilder
{
	private final PrimitiveArgument<String> rule = Arguments.word();
	private final PrimitiveArgument<String> value = Arguments.word();
	
	private final CommandNodeLiteral root = CommandNode.literal("gamerule")
			.then(CommandNode.argument("rule", this.rule)
					.label(Label.GAMERULE)
					.then(CommandNode.argument("value", this.value)
							.label(Label.GAMERULE_VALUE)));
	
	public PrimitiveArgument<String> rule()
	{
		return this.rule;
	}
	
	public PrimitiveArgument<String> value()
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
		GAMERULE,
		GAMERULE_VALUE;
	}
}
