package exopandora.worldhandler.gui.widget.button;

import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class GuiButtonTooltip extends GuiButtonBase
{
	protected Component tooltip;
	
	public GuiButtonTooltip(int x, int y, int width, int height, Component buttonText, Component tooltip, ActionHandler actionHandler)
	{
		super(x, y, width, height, buttonText, actionHandler);
		this.tooltip = tooltip;
	}
	
	public void renderTooltip(GuiGraphics guiGraphics, Font font, int mouseX, int mouseY)
	{
		if(this.isHovered() && this.tooltip != null && !this.tooltip.getString().isEmpty())
		{
			guiGraphics.renderTooltip(font, this.tooltip, mouseX, mouseY);
		}
	}
}
