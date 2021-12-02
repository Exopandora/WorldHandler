package exopandora.worldhandler.builder.impl;

public class BanCommandBuilder extends TargetReasonCommandBuilder<BanCommandBuilder.Label>
{
	public BanCommandBuilder()
	{
		super("ban", Label.BAN, Label.BAN_REASON);
	}
	
	public static enum Label
	{
		BAN,
		BAN_REASON;
	}
}