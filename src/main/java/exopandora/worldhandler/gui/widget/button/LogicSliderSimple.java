package exopandora.worldhandler.gui.widget.button;

import java.util.function.Consumer;

import exopandora.worldhandler.gui.widget.button.GuiSlider.ILogicSlider;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;

public class LogicSliderSimple implements ILogicSlider
{
	private final String id;
	private final MutableComponent text;
	private final Consumer<Integer> listener;
	
	public LogicSliderSimple(String id, MutableComponent text, Consumer<Integer> listener)
	{
		this.id = id;
		this.text = text;
		this.listener = listener;
	}
	
	@Override
	public MutableComponent formatPrefix(int value)
	{
		return this.text;
	}
	
	@Override
	public MutableComponent formatSuffix(int value)
	{
		return new TextComponent("");
	}
	
	@Override
	public MutableComponent formatValue(int value)
	{
		return new TextComponent(": " + String.valueOf(value));
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
