package exopandora.worldhandler.gui.button.logic;

import exopandora.worldhandler.gui.button.storage.ButtonStorage;
import exopandora.worldhandler.gui.container.Container;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IListButtonLogic<T>
{
	void actionPerformed(Container container, GuiButton button, ButtonStorage<T> storage);
	
	int getMax();
	
	T getObject(int index);
	
	String getDisplayString(ButtonStorage<T> storage);
	
	default String getTooltipString(ButtonStorage<T> storage)
	{
		if(storage != null && storage.getObject() != null)
		{
			return storage.getObject().toString() + " (" + (storage.getIndex() + 1) + "/" + this.getMax() + ")";
		}
		
		return null;
	}
	
	String getId();
}
