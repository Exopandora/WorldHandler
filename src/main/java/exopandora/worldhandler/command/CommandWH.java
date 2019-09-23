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
import exopandora.worldhandler.builder.types.BlockResourceLocation;
import exopandora.worldhandler.helper.BlockHelper;
import exopandora.worldhandler.helper.CommandHelper;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPredicateArgument;
import net.minecraft.command.arguments.BlockStateArgument;
import net.minecraft.command.arguments.BlockStateInput;
import net.minecraft.command.arguments.BlockStateParser;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.registries.ForgeRegistries;

public class CommandWH
{
	public static void register(CommandDispatcher<CommandSource> dispatcher)
	{
		dispatcher.register(Commands.literal("wh")
				.then(Commands.literal("pos1")
					.executes(context -> pos1(context.getSource())))
				.then(Commands.literal("pos2")
					.executes(context -> pos2(context.getSource())))
				.then(Commands.literal("fill")
					.requires(context -> context.hasPermissionLevel(2))
						.then(Commands.argument("block", BlockStateArgument.blockState())
							.executes(context -> fill(context.getSource(), BlockStateArgument.getBlockState(context, "block")))))
				.then(Commands.literal("replace")
					.requires(context -> context.hasPermissionLevel(2))
						.then(Commands.argument("block", BlockStateArgument.blockState())
							.then(Commands.argument("replace", BlockStateArgument.blockState())
								.executes(context -> replace(context.getSource(), BlockStateArgument.getBlockState(context, "block"), BlockStateArgument.getBlockState(context, "replace"))))))
				.then(Commands.literal("clone")
					.requires(context -> context.hasPermissionLevel(2))
					.executes(context -> clone(context.getSource(), EnumMask.MASKED))
						.then(Commands.literal("filtered")
							.then(Commands.argument("filter", StringBlockPredicateArgument.blockPredicate())
								.executes(context -> clone(context.getSource(), StringBlockPredicateArgument.getBlockPredicate(context, "filter")))))
						.then(Commands.literal("masked")
								.executes(context -> clone(context.getSource(), EnumMask.MASKED)))
						.then(Commands.literal("replace")
							.executes(context -> clone(context.getSource(), EnumMask.REPLACE)))));
	}
	
	private static int pos1(CommandSource source) throws CommandSyntaxException
	{
		BlockHelper.setPos1(BlockHelper.getFocusedBlockPos());
		BlockPos pos = BlockHelper.getPos1();
		ResourceLocation block = ForgeRegistries.BLOCKS.getKey(BlockHelper.getBlock(pos));
		CommandHelper.sendFeedback(source, "Set first position to " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " (" + block + ")");
		return 1;
	}
	
	private static int pos2(CommandSource source) throws CommandSyntaxException
	{
		BlockHelper.setPos2(BlockHelper.getFocusedBlockPos());
		BlockPos pos = BlockHelper.getPos2();
		ResourceLocation block = ForgeRegistries.BLOCKS.getKey(BlockHelper.getBlock(pos));
		CommandHelper.sendFeedback(source, "Set second position to " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " (" + block + ")");
		return 1;
	}
	
	private static int fill(CommandSource source, BlockStateInput block)
	{
		BuilderFill builder = new BuilderFill();
		builder.setBlock1(new BlockResourceLocation(block.getState().getBlock().getRegistryName(), block.getState(), block.tag));
		CommandHelper.sendCommand(builder);
		return 1;
	}
	
	private static int replace(CommandSource source, BlockStateInput block, BlockStateInput replace)
	{
		BuilderFill builder = new BuilderFill();
		builder.setPosition1(BlockHelper.getPos1());
		builder.setPosition2(BlockHelper.getPos2());
		builder.setBlock1(new BlockResourceLocation(block.getState().getBlock().getRegistryName(), block.getState(), block.tag));
		builder.setBlock2(new BlockResourceLocation(replace.getState().getBlock().getRegistryName(), replace.getState(), replace.tag));
		CommandHelper.sendCommand(builder);
		return 1;
	}
	
	private static int clone(CommandSource source, String filter)
	{
		BuilderClone builder = new BuilderClone();
		builder.setPosition1(BlockHelper.getPos1());
		builder.setPosition2(BlockHelper.getPos2());
		builder.setFilter(filter);
		CommandHelper.sendCommand(builder);
		return 1;
	}
	
	private static int clone(CommandSource source, EnumMask mask)
	{
		BuilderClone builder = new BuilderClone();
		builder.setPosition1(BlockHelper.getPos1());
		builder.setPosition2(BlockHelper.getPos2());
		builder.setMask(mask);
		CommandHelper.sendCommand(builder);
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
		
		public static String getBlockPredicate(CommandContext<CommandSource> context, String name) throws CommandSyntaxException
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
