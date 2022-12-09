package exopandora.worldhandler.gui.container;

import java.util.ArrayList;
import java.util.List;

import exopandora.worldhandler.gui.widget.menu.IMenu;
import exopandora.worldhandler.gui.widget.menu.Menu;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public abstract class Container extends Screen implements IContainer
{
	protected final List<IMenu> menus = new ArrayList<IMenu>();
	
	protected Container(Component title)
	{
		super(title);
	}
	
	// Workaround protected modifier
	@Override
	public void init()
	{
		super.init();
	}
	
	// Workaround protected modifier
	@Override
	public <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(T widget)
	{
		return super.addRenderableWidget(widget);
	}
	
	// Workaround protected modifier
	@Override
	public <T extends Renderable> T addRenderableOnly(T widget)
	{
		return super.addRenderableOnly(widget);
	}
	
	// Workaround protected modifier
	@Override
	public <T extends GuiEventListener & NarratableEntry> T addWidget(T widget)
	{
		return super.addWidget(widget);
	}
	
	@Override
	public Menu addMenu(Menu menu)
	{
		this.menus.add(menu);
		return menu;
	}
}
