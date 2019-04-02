package exopandora.worldhandler.gui.logic;

import java.util.List;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface ILogicClickList extends ILogic
{
	String translate(String key, int depth);
	
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
