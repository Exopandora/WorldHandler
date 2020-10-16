package exopandora.worldhandler.gui.widget.button;

import java.util.function.Consumer;

import exopandora.worldhandler.gui.widget.button.GuiSlider.ILogicSlider;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LogicSliderSimple implements ILogicSlider
{
	private final String id;
	private final IFormattableTextComponent text;
	private final Consumer<Integer> listener;
	
	public LogicSliderSimple(String id, IFormattableTextComponent text, Consumer<Integer> listener)
	{
		this.id = id;
		this.text = text;
		this.listener = listener;
	}
	
	@Override
	public IFormattableTextComponent formatPrefix(int value)
	{
		return this.text;
	}
	
	@Override
	public IFormattableTextComponent formatSuffix(int value)
	{
		return new StringTextComponent("");
	}
	
	@Override
	public IFormattableTextComponent formatValue(int value)
	{
		return new StringTextComponent(": " + String.valueOf(value));
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
