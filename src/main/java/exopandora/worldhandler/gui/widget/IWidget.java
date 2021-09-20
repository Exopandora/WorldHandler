package exopandora.worldhandler.gui.widget;

import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.gui.container.Container;
import net.minecraft.client.gui.components.events.GuiEventListener;

public interface IWidget extends GuiEventListener
{
	default void init(Container container)
	{
		
	}
	
	default void initGui(Container container, int x, int y)
	{
		
	}
	
	default void initButtons(Container container, int x, int y)
	{
		
	}
	
	default void tick(Container container)
	{
		
	}
	
	default void drawScreen(PoseStack stack, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		
	}
	
	default void onPlayerNameChanged(String username)
	{
		
	}
}
