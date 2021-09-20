package exopandora.worldhandler.builder.impl;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.types.ArgumentType;
import exopandora.worldhandler.util.EnumHelper;

public class BuilderScoreboardPlayers extends BuilderScoreboard
{
	public BuilderScoreboardPlayers()
	{
		this.init();
	}
	
	private void init()
	{
		this.setNode(0, "players");
	}
	
	@Nullable
	public EnumMode getMode()
	{
		return EnumHelper.valueOf(this.getNodeAsString(1), EnumMode.class);
	}
	
	public void setMode(String mode)
	{
		String objective = this.getObjective();
		String player = this.getPlayer();
		int points = this.getPoints();
		
		this.updateSyntax(this.getSyntax(mode));
		this.setNode(1, mode);
		this.setNode(2, player);
		this.setObjective(objective);
		
		if(!mode.equals("enable"))
		{
			this.setPoints(points);
		}
		
		this.init();
	}
	
	public void setPlayer(String player)
	{
		this.setNode(2, player);
	}
	
	@Nullable
	public String getPlayer()
	{
		return this.getNodeAsString(2);
	}
	
	public void setObjective(String name)
	{
		if(this.getMode() != null)
		{
			this.setNode(3, name != null ? name.replaceAll(" ", "_") : null);
		}
	}
	
	@Nullable
	public String getObjective()
	{
		if(this.getMode() != null)
		{
			return this.getNodeAsString(3);
		}
		
		return null;
	}
	
	public void setPoints(int points)
	{
		this.setNode(4, points);
	}
	
	public int getPoints()
	{
		EnumMode mode = this.getMode();
		
		if(mode != null && !EnumMode.ENABLE.equals(mode))
		{
			return this.getNodeAsInt(4);
		}
		
		return 0;
	}
	
	private CommandSyntax getSyntax(String mode)
	{
		CommandSyntax syntax = new CommandSyntax();
		
		if(mode.equals("enable"))
		{
			syntax.addRequired("players", ArgumentType.STRING);
			syntax.addRequired("enable", ArgumentType.STRING);
			syntax.addRequired("player", ArgumentType.STRING);
			syntax.addRequired("objective", ArgumentType.STRING);
			
			return syntax;
		}
		
		syntax.addRequired("players", ArgumentType.STRING);
		syntax.addRequired("add|set|remove", ArgumentType.STRING, "add|set|remove");
		syntax.addRequired("player", ArgumentType.STRING);
		syntax.addRequired("objective", ArgumentType.STRING);
		syntax.addRequired("score", ArgumentType.INT, 0);
		
		return syntax;
	}
	
	public BuilderScoreboardPlayers buildPoints(EnumMode mode)
	{
		return this.buildPoints(mode, this.getPoints());
	}
	
	public BuilderScoreboardPlayers buildPoints(EnumMode mode, int points)
	{
		BuilderScoreboardPlayers builder = new BuilderScoreboardPlayers();
		
		builder.setMode(mode.toString());
		builder.setPlayer(this.getPlayer());
		builder.setObjective(this.getObjective());
		builder.setPoints(points);
		
		return builder;
	}
	
	public BuilderScoreboardPlayers buildEnable()
	{
		BuilderScoreboardPlayers builder = new BuilderScoreboardPlayers();
		
		builder.setMode(EnumMode.ENABLE.toString());
		builder.setPlayer(this.getPlayer());
		builder.setObjective(this.getObjective());
		
		return builder;
	}
	
	@Override
	public final CommandSyntax getSyntax()
	{
		CommandSyntax syntax = new CommandSyntax();
		
		syntax.addRequired("players", ArgumentType.STRING);
		syntax.addRequired("add|enable|get|list|operation|remove|reset|set", ArgumentType.STRING);
		syntax.addOptional("...", ArgumentType.STRING);
		
		return syntax;
	}
	
	public static enum EnumMode
	{
		ADD,
		REMOVE,
		ENABLE,
		SET;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
}
