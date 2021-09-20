package exopandora.worldhandler.gui.widget.button;

public enum EnumIcon
{
	WEATHER_SUN(0, 0),
	WEATHER_RAIN(0, 1),
	WEATHER_STORM(0, 2),
	DIFFICULTY_PEACEFUL(1, 0),
	DIFFICULTY_EASY(1, 1),
	DIFFICULTY_NORMAL(1, 2),
	DIFFICULTY_HARD(1, 3),
	TIME_DAWN(2, 0),
	TIME_NOON(2, 1),
	TIME_SUNSET(2, 2),
	TIME_MIDNIGHT(2, 3),
	GAMEMODE_SURVIVAL(3, 0),
	GAMEMODE_CREATIVE(3, 1),
	GAMEMODE_ADVENTURE(3, 2),
	GAMEMODE_SPECTATOR(3, 3),
	BUTCHER(4, 0),
	POTION(4, 1),
	ACHIEVEMNTS(4, 2),
	COMMAND_STACK(4, 3),
	ARROW_UP(4, 4),
	ARROW_DOWN(4, 5),
	HOME(5, 0),
	SETTINGS(5, 1),
	RELOAD(5, 2);
	
	private int x;
	private int y;
	
	private EnumIcon(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
}