package exopandora.worldhandler.gui.content.impl;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.ICommandBuilderSyntax;
import exopandora.worldhandler.gui.button.GuiButtonWorldHandler;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.container.impl.GuiWorldHandlerContainer;
import exopandora.worldhandler.gui.content.impl.abstr.ContentChild;
import exopandora.worldhandler.main.WorldHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ContentContinue extends ContentChild
{
	private ICommandBuilder builder;
	private GuiTextFieldTooltip commandField;
	private boolean special;
	
	public ContentContinue withBuilder(ICommandBuilder builder)
	{
		return this.withBuilder(builder, false);
	}
	
	public ContentContinue withBuilder(ICommandBuilder builder, boolean special)
	{
		this.builder = builder;
		this.special = special;
		return this;
	}
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		return this.builder;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.commandField = new GuiTextFieldTooltip(x + 116 / 2, y + 12, 116, 20);
		this.commandField.setFocused(false);
		
		if(this.builder instanceof ICommandBuilderSyntax)
		{
			this.commandField.setText(((ICommandBuilderSyntax) this.builder).toActualCommand());
		}
		else
		{
			this.commandField.setText(this.builder.toCommand());
		}
		
		this.commandField.setCursorPositionZero();
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(new GuiButtonWorldHandler(0, x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back")));
		container.add(new GuiButtonWorldHandler(1, x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame")));
		
		container.add(new GuiButtonWorldHandler(2, x + 116 / 2, y + 36, 116, 20, ChatFormatting.RED + I18n.format("gui.worldhandler.generic.yes")));
		container.add(new GuiButtonWorldHandler(0, x + 116 / 2, y + 60, 116, 20, I18n.format("gui.worldhandler.generic.no")));
	}
	
	@Override
	public void actionPerformed(Container container, GuiButton button)
	{
		switch(button.id)
		{
			case 2:
				WorldHandler.sendCommand(this.builder, this.special);
				Minecraft.getMinecraft().displayGuiScreen(new GuiWorldHandlerContainer(this.parent));
				break;
			default:
				break;
		}
	}
	
	@Override
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		this.commandField.drawTextBox();
	}
	
	@Override
	public String[] getHeadline()
	{
		return new String[]{I18n.format("gui.worldhandler.continue.question")};
	}
	
	@Override
	public void keyTyped(Container container, char typedChar, int keyCode)
	{
		if(keyCode == Keyboard.KEY_RIGHT || keyCode == Keyboard.KEY_LEFT)
		{
			this.commandField.textboxKeyTyped(typedChar, keyCode);
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		this.commandField.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public String getTitle()
	{
		return this.parent.getTitle();
	}
}
