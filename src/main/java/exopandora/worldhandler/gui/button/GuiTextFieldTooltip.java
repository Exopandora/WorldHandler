package exopandora.worldhandler.gui.button;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiTextFieldTooltip extends GuiTextField
{
	private String tooltip;
	
	public GuiTextFieldTooltip(int x, int y, int width, int height)
	{
		this(0, x, y, width, height, null);
	}
	
	public GuiTextFieldTooltip(int id, int x, int y, int width, int height)
	{
		this(id, x, y, width, height, null);
	}
	
	public GuiTextFieldTooltip(int x, int y, int width, int height, String tooltip)
	{
		this(0, x, y, width, height, tooltip);
	}
	
	public GuiTextFieldTooltip(int id, int x, int y, int width, int height, String tooltip)
	{
		super(id, Minecraft.getInstance().fontRenderer, x, y, width, height);
		this.setMaxStringLength(Integer.MAX_VALUE);
		this.tooltip = tooltip;
	}
	
	@Override
	public void drawTextField(int x, int y, float partialTicks)
	{
		super.drawTextField(x, y, partialTicks);
		
		if(this.getVisible() && !this.isFocused() && this.tooltip != null && ChatFormatting.stripFormatting(this.getText()).isEmpty())
		{
            int tx = this.getEnableBackgroundDrawing() ? this.x + 4 : this.x;
            int ty = this.getEnableBackgroundDrawing() ? this.y + (this.height - 8) / 2 : this.y;
			
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
