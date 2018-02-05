package exopandora.worldhandler.builder.impl;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.impl.abstr.BuilderScoreboard;
import exopandora.worldhandler.builder.types.Type;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BuilderScoreboardTeams extends BuilderScoreboard
{
	public BuilderScoreboardTeams()
	{
		this.init();
	}
	
	private void init()
	{
		this.setNode(0, "teams");
	}
	
	public void setTeam(String name)
	{
		String mode = this.getMode();
		String team = name != null ? name.replaceAll(" ", "_") : null;
		
		if(mode != null)
		{
			if(mode.equals("add"))
			{
				this.setNode(3, name);
			}
		}
		
		this.setNode(2, team);
	}
	
	public String getMode()
	{
		return this.getNodeAsString(1);
	}
	
	@Nullable
	public String getTeam()
	{
		return this.getNodeAsString(2);
	}
	
	public void setMode(String mode)
	{
		String team = this.getTeam();
		String player = this.getPlayer();
		
		if(mode.equals("add") || mode.equals("remove|empty") || mode.equals("join|leave") || mode.equals("option"))
		{
			this.updateSyntax(this.getSyntax(mode));
			this.setNode(1, mode);
			
			if(team != null)
			{
				this.setTeam(team);
			}
			
			if(player != null && (mode.equals("join|leave")))
			{
				this.setPlayer(player);
			}
			
			this.init();
		}
	}
	
	public void setPlayer(String player)
	{
		String mode = this.getMode();
		
		if(mode != null)
		{
			if(mode.equals("join|leave"))
			{
				this.setNode(3, player);
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
				return this.getNodeAsString(3);
			}
		}
		
		return null;
	}
	
	public void setRule(String rule)
	{
		if(this.getMode() == null || !this.getMode().equals("option"))
		{
			this.setMode("option");
		}
		
		this.setNode(3, rule);
	}
	
	public String getRule()
	{
		if(this.getMode() == null || this.getMode().equals("option"))
		{
			return this.getNodeAsString(3);
		}
		
		return null;
	}
	
	public void setValue(String value)
	{
		if(this.getMode() == null || !this.getMode().equals("option"))
		{
			this.setMode("option");
		}
		
		this.setNode(4, value);
	}
	
	public String getValue()
	{
		if(this.getMode() == null || this.getMode().equals("option"))
		{
			return this.getNodeAsString(4);
		}
		
		return null;
	}
	
	@Nullable
	private Syntax getSyntax(String mode)
	{
		if(mode.equals("add"))
		{
			Syntax syntax = new Syntax();
			
			syntax.addRequired("teams", Type.STRING);
			syntax.addRequired("add", Type.STRING);
			syntax.addRequired("name", Type.STRING);
			syntax.addOptional("display_name...", Type.STRING);
			
			return syntax;
		}
		else if(mode.equals("remove|empty"))
		{
			Syntax syntax = new Syntax();
			
			syntax.addRequired("teams", Type.STRING);
			syntax.addRequired("remove|empty", Type.STRING, "remove|empty");
			syntax.addRequired("name", Type.STRING);
			
			return syntax;
		}
		else if(mode.equals("join|leave"))
		{
			Syntax syntax = new Syntax();
			
			syntax.addRequired("teams", Type.STRING);
			syntax.addRequired("join|leave", Type.STRING, "join|leave");
			syntax.addRequired("name", Type.STRING);
			syntax.addOptional("player", Type.STRING);
			
			return syntax;
		}
		else if(mode.equals("option"))
		{
			Syntax syntax = new Syntax();
			
			syntax.addRequired("teams", Type.STRING);
			syntax.addRequired("option", Type.STRING);
			syntax.addRequired("team", Type.STRING);
			syntax.addRequired("friendlyfire|color|seeFriendlyInvisibles|nametagVisibility|deathMessageVisibility|collisionRule", Type.STRING);
			syntax.addRequired("value", Type.STRING);
			
			return syntax;
		}
		
		return null;
	}
	
	public BuilderScoreboardTeams getBuilderForMode(EnumMode mode)
	{
		BuilderScoreboardTeams builder = new BuilderScoreboardTeams(); 
		
		switch(mode)
		{
			case JOIN:
			case LEAVE:
				builder.setNode(1, mode.toString());
				builder.setTeam(this.getTeam());
				builder.setPlayer(this.getPlayer());
				break;
			case REMOVE:
			case EMPTY:
				builder.setNode(1, mode.toString());
				builder.setTeam(this.getTeam());
				break;
			case ADD:
				builder.setMode(mode.toString());
				builder.setTeam(this.getTeam());
				break;
			case OPTION:
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
	public final Syntax getSyntax()
	{
		Syntax syntax = new Syntax();
		
		syntax.addRequired("teams", Type.STRING);
		syntax.addRequired("list|add|remove|empty|join|leave|option", Type.STRING);
		syntax.addOptional("...", Type.STRING);
		
		return syntax;
	}
	
	public static enum EnumMode
	{
		JOIN,
		LEAVE,
		REMOVE,
		EMPTY,
		ADD,
		OPTION;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
}
