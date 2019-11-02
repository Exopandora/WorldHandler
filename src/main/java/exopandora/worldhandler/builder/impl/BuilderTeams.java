package exopandora.worldhandler.builder.impl;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.types.GreedyString;
import exopandora.worldhandler.builder.types.ArgumentType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BuilderTeams extends CommandBuilder
{
	public void setTeam(String name)
	{
		String mode = this.getMode();
		String team = name != null ? name.replaceAll(" ", "_") : null;
		
		if(mode != null)
		{
			if(mode.equals("add"))
			{
				this.setNode(2, new GreedyString(name));
			}
		}
		
		this.setNode(1, team);
	}
	
	public String getMode()
	{
		return this.getNodeAsString(0);
	}
	
	@Nullable
	public String getTeam()
	{
		return this.getNodeAsString(1);
	}
	
	public void setMode(String mode)
	{
		String team = this.getTeam();
		String player = this.getPlayer();
		
		if(mode.equals("add") || mode.equals("remove|empty") || mode.equals("join|leave") || mode.equals("modify"))
		{
			this.updateSyntax(this.getSyntax(mode));
			this.setNode(0, mode);
			
			if(team != null)
			{
				this.setTeam(team);
			}
			
			if(player != null && (mode.equals("join|leave")))
			{
				this.setPlayer(player);
			}
		}
	}
	
	public void setPlayer(String player)
	{
		String mode = this.getMode();
		
		if(mode != null)
		{
			if(mode.equals("join|leave"))
			{
				this.setNode(2, player);
			}
		}
	}
	
	@Nullable
	public String getPlayer()
	{
		String mode = this.getMode();
		
		if(mode != null)
		{
			if(mode.equals("join|leave"))
			{
				return this.getNodeAsString(2);
			}
		}
		
		return null;
	}
	
	public void setRule(String rule)
	{
		if(this.getMode() == null || !this.getMode().equals("modify"))
		{
			this.setMode("modify");
		}
		
		this.setNode(2, rule);
	}
	
	public String getRule()
	{
		if(this.getMode() == null || this.getMode().equals("modify"))
		{
			return this.getNodeAsString(2);
		}
		
		return null;
	}
	
	public void setValue(String value)
	{
		if(this.getMode() == null || !this.getMode().equals("modify"))
		{
			this.setMode("modify");
		}
		
		this.setNode(3, value);
	}
	
	public String getValue()
	{
		if(this.getMode() == null || this.getMode().equals("modify"))
		{
			return this.getNodeAsString(3);
		}
		
		return null;
	}
	
	@Nullable
	private CommandSyntax getSyntax(String mode)
	{
		if(mode.equals("add"))
		{
			CommandSyntax syntax = new CommandSyntax();
			
			syntax.addRequired("add", ArgumentType.STRING);
			syntax.addRequired("name", ArgumentType.STRING);
			syntax.addOptional("display_name...", ArgumentType.GREEDY_STRING);
			
			return syntax;
		}
		else if(mode.equals("remove|empty"))
		{
			CommandSyntax syntax = new CommandSyntax();
			
			syntax.addRequired("remove|empty", ArgumentType.STRING, "remove|empty");
			syntax.addRequired("name", ArgumentType.STRING);
			
			return syntax;
		}
		else if(mode.equals("join|leave"))
		{
			CommandSyntax syntax = new CommandSyntax();
			
			syntax.addRequired("join|leave", ArgumentType.STRING, "join|leave");
			syntax.addRequired("name", ArgumentType.STRING);
			syntax.addOptional("player", ArgumentType.STRING);
			
			return syntax;
		}
		else if(mode.equals("modify"))
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
	
	public BuilderTeams getBuilderForMode(EnumMode mode)
	{
		BuilderTeams builder = new BuilderTeams(); 
		
		switch(mode)
		{
			case JOIN:
			case LEAVE:
				builder.setNode(0, mode.toString());
				builder.setTeam(this.getTeam());
				builder.setPlayer(this.getPlayer());
				break;
			case REMOVE:
			case EMPTY:
				builder.setNode(0, mode.toString());
				builder.setTeam(this.getTeam());
				break;
			case ADD:
				builder.setMode(mode.toString());
				builder.setTeam(this.getTeam());
				break;
			case MODIFY:
				builder.setMode(mode.toString());
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
	
	@OnlyIn(Dist.CLIENT)
	public static enum EnumMode
	{
		JOIN,
		LEAVE,
		REMOVE,
		EMPTY,
		ADD,
		MODIFY;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
}
