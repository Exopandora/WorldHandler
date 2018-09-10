package exopandora.worldhandler.format;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TextFormatting
{
	public static String shortenString(String str, int maxWidth, FontRenderer fontRenderer)
	{
		return TextFormatting.shortenString(str, "", maxWidth, fontRenderer);
	}
	
	public static String shortenString(String str, String prefix, int maxWidth, FontRenderer fontRenderer)
	{
	    String display = prefix;
	    
	    if(fontRenderer.getStringWidth(prefix + str) > (maxWidth - fontRenderer.getStringWidth(prefix)))
	    {
	    	for(int x = 0; x < str.length(); x++)
	    	{
	    		if(fontRenderer.getStringWidth(display + str.charAt(x) + "...") < maxWidth)
	            {
	    			display += str.charAt(x);
	    		}
	    		else
	    		{
	    			display += "...";
	    			break;
	    		}
	    	}
	    }
	    else
	    {
	    	display += str;
	    }
		
		return display;
	}
	
	public static String getTotalTimePlayed(long tick)
	{
		int days = 0;
		int hours = 0;
		int minutes = 0;
		int seconds = 0;
		
		seconds = (int) (tick / 20);
		
		if(seconds > 60)
		{
			int min = MathHelper.floor(seconds / 60);
			seconds = seconds % 60;
			minutes = min;
		}
		
		if(minutes > 60)
		{
			int hrs = MathHelper.floor(minutes / 60);
			minutes = minutes % 60;
			hours = hrs;
		}
		
		if(hours > 24)
		{
			int day = MathHelper.floor(hours / 24);
			hours = hours % 24;
			days = day;
		}
		
		return String.format("%d:%02d:%02d:%02d", days, hours, minutes, seconds);
	}
	
	public static int getHour(long tick)
	{
		int hour = MathHelper.floor((tick + 6000) / 1000F) % 24;
		
		return hour;
	}
	
	public static int getMinute(long tick)
	{
		int hour = MathHelper.floor((tick + 6000F) / 1000F);
		int minute = MathHelper.floor((tick + 6000F - hour * 1000) * 6 / 100);
		
		return minute;
	}
	
	public static String getWorldTime(long tick)
	{
		int hour = TextFormatting.getHour(tick);
		int minute = TextFormatting.getMinute(tick);
		
		return String.format("%02d:%02d", hour, minute);
	}
}
