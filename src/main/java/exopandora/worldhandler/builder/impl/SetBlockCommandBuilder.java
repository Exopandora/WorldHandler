package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.BlockPosArgument;
import exopandora.worldhandler.builder.argument.BlockStateArgument;

public class SetBlockCommandBuilder extends CommandBuilder
{
	private final BlockPosArgument pos = Arguments.blockPos();
	private final BlockStateArgument block = Arguments.blockState();
	
	private final CommandNodeLiteral root = CommandNode.literal("setblock")
			.then(CommandNode.argument("pos", this.pos)
					.then(CommandNode.argument("block", this.block)
							.label(Label.SETBLOCK)
							.then(CommandNode.literal("destroy")
									.label(Label.DESTROY))
							.then(CommandNode.literal("keep")
									.label(Label.KEEP))
							.then(CommandNode.literal("replace")
									.label(Label.REPLACE))));
	
	public BlockPosArgument pos()
	{
		return this.pos;
	}
	
	public BlockStateArgument block()
	{
		return this.block;
	}
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
	
	public static enum Label
	{
		SETBLOCK,
		DESTROY,
		KEEP,
		REPLACE;
	}
}
