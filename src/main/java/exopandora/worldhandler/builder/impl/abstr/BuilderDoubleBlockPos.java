package exopandora.worldhandler.builder.impl.abstr;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.types.Coordinate;
import exopandora.worldhandler.helper.BlockHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
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
	
	public void setX1(float x)
	{
		this.setX1(new Coordinate(x));
	}
	
	public void setY1(float y)
	{
		this.setY1(new Coordinate(y));
	}
	
	public void setZ1(float z)
	{
		this.setZ1(new Coordinate(z));
	}
	
	public void setX1(Coordinate x)
	{
		this.setNode(0, x);
		BlockHelper.setPos1(BlockHelper.setX(BlockHelper.getPos1(), x.getValue()));
	}
	
	public void setY1(Coordinate y)
	{
		this.setNode(1, y);
		BlockHelper.setPos1(BlockHelper.setY(BlockHelper.getPos1(), y.getValue()));
	}
	
	public void setZ1(Coordinate z)
	{
		this.setNode(2, z);
		BlockHelper.setPos1(BlockHelper.setZ(BlockHelper.getPos1(), z.getValue()));
	}
	
	public Coordinate getX1Coordiante()
	{
		return this.getNodeAsCoordinate(0);
	}
	
	public Coordinate getY1Coordiante()
	{
		return this.getNodeAsCoordinate(1);
	}
	
	public Coordinate getZ1Coordiante()
	{
		return this.getNodeAsCoordinate(2);
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
	
	public void setX2(float x)
	{
		this.setX2(new Coordinate(x));
	}
	
	public void setY2(float y)
	{
		this.setY2(new Coordinate(y));
	}
	
	public void setZ2(float z)
	{
		this.setZ2(new Coordinate(z));
	}
	
	public void setX2(Coordinate x)
	{
		this.setNode(3, x);
		BlockHelper.setPos2(BlockHelper.setX(BlockHelper.getPos2(), x.getValue()));
	}
	
	public void setY2(Coordinate y)
	{
		this.setNode(4, y);
		BlockHelper.setPos2(BlockHelper.setY(BlockHelper.getPos2(), y.getValue()));
	}
	
	public void setZ2(Coordinate z)
	{
		this.setNode(5, z);
		BlockHelper.setPos2(BlockHelper.setZ(BlockHelper.getPos2(), z.getValue()));
	}
	
	public Coordinate getX2Coordiante()
	{
		return this.getNodeAsCoordinate(3);
	}
	
	public Coordinate getY2Coordiante()
	{
		return this.getNodeAsCoordinate(4);
	}
	
	public Coordinate getZ2Coordiante()
	{
		return this.getNodeAsCoordinate(5);
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
	
	public <T extends BuilderDoubleBlockPos> T addPositionObservers()
	{
		BlockHelper.addPos1Observer(this::setPosition1);
		BlockHelper.addPos2Observer(this::setPosition2);
		
		return (T) this;
	}
}
