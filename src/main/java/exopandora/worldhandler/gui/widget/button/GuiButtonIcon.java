package exopandora.worldhandler.gui.widget.button;

import com.mojang.blaze3d.systems.RenderSystem;

import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.ResourceHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class GuiButtonIcon extends GuiButtonTooltip
{
	private final EnumIcon icon;
	
	public GuiButtonIcon(int x, int y, int width, int height, EnumIcon icon, Component tooltip, ActionHandler actionHandler)
	{
		super(x, y, width, height, tooltip, tooltip, actionHandler);
		this.icon = icon;
	}
	
	@Override
	public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks)
	{
		super.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
		
		if(this.icon != null)
		{
			this.renderIcon(guiGraphics);
		}
	}
	
	private void renderIcon(GuiGraphics guiGraphics)
	{
		if(this.active)
		{
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		}
		else
		{
			RenderSystem.setShaderColor(0.8F, 0.8F, 0.8F, 1.0F);
		}
		
		guiGraphics.blit(ResourceHelper.iconTexture(), this.getX() + this.width / 2 - 4, this.getY() + this.height / 2 - 4, this.icon.getX() * 8, this.icon.getY() * 8, 8, 8);
	}
}
