package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.AngleArgument;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.BlockPosArgument;
import exopandora.worldhandler.builder.argument.TargetArgument;

public class SetSpawnCommandBuilder extends CommandBuilder
{
	private final TargetArgument targets = Arguments.target();
	private final BlockPosArgument pos = Arguments.blockPos();
	private final AngleArgument angle = Arguments.angle();
	
	private final CommandNodeLiteral root = CommandNode.literal("spawnpoint")
			.then(CommandNode.argument("targets", this.targets)
					.label(Label.SPAWNPOINT)
					.then(CommandNode.argument("pos", this.pos)
							.label(Label.SPAWNPOINT_POS)
							.then(CommandNode.argument("angle", this.angle)
									.label(Label.SPAWNPOINT_POS_ANGLE))));
	
	public TargetArgument targets()
	{
		return this.targets;
	}
	
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
		SPAWNPOINT,
		SPAWNPOINT_POS,
		SPAWNPOINT_POS_ANGLE;
	}
}
