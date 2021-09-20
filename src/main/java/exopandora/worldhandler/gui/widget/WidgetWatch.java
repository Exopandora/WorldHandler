package exopandora.worldhandler.gui.widget;

import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.util.RenderUtils;
import exopandora.worldhandler.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;

public class WidgetWatch implements IContainerWidget
{
	@Override
	public void drawScreen(PoseStack matrix, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		final int watchX = container.getBackgroundX() + 233;
		final int watchY = container.getBackgroundY() + 5;
		
		long time = 0;
		
		if(Minecraft.getInstance().level != null)
		{
			time = Minecraft.getInstance().level.getLevelData().getDayTime();
		}
		
		RenderUtils.drawWatchIntoGui(matrix, container, watchX, watchY, time, Config.getSettings().smoothWatch());
		
		if(Config.getSettings().tooltips() && mouseX >= watchX && mouseX <= watchX + 9 && mouseY >= watchY && mouseY <= watchY + 9)
		{
			container.renderTooltip(matrix, new TextComponent(TextUtils.formatWorldTime(time)), mouseX, mouseY + 9);
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
}
