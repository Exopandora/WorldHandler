package exopandora.worldhandler.gui.button;

import java.util.Arrays;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.gui.GuiUtils;

@OnlyIn(Dist.CLIENT)
public class GuiButtonTooltip extends GuiButtonBase
{
	protected ITextComponent tooltip;
	
	public GuiButtonTooltip(int x, int y, int widthIn, int heightIn, ITextComponent buttonText, ITextComponent tooltip, ActionHandler actionHandler)
	{
		super(x, y, widthIn, heightIn, buttonText, actionHandler);
		this.tooltip = tooltip;
	}
	
	public void renderTooltip(MatrixStack matrix, int mouseX, int mouseY)
	{
		if(this.func_230449_g_() && this.tooltip != null && !this.tooltip.getString().isEmpty())
		{
			List<ITextComponent> list = Arrays.asList(this.tooltip);
			
			if(!list.isEmpty())
			{
				int tooltipWidth = Minecraft.getInstance().fontRenderer.func_238414_a_(this.tooltip) + 9;
				int width = Minecraft.getInstance().currentScreen.field_230708_k_;
				int height = Minecraft.getInstance().currentScreen.field_230709_l_;
				
				GuiUtils.drawHoveringText(matrix, list, mouseX, mouseY, width, height, tooltipWidth, Minecraft.getInstance().fontRenderer);
			}
		}
	}
}
