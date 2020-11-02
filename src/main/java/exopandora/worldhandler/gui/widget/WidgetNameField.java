package exopandora.worldhandler.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.widget.button.GuiTextFieldTooltip;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WidgetNameField implements IContainerWidget
{
	private final GuiTextFieldTooltip nameField = Util.make(new GuiTextFieldTooltip(0, 0, 0, 11), textfield -> textfield.setMaxStringLength(16));
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.nameField.setText(container.getPlayer());
		this.nameField.setResponder(text -> 
		{
			container.setPlayer(text);
			this.updateNameField(container);
		});
		this.updateNameField(container);
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(this.nameField);
	}
	
	@Override
	public void drawScreen(MatrixStack matrix, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		String username = container.getPlayer().isEmpty() && !this.nameField.isFocused() ? I18n.format("gui.worldhandler.generic.edit_username") : container.getPlayer();
		
		int xPos = container.getBackgroundX() + container.getBackgroundWidth() - this.watchOffset() - 7 - Minecraft.getInstance().fontRenderer.getStringWidth(username);
		int yPos = container.getBackgroundY() + 7;
		
		Minecraft.getInstance().fontRenderer.drawString(matrix, username, xPos, yPos, Config.getSkin().getLabelColor());
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int keyCode)
	{
		if(this.nameField.isFocused())
		{
			this.nameField.setCursorPositionEnd();
		}
		
		return false;
	}
	
	@Override
	public boolean charTyped(char charTyped, int keyCode)
	{
		if(this.nameField.isFocused())
		{
			this.nameField.setCursorPositionEnd();
		}
		
		return false;
	}
	
	private int watchOffset()
	{
		return Config.getSettings().watch() ? 9 : 0;
	}
	
	@Override
	public EnumLayer getLayer()
	{
		return EnumLayer.BACKGROUND;
	}
	
	private void updateNameField(Container container)
	{
		final FontRenderer font = Minecraft.getInstance().fontRenderer;
		
		int x = container.getBackgroundX() + container.getBackgroundWidth() - this.watchOffset() - 7;
		int y = container.getBackgroundY() + 6;
		
		if(container.getPlayer().isEmpty())
		{
			int width = font.getStringWidth(I18n.format("gui.worldhandler.generic.edit_username")) + 2;
			this.nameField.setWidth(width);
			this.nameField.setPosition(x - (font.getStringPropertyWidth(container.getContent().getTitle()) + 2), y);
		}
		else
		{
			int width = font.getStringWidth(container.getPlayer()) + 2;
			this.nameField.setWidth(width);
			this.nameField.setPosition(x - width, y);
		}
		
		container.getContent().onPlayerNameChanged(container.getPlayer());
	}
}
