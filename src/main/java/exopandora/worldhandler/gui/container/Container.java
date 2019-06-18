package exopandora.worldhandler.gui.container;

import java.util.ArrayList;
import java.util.List;

import exopandora.worldhandler.gui.content.element.Element;
import exopandora.worldhandler.gui.content.element.IElement;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class Container extends Screen implements IContainer
{
	protected Container(ITextComponent title)
	{
		super(title);
	}
	
	protected final List<IElement> elements = new ArrayList<IElement>();
	
	@Override
	public <T extends Widget> T add(T button)
	{
		return super.addButton(button);
	}
	
	public <T extends TextFieldWidget> T add(T textfield)
	{
		this.children.add(textfield);
		return textfield;
	}
	
	@Override
	public void init()
	{
		super.init();
	}
	
	@Override
	public void add(Element element)
	{
		this.elements.add(element);
	}
}
