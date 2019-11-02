package exopandora.worldhandler.gui.button;

import java.util.Arrays;
import java.util.List;

import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.config.GuiUtils;

@OnlyIn(Dist.CLIENT)
public class GuiButtonTooltip extends GuiButtonBase
{
	protected String tooltip;
	
	public GuiButtonTooltip(int x, int y, int widthIn, int heightIn, String buttonText, String tooltip, ActionHandler actionHandler)
	{
		super(x, y, widthIn, heightIn, buttonText, actionHandler);
		this.tooltip = tooltip;
	}
	
	public void renderTooltip(int mouseX, int mouseY)
	{
		if(this.isHovered() && this.tooltip != null && !this.tooltip.isEmpty())
		{
			List<String> list = Arrays.asList(this.tooltip.split("\n"));
			
			if(!list.isEmpty())
			{
				int tooltipWidth = Minecraft.getInstance().fontRenderer.getStringWidth(this.tooltip) + 9;
				int width = Minecraft.getInstance().currentScreen.width;
				int height = Minecraft.getInstance().currentScreen.height;
				
				GuiUtils.drawHoveringText(list, mouseX, mouseY, width, height, tooltipWidth, Minecraft.getInstance().fontRenderer);
			}
		}
	}
}
