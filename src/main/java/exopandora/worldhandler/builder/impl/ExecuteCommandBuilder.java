package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.ArgumentListArgument;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.BlockPosArgument;
import exopandora.worldhandler.builder.argument.CommandArgument;
import exopandora.worldhandler.builder.argument.DimensionArgument;
import exopandora.worldhandler.builder.argument.EntitySummonArgument;
import exopandora.worldhandler.builder.argument.NbtPathArgument;
import exopandora.worldhandler.builder.argument.PrimitiveArgument;
import exopandora.worldhandler.builder.argument.RangeArgument;
import exopandora.worldhandler.builder.argument.TargetArgument;
import exopandora.worldhandler.builder.argument.ArgumentListArgument.OptionalCommandBuilder;
import exopandora.worldhandler.builder.argument.PrimitiveArgument.Type;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.Direction.Axis;
import net.minecraft.resources.ResourceLocation;

public class ExecuteCommandBuilder extends CommandBuilder
{
	private final CommandArgument command = new CommandArgument();
	private final ArgumentListArgument modifiers = new ArgumentListArgument();
	private final CommandNodeLiteral root = CommandNode.literal("execute")
			.then(CommandNode.argument("modifiers", this.modifiers)
					.then(CommandNode.literal("run")
							.then(CommandNode.argument("command", this.command)
									.label(Label.RUN))));
	
	public ArgumentListArgument modifiers()
	{
		return this.modifiers;
	}
	
	public CommandArgument command()
	{
		return this.command;
	}
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
	
	public static enum Label
	{
		RUN;
	}
	
	public static class AlignOptionalArgument extends OptionalCommandBuilder<AlignOptionalArgument.Label>
	{
		private final PrimitiveArgument<Axis> axis = Arguments.axis();
		private final CommandNodeLiteral root = CommandNode.literal("align")
				.then(CommandNode.argument("axis", this.axis)
						.label(Label.AXIS));
		
		public AlignOptionalArgument(Label label)
		{
			super(label);
		}
		
		public PrimitiveArgument<Axis> axis()
		{
			return this.axis;
		}
		
		@Override
		protected CommandNodeLiteral root()
		{
			return this.root;
		}
		
		public static enum Label
		{
			AXIS;
		}
	}
	
	public static class AnchoredOptionalArgument extends OptionalCommandBuilder<AnchoredOptionalArgument.Label>
	{
		private final PrimitiveArgument<Anchor> anchor = Arguments.anchor();
		private final CommandNodeLiteral root = CommandNode.literal("anchored")
				.then(CommandNode.argument("anchor", this.anchor)
						.label(Label.ANCHOR));
		
		public AnchoredOptionalArgument(Label label)
		{
			super(label);
		}
		
		public PrimitiveArgument<Anchor> anchor()
		{
			return this.anchor;
		}
		
		@Override
		protected CommandNodeLiteral root()
		{
			return this.root;
		}
		
		public static enum Label
		{
			ANCHOR;
		}
	}
	
	public static class AsOptionalArgument extends OptionalCommandBuilder<AsOptionalArgument.Label>
	{
		private final TargetArgument targets = Arguments.target();
		private final CommandNodeLiteral root = CommandNode.literal("as")
				.then(CommandNode.argument("targets", this.targets)
						.label(Label.AS));
		
		public AsOptionalArgument(Label label)
		{
			super(label);
		}
		
		public TargetArgument targets()
		{
			return this.targets;
		}
		
		@Override
		protected CommandNodeLiteral root()
		{
			return this.root;
		}
		
		public static enum Label
		{
			AS;
		}
	}
	
	public static class AtOptionalArgument extends OptionalCommandBuilder<AtOptionalArgument.Label>
	{
		private final TargetArgument targets = Arguments.target();
		private final CommandNodeLiteral root = CommandNode.literal("at")
				.then(CommandNode.argument("targets", this.targets)
						.label(Label.AT));
		
		public AtOptionalArgument(Label label)
		{
			super(label);
		}
		
		public TargetArgument targets()
		{
			return this.targets;
		}
		
		@Override
		protected CommandNodeLiteral root()
		{
			return this.root;
		}
		
		public static enum Label
		{
			AT;
		}
	}
	
	public static class FacingOptionalArgument extends OptionalCommandBuilder<FacingOptionalArgument.Label>
	{
		private final PrimitiveArgument<Anchor> anchor = Arguments.anchor();
		private final BlockPosArgument pos = Arguments.blockPos();
		private final TargetArgument targets = Arguments.target();
		private final CommandNodeLiteral root = CommandNode.literal("facing")
				.then(CommandNode.argument("pos", this.pos)
						.label(Label.POS))
				.then(CommandNode.literal("entity")
						.then(CommandNode.argument("targets", this.targets)
								.then(CommandNode.argument("anchor", this.anchor)
										.label(Label.ENTITY))));
		
		public FacingOptionalArgument(Label label)
		{
			super(label);
		}
		
		public PrimitiveArgument<Anchor> anchor()
		{
			return this.anchor;
		}
		
		public BlockPosArgument pos()
		{
			return this.pos;
		}
		
		public TargetArgument targets()
		{
			return this.targets;
		}
		
		@Override
		protected CommandNodeLiteral root()
		{
			return this.root;
		}
		
		public static enum Label
		{
			POS,
			ENTITY;
		}
	}
	
	public static class InOptionalArgument extends OptionalCommandBuilder<InOptionalArgument.Label>
	{
		private final DimensionArgument dimension = Arguments.dimension();
		private final CommandNodeLiteral root = CommandNode.literal("in")
				.then(CommandNode.argument("dimension", this.dimension)
						.label(Label.IN));
		
		public InOptionalArgument(Label label)
		{
			super(label);
		}
		
		public DimensionArgument dimension()
		{
			return this.dimension;
		}
		
		@Override
		protected CommandNodeLiteral root()
		{
			return this.root;
		}
		
		public static enum Label
		{
			IN;
		}
	}
	
	public static class PositionedOptionalArgument extends OptionalCommandBuilder<PositionedOptionalArgument.Label>
	{
		private final BlockPosArgument pos = Arguments.blockPos();
		private final TargetArgument targets = Arguments.target();
		private final CommandNodeLiteral root = CommandNode.literal("positioned")
				.then(CommandNode.argument("pos", this.pos)
						.label(Label.POS))
				.then(CommandNode.literal("as")
						.then(CommandNode.argument("targets", this.targets)
								.label(Label.AS)))
				.then(CommandNode.literal("over")
						.then(CommandNode.literal("world_surface")
								.label(Label.OVER_WORLD_SURFACE))
						.then(CommandNode.literal("motion_blocking")
								.label(Label.OVER_MOTION_BLOCKING))
						.then(CommandNode.literal("motion_blocking_no_leaves")
								.label(Label.OVER_MOTION_BLOCKING_NO_LEAVES))
						.then(CommandNode.literal("ocean_floor")
								.label(Label.OVER_OCEAN_FLOOR)));
		
		public PositionedOptionalArgument(Label label)
		{
			super(label);
		}
		
		public BlockPosArgument pos()
		{
			return this.pos;
		}
		
		public TargetArgument targets()
		{
			return this.targets;
		}
		
		@Override
		protected CommandNodeLiteral root()
		{
			return this.root;
		}
		
		public static enum Label
		{
			POS,
			AS,
			OVER_WORLD_SURFACE,
			OVER_MOTION_BLOCKING,
			OVER_MOTION_BLOCKING_NO_LEAVES,
			OVER_OCEAN_FLOOR;
		}
	}
	
	public static class RotatedOptionalArgument extends OptionalCommandBuilder<RotatedOptionalArgument.Label>
	{
		private final TargetArgument targets = Arguments.target();
		private final CommandNodeLiteral root = CommandNode.literal("rotated")
				.then(CommandNode.literal("as")
						.then(CommandNode.argument("targets", this.targets)
								.label(Label.ROTATED)));
		
		public RotatedOptionalArgument(Label label)
		{
			super(label);
		}
		
		public TargetArgument targets()
		{
			return this.targets;
		}
		
		@Override
		protected CommandNodeLiteral root()
		{
			return this.root;
		}
		
		public static enum Label
		{
			ROTATED;
		}
	}
	
	private static class ConditionOptionalArgument extends OptionalCommandBuilder<ConditionOptionalArgument.Label>
	{
		private final BlockPosArgument pos = Arguments.blockPos();
		private final PrimitiveArgument<ResourceLocation> block = Arguments.resourceLocation();
		private final BlockPosArgument start = Arguments.blockPos();
		private final BlockPosArgument end = Arguments.blockPos();
		private final BlockPosArgument destination = Arguments.blockPos();
		private final DimensionArgument dimension = Arguments.dimension();
		private final NbtPathArgument path = Arguments.nbtPath();
		private final PrimitiveArgument<ResourceLocation> predicate = Arguments.resourceLocation();
		private final TargetArgument target = Arguments.target();
		private final TargetArgument source = Arguments.target();
		private final PrimitiveArgument<String> targetObjective = Arguments.word();
		private final PrimitiveArgument<String> sourceObjective = Arguments.word();
		private final RangeArgument<Integer> range = Arguments.intRange();
		private final CommandNodeLiteral root;
		
		public ConditionOptionalArgument(String condition, Label label)
		{
			super(label);
			this.root = CommandNode.literal(condition)
					.then(CommandNode.literal("block")
							.then(CommandNode.argument("pos", this.pos)
									.then(CommandNode.argument("block", this.block)
											.label(Label.BLOCK))))
					.then(CommandNode.literal("blocks")
							.then(CommandNode.argument("start", this.start)
									.then(CommandNode.argument("end", this.end)
											.then(CommandNode.argument("destination", this.destination)
													.then(CommandNode.literal("all")
															.label(Label.BLOCKS_ALL))
													.then(CommandNode.literal("masked")
															.label(Label.BLOCKS_MASKED))))))
					.then(CommandNode.literal("data")
							.then(CommandNode.literal("block")
									.then(CommandNode.argument("sourcePos", this.pos)
											.then(CommandNode.argument("sourcePath", this.path)
													.label(Label.DATA_BLOCK))))
							.then(CommandNode.literal("entity")
									.then(CommandNode.argument("sourceTarget", this.target)
											.then(CommandNode.argument("sourcePath", this.path)
													.label(Label.DATA_ENTITY))))
							.then(CommandNode.literal("storage")
									.then(CommandNode.argument("source", this.target)
											.then(CommandNode.argument("sourcePath", this.path)
													.label(Label.DATA_STORAGE)))))
					.then(CommandNode.literal("dimension")
							.then(CommandNode.argument("dimension", this.dimension)
									.label(Label.DIMENSION)))
					.then(CommandNode.literal("entity")
							.then(CommandNode.argument("entites", this.target)
									.label(Label.ENTITY)))
					.then(CommandNode.literal("loaded")
							.then(CommandNode.argument("pos", this.pos)
									.label(Label.LOADED)))
					.then(CommandNode.literal("predicate")
							.then(CommandNode.argument("predicate", this.predicate)
									.label(Label.PREDICATE)))
					.then(CommandNode.literal("score")
							.then(CommandNode.argument("target", this.target)
									.then(CommandNode.argument("targetObjective", this.targetObjective)
											.then(CommandNode.literal("<")
													.then(CommandNode.argument("source", this.source)
															.then(CommandNode.argument("sourceObjective", this.sourceObjective)
																	.label(Label.SCORE_LT))))
											.then(CommandNode.literal("<=")
													.then(CommandNode.argument("source", this.source)
															.then(CommandNode.argument("sourceObjective", this.sourceObjective)
																	.label(Label.SCORE_LE))))
											.then(CommandNode.literal("=")
													.then(CommandNode.argument("source", this.source)
															.then(CommandNode.argument("sourceObjective", this.sourceObjective)
																	.label(Label.SCORE_EQ))))
											.then(CommandNode.literal(">")
													.then(CommandNode.argument("source", this.source)
															.then(CommandNode.argument("sourceObjective", this.sourceObjective)
																	.label(Label.SCORE_GT))))
											.then(CommandNode.literal(">=")
													.then(CommandNode.argument("source", this.source)
															.then(CommandNode.argument("sourceObjective", this.sourceObjective)
																	.label(Label.SCORE_GE))))
											.then(CommandNode.literal("matches")
													.then(CommandNode.argument("range", this.range)
															.label(Label.SCORE_MATCHES))))));
		}
		
		public BlockPosArgument pos()
		{
			return this.pos;
		}
		
		public PrimitiveArgument<ResourceLocation> block()
		{
			return this.block;
		}
		
		public BlockPosArgument start()
		{
			return this.start;
		}
		
		public BlockPosArgument end()
		{
			return this.end;
		}
		
		public BlockPosArgument destination()
		{
			return this.destination;
		}
		
		public NbtPathArgument path()
		{
			return this.path;
		}
		
		public PrimitiveArgument<ResourceLocation> predicate()
		{
			return this.predicate;
		}
		
		public TargetArgument targets()
		{
			return this.target;
		}
		
		public TargetArgument source()
		{
			return this.source;
		}
		
		public PrimitiveArgument<String> targetObjective()
		{
			return this.targetObjective;
		}
		
		public PrimitiveArgument<String> sourceObjective()
		{
			return this.sourceObjective;
		}
		
		public RangeArgument<Integer> range()
		{
			return this.range;
		}
		
		@Override
		protected CommandNodeLiteral root()
		{
			return this.root;
		}
		
		public static enum Label
		{
			BLOCK,
			BLOCKS_ALL,
			BLOCKS_MASKED,
			DATA_BLOCK,
			DATA_ENTITY,
			DATA_STORAGE,
			DIMENSION,
			ENTITY,
			LOADED,
			PREDICATE,
			SCORE_LT,
			SCORE_LE,
			SCORE_EQ,
			SCORE_GT,
			SCORE_GE,
			SCORE_MATCHES;
		}
	}
	
	public static class IfOptionalArgument extends ConditionOptionalArgument
	{
		public IfOptionalArgument(Label label)
		{
			super("if", label);
		}
	}
	
	public static class UnlessOptionalArgument extends ConditionOptionalArgument
	{
		public UnlessOptionalArgument(Label label)
		{
			super("unless", label);
		}
	}
	
	public static class StoreOptionalArgument extends OptionalCommandBuilder<StoreOptionalArgument.Label>
	{
		private final BlockPosArgument targetPos = Arguments.blockPos();
		private final NbtPathArgument path = Arguments.nbtPath();
		private final PrimitiveArgument<Type> type = Arguments.type();
		private final PrimitiveArgument<Double> scale = Arguments.doubleArg();
		private final PrimitiveArgument<ResourceLocation> id = Arguments.resourceLocation();
		private final TargetArgument target = Arguments.target();
		private final PrimitiveArgument<String> objective = Arguments.word();
		
		private final CommandNodeLiteral root = CommandNode.literal("store")
				.then(CommandNode.literal("result")
						.then(CommandNode.literal("block")
								.then(CommandNode.argument("targetPos", this.targetPos)
										.then(CommandNode.argument("path", this.path)
												.then(CommandNode.argument("type", this.type)
														.then(CommandNode.argument("scale", this.scale)
																.label(Label.RESULT_BLOCK))))))
						.then(CommandNode.literal("bossbar")
								.then(CommandNode.argument("id", this.id)
										.then(CommandNode.literal("max")
												.label(Label.RESULT_BOSSBAR_MAX))
										.then(CommandNode.literal("value")
												.label(Label.RESULT_BOSSBAR_VALUE))))
						.then(CommandNode.literal("entity")
								.then(CommandNode.argument("target", this.target)
										.then(CommandNode.argument("path", this.path)
												.then(CommandNode.argument("type", this.type)
														.then(CommandNode.argument("scale", this.scale)
																.label(Label.RESULT_ENTITY))))))
						.then(CommandNode.literal("score")
								.then(CommandNode.argument("target", this.target)
										.then(CommandNode.argument("objective", this.objective)
												.label(Label.RESULT_SCORE))))
						.then(CommandNode.literal("storage")
								.then(CommandNode.argument("target", this.target)
										.then(CommandNode.argument("path", this.path)
												.then(CommandNode.argument("type", this.type)
														.then(CommandNode.argument("scale", this.scale)
																.label(Label.RESULT_STORAGE))))))
				.then(CommandNode.literal("success")
						.then(CommandNode.literal("block")
								.then(CommandNode.argument("targetPos", this.targetPos)
										.then(CommandNode.argument("path", this.path)
												.then(CommandNode.argument("type", this.type)
														.then(CommandNode.argument("scale", this.scale)
																.label(Label.SUCCESS_BLOCK))))))
						.then(CommandNode.literal("bossbar")
								.then(CommandNode.argument("id", this.id)
										.then(CommandNode.literal("max")
												.label(Label.SUCCESS_BOSSBAR_MAX))
										.then(CommandNode.literal("value")
												.label(Label.SUCCESS_BOSSBAR_VALUE))))
						.then(CommandNode.literal("entity")
								.then(CommandNode.argument("target", this.target)
										.then(CommandNode.argument("path", this.path)
												.then(CommandNode.argument("type", this.type)
														.then(CommandNode.argument("scale", this.scale)
																.label(Label.SUCCESS_ENTITY))))))
						.then(CommandNode.literal("score")
								.then(CommandNode.argument("target", this.target)
										.then(CommandNode.argument("objective", this.objective)
												.label(Label.SUCCESS_SCORE))))
						.then(CommandNode.literal("storage")
								.then(CommandNode.argument("target", this.target)
										.then(CommandNode.argument("path", this.path)
												.then(CommandNode.argument("type", this.type)
														.then(CommandNode.argument("scale", this.scale)
																.label(Label.SUCCESS_STORAGE))))))));
		
		public StoreOptionalArgument(Label label)
		{
			super(label);
		}
		
		public BlockPosArgument targetPos()
		{
			return this.targetPos;
		}
		
		public NbtPathArgument path()
		{
			return this.path;
		}
		
		public PrimitiveArgument<Type> type()
		{
			return this.type;
		}
		
		public PrimitiveArgument<Double> scale()
		{
			return this.scale;
		}
		
		public PrimitiveArgument<ResourceLocation> id()
		{
			return this.id;
		}
		
		public TargetArgument target()
		{
			return this.target;
		}
		
		public PrimitiveArgument<String> objective()
		{
			return this.objective;
		}
		
		@Override
		protected CommandNodeLiteral root()
		{
			return this.root;
		}
		
		public static enum Label
		{
			RESULT_BLOCK,
			RESULT_BOSSBAR_MAX,
			RESULT_BOSSBAR_VALUE,
			RESULT_ENTITY,
			RESULT_SCORE,
			RESULT_STORAGE,
			SUCCESS_BLOCK,
			SUCCESS_BOSSBAR_MAX,
			SUCCESS_BOSSBAR_VALUE,
			SUCCESS_ENTITY,
			SUCCESS_SCORE,
			SUCCESS_STORAGE;
		}
	}
	
	public static class OnOptionalArgument extends OptionalCommandBuilder<OnOptionalArgument.Label>
	{
		private final CommandNodeLiteral root = CommandNode.literal("on")
				.then(CommandNode.literal("attacker")
						.label(Label.ATTACKER))
				.then(CommandNode.literal("controller")
						.label(Label.CONTROLLER))
				.then(CommandNode.literal("leasher")
						.label(Label.LEASHER))
				.then(CommandNode.literal("origin")
						.label(Label.ORIGIN))
				.then(CommandNode.literal("owner")
						.label(Label.OWNER))
				.then(CommandNode.literal("passengers")
						.label(Label.PASSENGERS))
				.then(CommandNode.literal("target")
						.label(Label.TARGET))
				.then(CommandNode.literal("vehicle")
						.label(Label.VEHICLE));
		
		public OnOptionalArgument(Label label)
		{
			super(label);
		}
		
		@Override
		protected CommandNodeLiteral root()
		{
			return this.root;
		}
		
		public static enum Label
		{
			ATTACKER,
			CONTROLLER,
			LEASHER,
			ORIGIN,
			OWNER,
			PASSENGERS,
			TARGET,
			VEHICLE;
		}
	}
	
	public static class SummonOptionalArgument extends OptionalCommandBuilder<SummonOptionalArgument.Label>
	{
		private final EntitySummonArgument entity = Arguments.entitySummon();
		
		private final CommandNodeLiteral root = CommandNode.literal("on")
				.then(CommandNode.argument("entity", this.entity)
						.label(Label.SUMMON));
		
		public SummonOptionalArgument(Label label)
		{
			super(label);
		}
		
		public EntitySummonArgument entity()
		{
			return entity;
		}
		
		@Override
		protected CommandNodeLiteral root()
		{
			return this.root;
		}
		
		public static enum Label
		{
			SUMMON;
		}
	}
}
