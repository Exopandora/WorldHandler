package exopandora.worldhandler.util;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;

public class TextUtils
{
	public static final TextComponent ARROW_LEFT = new TextComponent("<");
	public static final TextComponent ARROW_RIGHT = new TextComponent(">");
	public static final MutableComponent ARROW_LEFT_BOLD = new TextComponent("<").withStyle(ChatFormatting.BOLD);
	public static final MutableComponent ARROW_RIGHT_BOLD = new TextComponent(">").withStyle(ChatFormatting.BOLD);
	
	public static MutableComponent stripText(MutableComponent string, int maxWidth, Font font)
	{
		return TextUtils.stripText(string, (MutableComponent) TextComponent.EMPTY, maxWidth, font);
	}
	
	public static MutableComponent stripText(MutableComponent string, MutableComponent prefix, int maxWidth, Font font)
	{
		if(font.width(prefix) + font.width(string) > (maxWidth - font.width(prefix)))
		{
			MutableComponent result = new TextComponent("").setStyle(string.getStyle()); 
			
			for(char c : string.getString().toCharArray())
			{
				MutableComponent extension = new TextComponent(result.getString() + c + "...").setStyle(string.getStyle());
				
				if(font.width(extension) < maxWidth)
				{
					result = new TextComponent(result.getString() + c).setStyle(string.getStyle());
				}
				else
				{
					return new TextComponent(result.getString() + "...").setStyle(string.getStyle());
				}
			}
		}
		
		return prefix.plainCopy().append(string);
	}
	
	public static String formatTotalTime(long tick)
	{
		int days = 0;
		int hours = 0;
		int minutes = 0;
		int seconds = 0;
		
		seconds = (int) (tick / 20);
		
		if(seconds > 60)
		{
			int min = seconds / 60;
			seconds = seconds % 60;
			minutes = min;
		}
		
		if(minutes > 60)
		{
			int hrs = minutes / 60;
			minutes = minutes % 60;
			hours = hrs;
		}
		
		if(hours > 24)
		{
			int day = hours / 24;
			hours = hours % 24;
			days = day;
		}
		
		return String.format("%d:%02d:%02d:%02d", days, hours, minutes, seconds);
	}
	
	public static int toHour(long tick)
	{
		return Mth.floor((tick + 6000) / 1000F) % 24;
	}
	
	public static int toMinute(long tick)
	{
		int hour = Mth.floor((tick + 6000F) / 1000F);
		int minute = Mth.floor((tick + 6000F - hour * 1000) * 6 / 100);
		
		return minute;
	}
	
	public static String formatWorldTime(long tick)
	{
		int hour = TextUtils.toHour(tick);
		int minute = TextUtils.toMinute(tick);
		
		return String.format("%02d:%02d", hour, minute);
	}
	
	public static MutableComponent formatNonnull(String text, Object... parameters)
	{
		if(text == null)
		{
			return (MutableComponent) TextComponent.EMPTY;
		}
		
		return new TranslatableComponent(text, parameters);
	}
}
