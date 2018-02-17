package exopandora.worldhandler.gui.content.element.logic;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public interface ILogicColorMenu
{
	default Predicate<String> getValidator()
	{
		return Predicates.notNull();
	}
	
	default boolean drawTextfield()
	{
		return true;
	}
	
	default boolean drawButtons()
	{
		return true;
	}
	
	default String getId()
	{
		return "color";
	}
}
