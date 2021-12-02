package exopandora.worldhandler.builder.impl;

public class OpCommandBuilder extends TargetCommandBuilder
{
	public OpCommandBuilder()
	{
		super("op", Label.OP);
	}
	
	public static enum Label
	{
		OP;
	}
}