package exopandora.worldhandler.util;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TextUtils
{
	public static final StringTextComponent ARROW_LEFT = new StringTextComponent("<");
	public static final StringTextComponent ARROW_RIGHT = new StringTextComponent(">");
	public static final IFormattableTextComponent ARROW_LEFT_BOLD = new StringTextComponent("<").withStyle(net.minecraft.util.text.TextFormatting.BOLD);
	public static final IFormattableTextComponent ARROW_RIGHT_BOLD = new StringTextComponent(">").withStyle(net.minecraft.util.text.TextFormatting.BOLD);
	
	public static IFormattableTextComponent stripText(IFormattableTextComponent string, int maxWidth, FontRenderer fontRenderer)
	{
		return TextUtils.stripText(string, (IFormattableTextComponent) StringTextComponent.EMPTY, maxWidth, fontRenderer);
	}
	
	public static IFormattableTextComponent stripText(IFormattableTextComponent string, IFormattableTextComponent prefix, int maxWidth, FontRenderer fontRenderer)
	{
		if(fontRenderer.width(prefix) + fontRenderer.width(string) > (maxWidth - fontRenderer.width(prefix)))
		{
			IFormattableTextComponent result = new StringTextComponent("").setStyle(string.getStyle()); 
			
			for(char c : string.getString().toCharArray())
			{
				IFormattableTextComponent extension = new StringTextComponent(result.getString() + c + "...").setStyle(string.getStyle());
				
				if(fontRenderer.width(extension) < maxWidth)
				{
					result = new StringTextComponent(result.getString() + c).setStyle(string.getStyle());
				}
				else
				{
					return new StringTextComponent(result.getString() + "...").setStyle(string.getStyle());
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
		return MathHelper.floor((tick + 6000) / 1000F) % 24;
	}
	
	public static int toMinute(long tick)
	{
		int hour = MathHelper.floor((tick + 6000F) / 1000F);
		int minute = MathHelper.floor((tick + 6000F - hour * 1000) * 6 / 100);
		
		return minute;
	}
	
	public static String formatWorldTime(long tick)
	{
		int hour = TextUtils.toHour(tick);
		int minute = TextUtils.toMinute(tick);
		
		return String.format("%02d:%02d", hour, minute);
	}
	
	public static IFormattableTextComponent formatNonnull(String text, Object... parameters)
	{
		if(text == null)
		{
			return (IFormattableTextComponent) StringTextComponent.EMPTY;
		}
		
		return new TranslationTextComponent(text, parameters);
	}
}
