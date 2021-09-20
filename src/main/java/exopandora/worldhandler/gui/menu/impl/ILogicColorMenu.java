package exopandora.worldhandler.gui.menu.impl;

import exopandora.worldhandler.util.ILogic;

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
