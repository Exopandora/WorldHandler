package exopandora.worldhandler.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.RenderUtils;
import exopandora.worldhandler.util.ResourceHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiButtonBase extends Button
{
	public GuiButtonBase(int x, int y, int widthIn, int heightIn, String translationKey, ActionHandler actionHandler)
	{
		this(x, y, widthIn, heightIn, new TranslationTextComponent(translationKey), actionHandler);
	}
	
	public GuiButtonBase(int x, int y, int widthIn, int heightIn, ITextComponent buttonText, ActionHandler actionHandler)
	{
		super(x, y, widthIn, heightIn, buttonText, button -> ActionHelper.tryRun(actionHandler));
	}
	
	@Override
	public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBg(matrix, Minecraft.getInstance(), mouseX, mouseY);
		AbstractGui.drawCenteredString(matrix, Minecraft.getInstance().font, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, this.getFGColor());
	}
	
	@Override
	protected void renderBg(MatrixStack matrix, Minecraft minecraft, int mouseX, int mouseY)
	{
		RenderSystem.enableBlend();
		RenderUtils.colorDefaultButton();
		
		int hovered = this.getYImage(this.isHovered());
    	Minecraft.getInstance().getTextureManager().bind(ResourceHelper.buttonTexture());
    	
		int hWidth = this.width / 2;
		int hHeight = this.height / 2;
		
		if(Config.getSkin().getTextureType().equals("resourcepack"))
		{
			int textureOffset = 46 + hovered * 20;
			
			this.blit(matrix, this.x, this.y, 0, textureOffset, hWidth, hHeight);
			this.blit(matrix, this.x, this.y + hHeight, 0, textureOffset + 20 - hHeight, hWidth, hHeight);
			this.blit(matrix, this.x + hWidth, this.y, 200 - hWidth, textureOffset, hWidth, hHeight);
			this.blit(matrix, this.x + hWidth, this.y + hHeight, 200 - hWidth, textureOffset + 20 - hHeight, hWidth, hHeight);
		}
		else
		{
			int textureOffset = hovered * 20;
			
			this.blit(matrix, this.x, this.y, 0, textureOffset, hWidth, hHeight);
			this.blit(matrix, this.x, this.y + hHeight, 0, textureOffset + 20 - hHeight, hWidth, hHeight);
			this.blit(matrix, this.x + hWidth, this.y, 200 - hWidth, textureOffset, this.width / 2, hHeight);
			this.blit(matrix, this.x + hWidth, this.y + hHeight, 200 - hWidth, textureOffset + 20 - hHeight, hWidth, hHeight);
		}
		
		RenderSystem.disableBlend();
		RenderUtils.color(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
