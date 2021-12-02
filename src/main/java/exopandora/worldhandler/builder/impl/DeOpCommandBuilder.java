package exopandora.worldhandler.builder.impl;

public class DeOpCommandBuilder extends TargetCommandBuilder
{
	public DeOpCommandBuilder()
	{
		super("deop", Label.DEOP);
	}
	
	public static enum Label
	{
		DEOP;
	}
}