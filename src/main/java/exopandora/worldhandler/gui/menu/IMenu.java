package exopandora.worldhandler.gui.menu;

import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.gui.container.Container;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IMenu
{
	void initGui(Container container);
	void initButtons(Container container);
	void tick();
	void draw(PoseStack matrix, int mouseX, int mouseY, float partialTicks);
}
