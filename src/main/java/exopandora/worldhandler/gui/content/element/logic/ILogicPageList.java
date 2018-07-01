package exopandora.worldhandler.gui.content.element.logic;

import exopandora.worldhandler.gui.container.Container;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface ILogicPageList<T, K> extends ILogic
{
	String translate(T key);
	String getRegistryName(T key);
	
	void onClick(T clicked);
	void onRegister(int id, int x, int y, int width, int height, String display, String registryKey, boolean enabled, T value, Container container);
	
	T getObject(K object);
}