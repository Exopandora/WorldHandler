package exopandora.worldhandler.builder.impl;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.types.Type;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BuilderWhitelist extends CommandBuilder
{
	public BuilderWhitelist()
	{
		
	}
	
	public BuilderWhitelist(EnumMode mode)
	{
		this.setMode(mode);
	}
	
	public BuilderWhitelist(EnumMode mode, String player)
	{
		this(mode);
		this.setPlayer(player);
	}
	
	public void setMode(EnumMode mode)
	{
		this.setNode(0, mode.toString());
	}
	
	public void setPlayer(String player)
	{
		this.setNode(1, player);
	}
	
	public String getPlayer()
	{
		return this.getNodeAsString(1);
	}
	
	@Override
	public String getCommandName()
	{
		return "whitelist";
	}
	
	@Nullable
	public BuilderWhitelist getBuilder(EnumMode mode)
	{
		switch(mode)
		{
			case ADD:
			case REMOVE:
				return new BuilderWhitelist(mode, this.getPlayer());
			case RELOAD:
			case ON:
			case OFF:
				return new BuilderWhitelist(mode);
			default:
				return null;
		}
	}
	
	@Override
	public final Syntax getSyntax()
	{
		Syntax syntax = new Syntax();
		
		syntax.addRequired("add|remove|reload|on|off", Type.STRING);
		syntax.addOptional("player", Type.STRING);
		
		return syntax;
	}
	
	public static enum EnumMode
	{
		ADD,
		REMOVE,
		RELOAD,
		ON,
		OFF;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
}
