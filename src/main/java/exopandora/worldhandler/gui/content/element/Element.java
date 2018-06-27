package exopandora.worldhandler.gui.content.element;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
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
