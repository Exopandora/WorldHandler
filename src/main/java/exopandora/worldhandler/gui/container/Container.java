package exopandora.worldhandler.gui.container;

import java.util.ArrayList;
import java.util.List;

import exopandora.worldhandler.gui.menu.IMenu;
import exopandora.worldhandler.gui.menu.Menu;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public abstract class Container extends Screen implements IContainer
{
	protected final List<IMenu> menus = new ArrayList<IMenu>();
	protected final List<AbstractWidget> widgetButtons = new ArrayList<AbstractWidget>();
	
	protected Container(Component title)
	{
		super(title);
	}
	
	@Override
	public void init()
	{
		super.init();
	}
	
	@Override
	public <T extends GuiEventListener & Widget & NarratableEntry> T add(T button)
	{
		return super.addRenderableWidget(button);
	}
	
	public <T extends EditBox> T add(T textfield)
	{
		return super.addWidget(textfield);
	}
	
	@Override
	public Menu add(Menu menu)
	{
		this.menus.add(menu);
		return menu;
	}
	
	@Override
	public AbstractWidget addWidget(AbstractWidget button)
	{
		this.widgetButtons.add(button);
		return button;
	}
}
