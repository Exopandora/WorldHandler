package exopandora.worldhandler.gui.content.impl;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiTextFieldTooltip;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class ContentContinue extends ContentChild
{
	private ICommandBuilder builder;
	private GuiTextFieldTooltip commandField;
	private Object label;
	private boolean special;
	
	public ContentContinue withBuilder(ICommandBuilder builder, Object label)
	{
		return this.withBuilder(builder, label, false);
	}
	
	public ContentContinue withBuilder(ICommandBuilder builder, Object label, boolean special)
	{
		this.builder = builder;
		this.label = label;
		this.special = special;
		return this;
	}
	
	@Override
	public CommandPreview getCommandPreview()
	{
		return new CommandPreview(this.builder, this.label);
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.commandField = new GuiTextFieldTooltip(x + 116 / 2, y + 12, 116, 20);
		this.commandField.setFocused(false);
		this.commandField.setValue(this.builder.toCommand(this.label, false));
		this.commandField.moveCursorToStart();
		this.commandField.setFilter(text -> text.equals(this.commandField.getValue()));
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.addRenderableWidget(new GuiButtonBase(x, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.addRenderableWidget(new GuiButtonBase(x + 118, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.addRenderableWidget(this.commandField);
		container.addRenderableWidget(new GuiButtonBase(x + 116 / 2, y + 36, 116, 20, Component.translatable("gui.worldhandler.generic.yes").withStyle(ChatFormatting.RED), () ->
		{
			CommandHelper.sendCommand(container.getPlayer(), this.builder, this.label, this.special);
			ActionHelper.open(this.getParentContent());
		}));
		container.addRenderableWidget(new GuiButtonBase(x + 116 / 2, y + 60, 116, 20, Component.translatable("gui.worldhandler.generic.no"), () -> ActionHelper.back(this)));
	}
}
