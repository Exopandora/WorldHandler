package exopandora.worldhandler.gui.button.logic;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface ISliderResponder
{
	void setValue(Object id, int value);
    String getText(Object id, String format, int value);
}