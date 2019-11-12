package exopandora.worldhandler.gui.button;

import com.mojang.blaze3d.platform.GlStateManager;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.ResourceHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiButtonBase extends Button
{
	public GuiButtonBase(int x, int y, int widthIn, int heightIn, String buttonText, ActionHandler actionHandler)
	{
		super(x, y, widthIn, heightIn, buttonText, button -> ActionHelper.tryRun(actionHandler));
	}
	
	@Override
	public void renderButton(int mouseX, int mouseY, float partialTicks)
	{
		this.renderBg(Minecraft.getInstance(), mouseX, mouseY);
		this.drawCenteredString(Minecraft.getInstance().fontRenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, this.getFGColor());
	}
	
	@Override
	protected void renderBg(Minecraft minecraft, int mouseX, int mouseY)
	{
		GlStateManager.enableBlend();
		GlStateManager.color4f(Config.getSkin().getButtonRedF(), Config.getSkin().getButtonGreenF(), Config.getSkin().getButtonBlueF(), Config.getSkin().getButtonAlphaF());
		
		int hovered = this.getYImage(this.isHovered());
		
    	Minecraft.getInstance().getTextureManager().bindTexture(ResourceHelper.getButtonTexture());
    	
		if(Config.getSkin().getTextureType().equals("resourcepack"))
		{
			this.blit(this.x, this.y, 0, 46 + hovered * 20, this.width / 2, this.height);
			this.blit(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + hovered * 20, this.width / 2, this.height);
		}
		else
		{
			this.blit(this.x, this.y, 0, hovered * 20, this.width / 2, this.height);
			this.blit(this.x + this.width / 2, this.y, 200 - this.width / 2, hovered * 20, this.width / 2, this.height);
		}
		
		GlStateManager.disableBlend();
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
