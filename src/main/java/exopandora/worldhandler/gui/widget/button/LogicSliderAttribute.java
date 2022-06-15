package exopandora.worldhandler.gui.widget.button;

import java.util.function.Consumer;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.registries.ForgeRegistries;

public class LogicSliderAttribute extends LogicSliderSimple
{
	public LogicSliderAttribute(Attribute attribute, MutableComponent text, Consumer<Integer> listener)
	{
		super(ForgeRegistries.ATTRIBUTES.getKey(attribute).toString(), text, listener);
	}
	
	@Override
	public MutableComponent formatSuffix(int value)
	{
		return Component.literal("%");
	}
}
