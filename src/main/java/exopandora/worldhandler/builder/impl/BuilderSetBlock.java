package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.types.ArgumentType;
import exopandora.worldhandler.builder.types.BlockResourceLocation;
import exopandora.worldhandler.builder.types.CoordinateInt;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.IProperty;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BuilderSetBlock extends BuilderBlockPos
{
	private final BlockResourceLocation blockResourceLocation = new BlockResourceLocation();
	
	public BuilderSetBlock()
	{
		super(0);
	}
	
	public BuilderSetBlock(BlockPos pos, ResourceLocation block, EnumMode mode)
	{
		this();
		this.setPosition(pos);
		this.setBlock(block);
		this.setMode(mode);
	}
	
	public <T extends Comparable<T>> BuilderSetBlock(CoordinateInt x, CoordinateInt y, CoordinateInt z, ResourceLocation block, EnumMode mode)
	{
		this();
		this.setX(x);
		this.setY(y);
		this.setZ(z);
		this.setBlock(block);
		this.setMode(mode);
	}
	
	public <T extends Comparable<T>> void setState(IProperty<T> property, T value)
	{
		this.blockResourceLocation.setProperty(property, value);
		this.setBlock(this.blockResourceLocation);
	}
	
	public void setBlock(ResourceLocation block)
	{
		this.blockResourceLocation.setResourceLocation(block);
		this.setBlock(this.blockResourceLocation);
	}
	
	public void setMode(EnumMode mode)
	{
		this.setNode(4, mode.toString());
	}
	
	public void setBlockNBT(CompoundNBT nbt)
	{
		this.blockResourceLocation.setNBT(nbt);
		this.setBlock(this.blockResourceLocation);
	}
	
	protected void setBlock(BlockResourceLocation block)
	{
		this.setNode(3, this.blockResourceLocation);
	}
	
	@Override
	public void setNBT(CompoundNBT nbt)
	{
		
	}
	
	@Override
	public String getCommandName()
	{
		return "setblock";
	}
	
	@Override
	public final CommandSyntax getSyntax()
	{
		CommandSyntax syntax = new CommandSyntax();
		
		syntax.addRequired("x", ArgumentType.COORDINATE_INT);
		syntax.addRequired("y", ArgumentType.COORDINATE_INT);
		syntax.addRequired("z", ArgumentType.COORDINATE_INT);
		syntax.addRequired("block", ArgumentType.BLOCK_RESOURCE_LOCATION);
		syntax.addOptional("mode", ArgumentType.STRING);
		
		return syntax;
	}
	
	@OnlyIn(Dist.CLIENT)
	public static enum EnumMode
	{
		KEEP,
		REPLACE,
		DESTROY;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
}
