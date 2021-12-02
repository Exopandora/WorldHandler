package exopandora.worldhandler.builder;

public interface ICommandBuilder
{
	static final int MAX_COMMAND_LENGTH = 256;
	
	String toCommand(Object label, boolean preview);
	
	default boolean needsCommandBlock(Object label, boolean preview)
	{
		return this.toCommand(label, preview).length() > MAX_COMMAND_LENGTH;
	}
}
