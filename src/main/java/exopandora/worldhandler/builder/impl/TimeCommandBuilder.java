package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.TimeArgument;

public class TimeCommandBuilder extends CommandBuilder
{
	private final TimeArgument time = Arguments.time();
	
	private final CommandNodeLiteral root = CommandNode.literal("time")
			.then(CommandNode.literal("add")
					.then(CommandNode.argument("time", this.time)
							.label(Label.ADD)))
			.then(CommandNode.literal("query")
					.then(CommandNode.literal("day")
							.label(Label.QUERY_DAY))
					.then(CommandNode.literal("daytime")
							.label(Label.QUERY_DAYTIME))
					.then(CommandNode.literal("gametime")
							.label(Label.QUERY_GAMETIME)))
			.then(CommandNode.literal("set")
					.then(CommandNode.argument("time", this.time)
							.label(Label.SET))
					.then(CommandNode.literal("day")
							.label(Label.SET_DAY))
					.then(CommandNode.literal("midnight")
							.label(Label.SET_MIDNIGHT))
					.then(CommandNode.literal("night")
							.label(Label.SET_NIGHT))
					.then(CommandNode.literal("noon")
							.label(Label.SET_NOON)));
	
	public TimeArgument time()
	{
		return this.time;
	}
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
	
	public static enum Label
	{
		ADD,
		QUERY_DAY,
		QUERY_DAYTIME,
		QUERY_GAMETIME,
		SET,
		SET_DAY,
		SET_MIDNIGHT,
		SET_NIGHT,
		SET_NOON;
	}
}
