package exopandora.worldhandler.gui.content.element;

import exopandora.worldhandler.gui.container.Container;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IElement
{
	void initGui(Container container);
	void initButtons(Container container);
	void draw();
	boolean actionPerformed(Container container, GuiButton button);
	
	default void keyTyped(Container container, char charTyped, int keyCode)
	{
		
	}
	
	default void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		
	}
}
