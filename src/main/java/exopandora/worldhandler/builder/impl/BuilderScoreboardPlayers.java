package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.impl.abstr.BuilderScoreboard;
import exopandora.worldhandler.builder.types.Type;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BuilderScoreboardPlayers extends BuilderScoreboard
{
	public BuilderScoreboardPlayers()
	{
		this.init();
	}
	
	public String getMode()
	{
		return this.getNodeAsString(1);
	}
	
	public void setMode(String mode)
	{
		String objective = this.getObjective();
		String player = this.getPlayer();
		String tag = this.getTag();
		int points = this.getPoints();
		
		if(mode.equals("add|set|remove") || mode.equals("tag") || mode.equals("enable"))
		{
			this.updateSyntax(this.getSyntax(mode));
			this.setNode(1, mode);
			this.setNode(2, player);
			
			if(mode.equals("add|set|remove") || mode.equals("enable"))
			{
				this.setObjective(objective);
			}
			
			if(mode.equals("add|set|remove"))
			{
				this.setPoints(points);
			}
			else if(mode.equals("tag"))
			{
				this.setTag(tag);
			}
			
			this.init();
		}
	}

	private void init()
	{
		this.setNode(0, "players");
	}
	
	public void setPlayer(String player)
	{
		this.setNode(2, player);
	}
	
	public String getPlayer()
	{
		return this.getNodeAsString(2);
	}
	
	public void setObjective(String name)
	{
		String mode = this.getMode();
		String objective = name != null ? name.replaceAll(" ", "_") : null;
		
		if(mode != null)
		{
			if(mode.equals("add|set|remove") || mode.equals("enable"))
			{
				this.setNode(3, objective);
			}
		}
	}
	
	public String getObjective()
	{
		String mode = this.getMode();
		
		if(mode != null)
		{
			if(mode.equals("add|set|remove") || mode.equals("enable"))
			{
				return this.getNodeAsString(3);
			}
		}
		
		return null;
	}
	
	public void setPoints(int points)
	{
		if(this.getMode() == null || !this.getMode().equals("add|set|remove"))
		{
			this.setMode("add|set|remove");
		}
		this.setNode(4, points);
	}
	
	public int getPoints()
	{
		if(this.getMode() != null && this.getMode().equals("add|set|remove"))
		{
			return this.getNodeAsInt(4);
		}
		
		return 0;
	}
	
	public void setTag(String name)
	{
		String tag = name != null ? name.replaceAll(" ", "_") : null;
		
		if(this.getMode() == null || !this.getMode().equals("tag"))
		{
			this.setMode("tag");
		}
		this.setNode(4, tag);
	}
	
	public String getTag()
	{
		if(this.getMode() != null && this.getMode().equals("tag"))
		{
			return this.getNodeAsString(4);
		}
		
		return null;
	}
	
	private Syntax getSyntax(String mode)
	{
		if(mode.equals("add|set|remove"))
		{
			Syntax syntax = new Syntax();
			
			syntax.addRequired("players", Type.STRING);
			syntax.addRequired("add|set|remove", Type.STRING, "add|set|remove");
			syntax.addRequired("player", Type.STRING);
			syntax.addRequired("objective", Type.STRING);
			syntax.addRequired("count", Type.INT, 0);
			syntax.addOptional("nbt", Type.NBT);
			
			return syntax;
		}
		else if(mode.equals("tag"))
		{
			Syntax syntax = new Syntax();
			
			syntax.addRequired("players", Type.STRING);
			syntax.addRequired("tag", Type.STRING);
			syntax.addRequired("player", Type.STRING);
			syntax.addRequired("add|remove|list", Type.STRING);
			syntax.addRequired("tag_name", Type.STRING);
			syntax.addOptional("nbt", Type.NBT);
			
			return syntax;
		}
		else if(mode.equals("enable"))
		{
			Syntax syntax = new Syntax();
			
			syntax.addRequired("players", Type.STRING);
			syntax.addRequired("enable", Type.STRING);
			syntax.addRequired("player", Type.STRING);
			syntax.addRequired("trigger", Type.STRING);
			
			return syntax;
		}
		
		return null;
	}
	
	public BuilderGeneric getBuilderForTag(EnumTag tag)
	{
		return new BuilderGeneric(this.getCommandName(), "players", this.getMode(), this.getPlayer(), tag.toString(), this.getTag());
	}
	
	public BuilderGeneric getBuilderForPoints(EnumPoints mode)
	{
		return this.getBuilderForPoints(mode, this.getPoints());
	}
	
	public BuilderGeneric getBuilderForPoints(EnumPoints mode, int points)
	{
		return new BuilderGeneric(this.getCommandName(), "players", mode.toString(), this.getPlayer(), this.getObjective(), String.valueOf(points));
	}
	
	@Override
	public final Syntax getSyntax()
	{
		Syntax syntax = new Syntax();
		
		syntax.addRequired("players", Type.STRING);
		syntax.addRequired("add|enable|list|operation|remove|reset|set|tag|test", Type.STRING);
		syntax.addOptional("...", Type.STRING);
		
		return syntax;
	}
	
	public static enum EnumTag
	{
		ADD,
		REMOVE;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
	
	public static enum EnumPoints
	{
		ADD,
		REMOVE,
		SET;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
}
