package exopandora.worldhandler.util;

public enum BlockPlacingMode
{
	KEEP,
	REPLACE,
	DESTROY;
	
	@Override
	public String toString()
	{
		return this.name().toLowerCase();
	}
}
