package exopandora.worldhandler.builder;

public interface ICommandBuilderSyntax extends ICommandBuilder
{
	String getCommandName();
	String toActualCommand();
	CommandSyntax getSyntax();
	
	@Override
	default boolean needsCommandBlock()
	{
		return this.toActualCommand().length() > MAX_COMMAND_LENGTH;
	}
}
