package exopandora.worldhandler.builder.impl.abstr;

import exopandora.worldhandler.builder.CommandBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class BuilderScoreboard extends CommandBuilder
{
	@Override
	public String getCommandName()
	{
		return "scoreboard";
	}
}
