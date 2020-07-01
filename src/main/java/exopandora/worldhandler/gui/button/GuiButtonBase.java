package exopandora.worldhandler.gui.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.RenderUtils;
import exopandora.worldhandler.util.ResourceHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiButtonBase extends Button
{
	public GuiButtonBase(int x, int y, int widthIn, int heightIn, ITextComponent buttonText, ActionHandler actionHandler)
	{
		super(x, y, widthIn, heightIn, buttonText, button -> ActionHelper.tryRun(actionHandler));
	}
	
	@Override
	public void func_230431_b_(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) //renderButton
	{
		this.func_230441_a_(matrix, Minecraft.getInstance(), mouseX, mouseY); //renderBg
		this.func_238472_a_(matrix, Minecraft.getInstance().fontRenderer, this.func_230458_i_(), this.field_230690_l_ + this.field_230688_j_ / 2, this.field_230691_m_ + (this.field_230689_k_ - 8) / 2, this.getFGColor()); //drawCenteredString
	}
	
	@Override
	protected void func_230441_a_(MatrixStack matrix, Minecraft minecraft, int mouseX, int mouseY) //renderBg
	{
		RenderSystem.enableBlend();
		RenderUtils.color(Config.getSkin().getButtonRedF(), Config.getSkin().getButtonGreenF(), Config.getSkin().getButtonBlueF(), Config.getSkin().getButtonAlphaF());
		
		int hovered = this.func_230989_a_(this.func_230449_g_());
    	Minecraft.getInstance().getTextureManager().bindTexture(ResourceHelper.getButtonTexture());
    	
		int hWidth = this.field_230688_j_ / 2;
		int hHeight = this.field_230689_k_ / 2;
		
		if(Config.getSkin().getTextureType().equals("resourcepack"))
		{
			int textureOffset = 46 + hovered * 20;
			
			this.func_238474_b_(matrix, this.field_230690_l_, this.field_230691_m_, 0, textureOffset, hWidth, hHeight); //blit
			this.func_238474_b_(matrix, this.field_230690_l_, this.field_230691_m_ + hHeight, 0, textureOffset + 20 - hHeight, hWidth, hHeight); //blit
			this.func_238474_b_(matrix, this.field_230690_l_ + hWidth, this.field_230691_m_, 200 - hWidth, textureOffset, hWidth, hHeight); //blit
			this.func_238474_b_(matrix, this.field_230690_l_ + hWidth, this.field_230691_m_ + hHeight, 200 - hWidth, textureOffset + 20 - hHeight, hWidth, hHeight); //blit
		}
		else
		{
			int textureOffset = hovered * 20;
			
			this.func_238474_b_(matrix, this.field_230690_l_, this.field_230691_m_, 0, textureOffset, hWidth, hHeight); //blit
			this.func_238474_b_(matrix, this.field_230690_l_, this.field_230691_m_ + hHeight, 0, textureOffset + 20 - hHeight, hWidth, hHeight); //blit
			this.func_238474_b_(matrix, this.field_230690_l_ + hWidth, this.field_230691_m_, 200 - hWidth, textureOffset, this.field_230688_j_ / 2, hHeight); //blit
			this.func_238474_b_(matrix, this.field_230690_l_ + hWidth, this.field_230691_m_ + hHeight, 200 - hWidth, textureOffset + 20 - hHeight, hWidth, hHeight); //blit
		}
		
		RenderSystem.disableBlend();
		RenderUtils.color(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
