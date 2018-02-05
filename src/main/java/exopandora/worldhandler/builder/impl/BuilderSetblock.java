package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.impl.abstr.BuilderBlockPos;
import exopandora.worldhandler.builder.types.Coordinate;
import exopandora.worldhandler.builder.types.Type;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class BuilderSetblock extends BuilderBlockPos
{
	@Deprecated
	public BuilderSetblock(BlockPos pos, ResourceLocation block, int meta, String mode)
	{
		this.setPosition(pos);
		this.setBlock(block);
		this.setMetadata(meta);
		this.setMode(mode);
	}
	
	@Deprecated
	public BuilderSetblock(Coordinate x, Coordinate y, Coordinate z, ResourceLocation block, int meta, String mode)
	{
		this.setX(x);
		this.setY(y);
		this.setZ(z);
		this.setBlock(block);
		this.setMetadata(meta);
		this.setMode(mode);
	}
	
	public BuilderSetblock(Coordinate x, Coordinate y, Coordinate z, ResourceLocation block, String mode)
	{
		this(x, y, z, block, 0, mode);
	}
	
	public void setBlock(ResourceLocation block)
	{
		this.setNode(3, block);
	}
	
	@Deprecated
	public void setMetadata(int meta)
	{
		this.setNode(4, meta);
	}
	
	public void setMode(String mode)
	{
		this.setNode(5, mode);
	}
	
	@Override
	public void setNBT(NBTTagCompound nbt)
	{
		this.setNode(6, nbt);
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
		
		syntax.addRequired("x", Type.COORDINATE);
		syntax.addRequired("y", Type.COORDINATE);
		syntax.addRequired("z", Type.COORDINATE);
		syntax.addRequired("block", Type.RESOURCE_LOCATION);
		syntax.addOptional("data_value", Type.INT);
		syntax.addOptional("mode", Type.STRING);
		syntax.addOptional("nbt", Type.NBT);
		
		return syntax;
	}
}
