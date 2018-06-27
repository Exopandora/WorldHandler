package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.types.Type;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BuilderGamemode extends CommandBuilder
{
	public BuilderGamemode()
	{
		
	}
	
	public BuilderGamemode(EnumGamemode mode)
	{
		this.setMode(mode);
	}
	
	public BuilderGamemode(EnumGamemode mode, String player)
	{
		this(mode);
		this.setPlayer(player);
	}
	
	public void setMode(EnumGamemode mode)
	{
		this.setNode(0, mode.toString());
	}
	
	public void setPlayer(String player)
	{
		this.setNode(1, player);
	}
	
	@Override
	public String getCommandName()
	{
		return "gamemode";
	}

	@Override
	public final Syntax getSyntax()
	{
		Syntax syntax = new Syntax();
		
		syntax.addRequired("mode", Type.STRING);
		syntax.addOptional("player", Type.STRING);
		
		return syntax;
	}
	
	@SideOnly(Side.CLIENT)
	public static enum EnumGamemode
	{
		SURVIVAL,
		CREATIVE,
		ADVENTURE,
		SPECTATOR;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
}
