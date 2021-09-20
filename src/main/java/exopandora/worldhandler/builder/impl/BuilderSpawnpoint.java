package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.types.ArgumentType;
import exopandora.worldhandler.builder.types.Coordinate.EnumType;
import exopandora.worldhandler.builder.types.CoordinateInt;

public class BuilderSpawnpoint extends CommandBuilder
{
	public BuilderSpawnpoint()
	{
		this.setX(new CoordinateInt(EnumType.GLOBAL));
		this.setY(new CoordinateInt(EnumType.GLOBAL));
		this.setZ(new CoordinateInt(EnumType.GLOBAL));
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
	public final CommandSyntax getSyntax()
	{
		CommandSyntax syntax = new CommandSyntax();
		
		syntax.addRequired("player", ArgumentType.STRING);
		syntax.addRequired("x", ArgumentType.COORDINATE_INT);
		syntax.addRequired("y", ArgumentType.COORDINATE_INT);
		syntax.addRequired("z", ArgumentType.COORDINATE_INT);
		
		return syntax;
	}
}
