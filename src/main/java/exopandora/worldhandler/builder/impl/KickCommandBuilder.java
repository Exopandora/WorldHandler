package exopandora.worldhandler.builder.impl;

public class KickCommandBuilder extends TargetReasonCommandBuilder<KickCommandBuilder.Label>
{
	public KickCommandBuilder()
	{
		super("kick", Label.KICK, Label.KICK_REASON);
	}
	
	public static enum Label
	{
		KICK,
		KICK_REASON;
	}
}