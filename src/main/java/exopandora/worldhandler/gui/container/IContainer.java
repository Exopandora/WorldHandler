package exopandora.worldhandler.gui.container;

import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.menu.Menu;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;

public interface IContainer
{
	<T extends GuiEventListener & Widget & NarratableEntry> T add(T button);
	
	void initButtons();
	Menu add(Menu menu);
	AbstractWidget addWidget(AbstractWidget button);
	
	String getPlayer();
	void setPlayer(String text);
	
	Content getContent();
	
	int getBackgroundX();
	int getBackgroundY();
	
	int getBackgroundWidth();
	int getBackgroundHeight();
	
	void bindBackground();
}
