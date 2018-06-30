package exopandora.worldhandler.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.ArrayUtils;

import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.builder.impl.BuilderSetblock;
import exopandora.worldhandler.builder.types.Coordinate;
import exopandora.worldhandler.config.ConfigSettings;
import exopandora.worldhandler.util.UtilPlayer;
import io.netty.buffer.Unpooled;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlockHelper
{
	private static BlockPos POS_1 = BlockPos.ORIGIN;
	private static BlockPos POS_2 = BlockPos.ORIGIN;
	private static final List<Consumer<BlockPos>> POS_1_OBSERVERS = new ArrayList<Consumer<BlockPos>>();
	private static final List<Consumer<BlockPos>> POS_2_OBSERVERS = new ArrayList<Consumer<BlockPos>>();
	private static final Block[] BLACKLIST = new Block[] {Blocks.AIR, Blocks.WATER, Blocks.LAVA};
	
	public static BlockPos getFocusedBlockPos()
	{
		RayTraceResult rayTrace = Minecraft.getMinecraft().objectMouseOver;
		
		if(rayTrace != null)
		{
			if(rayTrace.typeOfHit.equals(RayTraceResult.Type.BLOCK))
			{
				BlockPos position = rayTrace.getBlockPos();
				
				if(!ArrayUtils.contains(BLACKLIST, Minecraft.getMinecraft().world.getBlockState(position).getBlock()))
				{
					return position;
				}
			}
		}
		
		return Minecraft.getMinecraft().player.getPosition();
	}
	
	public static boolean isFocusedBlockEqualTo(Block block)
	{
		return Block.isEqualTo(getFocusedBlock(), block);
	}
	
	public static Block getFocusedBlock()
	{
		return Minecraft.getMinecraft().world.getBlockState(getFocusedBlockPos()).getBlock();
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
		return POS_1;
	}
	
	public static void setPos1(BlockPos pos1)
	{
		if(POS_1 != null && !POS_1.equals(pos1))
		{
			POS_1 = pos1;
			
			for(Consumer<BlockPos> observer : POS_1_OBSERVERS)
			{
				observer.accept(POS_1);
			}
		}
	}
	
	@Nonnull
	public static BlockPos getPos2()
	{
		return POS_2;
	}
	
	public static void setPos2(BlockPos pos2)
	{
		if(POS_2 != null && !POS_2.equals(pos2))
		{
			POS_2 = pos2;
			
			for(Consumer<BlockPos> observer : POS_2_OBSERVERS)
			{
				observer.accept(POS_2);
			}
		}
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
	
	public static boolean setCommandBlockNearPlayer(String command)
	{
		if(UtilPlayer.canIssueCommand())
		{
			BlockPos player = new BlockPos(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY, Minecraft.getMinecraft().player.posZ);
			BlockPos block = player;
			BlockPos button = player;
			
			int meta = 0;
			
			switch(UtilPlayer.getPlayerDirection())
			{
				case 0:
					block = block.add(0, 0, 2);
					button = button.add(0, 0, 1);
					meta = 4;
					break;
				case 1:
					block = block.add(-2, 0, 0);
					button = button.add(-1, 0, 0);
					meta = 1;
					break;
				case 2:
					block = block.add(0, 0, -2);
					button = button.add(0, 0, -1);
					meta = 3;
					break;
				case 3:
					block = block.add(2, 0, 0);
					button = button.add(1, 0, 0);
					meta = 2;
					break;
			}
			
			boolean flag = false;
			
			if(Minecraft.getMinecraft().world.isAirBlock(block))
			{
				Minecraft.getMinecraft().player.sendChatMessage(new BuilderSetblock(block, Blocks.COMMAND_BLOCK.getRegistryName(), 0, ConfigSettings.getMode()).toActualCommand());
				Minecraft.getMinecraft().player.sendChatMessage(new BuilderSetblock(button, Blocks.WOODEN_BUTTON.getRegistryName(), meta, ConfigSettings.getMode()).toActualCommand());
				flag = true;
			}
			
			if(Minecraft.getMinecraft().world.getBlockState(block).getBlock().equals(Blocks.COMMAND_BLOCK) || flag)
			{
		        if(Minecraft.getMinecraft().getConnection() != null)
		        {
					PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
			        packetbuffer.writeInt(block.getX());
			        packetbuffer.writeInt(block.getY());
			        packetbuffer.writeInt(block.getZ());
			        packetbuffer.writeString(command);
			        packetbuffer.writeBoolean(true);
			        packetbuffer.writeString(TileEntityCommandBlock.Mode.REDSTONE.name());
			        packetbuffer.writeBoolean(false);
			        packetbuffer.writeBoolean(false);
			        
		        	Minecraft.getMinecraft().getConnection().sendPacket(new CPacketCustomPayload("MC|AutoCmd", packetbuffer));
			        
		        	return true;
		        }
			}
		}
		
		return false;
	}
	
	public static void setBlockNearPlayer(Block block, byte southMeta, byte westMeta, byte northMeta, byte eastMeta)
	{
		int direction = UtilPlayer.getPlayerDirection();
		
		switch(direction)
		{
			case 0:
				WorldHandler.sendCommand(new BuilderSetblock(new Coordinate(), new Coordinate(), new Coordinate(2, true), block.getRegistryName(), southMeta, ConfigSettings.getMode()));
				break;
			case 1:
				WorldHandler.sendCommand(new BuilderSetblock(new Coordinate(-2, true), new Coordinate(), new Coordinate(), block.getRegistryName(), westMeta, ConfigSettings.getMode()));
				break;
			case 2:
				WorldHandler.sendCommand(new BuilderSetblock(new Coordinate(), new Coordinate(), new Coordinate(-2, true), block.getRegistryName(), northMeta, ConfigSettings.getMode()));
				break;
			case 3:
				WorldHandler.sendCommand(new BuilderSetblock(new Coordinate(2, true), new Coordinate(), new Coordinate(), block.getRegistryName(), eastMeta, ConfigSettings.getMode()));
				break;
		}
	}
}
