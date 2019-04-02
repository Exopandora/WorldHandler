package exopandora.worldhandler.gui.logic;

import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.util.ActionHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface ILogicPageList<T> extends ILogicMapped<T>
{
	GuiButtonBase onRegister(int x, int y, int width, int height, String text, T item, ActionHandler actionHandler);
	
	default boolean doDisable()
	{
		return true;
	}
}
