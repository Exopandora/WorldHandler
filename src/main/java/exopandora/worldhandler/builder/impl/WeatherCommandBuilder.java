package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.TimeArgument;

public class WeatherCommandBuilder extends CommandBuilder
{
	private final TimeArgument duration = Arguments.time();
	
	private final CommandNodeLiteral root = CommandNode.literal("weather")
			.then(CommandNode.literal("clear")
					.label(Label.CLEAR)
					.then(CommandNode.argument("duration", this.duration)
							.label(Label.CLEAR_DURATION)))
			.then(CommandNode.literal("rain")
					.label(Label.RAIN)
					.then(CommandNode.argument("duration", this.duration)
							.label(Label.RAIN_DURATION)))
			.then(CommandNode.literal("thunder")
					.label(Label.THUNDER)
					.then(CommandNode.argument("duration", this.duration)
							.label(Label.THUNDER_DURATION)));
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
	
	public static enum Label
	{
		CLEAR,
		CLEAR_DURATION,
		RAIN,
		RAIN_DURATION,
		THUNDER,
		THUNDER_DURATION;
	}
}
