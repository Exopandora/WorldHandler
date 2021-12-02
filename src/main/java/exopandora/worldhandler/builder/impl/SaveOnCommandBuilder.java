package exopandora.worldhandler.builder.impl;

public class SaveOnCommandBuilder extends LiteralCommandBuilder
{
	public SaveOnCommandBuilder()
	{
		super("save-on", Label.SAVE_ON);
	}
	
	public static enum Label
	{
		SAVE_ON;
	}
}
