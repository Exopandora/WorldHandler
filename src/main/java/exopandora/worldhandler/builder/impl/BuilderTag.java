package exopandora.worldhandler.builder.impl;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.types.ArgumentType;

public class BuilderTag extends CommandBuilder
{
	public BuilderTag()
	{
		super();
	}
	
	public BuilderTag(String player, EnumMode mode, String name)
	{
		this.setPlayer(player);
		this.setMode(mode);
		this.setName(name);
	}
	
	public void setPlayer(String player)
	{
		this.setNode(0, player);
	}
	
	@Nullable
	public String getPlayer()
	{
		return this.getNodeAsString(0);
	}
	
	public void setMode(EnumMode mode)
	{
		if(mode != null)
		{
			this.setNode(1, mode.toString());
		}
	}
	
	public void setName(String name)
	{
		this.setNode(2, name);
	}
	
	@Nullable
	public String getName()
	{
		return this.getNodeAsString(2);
	}
	
	@Override
	public String getCommandName()
	{
		return "tag";
	}
	
	@Override
	public CommandSyntax getSyntax()
	{
		CommandSyntax syntax = new CommandSyntax();
		
		syntax.addRequired("player", ArgumentType.STRING);
		syntax.addRequired("add|list|remove", ArgumentType.STRING);
		syntax.addRequired("name", ArgumentType.STRING);
		
		return syntax;
	}
	
	public BuilderTag build(EnumMode mode)
	{
		return new BuilderTag(this.getPlayer(), mode, this.getName());
	}
	
	public static enum EnumMode
	{
		ADD,
		LIST,
		REMOVE;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
}
