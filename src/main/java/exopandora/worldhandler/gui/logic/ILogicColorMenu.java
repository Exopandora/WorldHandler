package exopandora.worldhandler.gui.logic;

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
	
	@Override
	default String getId()
	{
		return "color";
	}
}
