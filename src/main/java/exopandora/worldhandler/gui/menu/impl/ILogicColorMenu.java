package exopandora.worldhandler.gui.menu.impl;

import exopandora.worldhandler.util.ILogic;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface ILogicColorMenu extends ILogic
{
	default boolean validate(String text)
	{
		return text != null;
	}
	
	default boolean doDrawButtons()
	{
		return true;
	}
	
	default boolean doDrawTextField()
	{
		return true;
	}
	
	@Override
	default String getId()
	{
		return "color";
	}
}
