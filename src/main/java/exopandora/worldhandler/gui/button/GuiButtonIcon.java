package exopandora.worldhandler.gui.button;

import exopandora.worldhandler.helper.ResourceHelper;
import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiButtonIcon extends GuiButtonTooltip
{
	private EnumIcon icon;
	
	public GuiButtonIcon(int x, int y, int widthIn, int heightIn, EnumIcon icon, String tooltip, ActionHandler actionHandler)
	{
		this(0, x, y, widthIn, heightIn, icon, tooltip, actionHandler);
	}
	
	public GuiButtonIcon(int id, int x, int y, int widthIn, int heightIn, EnumIcon icon, String tooltip, ActionHandler actionHandler)
	{
		super(id, x, y, widthIn, heightIn, null, tooltip, actionHandler);
		this.icon = icon;
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks)
	{
		super.render(mouseX, mouseY, partialTicks);
		
		if(this.icon != null)
		{
			this.renderIcon();
		}
	}
	
	private void renderIcon()
	{
		Minecraft.getInstance().getTextureManager().bindTexture(ResourceHelper.getIconTexture());
		
		if(this.enabled)
		{
			if(this.hovered)
			{
				GlStateManager.color4f(1.0F, 1.0F, 0.6F, 1.0F);
			}
			else
			{
				GlStateManager.color4f(0.95F, 0.95F, 0.95F, 1.0F);
			}
		}
		else
		{
			GlStateManager.color4f(0.8F, 0.8F, 0.8F, 1.0F);
		}
		
		this.drawTexturedModalRect(this.x + this.width / 2 - 4, this.y + 6, this.icon.getX() * 8, this.icon.getY() * 8, 8, 8);
	}
}
