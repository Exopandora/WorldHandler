package exopandora.worldhandler.gui.button;

import com.mojang.blaze3d.matrix.MatrixStack;

import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiButtonTooltip extends GuiButtonBase
{
	protected ITextComponent tooltip;
	
	public GuiButtonTooltip(int x, int y, int widthIn, int heightIn, ITextComponent buttonText, ITextComponent tooltip, ActionHandler actionHandler)
	{
		super(x, y, widthIn, heightIn, buttonText, actionHandler);
		this.tooltip = tooltip;
	}
	
	public void renderTooltip(Screen screen, MatrixStack matrix, int mouseX, int mouseY)
	{
		if(this.isHovered() && this.tooltip != null && !this.tooltip.getString().isEmpty())
		{
			screen.renderTooltip(matrix, this.tooltip, mouseX, mouseY);
		}
	}
}
