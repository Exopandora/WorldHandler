package exopandora.worldhandler.gui.widget.button;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

public class GuiHintTextField extends EditBox
{
	private Component hint;
	
	public GuiHintTextField(int x, int y, int width, int height)
	{
		this(x, y, width, height, null);
	}
	
	public GuiHintTextField(int x, int y, int width, int height, Component hint)
	{
		super(Minecraft.getInstance().font, x, y, width, height, null);
		this.setMaxLength(Integer.MAX_VALUE);
		this.hint = hint;
	}
	
	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks)
	{
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		
		if(this.isVisible() && !this.isFocused() && this.hint != null && ChatFormatting.stripFormatting(this.getValue()).isEmpty())
		{
			int x = this.getX();
			int y = this.getY();
			
			if(this.getInnerWidth() != this.width)
			{
				x += 4;
				y += (this.height - 8) / 2;
			}
			
			guiGraphics.drawString(Minecraft.getInstance().font, this.hint, x, y, 0x7F7F7F);
		}
	}
	
	public void setHint(Component hint)
	{
		this.hint = hint;
	}
	
	public Component getHint()
	{
		return this.hint;
	}
	
	public void setText(Component text)
	{
		if(text != null)
		{
			this.setValue(text.getString());
		}
		else
		{
			this.setValue((String) null);
		}
	}
}
