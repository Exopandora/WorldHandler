package exopandora.worldhandler.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiTextFieldTooltip extends TextFieldWidget
{
	private ITextComponent tooltip;
	
	public GuiTextFieldTooltip(int x, int y, int width, int height)
	{
		this(x, y, width, height, null);
	}
	
	public GuiTextFieldTooltip(int x, int y, int width, int height, ITextComponent tooltip)
	{
		super(Minecraft.getInstance().fontRenderer, x, y, width, height, null);
		this.setMaxStringLength(Integer.MAX_VALUE);
		this.tooltip = tooltip;
	}
	
	@Override
	public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partialTicks)
	{
		super.renderButton(matrix, mouseX, mouseY, partialTicks);
		
		if(this.getVisible() && !this.isFocused() && this.tooltip != null && TextFormatting.getTextWithoutFormattingCodes(this.getText()).isEmpty())
		{
			int x = this.x;
			int y = this.y;
			
			if(this.getAdjustedWidth() != this.width)
			{
				x += 4;
				y += (this.height - 8) / 2;
			}
			
			Minecraft.getInstance().fontRenderer.func_243246_a(matrix, this.tooltip, (float) x, (float) y, 0x7F7F7F); //drawStringWithShadow
		}
	}
	
	public void setTooltip(ITextComponent tooltip)
	{
		this.tooltip = tooltip;
	}
	
	public ITextComponent getTooltip()
	{
		return this.tooltip;
	}
	
	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void setText(ITextComponent text)
	{
		if(text != null)
		{
			this.setText(text.getString());
		}
		else
		{
			this.setText((String) null);
		}
	}
}
