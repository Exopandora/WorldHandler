package exopandora.worldhandler.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.RenderUtils;
import exopandora.worldhandler.util.ResourceHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class WidgetTabRenderer implements IContainerWidget
{
	private static final int SPACING = 2;
	private static final int WEDGE_HEIGHT = 10;
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		Content content = container.getContent();
		Category category = content.getCategory();
		
		int xPos = container.getBackgroundX();
		int yPos = container.getBackgroundY() - 20;
		
		int size = category.getSize();
		
		for(int index = 0; index < size; index++)
		{
			Content tab = category.getContent(index);
			
			if(!tab.equals(content.getActiveContent()))
			{
				int width = WidgetTabRenderer.width(container, index, size);
				int offset = WidgetTabRenderer.offset(container, index, size);
				
				container.addWidget(new GuiButtonBase(xPos + offset, yPos, width, 21, tab.getTabTitle(), () -> ActionHelper.open(tab)));
			}
		}
	}
	
	@Override
	public void drawScreen(GuiGraphics guiGraphics, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		Content content = container.getContent();
		Category category = content.getCategory();
		ResourceLocation texture = ResourceHelper.backgroundTexture();
		
		int xPos = container.getBackgroundX();
		int yPos = container.getBackgroundY();
		
		int size = category.getSize();
		
		for(int index = 0; index < size; index++)
		{
			int width = WidgetTabRenderer.width(container, index, size);
			int offset = WidgetTabRenderer.offset(container, index, size);
			
			Content tab = category.getContent(index);
			Component title = tab.getTabTitle().withStyle(ChatFormatting.UNDERLINE);
			
			if(content.getActiveContent().equals(tab))
			{
				int height = Config.getSkin().getBackgroundAlphaInt() == 255 ? 25 : 22;
				this.drawActiveTab(guiGraphics, container, texture, index, size, xPos + offset, yPos - 22, width, height, title);
			}
			else
			{
				this.drawInactiveTab(guiGraphics, container, texture, index, size, xPos + offset, yPos - 20, width, 20, title);
			}
		}
		
		RenderUtils.colorDefaultBackground();
	}
	
	private void drawActiveTab(GuiGraphics guiGraphics, Container container, ResourceLocation texture, int index, int size, int x, int y, int width, int height, Component title)
	{
		RenderUtils.colorDefaultBackground();
		this.drawTabBackground(guiGraphics, container, texture, x, y, width, height);
		
		if(!Config.getSkin().sharpEdges())
		{
			RenderSystem.enableBlend();
			
			if(Config.getSkin().getBackgroundAlphaInt() == 255)
			{
				if(index > 0)
				{
					RenderUtils.drawTexturedTriangleBL(guiGraphics, texture, x, y + height - 2, x - container.getBackgroundX(), 1, 2);
				}
				
				if(index < size - 1 || size == 1)
				{
					RenderUtils.drawTexturedTriangleBR(guiGraphics, texture, x + width - 2, y + height - 2, x - container.getBackgroundX() + width, 1, 2);
				}
				
				if(index == 0)
				{
					RenderUtils.drawTexturedWedgeGradientTL(guiGraphics, texture, x, y + height, 0, height, width, WidgetTabRenderer.WEDGE_HEIGHT);
				}
				
				if(index == size - 1 && size > 1)
				{
					RenderUtils.drawTexturedWedgeGradientTR(guiGraphics, texture, x, y + height, x - container.getBackgroundX(), height, width, WidgetTabRenderer.WEDGE_HEIGHT);
				}
			}
			else
			{
				this.drawTabBackgroundMerge(guiGraphics, container, texture, index, size, x, y, width, height);
			}
			
			RenderSystem.disableBlend();
		}
		
		this.drawTabTitle(guiGraphics, title, true, x, y, width, y + height, 0xFFFFFF);
	}
	
	private void drawInactiveTab(GuiGraphics guiGraphics, Container container, ResourceLocation texture, int index, int size, int x, int y, int width, int height, Component title)
	{
		RenderUtils.colorDarkBackground();
		this.drawTabBackground(guiGraphics, container, texture, x, y, width, 20);
		
		if(!Config.getSkin().sharpEdges())
		{
			RenderSystem.enableBlend();
			this.drawTabBackgroundMerge(guiGraphics, container, texture, index, size, x, y, width, height);
			RenderSystem.disableBlend();
		}
		
		this.drawTabTitle(guiGraphics, title, false, x, y + 2, width, y + height, 0xE0E0E0);
	}
	
	private void drawTabBackgroundMerge(GuiGraphics guiGraphics, Container container, ResourceLocation texture, int index, int size, int x, int y, int width, int height)
	{
		if(index == 0)
		{
			RenderUtils.drawTexturedTriangleTL(guiGraphics, texture, x, y + height, 0, height, 2);
		}
		
		if(index == size - 1)
		{
			RenderUtils.drawTexturedTriangleTR(guiGraphics, texture, x + width - 3, y + height, container.getBackgroundWidth() - 3, height, 3);
		}
	}
	
	private void drawTabBackground(GuiGraphics guiGraphics, Container container, ResourceLocation texture, int x, int y, int width, int height)
	{
		int left = Mth.ceil(width / 2D);
		int right = Mth.floor(width / 2D);
		
		RenderSystem.enableBlend();
		
		guiGraphics.blit(texture, x, y, 0, 0, left, height);
		guiGraphics.blit(texture, x + left, y, container.getBackgroundWidth() - right, 0, right, height);
		
		RenderSystem.disableBlend();
	}
	
	private void drawTabTitle(GuiGraphics guiGraphics, Component title, boolean isActive, int x, int y, int width, int height, int color)
	{
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		Font font = Minecraft.getInstance().font;
		AbstractWidget.renderScrollingString(guiGraphics, font, title, x + 5, y, x + width - 5, height, color);
	}
	
	@Override
	public EnumLayer getLayer()
	{
		return EnumLayer.BACKGROUND;
	}
	
	private static int width(Container container, int index, int size)
	{
		int width = (int) Math.round((container.getBackgroundWidth() - Math.max(size - 1, 1) * SPACING) / Math.max(size, 2));
		
		if(index == 1 && size == 3)
		{
			return width + 1;
		}
		
		return width;
	}
	
	private static int offset(Container container, int index, int size)
	{
		return (int) Math.round(index * (double) (container.getBackgroundWidth() + SPACING) / size);
	}
	
	@Override
	public void setFocused(boolean focused)
	{
		return;
	}
	
	@Override
	public boolean isFocused()
	{
		return false;
	}
}
