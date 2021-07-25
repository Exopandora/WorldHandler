package exopandora.worldhandler.command;

import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import exopandora.worldhandler.builder.impl.BuilderClone;
import exopandora.worldhandler.builder.impl.BuilderClone.EnumMask;
import exopandora.worldhandler.builder.impl.BuilderFill;
import exopandora.worldhandler.builder.impl.BuilderFill.EnumBlockFilter;
import exopandora.worldhandler.builder.types.BlockResourceLocation;
import exopandora.worldhandler.util.BlockHelper;
import exopandora.worldhandler.util.CommandHelper;
import exopandora.worldhandler.util.EnumHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.commands.arguments.blocks.BlockPredicateArgument;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.DistExecutor.SafeRunnable;
import net.minecraftforge.registries.ForgeRegistries;

public class CommandWH
{
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
	{
		dispatcher.register(Commands.literal("wh")
				.then(Commands.literal("pos1")
					.executes(context -> pos1(context.getSource())))
				.then(Commands.literal("pos2")
					.executes(context -> pos2(context.getSource())))
				.then(Commands.literal("fill")
					.requires(source -> source.hasPermission(2))
						.then(Commands.argument("block", BlockStateArgument.block())
							.executes(context -> fill(context.getSource(), BlockStateArgument.getBlock(context, "block")))))
				.then(Commands.literal("replace")
					.requires(source -> source.hasPermission(2))
						.then(Commands.argument("block", BlockStateArgument.block())
							.then(Commands.argument("replace", BlockStateArgument.block())
								.executes(context -> replace(context.getSource(), BlockStateArgument.getBlock(context, "block"), BlockStateArgument.getBlock(context, "replace"))))))
				.then(Commands.literal("clone")
					.requires(source -> source.hasPermission(2))
					.executes(context -> clone(context.getSource(), "masked"))
						.then(Commands.literal("filtered")
							.then(Commands.argument("filter", StringBlockPredicateArgument.blockPredicate())
								.executes(context -> clone(context.getSource(), "filter", StringBlockPredicateArgument.getBlockPredicate(context, "filter")))))
						.then(Commands.literal("masked")
								.executes(context -> clone(context.getSource(), "masked")))
						.then(Commands.literal("replace")
							.executes(context -> clone(context.getSource(), "replace")))));
	}
	
	private static int pos1(CommandSourceStack source) throws CommandSyntaxException
	{
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> new SafeRunnable()
		{
			private static final long serialVersionUID = 818420798194963795L;
			
			@Override
			public void run()
			{
				BlockHelper.setPos1(BlockHelper.getFocusedBlockPos());
				BlockPos pos = BlockHelper.getPos1();
				ResourceLocation block = ForgeRegistries.BLOCKS.getKey(BlockHelper.getBlock(pos));
				CommandHelper.sendFeedback(source, "Set first position to " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " (" + block + ")");
			}
		});
		
		return 1;
	}
	
	private static int pos2(CommandSourceStack source) throws CommandSyntaxException
	{
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> new SafeRunnable()
		{
			private static final long serialVersionUID = 5436684018502529063L;
			
			@Override
			public void run()
			{
				BlockHelper.setPos2(BlockHelper.getFocusedBlockPos());
				BlockPos pos = BlockHelper.getPos2();
				ResourceLocation block = ForgeRegistries.BLOCKS.getKey(BlockHelper.getBlock(pos));
				CommandHelper.sendFeedback(source, "Set second position to " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " (" + block + ")");
			}
		});
		
		return 1;
	}
	
	private static int fill(CommandSourceStack source, BlockInput block)
	{
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> new SafeRunnable()
		{
			private static final long serialVersionUID = 7622739284160142817L;
			
			@Override
			public void run()
			{
				BuilderFill builder = new BuilderFill();
				builder.setBlock1(new BlockResourceLocation(block.getState().getBlock().getRegistryName(), block.getState(), block.tag));
				CommandHelper.sendCommand(source.getTextName(), builder);
			}
		});
		
		return 1;
	}
	
	private static int replace(CommandSourceStack source, BlockInput block, BlockInput replace)
	{
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> new SafeRunnable()
		{
			private static final long serialVersionUID = -5007303344454187200L;
			
			@Override
			public void run()
			{
				BuilderFill builder = new BuilderFill();
				builder.setPosition1(BlockHelper.getPos1());
				builder.setPosition2(BlockHelper.getPos2());
				builder.setBlockHandling(EnumBlockFilter.REPLACE);
				builder.setBlock1(new BlockResourceLocation(replace.getState().getBlock().getRegistryName(), replace.getState(), replace.tag));
				builder.setBlock2(new BlockResourceLocation(block.getState().getBlock().getRegistryName(), block.getState(), block.tag));
				CommandHelper.sendCommand(source.getTextName(), builder);
			}
		});
		
		return 1;
	}
	
	private static int clone(CommandSourceStack source, String mask, String filter)
	{
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> new SafeRunnable()
		{
			private static final long serialVersionUID = -2849956095821394079L;
			
			@Override
			public void run()
			{
				BuilderClone builder = new BuilderClone();
				builder.setPosition1(BlockHelper.getPos1());
				builder.setPosition2(BlockHelper.getPos2());
				builder.setMask(EnumHelper.valueOf(mask, EnumMask.class));
				builder.setFilter(filter);
				CommandHelper.sendCommand(source.getTextName(), builder);
			}
		});
		
		return 1;
	}
	
	private static int clone(CommandSourceStack source, String mask)
	{
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> new SafeRunnable()
		{
			private static final long serialVersionUID = -7349335271543407747L;
			
			@Override
			public void run()
			{
				BuilderClone builder = new BuilderClone();
				builder.setPosition1(BlockHelper.getPos1());
				builder.setPosition2(BlockHelper.getPos2());
				builder.setMask(EnumHelper.valueOf(mask, EnumMask.class));
				CommandHelper.sendCommand(source.getTextName(), builder);
			}
		});
		
		return 1;
	}
	
	public static class StringBlockPredicateArgument implements ArgumentType<String>
	{
		public static StringBlockPredicateArgument blockPredicate()
		{
			return new StringBlockPredicateArgument();
		}
		
		@Override
		public String parse(StringReader reader) throws CommandSyntaxException
		{
			BlockStateParser blockstateparser = new BlockStateParser(reader, true).parse(true);
			
			if(blockstateparser.getState() != null)
			{
				return new BlockResourceLocation(blockstateparser.getState().getBlock().getRegistryName(), blockstateparser.getState(), blockstateparser.getNbt()).toString();
			}
			
			return "#" + blockstateparser.getTag();
		}
		
		public static String getBlockPredicate(CommandContext<CommandSourceStack> context, String name) throws CommandSyntaxException
		{
			return context.getArgument(name, String.class);
		}
		
		@Override
		public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder)
		{
			return BlockPredicateArgument.blockPredicate().listSuggestions(context, builder);
		}
	}
}
