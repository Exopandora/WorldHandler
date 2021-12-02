package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.BlockPosArgument;
import exopandora.worldhandler.builder.argument.EntitySummonArgument;
import exopandora.worldhandler.builder.argument.TagArgument;

public class SummonCommandBuilder extends CommandBuilder
{
	private final EntitySummonArgument entity = Arguments.entitySummon();
	private final BlockPosArgument pos = Arguments.blockPos();
	private final TagArgument nbt = Arguments.tag();
	
	private final CommandNodeLiteral root = CommandNode.literal("summon")
			.then(CommandNode.argument("entity", this.entity)
					.label(Label.SUMMON)
					.then(CommandNode.argument("pos", this.pos)
							.label(Label.SUMMON_POS)
							.then(CommandNode.argument("nbt", this.nbt)
									.label(Label.SUMMON_POS_NBT))));
	
	public EntitySummonArgument entity()
	{
		return this.entity;
	}
	
	public BlockPosArgument pos()
	{
		return this.pos;
	}
	
	public TagArgument nbt()
	{
		return this.nbt;
	}
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
	
	public static enum Label
	{
		SUMMON,
		SUMMON_POS,
		SUMMON_POS_NBT;
	}
}
