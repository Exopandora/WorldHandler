package exopandora.worldhandler.gui.button.logic;

import java.util.Arrays;

import com.mojang.realmsclient.gui.ChatFormatting;

import exopandora.worldhandler.format.EnumColor;
import exopandora.worldhandler.gui.button.persistence.ButtonValues;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class ColorListButtonLogic implements IListButtonLogic<Integer>
{
	@Override
	public final int getMax()
	{
		return (int) Arrays.stream(ChatFormatting.values()).filter(ChatFormatting::isColor).count();
	}
	
	@Override
	public Integer getObject(int index)
	{
		return index;
	}
	
	@Override
	public String getTooltipString(ButtonValues<Integer> values)
	{
		return null;
	}
	
	@Override
	public String getDisplayString(ButtonValues values)
	{
		EnumColor color = EnumColor.getColorFromId(values.getIndex());
		return color + I18n.format("gui.worldhandler.color") + ": " + I18n.format("gui.worldhandler.color." + color.getFormat());
	}
	
	@Override
	public String getId()
	{
		return "color";
	}
}
