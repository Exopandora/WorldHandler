package exopandora.worldhandler.gui.widget.button;

import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;

public class GuiButtonTab extends GuiButtonBase
{
	public GuiButtonTab(int x, int y, int widthIn, int heightIn, Component narration, ActionHandler actionHandler)
	{
		super(x, y, widthIn, heightIn, narration, actionHandler);
	}
	
	@Override
	public void render(PoseStack matrix, int mouseX, int mouseY, float partialTicks)
	{
		
	}
	
	@Override
	public void playDownSound(SoundManager soundManager)
	{
		
	}
}
