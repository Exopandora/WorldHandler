package exopandora.worldhandler.gui.widget.menu;

import exopandora.worldhandler.gui.container.Container;
import net.minecraft.client.gui.GuiGraphics;

public interface IMenu
{
	void initGui(Container container);
	void initButtons(Container container);
	void tick();
	void draw(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks);
}
