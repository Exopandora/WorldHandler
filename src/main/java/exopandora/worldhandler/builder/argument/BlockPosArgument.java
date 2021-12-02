package exopandora.worldhandler.builder.argument;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;

public class BlockPosArgument implements IArgument
{
	private Coordinate<?> x;
	private Coordinate<?> y;
	private Coordinate<?> z;
	
	protected BlockPosArgument()
	{
		super();
	}
	
	public void setX(@Nullable Coordinate<?> x)
	{
		this.x = x;
	}
	
	public void setY(@Nullable Coordinate<?> y)
	{
		this.y = y;
	}
	
	public void setZ(@Nullable Coordinate<?> z)
	{
		this.z = z;
	}
	
	public void set(@Nullable BlockPos pos)
	{
		if(pos != null)
		{
			this.x = new Coordinate.Ints(pos.getX());
			this.y = new Coordinate.Ints(pos.getY());
			this.z = new Coordinate.Ints(pos.getZ());
		}
		else
		{
			this.x = null;
			this.y = null;
			this.z = null;
		}
	}
	
	@Nullable
	public Coordinate<?> getX()
	{
		return this.x;
	}
	
	@Nullable
	public Coordinate<?> getY()
	{
		return this.y;
	}
	
	@Nullable
	public Coordinate<?> getZ()
	{
		return this.z;
	}
	
	@Nullable
	public BlockPos getBlockPos()
	{
		if(this.x == null && this.y == null && this.z == null)
		{
			return null;
		}
		
		return new BlockPos(this.x.getValue().doubleValue(), this.y.getValue().doubleValue(), this.z.getValue().doubleValue());
	}
	
	@Override
	@Nullable
	public String serialize()
	{
		if(this.x == null || this.y == null || this.z == null)
		{
			return null;
		}
		
		return this.x.serialize() + " " + this.y.serialize() + " " + this.z.serialize();
	}
	
	@Override
	public boolean isDefault()
	{
		return this.x == null && this.y == null && this.z == null;
	}
}
