package exopandora.worldhandler.builder;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface ICommandBuilder
{
	static final int MAX_COMMAND_LENGTH = 256;
	
	String toCommand();
	
	default boolean needsCommandBlock()
	{
		return this.toCommand().length() > MAX_COMMAND_LENGTH;
	}
}
