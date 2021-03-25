package exopandora.worldhandler.gui.widget.button;

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
	public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partialTicks)
	{
		super.renderBg(matrix, Minecraft.getInstance(), mouseX, mouseY);
		
		if(this.icon != null)
		{
			this.renderIcon(matrix);
		}
	}
	
	private void renderIcon(MatrixStack matrix)
	{
		Minecraft.getInstance().getTextureManager().bind(ResourceHelper.iconTexture());
		
		if(this.active)
		{
			RenderUtils.color(1.0F, 1.0F, 1.0F, 1.0F);
		}
		else
		{
			RenderUtils.color(0.8F, 0.8F, 0.8F, 1.0F);
		}
		
		this.blit(matrix, this.x + this.width / 2 - 4, this.y + this.height / 2 - 4, this.icon.getX() * 8, this.icon.getY() * 8, 8, 8);
	}
}
