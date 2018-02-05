package exopandora.worldhandler.gui.button.responder;

import java.util.function.Consumer;

import exopandora.worldhandler.builder.impl.abstr.EnumAttributes;
import exopandora.worldhandler.format.TextFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AttributeResponder extends SimpleResponder
{
	public AttributeResponder(Consumer<Integer> valueConsumer)
	{
		super(valueConsumer);
	}
	
	@Override
	public String getText(Object id, String format, int value)
	{
		String suffix = ": " + value + " " + ((EnumAttributes) id).getOperation().getDeclaration();
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		return TextFormatting.shortenString(format, 114 - fontRenderer.getStringWidth(suffix), fontRenderer) + suffix;
	}
}
