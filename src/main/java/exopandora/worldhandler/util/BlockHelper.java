package exopandora.worldhandler.util;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.ArrayUtils;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.argument.Coordinate;
import exopandora.worldhandler.builder.impl.ExecuteCommandBuilder;
import exopandora.worldhandler.builder.impl.FillCommandBuilder;
import exopandora.worldhandler.builder.impl.SetBlockCommandBuilder;
import exopandora.worldhandler.builder.impl.ExecuteCommandBuilder.AtOptionalArgument;
import exopandora.worldhandler.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.network.protocol.game.ServerboundSetCommandBlockPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;

public class BlockHelper
{
	private static MutableBlockPos pos1 = new MutableBlockPos(0, -64, 0);
	private static MutableBlockPos pos2 = new MutableBlockPos(0, -64, 0);
	private static final Block[] BLACKLIST = new Block[] {Blocks.AIR, Blocks.WATER, Blocks.LAVA};
	
	@Nonnull
	public static BlockPos getFocusedBlockPos()
	{
		Level level = Minecraft.getInstance().level;
		HitResult result = Minecraft.getInstance().hitResult;
		
		if(result != null && Type.BLOCK.equals(result.getType()) && level != null)
		{
			BlockHitResult blockResult = (BlockHitResult) result;
			
			if(!ArrayUtils.contains(BLACKLIST, level.getBlockState(blockResult.getBlockPos()).getBlock()))
			{
				return blockResult.getBlockPos();
			}
		}
		
		LocalPlayer player = Minecraft.getInstance().player;
		
		if(player != null)
		{
			return player.blockPosition();
		}
		
		return BlockPos.ZERO;
	}
	
	@Nonnull
	public static Block getFocusedBlock()
	{
		return BlockHelper.getBlock(BlockHelper.getFocusedBlockPos());
	}
	
	@Nonnull
	public static Block getBlock(BlockPos pos)
	{
		Level level = Minecraft.getInstance().level;
		
		if(level != null)
		{
			return level.getBlockState(pos).getBlock();
		}
		
		return Blocks.AIR;
	}
	
	public static boolean setCommandBlockNearPlayer(String player, ICommandBuilder command, Object label)
	{
		if(CommandHelper.canPlayerIssueCommand() && Minecraft.getInstance().getConnection() != null)
		{
			BlockPos pos = Minecraft.getInstance().player.blockPosition().offset(0, 3, 0);
			
			FillCommandBuilder place = new FillCommandBuilder();
			place.from().set(pos);
			place.to().set(pos.above());
			place.block().set(Blocks.COMMAND_BLOCK);
			
			FillCommandBuilder remove = new FillCommandBuilder();
			remove.from().setX(new Coordinate.Ints(0, Coordinate.Type.RELATIVE));
			remove.from().setY(new Coordinate.Ints(-1, Coordinate.Type.RELATIVE));
			remove.from().setZ(new Coordinate.Ints(0, Coordinate.Type.RELATIVE));
			remove.to().setX(new Coordinate.Ints(0, Coordinate.Type.RELATIVE));
			remove.to().setY(new Coordinate.Ints(0, Coordinate.Type.RELATIVE));
			remove.to().setZ(new Coordinate.Ints(0, Coordinate.Type.RELATIVE));
			remove.block().set(Blocks.AIR);
			
			Minecraft.getInstance().player.command(place.toCommand(FillCommandBuilder.Label.FILL, false).substring(1));
			
			ExecuteCommandBuilder execute = new ExecuteCommandBuilder();
			AtOptionalArgument at = new AtOptionalArgument(AtOptionalArgument.Label.AT);
			at.targets().setTarget(player);
			execute.modifiers().add(at);
			execute.command().set(command, label);
			
			Minecraft.getInstance().getConnection().send(new ServerboundSetCommandBlockPacket(pos, execute.toCommand(ExecuteCommandBuilder.Label.RUN, false), CommandBlockEntity.Mode.REDSTONE, true, false, true));
			Minecraft.getInstance().getConnection().send(new ServerboundSetCommandBlockPacket(pos.above(), remove.toCommand(FillCommandBuilder.Label.FILL, false), CommandBlockEntity.Mode.REDSTONE, true, false, true));
			
			return true;
		}
		
		return false;
	}
	
	public static void setBlockNearPlayer(String player, Block block)
	{
		SetBlockCommandBuilder builder = new SetBlockCommandBuilder();
		builder.pos().setX(new Coordinate.Ints(Coordinate.Type.LOCAL));
		builder.pos().setY(new Coordinate.Ints(Coordinate.Type.LOCAL));
		builder.pos().setZ(new Coordinate.Ints(2, Coordinate.Type.LOCAL));
		
		BlockState state = block.defaultBlockState();
		
		if(Minecraft.getInstance().player != null && state.hasProperty(BlockStateProperties.HORIZONTAL_FACING))
		{
			state = state.setValue(BlockStateProperties.HORIZONTAL_FACING, Minecraft.getInstance().player.getDirection().getOpposite());
		}
		
		builder.block().set(state);
		
		switch(Config.getSettings().getBlockPlacingMode())
		{
			case KEEP:
				CommandHelper.sendCommand(player, builder, SetBlockCommandBuilder.Label.KEEP);
				break;
			case REPLACE:
				CommandHelper.sendCommand(player, builder, SetBlockCommandBuilder.Label.REPLACE);
				break;
			case DESTROY:
				CommandHelper.sendCommand(player, builder, SetBlockCommandBuilder.Label.DESTROY);
				break;
		}
	}
	
	public static MutableBlockPos pos1()
	{
		return pos1;
	}
	
	public static MutableBlockPos pos2()
	{
		return pos2;
	}
}
