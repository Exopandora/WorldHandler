package exopandora.worldhandler.gui.button.responder;

import java.util.function.Consumer;

import exopandora.worldhandler.format.TextFormatting;
import exopandora.worldhandler.gui.button.logic.ISliderResponder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SimpleResponder<T> implements ISliderResponder
{
	private final Consumer<Integer> valueConsumer;
	
	public SimpleResponder(Consumer<Integer> valueConsumer)
	{
		this.valueConsumer = valueConsumer;
	}
	
	@Override
	public void setValue(Object id, int value)
	{
		this.valueConsumer.accept(value);
	}
	
	@Override
	public String getText(Object id, String format, int value)
	{
		String suffix = ": " + value;
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		return TextFormatting.shortenString(format, 114 - fontRenderer.getStringWidth(suffix), fontRenderer) + suffix;
	}
}
