package exopandora.worldhandler.gui.content.element.logic;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface ILogicClickList
{
	void consumeKey1(String key);
	
	default void consumeKey2(String key1, String key2)
	{
		this.consumeKey1(key1 + "." + key2);
	}
	
	String translate1(String key);
	
	default String translate2(String key1, String key2)
	{
		return this.translate1(key2);
	}
	
	String getId();
}
