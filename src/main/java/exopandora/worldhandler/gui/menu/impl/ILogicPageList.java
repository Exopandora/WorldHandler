package exopandora.worldhandler.gui.menu.impl;

import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface ILogicPageList<T> extends ILogicMapped<T>
{
	GuiButtonBase onRegister(int x, int y, int width, int height, MutableComponent text, T item, ActionHandler actionHandler);
	
	default boolean doDisable()
	{
		return true;
	}
}
