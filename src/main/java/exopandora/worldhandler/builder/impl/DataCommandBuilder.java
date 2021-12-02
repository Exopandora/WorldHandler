package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.BlockPosArgument;
import exopandora.worldhandler.builder.argument.NbtPathArgument;
import exopandora.worldhandler.builder.argument.PrimitiveArgument;
import exopandora.worldhandler.builder.argument.TagArgument;
import exopandora.worldhandler.builder.argument.TargetArgument;
import exopandora.worldhandler.builder.argument.PrimitiveArgument.Linkage;

public class DataCommandBuilder extends CommandBuilder
{
	private final BlockPosArgument targetPos = Arguments.blockPos();
	private final NbtPathArgument path = Arguments.nbtPath();
	private final PrimitiveArgument<Linkage> linkage = Arguments.linkage();
	private final PrimitiveArgument<Double> scale = Arguments.doubleArg();
	private final TargetArgument target = Arguments.target();
	private final TagArgument nbt = Arguments.tag();
	private final TargetArgument sourceTarget = Arguments.target();
	private final BlockPosArgument sourcePos = Arguments.blockPos();
	private final PrimitiveArgument<String> sourcePath = Arguments.word();
	private final PrimitiveArgument<String> value = Arguments.greedyString();
	private final PrimitiveArgument<Integer> index = Arguments.intArg();
	
	private final CommandNodeLiteral root = CommandNode.literal("data")
			.then(CommandNode.literal("get")
					.then(CommandNode.literal("block")
							.then(CommandNode.argument("targetPos", this.targetPos)
									.label(Label.GET_BLOCK)
									.then(CommandNode.argument("path", this.path)
											.label(Label.GET_BLOCK_PATH)
											.then(CommandNode.argument("scale", this.scale)
													.label(Label.GET_BLOCK_PATH_SCALE)))))
					.then(CommandNode.literal("entity")
							.then(CommandNode.argument("target", this.target)
									.label(Label.GET_ENTITY)
									.then(CommandNode.argument("path", this.path)
											.label(Label.GET_ENTITY_PATH)
											.then(CommandNode.argument("scale", this.scale)
													.label(Label.GET_ENTITY_PATH_SCALE)))))
					.then(CommandNode.literal("storage")
							.then(CommandNode.argument("target", this.target)
									.label(Label.GET_STORAGE)
									.then(CommandNode.argument("path", this.path)
											.label(Label.GET_STORAGE_PATH)
											.then(CommandNode.argument("scale", this.scale)
													.label(Label.GET_STORAGE_PATH_SCALE))))))
			.then(CommandNode.literal("merge")
					.then(CommandNode.literal("block")
							.then(CommandNode.argument("targetPos", this.targetPos)
									.then(CommandNode.argument("nbt", this.nbt)
											.label(Label.MERGE_BLOCK))))
					.then(CommandNode.literal("entity")
							.then(CommandNode.argument("target", this.target)
									.then(CommandNode.argument("nbt", this.nbt)
											.label(Label.MERGE_ENTITY))))
					.then(CommandNode.literal("storage")
							.then(CommandNode.argument("target", this.target)
									.then(CommandNode.argument("nbt", this.nbt)
											.label(Label.MERGE_STORAGE)))))
			.then(CommandNode.literal("modify")
					.then(CommandNode.literal("block")
							.then(CommandNode.argument("targetPos", this.targetPos)
									.then(CommandNode.argument("path", this.path)
											.then(CommandNode.argument("linkage", this.linkage)
													.then(CommandNode.literal("from")
															.then(CommandNode.literal("block")
																	.then(CommandNode.argument("sourcePos", this.sourcePos)
																			.label(Label.MODIFY_BLOCK_FROM_BLOCK)
																			.then(CommandNode.argument("sourcePath", this.sourcePath)
																					.label(Label.MODIFY_BLOCK_FROM_BLOCK_PATH))))
															.then(CommandNode.literal("entity")
																	.then(CommandNode.argument("sourceTarget", this.sourceTarget)
																			.label(Label.MODIFY_BLOCK_FROM_ENTITY)
																			.then(CommandNode.argument("sourcePath", this.sourcePath)
																					.label(Label.MODIFY_BLOCK_FROM_ENTITY_PATH))))
															.then(CommandNode.literal("storage")
																	.then(CommandNode.argument("source", this.sourceTarget)
																			.label(Label.MODIFY_BLOCK_FROM_STORAGE)
																			.then(CommandNode.argument("sourcePath", this.sourcePath)
																					.label(Label.MODIFY_BLOCK_FROM_STORAGE_PATH)))))
													.then(CommandNode.literal("value")
															.then(CommandNode.argument("value", this.value)
																	.label(Label.MODIFY_BLOCK_VALUE)))))))
					.then(CommandNode.literal("entity")
							.then(CommandNode.argument("target", this.target)
									.then(CommandNode.argument("path", this.path)
											.then(CommandNode.argument("linkage", this.linkage)
													.then(CommandNode.literal("from")
															.then(CommandNode.literal("block")
																	.then(CommandNode.argument("sourcePos", this.sourcePos)
																			.label(Label.MODIFY_ENTITY_FROM_BLOCK)
																			.then(CommandNode.argument("sourcePath", this.sourcePath)
																					.label(Label.MODIFY_ENTITY_FROM_BLOCK_PATH))))
															.then(CommandNode.literal("entity")
																	.then(CommandNode.argument("sourceTarget", this.sourceTarget)
																			.label(Label.MODIFY_ENTITY_FROM_ENTITY)
																			.then(CommandNode.argument("sourcePath", this.sourcePath)
																					.label(Label.MODIFY_ENTITY_FROM_ENTITY_PATH))))
															.then(CommandNode.literal("storage")
																	.then(CommandNode.argument("source", this.sourceTarget)
																			.label(Label.MODIFY_ENTITY_FROM_STORAGE)
																			.then(CommandNode.argument("sourcePath", this.sourcePath)
																					.label(Label.MODIFY_ENTITY_FROM_STORAGE_PATH)))))
													.then(CommandNode.literal("value")
															.then(CommandNode.argument("value", this.value)
																	.label(Label.MODIFY_ENTITY_VALUE)))))))
					.then(CommandNode.literal("storage")
							.then(CommandNode.argument("target", this.target)
									.then(CommandNode.argument("path", this.path)
											.then(CommandNode.argument("linkage", this.linkage)
													.then(CommandNode.literal("from")
															.then(CommandNode.literal("block")
																	.then(CommandNode.argument("sourcePos", this.sourcePos)
																			.label(Label.MODIFY_STORAGE_FROM_BLOCK)
																			.then(CommandNode.argument("sourcePath", this.sourcePath)
																					.label(Label.MODIFY_ENTITY_FROM_BLOCK_PATH))))
															.then(CommandNode.literal("entity")
																	.then(CommandNode.argument("sourceTarget", this.sourceTarget)
																			.label(Label.MODIFY_STORAGE_FROM_ENTITY)
																			.then(CommandNode.argument("sourcePath", this.sourcePath)
																					.label(Label.MODIFY_STORAGE_FROM_ENTITY_PATH))))
															.then(CommandNode.literal("storage")
																	.then(CommandNode.argument("source", this.sourceTarget)
																			.label(Label.MODIFY_STORAGE_FROM_STORAGE)
																			.then(CommandNode.argument("sourcePath", this.sourcePath)
																					.label(Label.MODIFY_STORAGE_FROM_STORAGE_PATH)))))
													.then(CommandNode.literal("value")
															.then(CommandNode.argument("value", this.value)
																	.label(Label.MODIFY_STORAGE_VALUE))))))))
			.then(CommandNode.literal("remove")
					.then(CommandNode.literal("block")
							.then(CommandNode.argument("targetPos", this.targetPos)
									.then(CommandNode.argument("path", this.path)
											.label(Label.REMOVE_BLOCK))))
					.then(CommandNode.literal("entity")
							.then(CommandNode.argument("target", this.target)
									.then(CommandNode.argument("path", this.path)
											.label(Label.REMOVE_ENTITY))))
					.then(CommandNode.literal("storage")
							.then(CommandNode.argument("target", this.target)
									.then(CommandNode.argument("path", this.path)
											.label(Label.REMOVE_STORAGE)))));
	
	public BlockPosArgument targetPos()
	{
		return this.targetPos;
	}
	
	public NbtPathArgument path()
	{
		return this.path;
	}
	
	public PrimitiveArgument<Linkage> linkage()
	{
		return this.linkage;
	}
	
	public PrimitiveArgument<Double> scale()
	{
		return this.scale;
	}
	
	public TargetArgument target()
	{
		return this.target;
	}
	
	public TagArgument nbt()
	{
		return this.nbt;
	}
	
	public TargetArgument sourceTarget()
	{
		return this.sourceTarget;
	}
	
	public BlockPosArgument sourcePos()
	{
		return this.sourcePos;
	}
	
	public PrimitiveArgument<String> sourcePath()
	{
		return this.sourcePath;
	}
	
	public PrimitiveArgument<String> value()
	{
		return this.value;
	}
	
	public PrimitiveArgument<Integer> index()
	{
		return this.index;
	}
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
	
	public static enum Label
	{
		GET_BLOCK,
		GET_BLOCK_PATH,
		GET_BLOCK_PATH_SCALE,
		GET_ENTITY,
		GET_ENTITY_PATH,
		GET_ENTITY_PATH_SCALE,
		GET_STORAGE,
		GET_STORAGE_PATH,
		GET_STORAGE_PATH_SCALE,
		MERGE_BLOCK,
		MERGE_ENTITY,
		MERGE_STORAGE,
		MODIFY_BLOCK_FROM_BLOCK,
		MODIFY_BLOCK_FROM_BLOCK_PATH,
		MODIFY_BLOCK_FROM_ENTITY,
		MODIFY_BLOCK_FROM_ENTITY_PATH,
		MODIFY_BLOCK_FROM_STORAGE,
		MODIFY_BLOCK_FROM_STORAGE_PATH,
		MODIFY_BLOCK_VALUE,
		MODIFY_ENTITY_FROM_BLOCK,
		MODIFY_ENTITY_FROM_BLOCK_PATH,
		MODIFY_ENTITY_FROM_ENTITY,
		MODIFY_ENTITY_FROM_ENTITY_PATH,
		MODIFY_ENTITY_FROM_STORAGE,
		MODIFY_ENTITY_FROM_STORAGE_PATH,
		MODIFY_ENTITY_VALUE,
		MODIFY_STORAGE_FROM_BLOCK,
		MODIFY_STORAGE_FROM_BLOCK_PATH,
		MODIFY_STORAGE_FROM_ENTITY,
		MODIFY_STORAGE_FROM_ENTITY_PATH,
		MODIFY_STORAGE_FROM_STORAGE,
		MODIFY_STORAGE_FROM_STORAGE_PATH,
		MODIFY_STORAGE_VALUE,
		REMOVE_BLOCK,
		REMOVE_ENTITY,
		REMOVE_STORAGE;
	}
}
