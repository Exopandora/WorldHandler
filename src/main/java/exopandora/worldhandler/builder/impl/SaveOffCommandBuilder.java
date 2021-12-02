package exopandora.worldhandler.builder.impl;

public class SaveOffCommandBuilder extends LiteralCommandBuilder
{
	public SaveOffCommandBuilder()
	{
		super("save-off", Label.SAVE_OFF);
	}
	
	public static enum Label
	{
		SAVE_OFF;
	}
}
