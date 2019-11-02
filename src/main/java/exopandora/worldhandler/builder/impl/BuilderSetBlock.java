package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.types.BlockResourceLocation;
import exopandora.worldhandler.builder.types.CoordinateInt;
import exopandora.worldhandler.builder.types.ArgumentType;
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
}
