package exopandora.worldhandler.gui.widget.button;

import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class GuiButtonTooltip extends GuiButtonBase
{
	protected Component tooltip;
	
	public GuiButtonTooltip(int x, int y, int width, int height, Component buttonText, Component tooltip, ActionHandler actionHandler)
	{
		super(x, y, width, height, buttonText, actionHandler);
		this.tooltip = tooltip;
	}
	
	public void renderTooltip(Screen screen, PoseStack poseStack, int mouseX, int mouseY)
	{
		if(this.isHoveredOrFocused() && this.tooltip != null && !this.tooltip.getString().isEmpty())
		{
			screen.renderTooltip(poseStack, this.tooltip, mouseX, mouseY);
		}
	}
}
