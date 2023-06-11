package exopandora.worldhandler.gui.widget;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.util.RenderUtils;
import exopandora.worldhandler.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class WidgetWatch implements IContainerWidget
{
	private boolean focused = false;
	
	@Override
	public void drawScreen(GuiGraphics guiGraphics, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		final int watchX = container.getBackgroundX() + 233;
		final int watchY = container.getBackgroundY() + 5;
		
		long time = 0;
		
		if(Minecraft.getInstance().level != null)
		{
			time = Minecraft.getInstance().level.getLevelData().getDayTime();
		}
		
		RenderUtils.drawWatchIntoGui(guiGraphics, watchX, watchY, time, Config.getSettings().smoothWatch());
		
		if(Config.getSettings().tooltips() && mouseX >= watchX && mouseX <= watchX + 9 && mouseY >= watchY && mouseY <= watchY + 9)
		{
			guiGraphics.renderTooltip(Minecraft.getInstance().font, Component.literal(TextUtils.formatWorldTime(time)), mouseX, mouseY + 9);
		}
	}
	
	@Override
	public boolean isEnabled()
	{
		return Config.getSettings().watch();
	}
	
	@Override
	public EnumLayer getLayer()
	{
		return EnumLayer.BACKGROUND;
	}
	
	@Override
	public void setFocused(boolean focused)
	{
		this.focused = focused;
	}
	
	@Override
	public boolean isFocused()
	{
		return this.focused;
	}
}
