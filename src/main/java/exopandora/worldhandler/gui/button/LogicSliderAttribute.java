package exopandora.worldhandler.gui.button;

import java.util.function.Consumer;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LogicSliderAttribute extends LogicSliderSimple
{
	public LogicSliderAttribute(Attribute attribute, IFormattableTextComponent text, Consumer<Integer> listener)
	{
		super(attribute.getRegistryName().toString(), text, listener);
	}
	
	@Override
	public IFormattableTextComponent formatSuffix(int value)
	{
		return new StringTextComponent("%");
	}
}
