package exopandora.worldhandler.gui.menu.impl;

import java.util.List;

import exopandora.worldhandler.util.ILogic;
import net.minecraft.network.chat.MutableComponent;

public interface ILogicButtonList extends ILogic
{
	MutableComponent translate(String key, int depth);
	
	default String buildTranslationKey(List<String> keys, int depth)
	{
		return keys.get(depth);
	}
	
	default String buildEventKey(List<String> keys, int depth)
	{
		return String.join(".", keys);
	}
	
	void onClick(String key, int depth);
}
