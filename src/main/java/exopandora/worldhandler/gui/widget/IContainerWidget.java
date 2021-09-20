package exopandora.worldhandler.gui.widget;

public interface IContainerWidget extends IWidget
{
	default boolean isEnabled()
	{
		return true;
	}
	
	EnumLayer getLayer();
	
	public static enum EnumLayer
	{
		BACKGROUND,
		FOREGROUND;
	}
}
