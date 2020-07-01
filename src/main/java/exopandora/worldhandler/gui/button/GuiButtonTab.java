package exopandora.worldhandler.gui.button;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class GuiButtonTab extends AbstractButton
{
	public GuiButtonTab(int x, int y, int widthIn, int heightIn, ITextComponent narration)
	{
		super(x, y, widthIn, heightIn, narration);
	}
	
	@Override
	public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) //render
	{
		
	}
	
	@Override
	public void func_230988_a_(SoundHandler soundHandler) //playDownSound
	{
		
	}
}
