package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.impl.abstr.BuilderScoreboard;
import exopandora.worldhandler.builder.types.Type;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
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
	
	public String getMode()
	{
		return this.getNodeAsString(1);
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
			this.setNode(3, objective);
		}
	}
	
	public String getObjective()
	{
		String mode = this.getMode();
		
		if(mode != null)
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
		if(this.getMode() != null && !this.getMode().equals("enable"))
		{
			return this.getNodeAsInt(4);
		}
		
		return 0;
	}
	
	private Syntax getSyntax(String mode)
	{
		Syntax syntax = new Syntax();
		
		if(mode.equals("enable"))
		{
			syntax.addRequired("players", Type.STRING);
			syntax.addRequired("enable", Type.STRING);
			syntax.addRequired("player", Type.STRING);
			syntax.addRequired("objective", Type.STRING);
			
			return syntax;
		}
		
		syntax.addRequired("players", Type.STRING);
		syntax.addRequired("add|set|remove", Type.STRING, "add|set|remove");
		syntax.addRequired("player", Type.STRING);
		syntax.addRequired("objective", Type.STRING);
		syntax.addRequired("score", Type.INT, 0);
		
		return syntax;
		
	}
	
	public BuilderScoreboardPlayers getBuilderForPoints(EnumMode mode)
	{
		return this.getBuilderForPoints(mode, this.getPoints());
	}
	
	public BuilderScoreboardPlayers getBuilderForPoints(EnumMode mode, int points)
	{
		BuilderScoreboardPlayers builder = new BuilderScoreboardPlayers();
		
		builder.setMode(mode.toString());
		builder.setPlayer(this.getPlayer());
		builder.setObjective(this.getObjective());
		builder.setPoints(points);
		
		return builder;
	}
	
	public BuilderScoreboardPlayers getBuilderForEnable()
	{
		BuilderScoreboardPlayers builder = new BuilderScoreboardPlayers();
		
		builder.setMode(EnumMode.ENABLE.toString());
		builder.setPlayer(this.getPlayer());
		builder.setObjective(this.getObjective());
		
		return builder;
	}
	
	@Override
	public final Syntax getSyntax()
	{
		Syntax syntax = new Syntax();
		
		syntax.addRequired("players", Type.STRING);
		syntax.addRequired("add|enable|get|list|operation|remove|reset|set", Type.STRING);
		syntax.addOptional("...", Type.STRING);
		
		return syntax;
	}
	
	@OnlyIn(Dist.CLIENT)
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
