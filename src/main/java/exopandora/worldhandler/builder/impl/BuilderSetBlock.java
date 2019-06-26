package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.impl.abstr.BuilderBlockPos;
import exopandora.worldhandler.builder.types.BlockResourceLocation;
import exopandora.worldhandler.builder.types.CoordinateInt;
import exopandora.worldhandler.builder.types.Type;
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
	
	public BuilderSetBlock(BlockPos pos, ResourceLocation block, String mode)
	{
		this();
		this.setPosition(pos);
		this.setBlock(block);
		this.setMode(mode);
	}
	
	public BuilderSetBlock(CoordinateInt x, CoordinateInt y, CoordinateInt z, ResourceLocation block, String mode)
	{
		this();
		this.setX(x);
		this.setY(y);
		this.setZ(z);
		this.setBlock(block);
		this.setMode(mode);
	}
	
	public <T extends Comparable<T>> BuilderSetBlock withState(IProperty<T> property, T value)
	{
		this.blockResourceLocation.withState(property, value);
		return this;
	}
	
	public void setBlock(ResourceLocation block)
	{
		this.blockResourceLocation.setResourceLocation(block);
		this.setNode(3, this.blockResourceLocation);
	}
	
	public void setMode(String mode)
	{
		this.setNode(4, mode);
	}
	
	@Override
	public void setNBT(CompoundNBT nbt)
	{
		this.blockResourceLocation.setNBT(nbt);
		this.setNode(3, this.blockResourceLocation);
	}
	
	@Override
	public String getCommandName()
	{
		return "setblock";
	}
	
	@Override
	public final Syntax getSyntax()
	{
		Syntax syntax = new Syntax();
		
		syntax.addRequired("x", Type.COORDINATE_INT);
		syntax.addRequired("y", Type.COORDINATE_INT);
		syntax.addRequired("z", Type.COORDINATE_INT);
		syntax.addRequired("block", Type.BLOCK_RESOURCE_LOCATION);
		syntax.addOptional("mode", Type.STRING);
		
		return syntax;
	}
}
