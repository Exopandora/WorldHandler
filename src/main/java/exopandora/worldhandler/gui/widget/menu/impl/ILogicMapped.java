package exopandora.worldhandler.gui.widget.menu.impl;

import exopandora.worldhandler.util.ILogic;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public interface ILogicMapped<T> extends ILogic
{
	MutableComponent translate(T item);
	MutableComponent toTooltip(T item);
	
	default MutableComponent formatTooltip(T item, int index, int max)
	{
		MutableComponent tooltip = this.toTooltip(item);
		
		if(tooltip != null)
		{
			return tooltip.append(String.format(" (%d/%d)", index, max));
		}
		
		return Component.empty();
	}
	
	void onClick(T item);
	
	default void onInit(T item)
	{
		
	}
}
