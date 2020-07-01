package exopandora.worldhandler.gui.button;

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
	public void func_230431_b_(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) //renderButton
	{
		super.func_230431_b_(matrix, mouseX, mouseY, partialTicks); // renderButton
		
		if(this.getVisible() && !this.func_230999_j_() && this.tooltip != null && TextFormatting.getTextWithoutFormattingCodes(this.getText()).isEmpty())
		{
			boolean enableBackgroundDrawing = this.getAdjustedWidth() != this.field_230688_j_;
			int tx = enableBackgroundDrawing ? this.field_230690_l_ + 4 : this.field_230690_l_;
			int ty = enableBackgroundDrawing ? this.field_230691_m_ + (this.field_230689_k_ - 8) / 2 : this.field_230691_m_;
			
			Minecraft.getInstance().fontRenderer.func_238407_a_(matrix, this.tooltip, (float) tx, (float) ty, 0x7F7F7F); //drawStringWithShadow
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
		this.field_230690_l_ = x;
		this.field_230691_m_ = y;
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
