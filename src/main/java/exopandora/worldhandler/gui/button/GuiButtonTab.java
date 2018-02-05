package exopandora.worldhandler.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiButtonTab extends GuiButton
{
	private final int index;
	
	public GuiButtonTab(int buttonId, int x, int y, int widthIn, int heightIn, int index)
	{
		super(buttonId, x, y, widthIn, heightIn, null);
		this.index = index;
	}
	
    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY, float partialTicks)
    {
    	
    }
    
    @Override
    public void playPressSound(SoundHandler soundHandlerIn)
    {
    	
    }
    
    public int getIndex()
    {
    	return this.index;
    }
}
