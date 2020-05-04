package exopandora.worldhandler.gui.button;

import com.mojang.blaze3d.systems.RenderSystem;

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
		RenderSystem.enableBlend();
		RenderSystem.color4f(Config.getSkin().getButtonRedF(), Config.getSkin().getButtonGreenF(), Config.getSkin().getButtonBlueF(), Config.getSkin().getButtonAlphaF());
		
		int hovered = this.getYImage(this.isHovered());
    	Minecraft.getInstance().getTextureManager().bindTexture(ResourceHelper.getButtonTexture());
    	
		int hWidth = this.width / 2;
		int hHeight = this.height / 2;
		
		if(Config.getSkin().getTextureType().equals("resourcepack"))
		{
			int textureOffset = 46 + hovered * 20;
			
			this.blit(this.x, this.y, 0, textureOffset, hWidth, hHeight);
			this.blit(this.x, this.y + hHeight, 0, textureOffset + 20 - hHeight, hWidth, hHeight);
			this.blit(this.x + hWidth, this.y, 200 - hWidth, textureOffset, hWidth, hHeight);
			this.blit(this.x + hWidth, this.y + hHeight, 200 - hWidth, textureOffset + 20 - hHeight, hWidth, hHeight);
		}
		else
		{
			int textureOffset = hovered * 20;
			
			this.blit(this.x, this.y, 0, textureOffset, hWidth, hHeight);
			this.blit(this.x, this.y + hHeight, 0, textureOffset + 20 - hHeight, hWidth, hHeight);
			this.blit(this.x + hWidth, this.y, 200 - hWidth, textureOffset, this.width / 2, hHeight);
			this.blit(this.x + hWidth, this.y + hHeight, 200 - hWidth, textureOffset + 20 - hHeight, hWidth, hHeight);
		}
		
		RenderSystem.disableBlend();
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
