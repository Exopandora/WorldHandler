package exopandora.worldhandler.builder.impl;

public class SaveAllCommandBuilder extends LiteralCommandBuilder
{
	public SaveAllCommandBuilder()
	{
		super("save-all", Label.SAVE_ALL);
	}
	
	public static enum Label
	{
		SAVE_ALL;
	}
}
