package exopandora.worldhandler.gui.widget;

import com.google.common.base.Predicates;

import exopandora.worldhandler.builder.impl.WorldHandlerCommandBuilder;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.widget.button.GuiHintTextField;

public class WidgetCommandSyntax implements IContainerWidget
{
	private static final WorldHandlerCommandBuilder BUILDER_WORLD_HANDLER = new WorldHandlerCommandBuilder();
	
	private GuiHintTextField syntaxField;
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.syntaxField = new GuiHintTextField(container.width / 2 - 156, container.height - 22, 312, 20);
		this.updateSyntax(container);
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.addRenderableWidget(this.syntaxField);
	}
	
	@Override
	public void tick(Container container)
	{
		this.updateSyntax(container);
	}
	
	private void updateSyntax(Container container)
	{
		if(this.syntaxField != null && !this.syntaxField.isFocused())
		{
			this.syntaxField.setFilter(Predicates.alwaysTrue());
			
			if(container.getContent().getCommandPreview() != null)
			{
				this.syntaxField.setValue(container.getContent().getCommandPreview().toString());
			}
			else
			{
				this.syntaxField.setValue(BUILDER_WORLD_HANDLER.toCommand(null, true));
			}
			
			this.syntaxField.setFilter(string -> string.equals(this.syntaxField.getValue()));
			this.syntaxField.moveCursorToStart(false);
		}
	}
	
	@Override
	public boolean isEnabled()
	{
		return Config.getSettings().commandSyntax();
	}
	
	@Override
	public EnumLayer getLayer()
	{
		return EnumLayer.FOREGROUND;
	}
	
	@Override
	public void setFocused(boolean focused)
	{
		this.syntaxField.setFocused(focused);
	}
	
	@Override
	public boolean isFocused()
	{
		return this.syntaxField.isFocused();
	}
}
