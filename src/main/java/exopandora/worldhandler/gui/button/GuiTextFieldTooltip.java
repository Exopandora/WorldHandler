package exopandora.worldhandler.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiTextFieldTooltip extends TextFieldWidget
{
	private String tooltip;
	
	public GuiTextFieldTooltip(int x, int y, int width, int height)
	{
		this(x, y, width, height, null);
	}
	
	public GuiTextFieldTooltip(int x, int y, int width, int height, String tooltip)
	{
		super(Minecraft.getInstance().fontRenderer, x, y, width, height, null);
		this.setMaxStringLength(Integer.MAX_VALUE);
		this.tooltip = tooltip;
	}
	
	@Override
	public void renderButton(int x, int y, float partialTicks)
	{
		super.renderButton(x, y, partialTicks);
		
		if(this.getVisible() && !this.isFocused() && this.tooltip != null && TextFormatting.getTextWithoutFormattingCodes(this.getText()).isEmpty())
		{
			boolean enableBackgroundDrawing = this.getAdjustedWidth() != this.width;
			int tx = enableBackgroundDrawing ? this.x + 4 : this.x;
			int ty = enableBackgroundDrawing ? this.y + (this.height - 8) / 2 : this.y;
			
			Minecraft.getInstance().fontRenderer.drawStringWithShadow(this.tooltip, (float) tx, (float) ty, 0x7F7F7F);
		}
	}
	
	public void setTooltip(String tooltip)
	{
		this.tooltip = tooltip;
	}
	
	public String getTooltip()
	{
		return this.tooltip;
	}
	
	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void setWidth(int width)
	{
		this.width = width;
	}
	
	public void setHeight(int height)
	{
		this.height = height;
	}
}
