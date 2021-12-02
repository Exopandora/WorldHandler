package exopandora.worldhandler.builder.argument;

public enum Gamemode
{
	SURVIVAL,
	CREATIVE,
	ADVENTURE,
	SPECTATOR;
	
	@Override
	public String toString()
	{
		return this.name().toLowerCase();
	}
}