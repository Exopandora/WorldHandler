package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.types.Type;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BuilderPlayer extends CommandBuilder
{
	private final String command;
	
	public BuilderPlayer(String command)
	{
		this.command = command;
	}
	
	public void setPlayer(String player)
	{
		this.setNode(0, player);
	}
	
	public String getPlayer()
	{
		return this.getNodeAsString(0);
	}
	
	@Override
	public String getCommandName()
	{
		return this.command;
	}
	
	@Override
	public final Syntax getSyntax()
	{
		Syntax syntax = new Syntax();
		
		syntax.addRequired("player", Type.STRING);
		
		return syntax;
	}
}
