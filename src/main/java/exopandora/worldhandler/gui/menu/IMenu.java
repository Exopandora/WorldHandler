package exopandora.worldhandler.gui.menu;

import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.gui.container.Container;

public interface IMenu
{
	void initGui(Container container);
	void initButtons(Container container);
	void tick();
	void draw(PoseStack matrix, int mouseX, int mouseY, float partialTicks);
}
