package exopandora.worldhandler.gui.widget.button;

import java.util.function.Consumer;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LogicSliderAttribute extends LogicSliderSimple
{
	public LogicSliderAttribute(Attribute attribute, MutableComponent text, Consumer<Integer> listener)
	{
		super(attribute.getRegistryName().toString(), text, listener);
	}
	
	@Override
	public MutableComponent formatSuffix(int value)
	{
		return new TextComponent("%");
	}
}
