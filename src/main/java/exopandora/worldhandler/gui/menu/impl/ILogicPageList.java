package exopandora.worldhandler.gui.menu.impl;

import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface ILogicPageList<T> extends ILogicMapped<T>
{
	GuiButtonBase onRegister(int x, int y, int width, int height, IFormattableTextComponent text, T item, ActionHandler actionHandler);
	
	default boolean doDisable()
	{
		return true;
	}
}
