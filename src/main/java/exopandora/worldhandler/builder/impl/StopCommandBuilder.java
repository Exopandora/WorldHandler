package exopandora.worldhandler.builder.impl;

public class StopCommandBuilder extends LiteralCommandBuilder
{
	public StopCommandBuilder()
	{
		super("stop", Label.STOP);
	}
	
	public static enum Label
	{
		STOP;
	}
}
