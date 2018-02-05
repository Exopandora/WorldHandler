package exopandora.worldhandler.gui.button;

import exopandora.worldhandler.config.ConfigSkin;
import exopandora.worldhandler.helper.ResourceHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiButtonWorldHandler extends GuiButton
{
	protected String displayTooltip;
	protected EnumTooltip tooltipType;
	protected EnumIcon icon;
	protected boolean isActive;
	
	public GuiButtonWorldHandler(int id, int x, int y, int width, int height, String displayString)
	{
		this(id, x, y, width, height, displayString, null, null);
	}
	
	public GuiButtonWorldHandler(int id, int x, int y, int width, int height, String displayString, String tooltip, EnumTooltip tooltipType)
	{
		this(id, x, y, width, height, displayString, tooltip, tooltipType, null);
	}
	
	public GuiButtonWorldHandler(int id, int x, int y, int width, int height, String displayString, EnumIcon icon)
	{
		this(id, x, y, width, height, displayString, null, null, icon);
	}
	
	public GuiButtonWorldHandler(int id, int x, int y, int width, int height, String displayString, String tooltip, EnumTooltip tooltipType, EnumIcon icon)
	{
		super(id, x, y, width, height, displayString);
		this.displayTooltip = tooltip;
		this.tooltipType = tooltipType;
		this.icon = icon;
	}
	
	@Override
	public void drawButton(Minecraft minecraft, int mouseX, int mouseY, float partialTicks)
	{
		if(this.visible)
		{
			this.drawBackground(minecraft, mouseX, mouseY);
			this.drawCenteredString(minecraft.fontRenderer, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, this.getTextColor());
						
			if(this.icon != null)
			{
				this.drawIcons();
			}
			
			this.isActive = true;
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
	
	protected void drawBackground(Minecraft minecraft, int mouseX, int mouseY)
	{
		GlStateManager.enableBlend();
		GlStateManager.color((float) ConfigSkin.getButtonRed() / 255, (float) ConfigSkin.getButtonGreen() / 255, (float) ConfigSkin.getButtonBlue() / 255, (float) ConfigSkin.getButtonAlpha() / 255);
		
		this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		int hovered = this.getHoverState(this.hovered);
		
    	Minecraft.getMinecraft().renderEngine.bindTexture(ResourceHelper.getButtonTexture());
    	
		if(ConfigSkin.getTextureType().equals("resourcepack"))
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
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	public void drawTooltip(int mouseX, int mouseY, int width, int height)
	{
		if(this.hovered && this.displayTooltip != null)
		{
			int tooltipWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(this.displayTooltip) + 9;
			int xOffset = 12;
			int yOffset = 12;
			boolean right = mouseX + xOffset + tooltipWidth > width;
			boolean left = mouseX > tooltipWidth + xOffset;
			
			switch(this.tooltipType)
			{
				case TOP_RIGHT:
					this.renderTooltip(mouseX, mouseY, xOffset, -yOffset, right);
					break;
				case TOP_LEFT:
					this.renderTooltip(mouseX, mouseY, xOffset, -yOffset, left);
					break;
				case BOTTOM_RIGHT:
					this.renderTooltip(mouseX, mouseY, xOffset, yOffset, right);
					break;
				case BOTTOM_LEFT:
					this.renderTooltip(mouseX, mouseY, xOffset, yOffset, left);
					break;
				case RIGHT:
					this.renderTooltip(mouseX, mouseY, xOffset, 0, right);
					break;
				case LEFT:
					this.renderTooltip(mouseX, mouseY, xOffset, 0, left);
					break;
				default:
					break;
			}
		}
	}
	
	protected void renderTooltip(int mouseX, int mouseY, int xOffset, int yOffset, boolean left)
	{
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		
		GlStateManager.disableDepth();
		
		String[] text = this.displayTooltip.split("\n");
		
		int tooltipTextWidth = 0;
		int tooltipX = mouseX + xOffset;
		int tooltipY = mouseY + yOffset;
		
		for(String line : text)
		{
			int length = fontRenderer.getStringWidth(line) + 1;
			
			if(length > tooltipTextWidth)
			{
				tooltipTextWidth = length;
			}
		}
		
		if(left)
		{
			tooltipX = mouseX - xOffset - tooltipTextWidth;
		}
		
		int tooltipHeight = fontRenderer.FONT_HEIGHT * text.length;
		int backgroundColor = 0xF0100010;
		int borderColorStart = 0x505000FF;
		int borderColorEnd = (borderColorStart & 0xFEFEFE) >> 1 | borderColorStart & 0xFF000000;
		
		int zLevel = 300;
		
		GuiUtils.drawGradientRect(zLevel, tooltipX - 3, tooltipY - 4, tooltipX + tooltipTextWidth + 3, tooltipY - 3, backgroundColor, backgroundColor);
		GuiUtils.drawGradientRect(zLevel, tooltipX - 3, tooltipY + tooltipHeight + 3, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 4, backgroundColor, backgroundColor);
		GuiUtils.drawGradientRect(zLevel, tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
		GuiUtils.drawGradientRect(zLevel, tooltipX - 4, tooltipY - 3, tooltipX - 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
		GuiUtils.drawGradientRect(zLevel, tooltipX + tooltipTextWidth + 3, tooltipY - 3, tooltipX + tooltipTextWidth + 4, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
		
		GuiUtils.drawGradientRect(zLevel, tooltipX - 3, tooltipY - 3 + 1, tooltipX - 3 + 1, tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
		GuiUtils.drawGradientRect(zLevel, tooltipX + tooltipTextWidth + 2, tooltipY - 3 + 1, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
		GuiUtils.drawGradientRect(zLevel, tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3, tooltipY - 3 + 1, borderColorStart, borderColorStart);
		GuiUtils.drawGradientRect(zLevel, tooltipX - 3, tooltipY + tooltipHeight + 2, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3, borderColorEnd, borderColorEnd);
		
		for(int x = 0; x < text.length; x++)
		{
			fontRenderer.drawStringWithShadow(text[x], tooltipX + 1, tooltipY + 1 + fontRenderer.FONT_HEIGHT * x, -1);
		}
		
		GlStateManager.enableDepth();
	}
	
	protected void drawIcons()
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceHelper.getIconTexture());
		
		if(this.enabled == true)
		{
			if(this.hovered)
			{
				GlStateManager.color(1.0F, 1.0F, 0.6F, 1.0F);
			}
			else
			{
				GlStateManager.color(0.95F, 0.95F, 0.95F, 1.0F);
			}
		}
		else
		{
			GlStateManager.color(0.8F, 0.8F, 0.8F, 1.0F);
		}
		
		this.drawTexturedModalRect(this.x + this.width / 2 - 4, this.y + 6, this.icon.getX() * 8, this.icon.getY() * 8, 8, 8);
	}
	
	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
	{
		return super.mousePressed(mc, mouseX, mouseY) && this.isActive;
	}
}
