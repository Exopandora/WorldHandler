package exopandora.worldhandler.text;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MutableStringTextComponent extends StringTextComponent
{
	private String text;
	
	public MutableStringTextComponent()
	{
		super(null);
		this.text = "";
	}
	
	public MutableStringTextComponent(String text)
	{
		super(null);
		this.text = text;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public String getText()
	{
		return this.text;
	}
	
	@Override
	public String getUnformattedComponentText()
	{
		return this.text;
	}
	
	@Override
	public String getFormattedText()
	{
		String formatted = super.getFormattedText();
		
		if(this.isSpecial())
		{
			return MutableStringTextComponent.getSpecialFormattedText(formatted);
		}
		
		return formatted;
	}
	
	public boolean isSpecial()
	{
		return this.text != null && !this.text.isEmpty() && MutableStringTextComponent.getSpecialFormattedText(this.text).contains("\u00A7");
	}
	
	public static String getSpecialFormattedText(String text)
	{
		String result = text.replaceAll("\u0026", "\u00A7").replaceAll("\u00A7\u00A7", "\u0026");
		
		if(result.contains("\u00A7"))
		{
			result += TextFormatting.RESET;
		}
		
		return result;
	}
	
	public String formatter(String string, Integer index)
	{
		return this.getStyle().getFormattingCode() + string;
	}
	
	public String serialize()
	{
		MutableStringTextComponent serial = (MutableStringTextComponent) this.deepCopy();
		serial.setText(MutableStringTextComponent.getSpecialFormattedText(this.getUnformattedComponentText()));
		return ITextComponent.Serializer.toJson(serial);
	}
	
	@Override
	public MutableStringTextComponent shallowCopy()
	{
		return new MutableStringTextComponent(this.text);
	}
	
	@Override
	public boolean equals(Object object)
	{
		if(this == object)
		{
			return true;
		}
		else if(!(object instanceof MutableStringTextComponent))
		{
			return false;
		}
		else
		{
			MutableStringTextComponent stringtextcomponent = (MutableStringTextComponent) object;
			return this.text.equals(stringtextcomponent.getText()) && super.equals(object);
		}
	}
}
