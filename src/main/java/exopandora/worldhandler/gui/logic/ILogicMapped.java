package exopandora.worldhandler.gui.logic;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface ILogicMapped<T> extends ILogic
{
	String translate(T item);
	String toTooltip(T item);
	
	default String formatTooltip(T item, int index, int max)
	{
		String tooltip = this.toTooltip(item);
		
		if(tooltip != null)
		{
			return String.format("%s (%d/%d)", tooltip, index, max);
		}
		
		return null;
	}
	
	void onClick(T item);
	
	default void onInit(T item)
	{
		
	}
}
