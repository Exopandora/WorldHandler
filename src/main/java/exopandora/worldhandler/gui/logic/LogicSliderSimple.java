package exopandora.worldhandler.gui.logic;

import java.util.function.Consumer;

import exopandora.worldhandler.gui.button.GuiSlider.ILogicSlider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LogicSliderSimple implements ILogicSlider
{
	private final String id;
	private final String text;
	private final Consumer<Integer> listener;
	
	public LogicSliderSimple(String id, String text, Consumer<Integer> listener)
	{
		this.id = id;
		this.text = text;
		this.listener = listener;
	}
	
	@Override
	public String formatPrefix(int value)
	{
		return this.text;
	}
	
	@Override
	public String formatSuffix(int value)
	{
		return "";
	}
	
	@Override
	public String formatValue(int value)
	{
		return ": " + String.valueOf(value);
	}
	
	@Override
	public void onChangeSliderValue(int value)
	{
		this.listener.accept(value);
	}
	
	@Override
	public String getId()
	{
		return this.id;
	}
}
