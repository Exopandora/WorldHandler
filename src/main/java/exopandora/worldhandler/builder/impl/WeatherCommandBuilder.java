package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;

public class WeatherCommandBuilder extends CommandBuilder
{
	private final CommandNodeLiteral root = CommandNode.literal("weather")
			.then(CommandNode.literal("clear")
					.label(Label.CLEAR))
			.then(CommandNode.literal("rain")
					.label(Label.RAIN))
			.then(CommandNode.literal("thunder")
					.label(Label.THUNDER));
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
	
	public static enum Label
	{
		CLEAR,
		RAIN,
		THUNDER;
	}
}
