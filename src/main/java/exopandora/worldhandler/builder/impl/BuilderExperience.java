package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.types.Level;
import exopandora.worldhandler.builder.types.Type;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BuilderExperience extends CommandBuilder
{
	public BuilderExperience()
	{
		
	}
	
	public BuilderExperience(int level, String player)
	{
		this.setLevel(level);
		this.setPlayer(player);
	}
	
	public void setLevel(int level)
	{
		this.setNode(0, new Level(level));
	}
	
	public int getLevel()
	{
		Level level = this.getNodeAsLevel(0);
		
		if(level != null)
		{
			return level.getLevel();
		}
		
		return 0;
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
		return "xp";
	}
	
	@Override
	public Syntax getSyntax()
	{
		Syntax syntax = new Syntax();
		
		syntax.addRequired("amount", Type.LEVEL, new Level(0));
		syntax.addOptional("player", Type.STRING);
		
		return syntax;
	}
}
