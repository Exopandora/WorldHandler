package exopandora.worldhandler.gui.content.element.logic;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface ILogicColorMenu extends ILogic
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
	
	@Override
	default String getId()
	{
		return "color";
	}
}
