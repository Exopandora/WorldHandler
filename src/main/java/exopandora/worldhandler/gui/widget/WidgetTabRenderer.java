package exopandora.worldhandler.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.widget.button.GuiButtonTab;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.RenderUtils;
import exopandora.worldhandler.util.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class WidgetTabRenderer implements IContainerWidget
{
	private static final int SPACING = 2;
	private static final int WEDGE_HEIGHT = 10;
	
	@Override
	public void initGui(Container container, int x, int y)
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
				
				container.addWidget(new GuiButtonTab(xPos + offset, yPos, width, 21, tab.getTabTitle(), () -> ActionHelper.open(tab)));
			}
		}
	}
	
	@Override
	public void drawScreen(PoseStack matrix, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		Content content = container.getContent();
		Category category = content.getCategory();
		
		int xPos = container.getBackgroundX();
		int yPos = container.getBackgroundY();
		
		int size = category.getSize();
		
		container.setBlitOffset(0);
		
		for(int index = 0; index < size; index++)
		{
			int width = WidgetTabRenderer.width(container, index, size);
			int offset = WidgetTabRenderer.offset(container, index, size);
			
			Content tab = category.getContent(index);
			Component title = TextUtils.stripText(tab.getTabTitle().withStyle(ChatFormatting.UNDERLINE), width, Minecraft.getInstance().font);
			
			if(content.getActiveContent().equals(tab))
			{
				int height = Config.getSkin().getBackgroundAlphaInt() == 255 ? 25 : 22;
				this.drawActiveTab(matrix, container, index, size, xPos + offset, yPos - 22, width, height, title);
			}
			else
			{
				this.drawInactiveTab(matrix, container, index, size, xPos + offset, yPos - 20, width, 20, title);
			}
		}
		
		RenderUtils.colorDefaultBackground();
	}
	
	private void drawActiveTab(PoseStack matrix, Container container, int index, int size, int x, int y, int width, int height, Component title)
	{
		RenderUtils.colorDefaultBackground();
		this.drawTabBackground(matrix, container, x, y, width, height);
		
		if(!Config.getSkin().sharpEdges())
		{
			RenderSystem.enableBlend();
			
			if(Config.getSkin().getBackgroundAlphaInt() == 255)
			{
				if(index > 0)
				{
					RenderUtils.drawTexturedTriangleBL(matrix, container, x, y + height - 2, x - container.getBackgroundX(), 1, 2);
				}
				
				if(index < size - 1 || size == 1)
				{
					RenderUtils.drawTexturedTriangleBR(matrix, container, x + width - 2, y + height - 2, x - container.getBackgroundX() + width, 1, 2);
				}
				
				if(index == 0)
				{
					RenderUtils.drawTexturedWedgeGradientTL(matrix, container, x, y + height, 0, height, width, WidgetTabRenderer.WEDGE_HEIGHT);
				}
				
				if(index == size - 1 && size > 1)
				{
					RenderUtils.drawTexturedWedgeGradientTR(matrix, container, x, y + height, x - container.getBackgroundX(), height, width, WidgetTabRenderer.WEDGE_HEIGHT);
				}
			}
			else
			{
				this.drawTabBackgroundMerge(matrix, container, index, size, x, y, width, height);
			}
			
			RenderSystem.disableBlend();
		}
		
		this.drawTabTitle(matrix, container, title, x + width / 2, y + 9, 0xFFFFFF);
	}
	
	private void drawInactiveTab(PoseStack matrix, Container container, int index, int size, int x, int y, int width, int height, Component title)
	{
		RenderUtils.colorDarkBackground();
		this.drawTabBackground(matrix, container, x, y, width, 20);
		
		if(!Config.getSkin().sharpEdges())
		{
			RenderSystem.enableBlend();
			this.drawTabBackgroundMerge(matrix, container, index, size, x, y, width, height);
			RenderSystem.disableBlend();
		}
		
		this.drawTabTitle(matrix, container, title, x + width / 2, y + 7, 0xE0E0E0);
	}
	
	private void drawTabBackgroundMerge(PoseStack matrix, Container container, int index, int size, int x, int y, int width, int height)
	{
		if(index == 0)
		{
			RenderUtils.drawTexturedTriangleTL(matrix, container, x, y + height, 0, height, 2);
		}
		
		if(index == size - 1)
		{
			RenderUtils.drawTexturedTriangleTR(matrix, container, x + width - 3, y + height, container.getBackgroundWidth() - 3, height, 3);
		}
	}
	
	private void drawTabBackground(PoseStack matrix, Container container, int x, int y, int width, int height)
	{
		container.bindBackground();
		container.setBlitOffset(-1);
		
		int left = Mth.ceil(width / 2D);
		int right = Mth.floor(width / 2D);
		
		RenderSystem.enableBlend();
		
		container.blit(matrix, x, y, 0, 0, left, height);
		container.blit(matrix, x + left, y, container.getBackgroundWidth() - right, 0, right, height);
		
		RenderSystem.disableBlend();
	}
	
	private void drawTabTitle(PoseStack matrix, GuiComponent gui, Component title, int x, int y, int color)
	{
		gui.setBlitOffset(0);
		GuiComponent.drawCenteredString(matrix, Minecraft.getInstance().font, title, x, y, color);
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
}
