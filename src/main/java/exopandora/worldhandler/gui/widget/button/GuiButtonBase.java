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
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class GuiButtonBase extends AbstractButton
{
	private final ActionHandler actionHandler;
	
	public GuiButtonBase(int x, int y, int width, int height, String translationKey, ActionHandler actionHandler)
	{
		this(x, y, width, height, Component.translatable(translationKey), actionHandler);
	}
	
	public GuiButtonBase(int x, int y, int width, int height, Component buttonText, ActionHandler actionHandler)
	{
		super(x, y, width, height, buttonText);
		this.actionHandler = actionHandler;
	}
	
	@Override
	public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(poseStack, mouseX, mouseY, partialTicks);
		this.renderString(poseStack, Minecraft.getInstance().font, this.getFGColor() | Mth.ceil(this.alpha * 255.0F) << 24);
	}
	
	protected void renderBackground(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
	{
		RenderSystem.enableBlend();
		RenderUtils.colorDefaultButton();
		
		int textureY = this.getTextureY();
		RenderSystem.setShaderTexture(0, ResourceHelper.buttonTexture());
    	
		int hWidth = this.width / 2;
		int hHeight = this.height / 2;
		
		if(Config.getSkin().getTextureType().equals("resourcepack"))
		{
			int textureOffset = 46 + textureY * 20;
			
			GuiComponent.blit(poseStack, this.getX(), this.getY(), 0, textureOffset, hWidth, hHeight);
			GuiComponent.blit(poseStack, this.getX(), this.getY() + hHeight, 0, textureOffset + 20 - hHeight, hWidth, hHeight);
			GuiComponent.blit(poseStack, this.getX() + hWidth, this.getY(), 200 - hWidth, textureOffset, hWidth, hHeight);
			GuiComponent.blit(poseStack, this.getX() + hWidth, this.getY() + hHeight, 200 - hWidth, textureOffset + 20 - hHeight, hWidth, hHeight);
		}
		else
		{
			int textureOffset = textureY * 20;
			
			GuiComponent.blit(poseStack, this.getX(), this.getY(), 0, textureOffset, hWidth, hHeight);
			GuiComponent.blit(poseStack, this.getX(), this.getY() + hHeight, 0, textureOffset + 20 - hHeight, hWidth, hHeight);
			GuiComponent.blit(poseStack, this.getX() + hWidth, this.getY(), 200 - hWidth, textureOffset, this.width / 2, hHeight);
			GuiComponent.blit(poseStack, this.getX() + hWidth, this.getY() + hHeight, 200 - hWidth, textureOffset + 20 - hHeight, hWidth, hHeight);
		}
		
		RenderSystem.disableBlend();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	protected int getTextureY()
	{
		int i = 1;
		
		if(!this.active)
		{
			i = 0;
		}
		else if(this.isHoveredOrFocused())
		{
			i = 2;
		}
		
		return i;
	}
	
	@Override
	public void onPress()
	{
		ActionHelper.tryRun(this.actionHandler);
	}
	
	@Override
	protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput)
	{
		this.defaultButtonNarrationText(narrationElementOutput);
	}
}
