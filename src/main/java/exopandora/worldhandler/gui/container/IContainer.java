package exopandora.worldhandler.gui.container;

import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.widget.menu.Menu;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;

public interface IContainer
{
	void initButtons();
	
	<T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(T widget);
	<T extends Renderable> T addRenderableOnly(T widget);
	<T extends GuiEventListener & NarratableEntry> T addWidget(T widget);
	Menu addMenu(Menu menu);
	
	String getPlayer();
	void setPlayer(String text);
	
	Content getContent();
	
	int getBackgroundX();
	int getBackgroundY();
	
	int getBackgroundWidth();
	int getBackgroundHeight();
	
	void bindBackground();
}
