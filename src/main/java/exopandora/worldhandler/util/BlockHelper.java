package exopandora.worldhandler.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.ArrayUtils;

import exopandora.worldhandler.builder.impl.BuilderExecute;
import exopandora.worldhandler.builder.impl.BuilderExecute.EnumMode;
import exopandora.worldhandler.builder.impl.BuilderFill;
import exopandora.worldhandler.builder.impl.BuilderSetBlock;
import exopandora.worldhandler.builder.types.BlockResourceLocation;
import exopandora.worldhandler.builder.types.Coordinate.EnumType;
import exopandora.worldhandler.builder.types.CoordinateInt;
import exopandora.worldhandler.config.Config;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.client.CUpdateCommandBlockPacket;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.CommandBlockTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockHelper
{
	private static BlockPos pos1 = BlockPos.ZERO;
	private static BlockPos pos2 = BlockPos.ZERO;
	private static final List<Consumer<BlockPos>> POS_1_OBSERVERS = new ArrayList<Consumer<BlockPos>>();
	private static final List<Consumer<BlockPos>> POS_2_OBSERVERS = new ArrayList<Consumer<BlockPos>>();
	private static final Block[] BLACKLIST = new Block[] {Blocks.AIR, Blocks.WATER, Blocks.LAVA};
	
	@Nonnull
	public static BlockPos getFocusedBlockPos()
	{
		World world = Minecraft.getInstance().level;
		RayTraceResult result = Minecraft.getInstance().hitResult;
		
		if(result != null && Type.BLOCK.equals(result.getType()) && world != null)
		{
			BlockRayTraceResult blockResult = (BlockRayTraceResult) result;
			
			if(!ArrayUtils.contains(BLACKLIST, world.getBlockState(blockResult.getBlockPos()).getBlock()))
			{
				return blockResult.getBlockPos();
			}
		}
		
		PlayerEntity player = Minecraft.getInstance().player;
		
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
		World world = Minecraft.getInstance().level;
		
		if(world != null)
		{
			return world.getBlockState(pos).getBlock();
		}
		
		return Blocks.AIR;
	}
	
	public static BlockPos setX(BlockPos pos, double x)
	{
		return new BlockPos(x, pos.getY(), pos.getZ());
	}
	
	public static BlockPos setY(BlockPos pos, double y)
	{
		return new BlockPos(pos.getX(), y, pos.getZ());
	}
	
	public static BlockPos setZ(BlockPos pos, double z)
	{
		return new BlockPos(pos.getX(), pos.getY(), z);
	}
	
	@Nonnull
	public static BlockPos getPos1()
	{
		return BlockHelper.pos1;
	}
	
	public static void setPos1(BlockPos pos)
	{
		if(pos != null && !BlockHelper.pos1.equals(pos))
		{
			BlockHelper.pos1 = pos;
			
			for(Consumer<BlockPos> observer : POS_1_OBSERVERS)
			{
				observer.accept(BlockHelper.pos1);
			}
		}
	}
	
	@Nonnull
	public static BlockPos getPos2()
	{
		return BlockHelper.pos2;
	}
	
	public static void setPos2(BlockPos pos)
	{
		if(pos != null && !BlockHelper.pos2.equals(pos))
		{
			BlockHelper.pos2 = pos;
			
			for(Consumer<BlockPos> observer : POS_2_OBSERVERS)
			{
				observer.accept(BlockHelper.pos2);
			}
		}
	}
	
	public static <T> T addPositionObservers(T observer, Function<T, Consumer<BlockPos>> pos1generator, Function<T, Consumer<BlockPos>> pos2generator)
	{
		BlockHelper.addPos1Observer(pos1generator.apply(observer));
		BlockHelper.addPos2Observer(pos2generator.apply(observer));
		return observer;
	}
	
	public static void addPos1Observer(Consumer<BlockPos> observer)
	{
		POS_1_OBSERVERS.add(observer);
	}
	
	public static void removePos1Observer(Consumer<BlockPos> observer)
	{
		POS_1_OBSERVERS.remove(observer);
	}
	
	public static void addPos2Observer(Consumer<BlockPos> observer)
	{
		POS_2_OBSERVERS.add(observer);
	}
	
	public static void removePos2Observer(Consumer<BlockPos> observer)
	{
		POS_2_OBSERVERS.add(observer);
	}
	
	public static boolean setCommandBlockNearPlayer(String player, String command)
	{
		if(CommandHelper.canPlayerIssueCommand() && Minecraft.getInstance().getConnection() != null)
		{
			BlockPos pos = Minecraft.getInstance().player.blockPosition().offset(0, 3, 0);
			
			BuilderFill placeFill = new BuilderFill();
			placeFill.setPosition1(pos);
			placeFill.setPosition2(pos.above());
			placeFill.setBlock1(new BlockResourceLocation(Blocks.COMMAND_BLOCK.getRegistryName()));
			
			BuilderFill removeFill = new BuilderFill();
			removeFill.setX1(new CoordinateInt(0, EnumType.GLOBAL));
			removeFill.setY1(new CoordinateInt(-1, EnumType.GLOBAL));
			removeFill.setZ1(new CoordinateInt(0, EnumType.GLOBAL));
			removeFill.setX2(new CoordinateInt(0, EnumType.GLOBAL));
			removeFill.setY2(new CoordinateInt(0, EnumType.GLOBAL));
			removeFill.setZ2(new CoordinateInt(0, EnumType.GLOBAL));
			removeFill.setBlock1(new BlockResourceLocation(Blocks.AIR.getRegistryName()));
			
			Minecraft.getInstance().player.chat(placeFill.toActualCommand());
			
			BuilderExecute wrapped = new BuilderExecute();
			wrapped.setMode1(EnumMode.AT);
			wrapped.setTarget(player);
			wrapped.setMode2(EnumMode.RUN);
			wrapped.setCommand(command);
			
			Minecraft.getInstance().getConnection().send(new CUpdateCommandBlockPacket(pos, wrapped.toActualCommand(), CommandBlockTileEntity.Mode.REDSTONE, true, false, true));
			Minecraft.getInstance().getConnection().send(new CUpdateCommandBlockPacket(pos.above(), removeFill.toActualCommand(), CommandBlockTileEntity.Mode.REDSTONE, true, false, true));
			
			return true;
		}
		
		return false;
	}
	
	public static void setBlockNearPlayer(String player, Block block)
	{
		BuilderSetBlock builder = new BuilderSetBlock(new CoordinateInt(EnumType.LOCAL), new CoordinateInt(EnumType.LOCAL), new CoordinateInt(2, EnumType.LOCAL), block.getRegistryName(), Config.getSettings().getBlockPlacingMode());
		
		if(Minecraft.getInstance().player != null)
		{
			builder.setState(BlockStateProperties.HORIZONTAL_FACING, Minecraft.getInstance().player.getDirection().getOpposite());
		}
		
		CommandHelper.sendCommand(player, builder);
	}
}
