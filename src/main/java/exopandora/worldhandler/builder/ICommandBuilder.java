package exopandora.worldhandler.builder;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface ICommandBuilder
{
	static final int MAX_COMMAND_LENGTH = 256;
	
	String toCommand();
	
	default boolean needsCommandBlock()
	{
		return this.toCommand().length() > MAX_COMMAND_LENGTH;
	}
}
