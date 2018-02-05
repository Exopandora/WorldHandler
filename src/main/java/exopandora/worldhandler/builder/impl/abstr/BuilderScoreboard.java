package exopandora.worldhandler.builder.impl.abstr;

import exopandora.worldhandler.builder.CommandBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class BuilderScoreboard extends CommandBuilder
{
	@Override
	public String getCommandName()
	{
		return "scoreboard";
	}
}
