package exopandora.worldhandler.gui.menu;

public abstract class Menu implements IMenu
{
	protected int x;
	protected int y;
	
	public Menu(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}
