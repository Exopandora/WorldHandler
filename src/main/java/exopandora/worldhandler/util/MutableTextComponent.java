package exopandora.worldhandler.util;

import exopandora.worldhandler.builder.INBTWritable;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.FormattedCharSequence;

public class MutableTextComponent extends TextComponent implements INBTWritable
{	
	public MutableTextComponent()
	{
		super("");
	}
	
	public MutableTextComponent(String text)
	{
		super(text);
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
			return MutableTextComponent.getSpecialFormattedText(this.text);
		}
		
		return this.text;
	}
	
	@Override
	public String getContents()
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
		return this.text != null && !this.text.isEmpty() && MutableTextComponent.getSpecialFormattedText(this.text).contains("\u00A7");
	}
	
	public static String getSpecialFormattedText(String text)
	{
		String result = text.replaceAll("\u0026", "\u00A7").replaceAll("\u00A7\u00A7", "\u0026");
		
		if(result.contains("\u00A7"))
		{
			result += ChatFormatting.RESET;
		}
		
		return result;
	}
	
	public FormattedCharSequence formatter(String string, Integer index)
	{
		return FormattedCharSequence.forward(string, this.getStyle());
	}
	
	@Override
	public Tag serialize()
	{
		if(this.getContents() != null && !this.getContents().isEmpty())
		{
			return StringTag.valueOf(this.toString());
		}
		
		return null;
	}
	
	@Override
	public String toString()
	{
		MutableTextComponent serial = (MutableTextComponent) this.copy();
		serial.setText(MutableTextComponent.getSpecialFormattedText(this.getContents()));
		return Component.Serializer.toJson(serial);
	}
	
	@Override
	public MutableTextComponent plainCopy()
	{
		return new MutableTextComponent(this.text);
	}
	
	@Override
	public boolean equals(Object object)
	{
		if(this == object)
		{
			return true;
		}
		else if(!(object instanceof MutableTextComponent))
		{
			return false;
		}
		else
		{
			MutableTextComponent stringtextcomponent = (MutableTextComponent) object;
			return this.text.equals(stringtextcomponent.getContents()) && super.equals(object);
		}
	}
}
