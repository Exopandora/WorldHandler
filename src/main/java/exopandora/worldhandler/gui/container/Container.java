package exopandora.worldhandler.gui.container;

import java.util.ArrayList;
import java.util.List;

import exopandora.worldhandler.gui.content.element.Element;
import exopandora.worldhandler.gui.content.element.IElement;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class Container extends GuiScreen implements IContainer
{
	protected final List<IElement> elements = new ArrayList<IElement>();
	
	@Override
	public <T extends GuiButton> T add(T button)
	{
		return super.addButton(button);
	}
	
	public <T extends GuiTextField> T add(T textfield)
	{
		this.children.add(textfield);
		return textfield;
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
	}
	
	@Override
	public void add(Element element)
	{
		this.elements.add(element);
	}
}
