package exopandora.worldhandler.gui.menu;

import exopandora.worldhandler.gui.container.Container;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IMenu
{
	void initGui(Container container);
	void initButtons(Container container);
	void tick();
	void draw(int mouseX, int mouseY, float partialTicks);
}
