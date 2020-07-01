package exopandora.worldhandler.gui.menu.impl;

import exopandora.worldhandler.util.ILogic;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface ILogicMapped<T> extends ILogic
{
	IFormattableTextComponent translate(T item);
	IFormattableTextComponent toTooltip(T item);
	
	default IFormattableTextComponent formatTooltip(T item, int index, int max)
	{
		IFormattableTextComponent tooltip = this.toTooltip(item);
		
		if(tooltip != null)
		{
			return tooltip.func_240702_b_(String.format(" (%d/%d)", index, max));
		}
		
		return (IFormattableTextComponent) StringTextComponent.field_240750_d_;
	}
	
	void onClick(T item);
	
	default void onInit(T item)
	{
		
	}
}
