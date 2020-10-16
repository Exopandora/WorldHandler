package exopandora.worldhandler.gui.container.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.widget.IContainerWidget;
import exopandora.worldhandler.gui.widget.button.GuiButtonTab;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.RenderUtils;
import exopandora.worldhandler.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TabRenderer implements IContainerWidget
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
				int width = TabRenderer.width(container, index, size);
				int offset = TabRenderer.offset(container, index, size);
				
				container.addWidget(new GuiButtonTab(xPos + offset, yPos, width, 21, tab.getTabTitle(), () -> ActionHelper.open(tab)));
			}
		}
	}
	
	@Override
	public void drawScreen(MatrixStack matrix, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		Content content = container.getContent();
		Category category = content.getCategory();
		
		int xPos = container.getBackgroundX();
		int yPos = container.getBackgroundY();
		
		int size = category.getSize();
		
		container.setBlitOffset(0);
		
		for(int index = 0; index < size; index++)
		{
			int width = TabRenderer.width(container, index, size);
			int offset = TabRenderer.offset(container, index, size);
			
			Content tab = category.getContent(index);
			ITextComponent title = TextUtils.stripText(tab.getTabTitle().mergeStyle(TextFormatting.UNDERLINE), width, Minecraft.getInstance().fontRenderer);
			
			if(content.getActiveContent().equals(tab))
			{
				this.drawActiveTab(matrix, container, index, size, xPos + offset, yPos - 22, width, 25, title);
			}
			else
			{
				this.drawInactiveTab(matrix, container, index, size, xPos + offset, yPos - 20, width, 20, title);
			}
		}
		
		RenderUtils.colorDefaultBackground();
	}
	
	private void drawActiveTab(MatrixStack matrix, Container container, int index, int size, int x, int y, int width, int height, ITextComponent title)
	{
		RenderUtils.colorDefaultBackground();
		this.drawTabBackground(matrix, container, x, y, width, height);
		
		if(!Config.getSkin().sharpEdges())
		{
			RenderSystem.enableBlend();
			
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
				RenderUtils.drawTexturedWedgeGradientTL(matrix, container, x, y + height, 0, height, width, TabRenderer.WEDGE_HEIGHT);
			}
			
			if(index == size - 1 && size > 1)
			{
				RenderUtils.drawTexturedWedgeGradientTR(matrix, container, x, y + height, x - container.getBackgroundX(), height, width, TabRenderer.WEDGE_HEIGHT);
			}
			
			RenderSystem.disableBlend();
		}
		
		this.drawTabTitle(matrix, container, title, x + width / 2, y + 9, 0xFFFFFF);
	}
	
	private void drawInactiveTab(MatrixStack matrix, Container container, int index, int size, int x, int y, int width, int height, ITextComponent title)
	{
		RenderUtils.colorDarkBackground();
		this.drawTabBackground(matrix, container, x, y, width, 20);
		
		if(!Config.getSkin().sharpEdges())
		{
			RenderSystem.enableBlend();
			
			if(index == 0)
			{
				RenderUtils.drawTexturedTriangleTL(matrix, container, x, y + height, 0, height, 2);
			}
			
			if(index == size - 1)
			{
				RenderUtils.drawTexturedTriangleTR(matrix, container, x + width - 3, y + height, container.getBackgroundWidth() - 3, height, 3);
			}
			
			RenderSystem.disableBlend();
		}
		
		this.drawTabTitle(matrix, container, title, x + width / 2, y + 7, 0xE0E0E0);
	}
	
	private void drawTabBackground(MatrixStack matrix, Container container, int x, int y, int width, int height)
	{
		container.bindBackground();
		container.setBlitOffset(-1);
		
		int left = MathHelper.ceil(width / 2D);
		int right = MathHelper.floor(width / 2D);
		
		RenderSystem.enableBlend();
		
		container.blit(matrix, x, y, 0, 0, left, height);
		container.blit(matrix, x + left, y, container.getBackgroundWidth() - right, 0, right, height);
		
		RenderSystem.disableBlend();
	}
	
	private void drawTabTitle(MatrixStack matrix, AbstractGui gui, ITextComponent title, int x, int y, int color)
	{
		gui.setBlitOffset(0);
		AbstractGui.drawCenteredString(matrix, Minecraft.getInstance().fontRenderer, title, x, y, color);
	}
	
	@Override
	public EnumLayer getLayer()
	{
		return EnumLayer.BACKGROUND;
	}
	
	private static int width(Container container, int index, int size)
	{
		int adjust = 0;
		
		if(index == 1 && size == 3)
		{
			adjust = 1;
		}
		
		return (int) Math.round((container.getBackgroundWidth() - Math.max(size - 1, 1) * TabRenderer.SPACING) / Math.max(size, 2)) + adjust;
	}
	
	private static int offset(Container container, int index, int size)
	{
		return (int) Math.round(index * (double) (container.getBackgroundWidth() + TabRenderer.SPACING) / size);
	}
}
