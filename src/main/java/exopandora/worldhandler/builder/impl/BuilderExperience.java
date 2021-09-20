package exopandora.worldhandler.builder.impl;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.types.ArgumentType;
import exopandora.worldhandler.util.EnumHelper;

public class BuilderExperience extends CommandBuilder
{
	public BuilderExperience()
	{
		this.setLevel(0);
	}
	
	public BuilderExperience(EnumMode mode, int level, String player, EnumUnit unit)
	{
		this.setMode(mode);
		this.setLevel(level);
		this.setPlayer(player);
		this.setUnit(unit);
	}
	
	public void setMode(EnumMode mode)
	{
		this.setNode(0, mode.toString());
	}
	
	@Nullable
	public EnumMode getMode()
	{
		return EnumHelper.valueOf(this.getNodeAsString(0), EnumMode.class);
	}
	
	public void setPlayer(String player)
	{
		this.setNode(1, player);
	}
	
	@Nullable
	public String getPlayer()
	{
		return this.getNodeAsString(1);
	}
	
	public void setLevel(int level)
	{
		this.setNode(2, level);
	}
	
	@Nullable
	public int getLevel()
	{
		return this.getNodeAsInt(2);
	}
	
	public void setUnit(EnumUnit unit)
	{
		this.setNode(3, unit.toString());
	}
	
	@Nullable
	public EnumUnit getUnit()
	{
		return EnumHelper.valueOf(this.getNodeAsString(3), EnumUnit.class);
	}
	
	@Override
	public String getCommandName()
	{
		return "experience";
	}
	
	@Override
	public CommandSyntax getSyntax()
	{
		CommandSyntax syntax = new CommandSyntax();
		
		syntax.addRequired("add|set|query", ArgumentType.STRING);
		syntax.addRequired("player", ArgumentType.STRING);
		syntax.addRequired("amount", ArgumentType.INT);
		syntax.addOptional("levels|points", ArgumentType.STRING);
		
		return syntax;
	}
	
	public BuilderExperience buildAdd()
	{
		return new BuilderExperience(EnumMode.ADD, this.getLevel(), this.getPlayer(), EnumUnit.LEVELS);
	}
	
	public BuilderExperience buildRemove()
	{
		return new BuilderExperience(EnumMode.ADD, -this.getLevel(), this.getPlayer(), EnumUnit.LEVELS);
	}
	
	public BuilderExperience buildReset()
	{
		return new BuilderExperience(EnumMode.SET, 0, this.getPlayer(), EnumUnit.LEVELS);
	}
	
	public static enum EnumMode
	{
		ADD,
		SET,
		QUERY;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
	
	public static enum EnumUnit
	{
		LEVELS,
		POINTS;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
}
