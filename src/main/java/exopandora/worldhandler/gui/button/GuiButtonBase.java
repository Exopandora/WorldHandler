package exopandora.worldhandler.gui.button;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.helper.ActionHelper;
import exopandora.worldhandler.helper.ResourceHelper;
import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiButtonBase extends GuiButton
{
	protected ActionHandler actionHandler;
	
	public GuiButtonBase(int x, int y, int widthIn, int heightIn, String buttonText, ActionHandler actionHandler)
	{
		this(0, x, y, widthIn, heightIn, buttonText, actionHandler);
	}
	
	public GuiButtonBase(int id, int x, int y, int widthIn, int heightIn, String buttonText, ActionHandler actionHandler)
	{
		super(id, x, y, widthIn, heightIn, buttonText);
		this.actionHandler = actionHandler;
	}
	
	protected void drawBackground(int mouseX, int mouseY)
	{
		GlStateManager.enableBlend();
		GlStateManager.color4f(Config.getSkin().getButtonRedF(), Config.getSkin().getButtonGreenF(), Config.getSkin().getButtonBlueF(), Config.getSkin().getButtonAlphaF());
		
		this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		int hovered = this.getHoverState(this.hovered);
		
    	Minecraft.getInstance().getTextureManager().bindTexture(ResourceHelper.getButtonTexture());
    	
		if(Config.getSkin().getTextureType().equals("resourcepack"))
		{
			this.drawTexturedModalRect(this.x, this.y, 0, 46 + hovered * 20, this.width / 2, this.height);
			this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + hovered * 20, this.width / 2, this.height);
		}
		else
		{
			this.drawTexturedModalRect(this.x, this.y, 0, hovered * 20, this.width / 2, this.height);
			this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, hovered * 20, this.width / 2, this.height);
		}
		
		GlStateManager.disableBlend();
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks)
	{
		if(this.visible)
		{
			this.drawBackground(mouseX, mouseY);
			this.renderBg(Minecraft.getInstance(), mouseX, mouseY);
			this.drawCenteredString(Minecraft.getInstance().fontRenderer, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, this.getTextColor());
		}
	}
	
	@Override
	public void onClick(double mouseX, double mouseY)
	{
		super.onClick(mouseX, mouseY);
		
		if(this.actionHandler != null)
		{
			ActionHelper.tryRun(this.actionHandler);
		}
	}
	
	protected int getTextColor()
	{
		int textColor = 0xE0E0E0;
        
        if(!this.enabled)
        {
        	textColor = 0xA0A0A0;
        }
        else if(this.hovered)
        {
        	textColor = 0xFFFFA0;
        }
        
        return textColor;
	}
}
