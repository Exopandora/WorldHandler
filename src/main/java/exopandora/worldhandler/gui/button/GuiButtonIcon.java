package exopandora.worldhandler.gui.button;

import com.mojang.blaze3d.matrix.MatrixStack;

import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.RenderUtils;
import exopandora.worldhandler.util.ResourceHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiButtonIcon extends GuiButtonTooltip
{
	private final EnumIcon icon;
	
	public GuiButtonIcon(int x, int y, int widthIn, int heightIn, EnumIcon icon, ITextComponent tooltip, ActionHandler actionHandler)
	{
		super(x, y, widthIn, heightIn, tooltip, tooltip, actionHandler);
		this.icon = icon;
	}
	
	@Override
	public void func_230431_b_(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) //renderButton
	{
		super.func_230441_a_(matrix, Minecraft.getInstance(), mouseX, mouseY); //renderBg
		
		if(this.icon != null)
		{
			this.renderIcon(matrix);
		}
	}
	
	private void renderIcon(MatrixStack matrix)
	{
		Minecraft.getInstance().getTextureManager().bindTexture(ResourceHelper.getIconTexture());
		
		if(this.field_230693_o_)
		{
			RenderUtils.color(1.0F, 1.0F, 1.0F, 1.0F);
		}
		else
		{
			RenderUtils.color(0.8F, 0.8F, 0.8F, 1.0F);
		}
		
		this.func_238474_b_(matrix, this.field_230690_l_ + this.field_230688_j_ / 2 - 4, this.field_230691_m_ + this.field_230689_k_ / 2 - 4, this.icon.getX() * 8, this.icon.getY() * 8, 8, 8); //blit
	}
}
