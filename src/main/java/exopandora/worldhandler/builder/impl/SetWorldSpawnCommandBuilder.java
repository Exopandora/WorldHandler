package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.AngleArgument;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.BlockPosArgument;

public class SetWorldSpawnCommandBuilder extends CommandBuilder
{
	private final BlockPosArgument pos = Arguments.blockPos();
	private final AngleArgument angle = Arguments.angle();
	
	private final CommandNodeLiteral root = CommandNode.literal("setworldspawn")
			.label(Label.SET_WORLD_SPAWN)
			.then(CommandNode.argument("pos", this.pos)
					.label(Label.SET_WORLD_SPAWN_POS)
					.then(CommandNode.argument("angle", this.angle)
							.label(Label.SET_WORLD_SPAWN_POS_ANGLE)));
	
	public BlockPosArgument pos()
	{
		return this.pos;
	}
	
	public AngleArgument angle()
	{
		return this.angle;
	}
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
	
	public static enum Label
	{
		SET_WORLD_SPAWN,
		SET_WORLD_SPAWN_POS,
		SET_WORLD_SPAWN_POS_ANGLE;
	}
}
