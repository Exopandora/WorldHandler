package exopandora.worldhandler.gui.widget;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IContainerWidget extends IWidget
{
	default boolean isEnabled()
	{
		return true;
	}
	
	EnumLayer getLayer();
	
	@OnlyIn(Dist.CLIENT)
	public static enum EnumLayer
	{
		BACKGROUND,
		FOREGROUND;
	}
}
