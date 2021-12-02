package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.PrimitiveArgument;
import net.minecraft.world.Difficulty;

public class DifficultyCommandBuilder extends CommandBuilder
{
	private final PrimitiveArgument<Difficulty> difficulty = Arguments.difficulty();
	
	private final CommandNodeLiteral root = CommandNode.literal("difficulty")
			.then(CommandNode.argument("difficulty", this.difficulty)
					.label(Label.DIFFICULTY));
	
	public PrimitiveArgument<Difficulty> difficulty()
	{
		return this.difficulty;
	}
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
	
	public static enum Label
	{
		DIFFICULTY;
	}
}
