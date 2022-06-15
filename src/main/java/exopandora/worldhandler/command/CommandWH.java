package exopandora.worldhandler.command;

import java.util.List;

import javax.annotation.Nullable;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.ParsedCommandNode;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import exopandora.worldhandler.builder.argument.Coordinate;
import exopandora.worldhandler.builder.argument.Coordinate.Type;
import exopandora.worldhandler.builder.impl.CloneCommandBuilder;
import exopandora.worldhandler.builder.impl.FillCommandBuilder;
import exopandora.worldhandler.util.BlockHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.commands.arguments.blocks.BlockPredicateArgument;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.ForgeRegistries;

public class CommandWH
{
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext)
	{
		dispatcher.register(Commands.literal("wh")
				.then(Commands.literal("pos1")
					.executes(context -> pos1(context.getSource())))
				.then(Commands.literal("pos2")
					.executes(context -> pos2(context.getSource())))
				.then(Commands.literal("fill")
					.requires(source -> source.hasPermission(2))
						.then(Commands.argument("block", BlockStateArgument.block(buildContext))
							.executes(context -> fill(context.getSource(), BlockStateArgument.getBlock(context, "block")))))
				.then(Commands.literal("replace")
					.requires(source -> source.hasPermission(2))
						.then(Commands.argument("filter", BlockPredicateArgument.blockPredicate(buildContext))
								.then(Commands.argument("block", BlockStateArgument.block(buildContext))
								.executes(context -> replace(context.getSource(), getCommandNode("filter", context.getNodes()).getRange().get(context.getInput()), BlockStateArgument.getBlock(context, "block"))))))
				.then(Commands.literal("clone")
					.requires(source -> source.hasPermission(2))
					.executes(context -> clone(context.getSource(), "masked", null))
						.then(Commands.literal("filtered")
							.then(Commands.argument("filter", BlockPredicateArgument.blockPredicate(buildContext))
								.executes(context -> clone(context.getSource(), "filter", getCommandNode("filter", context.getNodes()).getRange().get(context.getInput())))))
						.then(Commands.literal("masked")
								.executes(context -> clone(context.getSource(), "masked", null)))
						.then(Commands.literal("replace")
							.executes(context -> clone(context.getSource(), "replace", null)))));
	}
	
	private static int pos1(CommandSourceStack source) throws CommandSyntaxException
	{
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> 
		{
			BlockHelper.pos1().set(BlockHelper.getFocusedBlockPos());
			BlockPos pos = BlockHelper.pos1();
			ResourceLocation block = ForgeRegistries.BLOCKS.getKey(BlockHelper.getBlock(pos));
			CommandHelper.sendFeedback(source, "Set first position to " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " (" + block + ")");
		});
		
		return 1;
	}
	
	private static int pos2(CommandSourceStack source) throws CommandSyntaxException
	{
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> 
		{
			BlockHelper.pos2().set(BlockHelper.getFocusedBlockPos());
			BlockPos pos = BlockHelper.pos2();
			ResourceLocation block = ForgeRegistries.BLOCKS.getKey(BlockHelper.getBlock(pos));
			CommandHelper.sendFeedback(source, "Set second position to " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " (" + block + ")");
		});
		
		return 1;
	}
	
	private static int fill(CommandSourceStack source, BlockInput block)
	{
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> 
		{
			FillCommandBuilder builder = new FillCommandBuilder();
			builder.from().set(BlockHelper.pos1());
			builder.to().set(BlockHelper.pos2());
			builder.block().set(block.getState(), block.tag);
			CommandHelper.sendCommand(source.getTextName(), builder, FillCommandBuilder.Label.FILL);
		});
		
		return 1;
	}
	
	private static int replace(CommandSourceStack source, String filter, BlockInput block)
	{
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> 
		{
			FillCommandBuilder builder = new FillCommandBuilder();
			builder.from().set(BlockHelper.pos1());
			builder.to().set(BlockHelper.pos2());
			builder.block().set(block.getState(), block.tag);
			builder.filter().deserialize(filter);
			CommandHelper.sendCommand(source.getTextName(), builder, FillCommandBuilder.Label.REPLACE);
		});
		
		return 1;
	}
	
	private static int clone(CommandSourceStack source, String mask, String filter)
	{
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> 
		{
			CloneCommandBuilder builder = new CloneCommandBuilder();
			builder.begin().set(BlockHelper.pos1());
			builder.end().set(BlockHelper.pos2());
			builder.destination().setX(new Coordinate.Ints(Type.RELATIVE));
			builder.destination().setY(new Coordinate.Ints(Type.RELATIVE));
			builder.destination().setZ(new Coordinate.Ints(Type.RELATIVE));
			
			switch(mask)
			{
				case "filtered":
					builder.filter().deserialize(filter);
					CommandHelper.sendCommand(source.getTextName(), builder, CloneCommandBuilder.Label.FILTERED);
					break;
				case "masked":
					CommandHelper.sendCommand(source.getTextName(), builder, CloneCommandBuilder.Label.MASKED);
					break;
				case "replace":
					CommandHelper.sendCommand(source.getTextName(), builder, CloneCommandBuilder.Label.REPLACE);
					break;
			}
		});
		
		return 1;
	}
	
	@Nullable
	private static <T> ParsedCommandNode<T> getCommandNode(String name, List<ParsedCommandNode<T>> nodes)
	{
		for(ParsedCommandNode<T> node : nodes)
		{
			if(name.equals(node.getNode().getName()))
			{
				return node;
			}
		}
		
		return null;
	}
}
