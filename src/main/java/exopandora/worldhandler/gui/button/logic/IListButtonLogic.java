package exopandora.worldhandler.gui.button.logic;

import exopandora.worldhandler.gui.button.persistence.ButtonValue;
import exopandora.worldhandler.gui.container.Container;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IListButtonLogic<T>
{
	void actionPerformed(Container container, GuiButton button, ButtonValue<T> values);
	
	int getMax();
	
	T getObject(int index);
	
	String getDisplayString(ButtonValue<T> values);
	
	default String getTooltipString(ButtonValue<T> values)
	{
		if(values != null && values.getObject() != null)
		{
			return values.getObject().toString() + " (" + (values.getIndex() + 1) + "/" + this.getMax() + ")";
		}
		
		return null;
	}
	
	String getId();
}
