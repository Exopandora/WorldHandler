package exopandora.worldhandler.gui.menu;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
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
