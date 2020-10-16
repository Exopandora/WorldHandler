package exopandora.worldhandler.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;

import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiButtonTab extends GuiButtonBase
{
	public GuiButtonTab(int x, int y, int widthIn, int heightIn, ITextComponent narration, ActionHandler actionHandler)
	{
		super(x, y, widthIn, heightIn, narration, actionHandler);
	}
	
	@Override
	public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks)
	{
		
	}
	
	@Override
	public void playDownSound(SoundHandler soundHandler)
	{
		
	}
}
