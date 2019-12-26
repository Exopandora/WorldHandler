package exopandora.worldhandler.gui.button;

import com.mojang.blaze3d.systems.RenderSystem;

import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.ResourceHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiButtonIcon extends GuiButtonTooltip
{
	private final EnumIcon icon;
	
	public GuiButtonIcon(int x, int y, int widthIn, int heightIn, EnumIcon icon, String tooltip, ActionHandler actionHandler)
	{
		super(x, y, widthIn, heightIn, tooltip, tooltip, actionHandler);
		this.icon = icon;
	}
	
	@Override
	public void renderButton(int mouseX, int mouseY, float partialTicks)
	{
		super.renderBg(Minecraft.getInstance(), mouseX, mouseY);
		
		if(this.icon != null)
		{
			this.renderIcon();
		}
	}
	
	private void renderIcon()
	{
		Minecraft.getInstance().getTextureManager().bindTexture(ResourceHelper.getIconTexture());
		
		if(this.active)
		{
			if(this.isHovered())
			{
				RenderSystem.color4f(1.0F, 1.0F, 0.6F, 1.0F);
			}
			else
			{
				RenderSystem.color4f(0.95F, 0.95F, 0.95F, 1.0F);
			}
		}
		else
		{
			RenderSystem.color4f(0.8F, 0.8F, 0.8F, 1.0F);
		}
		
		this.blit(this.x + this.width / 2 - 4, this.y + 6, this.icon.getX() * 8, this.icon.getY() * 8, 8, 8);
	}
}
