package exopandora.worldhandler.gui.widget.button;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

public class GuiTextFieldTooltip extends EditBox
{
	private Component tooltip;
	
	public GuiTextFieldTooltip(int x, int y, int width, int height)
	{
		this(x, y, width, height, null);
	}
	
	public GuiTextFieldTooltip(int x, int y, int width, int height, Component tooltip)
	{
		super(Minecraft.getInstance().font, x, y, width, height, null);
		this.setMaxLength(Integer.MAX_VALUE);
		this.tooltip = tooltip;
	}
	
	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
	{
		super.render(poseStack, mouseX, mouseY, partialTicks);
		
		if(this.isVisible() && !this.isFocused() && this.tooltip != null && ChatFormatting.stripFormatting(this.getValue()).isEmpty())
		{
			int x = this.getX();
			int y = this.getY();
			
			if(this.getInnerWidth() != this.width)
			{
				x += 4;
				y += (this.height - 8) / 2;
			}
			
			Minecraft.getInstance().font.drawShadow(poseStack, this.tooltip, (float) x, (float) y, 0x7F7F7F);
		}
	}
	
	public void setTooltip(Component tooltip)
	{
		this.tooltip = tooltip;
	}
	
	public Component getTooltip()
	{
		return this.tooltip;
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
