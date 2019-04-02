package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.impl.abstr.BuilderDoubleBlockPos;
import exopandora.worldhandler.builder.types.Coordinate.CoordinateType;
import exopandora.worldhandler.builder.types.CoordinateInt;
import exopandora.worldhandler.builder.types.Type;
import exopandora.worldhandler.helper.EnumHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BuilderClone extends BuilderDoubleBlockPos
{
	public BuilderClone()
	{
		this.setX(new CoordinateInt(CoordinateType.GLOBAL));
		this.setY(new CoordinateInt(CoordinateType.GLOBAL));
		this.setZ(new CoordinateInt(CoordinateType.GLOBAL));
		this.setMask(EnumMask.values()[0]);
		this.setNode(10, "force");
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
	
	public CoordinateInt getXCoordiante()
	{
		return this.getNodeAsCoordinateInt(6);
	}
	
	public CoordinateInt getYCoordiante()
	{
		return this.getNodeAsCoordinateInt(7);
	}
	
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
	
	public EnumMask getMask()
	{
		return EnumHelper.valueOf(this.getNodeAsString(9), EnumMask.class);
	}
	
	@Override
	public String getCommandName()
	{
		return "clone";
	}
	
	@Override
	public final Syntax getSyntax()
	{
		Syntax syntax = new Syntax();
		
		syntax.addRequired("x1", Type.COORDINATE_INT);
		syntax.addRequired("y1", Type.COORDINATE_INT);
		syntax.addRequired("z1", Type.COORDINATE_INT);
		syntax.addRequired("x2", Type.COORDINATE_INT);
		syntax.addRequired("y2", Type.COORDINATE_INT);
		syntax.addRequired("z2", Type.COORDINATE_INT);
		syntax.addRequired("x", Type.COORDINATE_INT);
		syntax.addRequired("y", Type.COORDINATE_INT);
		syntax.addRequired("z", Type.COORDINATE_INT);
		syntax.addOptional("mask", Type.STRING);
		syntax.addOptional("filter", Type.STRING);
		
		return syntax;
	}
	
	@OnlyIn(Dist.CLIENT)
	public static enum EnumMask
	{
		REPLACE,
		MASKED,
		FILTERED;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
}
