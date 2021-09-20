package exopandora.worldhandler.gui.widget;

import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.widget.button.GuiTextFieldTooltip;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.resources.language.I18n;

public class WidgetNameField implements IContainerWidget
{
	private final GuiTextFieldTooltip nameField = Util.make(new GuiTextFieldTooltip(0, 0, 0, 11), textfield -> textfield.setMaxLength(16));
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.nameField.setValue(container.getPlayer());
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
	public void drawScreen(PoseStack matrix, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		String username = container.getPlayer().isEmpty() && !this.nameField.isFocused() ? I18n.get("gui.worldhandler.generic.edit_username") : container.getPlayer();
		
		int xPos = container.getBackgroundX() + container.getBackgroundWidth() - this.watchOffset() - 7 - Minecraft.getInstance().font.width(username);
		int yPos = container.getBackgroundY() + 7;
		
		Minecraft.getInstance().font.draw(matrix, username, xPos, yPos, Config.getSkin().getLabelColor());
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int keyCode)
	{
		if(this.nameField.isFocused())
		{
			this.nameField.moveCursorToEnd();
		}
		
		return false;
	}
	
	@Override
	public boolean charTyped(char charTyped, int keyCode)
	{
		if(this.nameField.isFocused())
		{
			this.nameField.moveCursorToEnd();
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
		final Font font = Minecraft.getInstance().font;
		
		int x = container.getBackgroundX() + container.getBackgroundWidth() - this.watchOffset() - 7;
		int y = container.getBackgroundY() + 6;
		
		if(container.getPlayer().isEmpty())
		{
			int width = font.width(I18n.get("gui.worldhandler.generic.edit_username")) + 2;
			this.nameField.setWidth(width);
			this.nameField.setPosition(x - (font.width(container.getContent().getTitle()) + 2), y);
		}
		else
		{
			int width = font.width(container.getPlayer()) + 2;
			this.nameField.setWidth(width);
			this.nameField.setPosition(x - width, y);
		}
		
		container.getContent().onPlayerNameChanged(container.getPlayer());
	}
}
