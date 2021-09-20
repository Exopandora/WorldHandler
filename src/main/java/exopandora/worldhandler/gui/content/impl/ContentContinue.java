package exopandora.worldhandler.gui.content.impl;

import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.ICommandBuilderSyntax;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiTextFieldTooltip;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;

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
		this.commandField.setFocus(false);
		
		if(this.builder instanceof ICommandBuilderSyntax)
		{
			this.commandField.setValue(((ICommandBuilderSyntax) this.builder).toActualCommand());
		}
		else
		{
			this.commandField.setValue(this.builder.toCommand());
		}
		
		this.commandField.moveCursorToStart();
		this.commandField.setFilter(text -> text.equals(this.commandField.getValue()));
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(new GuiButtonBase(x, y + 96, 114, 20, new TranslatableComponent("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, new TranslatableComponent("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(this.commandField);
		container.add(new GuiButtonBase(x + 116 / 2, y + 36, 116, 20, new TranslatableComponent("gui.worldhandler.generic.yes").withStyle(ChatFormatting.RED), () ->
		{
			CommandHelper.sendCommand(container.getPlayer(), this.builder, this.special);
			ActionHelper.open(this.getParentContent());
		}));
		container.add(new GuiButtonBase(x + 116 / 2, y + 60, 116, 20, new TranslatableComponent("gui.worldhandler.generic.no"), () -> ActionHelper.back(this)));
	}
	
	@Override
	public void drawScreen(PoseStack matrix, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		this.commandField.renderButton(matrix, mouseX, mouseY, partialTicks);
	}
}
