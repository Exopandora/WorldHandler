package exopandora.worldhandler.builder.impl;

import javax.annotation.Nonnull;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.types.CoordinateInt;
import exopandora.worldhandler.util.BlockHelper;
import net.minecraft.core.BlockPos;

public abstract class BuilderDoubleBlockPos extends CommandBuilder
{
	public BuilderDoubleBlockPos()
	{
		this.setPosition1(BlockHelper.getPos1());
		this.setPosition2(BlockHelper.getPos2());
	}
	
	public void setPosition1(BlockPos pos)
	{
		this.setX1(pos.getX());
		this.setY1(pos.getY());
		this.setZ1(pos.getZ());
	}
	
	public void setX1(int x)
	{
		this.setX1(new CoordinateInt(x));
	}
	
	public void setY1(int y)
	{
		this.setY1(new CoordinateInt(y));
	}
	
	public void setZ1(int z)
	{
		this.setZ1(new CoordinateInt(z));
	}
	
	public void setX1(CoordinateInt x)
	{
		this.setNode(0, x);
	}
	
	public void setY1(CoordinateInt y)
	{
		this.setNode(1, y);
	}
	
	public void setZ1(CoordinateInt z)
	{
		this.setNode(2, z);
	}
	
	@Nonnull
	public CoordinateInt getX1Coordiante()
	{
		return this.getNodeAsCoordinateInt(0);
	}
	
	@Nonnull
	public CoordinateInt getY1Coordiante()
	{
		return this.getNodeAsCoordinateInt(1);
	}
	
	@Nonnull
	public CoordinateInt getZ1Coordiante()
	{
		return this.getNodeAsCoordinateInt(2);
	}
	
	public double getX1()
	{
		return this.getX1Coordiante().getValue();
	}
	
	public double getY1()
	{
		return this.getY1Coordiante().getValue();
	}
	
	public double getZ1()
	{
		return this.getZ1Coordiante().getValue();
	}
	
	public BlockPos getBlockPos1()
	{
		return new BlockPos(this.getX1(), this.getY1(), this.getZ1());
	}
	
	public void setPosition2(BlockPos pos)
	{
		this.setX2(pos.getX());
		this.setY2(pos.getY());
		this.setZ2(pos.getZ());
	}
	
	public void setX2(int x)
	{
		this.setX2(new CoordinateInt(x));
	}
	
	public void setY2(int y)
	{
		this.setY2(new CoordinateInt(y));
	}
	
	public void setZ2(int z)
	{
		this.setZ2(new CoordinateInt(z));
	}
	
	public void setX2(CoordinateInt x)
	{
		this.setNode(3, x);
	}
	
	public void setY2(CoordinateInt y)
	{
		this.setNode(4, y);
	}
	
	public void setZ2(CoordinateInt z)
	{
		this.setNode(5, z);
	}
	
	@Nonnull
	public CoordinateInt getX2Coordiante()
	{
		return this.getNodeAsCoordinateInt(3);
	}
	
	@Nonnull
	public CoordinateInt getY2Coordiante()
	{
		return this.getNodeAsCoordinateInt(4);
	}
	
	@Nonnull
	public CoordinateInt getZ2Coordiante()
	{
		return this.getNodeAsCoordinateInt(5);
	}
	
	public double getX2()
	{
		return this.getX2Coordiante().getValue();
	}
	
	public double getY2()
	{
		return this.getY2Coordiante().getValue();
	}
	
	public double getZ2()
	{
		return this.getZ2Coordiante().getValue();
	}
	
	public BlockPos getBlockPos2()
	{
		return new BlockPos(this.getX2(), this.getY2(), this.getZ2());
	}
}
