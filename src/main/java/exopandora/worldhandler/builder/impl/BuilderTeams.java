package exopandora.worldhandler.builder.impl;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.types.ArgumentType;
import exopandora.worldhandler.builder.types.GreedyString;
import exopandora.worldhandler.util.EnumHelper;

public class BuilderTeams extends CommandBuilder
{
	public void setTeam(String name)
	{
		String team = name != null ? name.replaceAll(" ", "_") : null;
		
		if(EnumMode.ADD.equals(this.getMode()))
		{
			this.setNode(2, new GreedyString(name));
		}
		
		this.setNode(1, team);
	}
	
	@Nullable
	public EnumMode getMode()
	{
		return EnumHelper.valueOf(this.getNodeAsString(0), EnumMode.class);
	}
	
	@Nullable
	public String getTeam()
	{
		return this.getNodeAsString(1);
	}
	
	public void setMode(EnumMode mode)
	{
		String team = this.getTeam();
		String player = this.getPlayer();
		
		this.updateSyntax(this.getSyntax(mode));
		this.setNode(0, mode.toString());
		
		if(team != null)
		{
			this.setTeam(team);
		}
		
		if(player != null && (EnumMode.JOIN.equals(mode) || EnumMode.LEAVE.equals(mode) || EnumMode.JOIN_OR_LEAVE.equals(mode)))
		{
			this.setPlayer(player);
		}
	}
	
	public void setPlayer(String player)
	{
		EnumMode mode = this.getMode();
		
		if(EnumMode.JOIN.equals(mode) || EnumMode.LEAVE.equals(mode) || EnumMode.JOIN_OR_LEAVE.equals(mode))
		{
			this.setNode(2, player);
		}
	}
	
	@Nullable
	public String getPlayer()
	{
		EnumMode mode = this.getMode();
		
		if(EnumMode.JOIN.equals(mode) || EnumMode.LEAVE.equals(mode) || EnumMode.JOIN_OR_LEAVE.equals(mode))
		{
			return this.getNodeAsString(2);
		}
		
		return null;
	}
	
	public void setRule(String rule)
	{
		if(!EnumMode.MODIFY.equals(this.getMode()))
		{
			this.setMode(EnumMode.MODIFY);
		}
		
		this.setNode(2, rule);
	}
	
	@Nullable
	public String getRule()
	{
		EnumMode mode = this.getMode();
		
		if(mode == null || EnumMode.MODIFY.equals(mode))
		{
			return this.getNodeAsString(2);
		}
		
		return null;
	}
	
	public void setValue(String value)
	{
		if(!EnumMode.MODIFY.equals(this.getMode()))
		{
			this.setMode(EnumMode.MODIFY);
		}
		
		this.setNode(3, value);
	}
	
	@Nullable
	public String getValue()
	{
		EnumMode mode = this.getMode();
		
		if(mode == null || EnumMode.MODIFY.equals(mode))
		{
			return this.getNodeAsString(3);
		}
		
		return null;
	}
	
	@Nullable
	private CommandSyntax getSyntax(EnumMode mode)
	{
		if(EnumMode.ADD.equals(mode))
		{
			CommandSyntax syntax = new CommandSyntax();
			
			syntax.addRequired("add", ArgumentType.STRING);
			syntax.addRequired("name", ArgumentType.STRING);
			syntax.addOptional("display_name...", ArgumentType.GREEDY_STRING);
			
			return syntax;
		}
		else if(EnumMode.REMOVE.equals(mode) || EnumMode.EMPTY.equals(mode) || EnumMode.REMOVE_OR_EMPTY.equals(mode))
		{
			CommandSyntax syntax = new CommandSyntax();
			
			syntax.addRequired("remove|empty", ArgumentType.STRING, "remove|empty");
			syntax.addRequired("name", ArgumentType.STRING);
			
			return syntax;
		}
		else if(EnumMode.JOIN.equals(mode) || EnumMode.LEAVE.equals(mode) || EnumMode.JOIN_OR_LEAVE.equals(mode))
		{
			CommandSyntax syntax = new CommandSyntax();
			
			syntax.addRequired("join|leave", ArgumentType.STRING, "join|leave");
			syntax.addRequired("player|team", ArgumentType.STRING);
			syntax.addOptional("player", ArgumentType.STRING);
			
			return syntax;
		}
		else if(EnumMode.MODIFY.equals(mode))
		{
			CommandSyntax syntax = new CommandSyntax();
			
			syntax.addRequired("modify", ArgumentType.STRING);
			syntax.addRequired("team", ArgumentType.STRING);
			syntax.addRequired("friendlyfire|color|seeFriendlyInvisibles|nametagVisibility|deathMessageVisibility|collisionRule", ArgumentType.STRING);
			syntax.addRequired("value", ArgumentType.STRING);
			
			return syntax;
		}
		
		return null;
	}
	
	public BuilderTeams build(EnumMode mode)
	{
		BuilderTeams builder = new BuilderTeams(); 
		
		switch(mode)
		{
			case JOIN_OR_LEAVE:
			case JOIN:
				builder.setNode(0, mode.toString());
				builder.setTeam(this.getTeam());
				builder.setNode(2, this.getPlayer());
				break;
			case LEAVE:
				builder.setNode(0, mode.toString());
				builder.setNode(1, this.getPlayer());
				break;
			case REMOVE_OR_EMPTY:
			case REMOVE:
			case EMPTY:
				builder.setNode(0, mode.toString());
				builder.setTeam(this.getTeam());
				break;
			case ADD:
				builder.setMode(mode);
				builder.setTeam(this.getTeam());
				break;
			case MODIFY:
				builder.setMode(mode);
				builder.setTeam(this.getTeam());
				builder.setRule(this.getRule());
				builder.setValue(this.getValue());
				break;
			default:
				break;
		}
		
		return builder;
	}
	
	@Override
	public final CommandSyntax getSyntax()
	{
		CommandSyntax syntax = new CommandSyntax();
		
		syntax.addRequired("list|add|remove|empty|join|leave|modify", ArgumentType.STRING);
		syntax.addOptional("...", ArgumentType.STRING);
		
		return syntax;
	}
	
	@Override
	public String getCommandName()
	{
		return "team";
	}
	
	public static enum EnumMode
	{
		JOIN,
		LEAVE,
		REMOVE,
		EMPTY,
		ADD,
		MODIFY,
		JOIN_OR_LEAVE,
		REMOVE_OR_EMPTY;
		
		@Override
		public String toString()
		{
			if(EnumMode.JOIN_OR_LEAVE.equals(this))
			{
				return "join|leave";
			}
			else if(EnumMode.REMOVE_OR_EMPTY.equals(this))
			{
				return "remove|empty";
			}
			
			return this.name().toLowerCase();
		}
	}
}
