package exopandora.worldhandler.builder;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface ICommandBuilderSyntax extends ICommandBuilder
{
	String getCommandName();
	String toActualCommand();
	Syntax getSyntax();
	
	@Override
	default boolean needsCommandBlock()
	{
		return this.toActualCommand().length() > MAX_COMMAND_LENGTH;
	}
}
