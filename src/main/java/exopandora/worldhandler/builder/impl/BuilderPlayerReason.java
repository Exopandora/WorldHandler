package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.types.Type;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BuilderPlayerReason extends CommandBuilder
{
	private final String command;
	
	public BuilderPlayerReason(String command)
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
	
	public void setReason(String reason)
	{
		this.setNode(1, reason);
	}
	
	public String getReason()
	{
		return this.getNodeAsString(1);
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
		syntax.addOptional("reason", Type.STRING);
		
		return syntax;
	}
}
