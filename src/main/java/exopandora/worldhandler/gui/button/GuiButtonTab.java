package exopandora.worldhandler.gui.button;

import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiButtonTab extends GuiButton
{
	public GuiButtonTab(int x, int y, int widthIn, int heightIn)
	{
		super(0, x, y, widthIn, heightIn, null);
	}
	
    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
    {
    	
    }
    
    @Override
    public void playPressSound(SoundHandler soundHandlerIn)
    {
    	
    }
}
