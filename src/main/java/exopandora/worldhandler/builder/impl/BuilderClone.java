package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.impl.abstr.BuilderDoubleBlockPos;
import exopandora.worldhandler.builder.types.Coordinate;
import exopandora.worldhandler.builder.types.Type;
import exopandora.worldhandler.helper.EnumHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BuilderClone extends BuilderDoubleBlockPos
{
	public BuilderClone()
	{
		this.setX(new Coordinate());
		this.setY(new Coordinate());
		this.setZ(new Coordinate());
		this.setMask(EnumMask.values()[0]);
		this.setNode(10, "force");
	}
	
	public void setPosition(BlockPos pos)
	{
		this.setX(pos.getX());
		this.setY(pos.getY());
		this.setZ(pos.getZ());
	}
	
	public void setX(float x)
	{
		this.setX(new Coordinate(x));
	}
	
	public void setY(float y)
	{
		this.setY(new Coordinate(y));
	}
	
	public void setZ(float z)
	{
		this.setZ(new Coordinate(z));
	}
	
	public void setX(Coordinate x)
	{
		this.setNode(6, x);
	}
	
	public void setY(Coordinate y)
	{
		this.setNode(7, y);
	}
	
	public void setZ(Coordinate z)
	{
		this.setNode(8, z);
	}
	
	public Coordinate getXCoordiante()
	{
		return this.getNodeAsCoordinate(6);
	}
	
	public Coordinate getYCoordiante()
	{
		return this.getNodeAsCoordinate(7);
	}
	
	public Coordinate getZCoordiante()
	{
		return this.getNodeAsCoordinate(8);
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
		return EnumHelper.valueOf(EnumMask.class, this.getNodeAsString(9));
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
		
		syntax.addRequired("x1", Type.COORDINATE);
		syntax.addRequired("y1", Type.COORDINATE);
		syntax.addRequired("z1", Type.COORDINATE);
		syntax.addRequired("x2", Type.COORDINATE);
		syntax.addRequired("y2", Type.COORDINATE);
		syntax.addRequired("z2", Type.COORDINATE);
		syntax.addRequired("x", Type.COORDINATE);
		syntax.addRequired("y", Type.COORDINATE);
		syntax.addRequired("z", Type.COORDINATE);
		syntax.addOptional("mask_mode", Type.STRING);
		syntax.addOptional("clone_mode", Type.STRING);
		
		return syntax;
	}
	
	@SideOnly(Side.CLIENT)
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
