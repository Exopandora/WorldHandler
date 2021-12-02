package exopandora.worldhandler.builder.impl;

public class PardonCommandBuilder extends TargetCommandBuilder
{
	public PardonCommandBuilder()
	{
		super("pardon", Label.PARDON);
	}
	
	public static enum Label
	{
		PARDON;
	}
}