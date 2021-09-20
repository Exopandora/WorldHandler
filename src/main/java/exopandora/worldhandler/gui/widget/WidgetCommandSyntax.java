package exopandora.worldhandler.gui.widget;

import com.google.common.base.Predicates;
import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.builder.impl.BuilderWorldHandler;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.widget.button.GuiTextFieldTooltip;

public class WidgetCommandSyntax implements IContainerWidget
{
	private static final BuilderWorldHandler BUILDER_WORLD_HANDLER = new BuilderWorldHandler();
	
	private GuiTextFieldTooltip syntaxField;
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.syntaxField = new GuiTextFieldTooltip(container.width / 2 - 156, container.height - 22, 312, 20);
		this.updateSyntax(container);
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(this.syntaxField);
	}
	
	@Override
	public void tick(Container container)
	{
		this.updateSyntax(container);
		
		if(this.syntaxField != null)
		{
			this.syntaxField.tick();
		}
	}
	
	@Override
	public void drawScreen(PoseStack matrix, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(this.syntaxField != null)
		{
			this.syntaxField.renderButton(matrix, mouseX, mouseY, partialTicks);
		}
	}
	
	private void updateSyntax(Container container)
	{
		if(this.syntaxField != null && !this.syntaxField.isFocused())
		{
			this.syntaxField.setFilter(Predicates.alwaysTrue());
			
			if(container.getContent().getCommandBuilder() != null)
			{
				this.syntaxField.setValue(container.getContent().getCommandBuilder().toCommand());
			}
			else
			{
				this.syntaxField.setValue(BUILDER_WORLD_HANDLER.toCommand());
			}
			
			this.syntaxField.setFilter(string -> string.equals(this.syntaxField.getValue()));
			this.syntaxField.moveCursorToStart();
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
}
