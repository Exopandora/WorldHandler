package exopandora.worldhandler.util;


import exopandora.worldhandler.builder.INBTWritable;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MutableStringTextComponent extends StringTextComponent implements INBTWritable
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
	
	@Override
	public String getText()
	{
		if(this.isSpecial())
		{
			return MutableStringTextComponent.getSpecialFormattedText(this.text);
		}
		
		return this.text;
	}
	
	@Override
	public String getUnformattedComponentText()
	{
		return this.text;
	}
	
	@Override
	public String getString()
	{
		return this.text;
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
	
	public IReorderingProcessor formatter(String string, Integer index)
	{
		return IReorderingProcessor.func_242239_a(string, this.getStyle());
	}
	
	@Override
	public INBT serialize()
	{
		if(this.getUnformattedComponentText() != null && !this.getUnformattedComponentText().isEmpty())
		{
			return StringNBT.valueOf(this.toString());
		}
		
		return null;
	}
	
	@Override
	public String toString()
	{
		MutableStringTextComponent serial = (MutableStringTextComponent) this.deepCopy();
		serial.setText(MutableStringTextComponent.getSpecialFormattedText(this.getUnformattedComponentText()));
		return ITextComponent.Serializer.toJson(serial);
	}
	
	@Override
	public MutableStringTextComponent copyRaw()
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
			return this.text.equals(stringtextcomponent.getUnformattedComponentText()) && super.equals(object);
		}
	}
}
