package exopandora.worldhandler.gui.widget.button;

import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;

public class GuiButtonTab extends GuiButtonBase
{
	public GuiButtonTab(int x, int y, int width, int height, Component narration, ActionHandler actionHandler)
	{
		super(x, y, width, height, narration, actionHandler);
	}
	
	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
	{
		
	}
	
	@Override
	public void playDownSound(SoundManager soundManager)
	{
		
	}
}
