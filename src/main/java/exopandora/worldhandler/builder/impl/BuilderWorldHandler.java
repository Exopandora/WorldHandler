package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.types.Type;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BuilderWorldHandler extends CommandBuilder
{
	@Override
	public String getCommandName()
	{
		return "worldhandler";
	}
	
	@Override
	public final Syntax getSyntax()
	{
		Syntax syntax = new Syntax();
		
		syntax.addRequired("help|display|version", Type.STRING);
		
		return syntax;
	}
}
