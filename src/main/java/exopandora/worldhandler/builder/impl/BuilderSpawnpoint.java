package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.types.Coordinate.CoordinateType;
import exopandora.worldhandler.builder.types.CoordinateInt;
import exopandora.worldhandler.builder.types.Type;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BuilderSpawnpoint extends CommandBuilder
{
	public BuilderSpawnpoint()
	{
		this.setX(new CoordinateInt(CoordinateType.GLOBAL));
		this.setY(new CoordinateInt(CoordinateType.GLOBAL));
		this.setZ(new CoordinateInt(CoordinateType.GLOBAL));
	}
	
	@Override
	public String getCommandName()
	{
		return "spawnpoint";
	}
	
	public void setPlayer(String player)
	{
		this.setNode(0, player);
	}
	
	public void setX(CoordinateInt x)
	{
		this.setNode(1, x);
	}
	
	public void setY(CoordinateInt y)
	{
		this.setNode(2, y);
	}
	
	public void setZ(CoordinateInt z)
	{
		this.setNode(3, z);
	}
	
	@Override
	public final Syntax getSyntax()
	{
		Syntax syntax = new Syntax();
		
		syntax.addRequired("player", Type.STRING);
		syntax.addRequired("x", Type.COORDINATE_INT);
		syntax.addRequired("y", Type.COORDINATE_INT);
		syntax.addRequired("z", Type.COORDINATE_INT);
		
		return syntax;
	}
}
