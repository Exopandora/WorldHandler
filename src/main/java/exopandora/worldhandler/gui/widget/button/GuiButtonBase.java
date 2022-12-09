package exopandora.worldhandler.gui.widget.button;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.RenderUtils;
import exopandora.worldhandler.util.ResourceHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class GuiButtonBase extends Button
{
	public GuiButtonBase(int x, int y, int width, int height, String translationKey, ActionHandler actionHandler)
	{
		this(x, y, width, height, Component.translatable(translationKey), actionHandler);
	}
	
	public GuiButtonBase(int x, int y, int width, int height, Component buttonText, ActionHandler actionHandler)
	{
		super(x, y, width, height, buttonText, button -> ActionHelper.tryRun(actionHandler), Button.DEFAULT_NARRATION);
	}
	
	@Override
	public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBg(poseStack, Minecraft.getInstance(), mouseX, mouseY);
		GuiComponent.drawCenteredString(poseStack, Minecraft.getInstance().font, this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, this.getFGColor());
	}
	
	@Override
	protected void renderBg(PoseStack poseStack, Minecraft minecraft, int mouseX, int mouseY)
	{
		RenderSystem.enableBlend();
		RenderUtils.colorDefaultButton();
		
		int hovered = this.getYImage(this.isHoveredOrFocused());
		RenderSystem.setShaderTexture(0, ResourceHelper.buttonTexture());
    	
		int hWidth = this.width / 2;
		int hHeight = this.height / 2;
		
		if(Config.getSkin().getTextureType().equals("resourcepack"))
		{
			int textureOffset = 46 + hovered * 20;
			
			this.blit(poseStack, this.getX(), this.getY(), 0, textureOffset, hWidth, hHeight);
			this.blit(poseStack, this.getX(), this.getY() + hHeight, 0, textureOffset + 20 - hHeight, hWidth, hHeight);
			this.blit(poseStack, this.getX() + hWidth, this.getY(), 200 - hWidth, textureOffset, hWidth, hHeight);
			this.blit(poseStack, this.getX() + hWidth, this.getY() + hHeight, 200 - hWidth, textureOffset + 20 - hHeight, hWidth, hHeight);
		}
		else
		{
			int textureOffset = hovered * 20;
			
			this.blit(poseStack, this.getX(), this.getY(), 0, textureOffset, hWidth, hHeight);
			this.blit(poseStack, this.getX(), this.getY() + hHeight, 0, textureOffset + 20 - hHeight, hWidth, hHeight);
			this.blit(poseStack, this.getX() + hWidth, this.getY(), 200 - hWidth, textureOffset, this.width / 2, hHeight);
			this.blit(poseStack, this.getX() + hWidth, this.getY() + hHeight, 200 - hWidth, textureOffset + 20 - hHeight, hWidth, hHeight);
		}
		
		RenderSystem.disableBlend();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
