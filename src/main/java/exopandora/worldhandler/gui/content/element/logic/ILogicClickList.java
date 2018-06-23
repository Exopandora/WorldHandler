package exopandora.worldhandler.gui.content.element.logic;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface ILogicClickList extends ILogic
{
	String translate(String... keys);
	void consumeKey(String... keys);
	
	default void consumeKeyImpl(String... keys)
	{
		this.consumeKey(keys[0] + "." + keys[1]);
	}
}
