package exopandora.worldhandler.gui.widget.button;

import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class GuiButtonTooltip extends GuiButtonBase
{
	protected Component tooltip;
	
	public GuiButtonTooltip(int x, int y, int widthIn, int heightIn, Component buttonText, Component tooltip, ActionHandler actionHandler)
	{
		super(x, y, widthIn, heightIn, buttonText, actionHandler);
		this.tooltip = tooltip;
	}
	
	public void renderTooltip(Screen screen, PoseStack matrix, int mouseX, int mouseY)
	{
		if(this.isHovered() && this.tooltip != null && !this.tooltip.getString().isEmpty())
		{
			screen.renderTooltip(matrix, this.tooltip, mouseX, mouseY);
		}
	}
}
