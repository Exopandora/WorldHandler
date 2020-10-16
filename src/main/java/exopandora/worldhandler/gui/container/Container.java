package exopandora.worldhandler.gui.container;

import java.util.ArrayList;
import java.util.List;

import exopandora.worldhandler.gui.menu.IMenu;
import exopandora.worldhandler.gui.menu.Menu;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class Container extends Screen implements IContainer
{
	protected final List<IMenu> menus = new ArrayList<IMenu>();
	protected final List<Widget> widgetButtons = new ArrayList<Widget>();
	
	protected Container(ITextComponent title)
	{
		super(title);
	}
	
	@Override
	public void init()
	{
		super.init();
	}
	
	@Override
	public <T extends Widget> T add(T button)
	{
		return super.addButton(button);
	}
	
	public <T extends TextFieldWidget> T add(T textfield)
	{
		return super.addListener(textfield);
	}
	
	@Override
	public Menu add(Menu menu)
	{
		this.menus.add(menu);
		return menu;
	}
	
	@Override
	public Widget addWidget(Widget button)
	{
		this.widgetButtons.add(button);
		return button;
	}
}
