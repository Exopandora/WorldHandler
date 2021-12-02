package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.Gamemode;
import exopandora.worldhandler.builder.argument.PrimitiveArgument;
import exopandora.worldhandler.builder.argument.TargetArgument;

public class GamemodeCommandBuilder extends CommandBuilder
{
	private final PrimitiveArgument<Gamemode> gamemode = Arguments.gamemode();
	private final TargetArgument target = Arguments.target();
	
	private final CommandNodeLiteral root = CommandNode.literal("gamemode")
			.then(CommandNode.argument("gamemode", this.gamemode)
					.label(Label.GAMEMODE)
					.then(CommandNode.argument("target", this.target)
							.label(Label.PLAYER)));
	
	public PrimitiveArgument<Gamemode> gamemode()
	{
		return this.gamemode;
	}
	
	public TargetArgument target()
	{
		return this.target;
	}
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
	
	public enum Label
	{
		GAMEMODE,
		PLAYER;
	}
}
