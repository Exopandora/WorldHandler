package exopandora.worldhandler.builder;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
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
