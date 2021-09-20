package exopandora.worldhandler.builder.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.types.ArgumentType;
import exopandora.worldhandler.builder.types.Coordinate.EnumType;
import exopandora.worldhandler.builder.types.CoordinateInt;
import exopandora.worldhandler.util.EnumHelper;
import net.minecraft.core.BlockPos;

public class BuilderClone extends BuilderDoubleBlockPos
{
	public BuilderClone()
	{
		this.setX(new CoordinateInt(EnumType.GLOBAL));
		this.setY(new CoordinateInt(EnumType.GLOBAL));
		this.setZ(new CoordinateInt(EnumType.GLOBAL));
		this.setMask(EnumMask.values()[0]);
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
		this.setNode(6, x);
	}
	
	public void setY(CoordinateInt y)
	{
		this.setNode(7, y);
	}
	
	public void setZ(CoordinateInt z)
	{
		this.setNode(8, z);
	}
	
	@Nonnull
	public CoordinateInt getXCoordiante()
	{
		return this.getNodeAsCoordinateInt(6);
	}
	
	@Nonnull
	public CoordinateInt getYCoordiante()
	{
		return this.getNodeAsCoordinateInt(7);
	}
	
	@Nonnull
	public CoordinateInt getZCoordiante()
	{
		return this.getNodeAsCoordinateInt(8);
	}
	
	public double getX()
	{
		return this.getXCoordiante().getValue();
	}
	
	public double getY()
	{
		return this.getYCoordiante().getValue();
	}
	
	public double getZ()
	{
		return this.getZCoordiante().getValue();
	}
	
	public BlockPos getBlockPos()
	{
		return new BlockPos(this.getX(), this.getY(), this.getZ());
	}
	
	public void setMask(EnumMask mask)
	{
		this.setNode(9, mask != null ? mask.toString() : null);
	}
	
	@Nullable
	public EnumMask getMask()
	{
		return EnumHelper.valueOf(this.getNodeAsString(9), EnumMask.class);
	}
	
	public void setFilter(String filter)
	{
		if(filter != null)
		{
			this.setMask(EnumMask.FILTERED);
		}
		
		this.setNode(10, filter);
	}
	
	@Nullable
	public String getFilter()
	{
		if(EnumMask.FILTERED.equals(this.getMask()))
		{
			return this.getNodeAsString(10);
		}
		
		return null;
	}
	
	@Override
	public String getCommandName()
	{
		return "clone";
	}
	
	@Override
	public final CommandSyntax getSyntax()
	{
		CommandSyntax syntax = new CommandSyntax();
		
		syntax.addRequired("x1", ArgumentType.COORDINATE_INT);
		syntax.addRequired("y1", ArgumentType.COORDINATE_INT);
		syntax.addRequired("z1", ArgumentType.COORDINATE_INT);
		syntax.addRequired("x2", ArgumentType.COORDINATE_INT);
		syntax.addRequired("y2", ArgumentType.COORDINATE_INT);
		syntax.addRequired("z2", ArgumentType.COORDINATE_INT);
		syntax.addRequired("x", ArgumentType.COORDINATE_INT);
		syntax.addRequired("y", ArgumentType.COORDINATE_INT);
		syntax.addRequired("z", ArgumentType.COORDINATE_INT);
		syntax.addOptional("mask", ArgumentType.STRING);
		syntax.addOptional("filter", ArgumentType.STRING);
		
		return syntax;
	}
	
	public static enum EnumMask
	{
		FILTERED,
		MASKED,
		REPLACE;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
}
