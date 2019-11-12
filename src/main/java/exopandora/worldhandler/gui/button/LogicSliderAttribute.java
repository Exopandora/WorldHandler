package exopandora.worldhandler.gui.button;

import java.util.function.Consumer;

import exopandora.worldhandler.builder.impl.EnumAttributes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LogicSliderAttribute extends LogicSliderSimple
{
	private final EnumAttributes attribute;
	
	public LogicSliderAttribute(EnumAttributes attribute, String text, Consumer<Integer> listener)
	{
		super(attribute.getAttribute(), text, listener);
		this.attribute = attribute;
	}
	
	@Override
	public String formatSuffix(int value)
	{
		return " " + this.attribute.getOperation().getDeclaration();
	}
}
