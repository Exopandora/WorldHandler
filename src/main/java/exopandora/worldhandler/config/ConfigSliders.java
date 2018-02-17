package exopandora.worldhandler.config;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ConfigSliders
{
	private static double MAX_POTION_AMPLIFIER;
	
	private static double MAX_ITEM_ENCHANTENT;
	private static double MAX_ITEM_ATTRIBUTES;
	
	private static double MAX_SUMMON_POTION_AMPLIFIER;
	private static double MAX_SUMMON_POTION_MINUTES;
	private static double MAX_SUMMON_ATTRIBUTES;
	
	private static double MAX_EXPERIENCE;
	private static double MAX_PLAYER_POINTS;
	
	public static final String CATEGORY = "sliders";
	
	public static void load(Configuration config)
	{
		MAX_POTION_AMPLIFIER = config.get(CATEGORY, "max_potion_amplifier", 100D, I18n.format("gui.worldhandler.config.comment.sliders.max_potion_amplifier"), 0D, Byte.MAX_VALUE).setLanguageKey("gui.worldhandler.config.key.sliders.max_potion_amplifier").getDouble();
		
		MAX_ITEM_ENCHANTENT = config.get(CATEGORY, "max_item_enchantment", 100D, I18n.format("gui.worldhandler.config.comment.sliders.max_item_enchantment"), 0D, Integer.MAX_VALUE).setLanguageKey("gui.worldhandler.config.key.sliders.max_item_enchantment").getDouble();
		MAX_ITEM_ATTRIBUTES = config.get(CATEGORY, "max_item_attributes", 100D, I18n.format("gui.worldhandler.config.comment.sliders.max_item_attributes"), 0D, Double.MAX_VALUE).setLanguageKey("gui.worldhandler.config.key.sliders.max_item_attributes").getDouble();
		
		MAX_SUMMON_POTION_AMPLIFIER = config.get(CATEGORY, "max_summon_potion_amplifier", 100D, I18n.format("gui.worldhandler.config.comment.sliders.max_summon_potion_amplifier"), 0D, Byte.MAX_VALUE).setLanguageKey("gui.worldhandler.config.key.sliders.max_summon_potion_amplifier").getDouble();
		MAX_SUMMON_POTION_MINUTES = config.get(CATEGORY, "max_summon_potion_minutes", 100D, I18n.format("gui.worldhandler.config.comment.sliders.max_summon_potion_minutes"), 0D, 16000).setLanguageKey("gui.worldhandler.config.key.sliders.max_summon_potion_minutes").getDouble();
		MAX_SUMMON_ATTRIBUTES = config.get(CATEGORY, "max_summon_attributes", 100D, I18n.format("gui.worldhandler.config.comment.sliders.max_summon_attributes"), 0D, Double.MAX_VALUE).setLanguageKey("gui.worldhandler.config.key.sliders.max_summon_attributes").getDouble();
		
		MAX_EXPERIENCE = config.get(CATEGORY, "max_experience", 100D, I18n.format("gui.worldhandler.config.comment.sliders.max_experience"), 0D, 100000D).setLanguageKey("gui.worldhandler.config.key.sliders.max_experience").getDouble();
		MAX_PLAYER_POINTS = config.get(CATEGORY, "max_player_points", 100D, I18n.format("gui.worldhandler.config.comment.sliders.max_player_points"), 0D, 100000D).setLanguageKey("gui.worldhandler.config.key.sliders.max_player_points").getDouble();
		
		if(config.hasChanged())
		{
			config.save();
		}
	}
	
	public static double getMaxPotionAmplifier()
	{
		return MAX_POTION_AMPLIFIER;
	}
	
	public static double getMaxItemEnchantment()
	{
		return MAX_ITEM_ENCHANTENT;
	}
	
	public static double getMaxItemAttributes()
	{
		return MAX_ITEM_ATTRIBUTES;
	}
	
	public static double getMaxSummonPotionAmplifier()
	{
		return MAX_SUMMON_POTION_AMPLIFIER;
	}
	
	public static double getMaxSummonPotionMinutes()
	{
		return MAX_SUMMON_POTION_MINUTES;
	}
	
	public static double getMaxSummonAttributes()
	{
		return MAX_SUMMON_ATTRIBUTES;
	}
	
	public static double getMaxExperience()
	{
		return MAX_EXPERIENCE;
	}
	
	public static double getMaxPlayerPoints()
	{
		return MAX_PLAYER_POINTS;
	}
}
