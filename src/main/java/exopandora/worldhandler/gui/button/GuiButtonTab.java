package exopandora.worldhandler.gui.button;

import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class GuiButtonTab extends AbstractButton
{
	public GuiButtonTab(int x, int y, int widthIn, int heightIn, String narration)
	{
		super(x, y, widthIn, heightIn, narration);
	}
	
    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
    {
    	
    }
    
    @Override
    public void playDownSound(SoundHandler soundHandlerIn)
    {
    	
    }
}
