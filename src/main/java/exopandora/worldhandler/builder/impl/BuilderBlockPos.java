package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilderNBT;
import exopandora.worldhandler.builder.types.CoordinateInt;
import net.minecraft.core.BlockPos;

public abstract class BuilderBlockPos extends CommandBuilderNBT
{
	private final int offset;
	
	public BuilderBlockPos(int offset)
	{
		this.offset = offset;
	}
	
	public void setPosition(BlockPos pos)
	{
		this.setX(pos.getX());
		this.setY(pos.getY());
		this.setZ(pos.getZ());
	}
	
	public void setX(int x)
	{
		this.setX(new CoordinateInt(x));
	}
	
	public void setY(int y)
	{
		this.setY(new CoordinateInt(y));
	}
	
	public void setZ(int z)
	{
		this.setZ(new CoordinateInt(z));
	}
	
	public void setX(CoordinateInt x)
	{
		this.setNode(this.offset, x);
	}
	
	public void setY(CoordinateInt y)
	{
		this.setNode(this.offset + 1, y);
	}
	
	public void setZ(CoordinateInt z)
	{
		this.setNode(this.offset + 2, z);
	}
	
	public CoordinateInt getXCoordinate()
	{
		return this.getNodeAsCoordinateInt(this.offset);
	}
	
	public CoordinateInt getYCoordinate()
	{
		return this.getNodeAsCoordinateInt(this.offset + 1);
	}
	
	public CoordinateInt getZCoordinate()
	{
		return this.getNodeAsCoordinateInt(this.offset + 2);
	}
	
	public int getX()
	{
		return this.getXCoordinate().getValue();
	}
	
	public int getY()
	{
		return this.getYCoordinate().getValue();
	}
	
	public int getZ()
	{
		return this.getZCoordinate().getValue();
	}
	
	public BlockPos getBlockPos()
	{
		return new BlockPos(this.getX(), this.getY(), this.getZ());
	}
}
