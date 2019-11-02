package exopandora.worldhandler.gui.element;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class Element implements IElement
{
	protected int x;
	protected int y;
	
	public Element(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}
