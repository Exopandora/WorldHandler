package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.types.Coordinate;
import exopandora.worldhandler.builder.types.Type;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BuilderSpawnpoint extends CommandBuilder
{
	public BuilderSpawnpoint(String player)
	{
		this.setX(new Coordinate(0, true));
		this.setY(new Coordinate(0, true));
		this.setZ(new Coordinate(0, true));
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
	
	public void setX(Coordinate x)
	{
		this.setNode(1, x);
	}
	
	public void setY(Coordinate y)
	{
		this.setNode(2, y);
	}
	
	public void setZ(Coordinate z)
	{
		this.setNode(3, z);
	}
	
	@Override
	public final Syntax getSyntax()
	{
		Syntax syntax = new Syntax();
		
		syntax.addRequired("player", Type.STRING);
		syntax.addRequired("x", Type.COORDINATE);
		syntax.addRequired("y", Type.COORDINATE);
		syntax.addRequired("z", Type.COORDINATE);
		
		return syntax;
	}
}
