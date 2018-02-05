package exopandora.worldhandler.gui.container;

import java.util.ArrayList;
import java.util.List;

import exopandora.worldhandler.gui.content.element.Element;
import exopandora.worldhandler.gui.content.element.IElement;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class Container extends GuiScreen implements IContainer
{
	protected final List<IElement> elements = new ArrayList<IElement>();
	
	@Override
	public void add(GuiButton button)
	{
		this.buttonList.add(button);
	}
	
	@Override
	public void add(Element element)
	{
		this.elements.add(element);
	}
}
