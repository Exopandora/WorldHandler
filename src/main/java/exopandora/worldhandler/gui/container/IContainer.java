package exopandora.worldhandler.gui.container;

import exopandora.worldhandler.gui.content.element.Element;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IContainer
{
	void add(GuiButton button);
	void initButtons();
	void add(Element element);
	
	String getPlayer();
}
