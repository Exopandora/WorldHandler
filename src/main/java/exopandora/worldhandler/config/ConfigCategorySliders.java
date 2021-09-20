package exopandora.worldhandler.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;

public class ConfigCategorySliders
{
	private final DoubleValue maxPotionAmplifier;
	private final DoubleValue maxItemEnchantment;
	private final DoubleValue maxItemAttributes;
	private final DoubleValue maxSummonPotionAmplifier;
	private final DoubleValue maxSummonPotionMinutes;
	private final DoubleValue maxSummonAttributes;
	private final DoubleValue maxExperience;
	private final DoubleValue maxPlayerPoints;
	private final DoubleValue maxTriggerValue;
	
	public ConfigCategorySliders(ForgeConfigSpec.Builder builder)
	{
		builder.push("sliders");
		
		this.maxPotionAmplifier = builder
				.translation("gui.worldhandler.config.sliders.max_potion_amplifier")
				.comment("Maximum value for the potion amplifier")
				.defineInRange("max_potion_amplifier", 100D, 0D, Byte.MAX_VALUE);
		
		this.maxItemEnchantment = builder
				.translation("gui.worldhandler.config.sliders.max_item_enchantment")
				.comment("Maximum value for an item enchantment")
				.defineInRange("max_item_enchantment", 100D, 0D, Integer.MAX_VALUE);
		this.maxItemAttributes = builder
				.translation("gui.worldhandler.config.sliders.max_item_attributes")
				.comment("Maximum value for an item attribute")
				.defineInRange("max_item_attributes", 100D, 0D, Double.MAX_VALUE);
		
		this.maxSummonPotionAmplifier = builder
				.translation("gui.worldhandler.config.sliders.max_summon_potion_amplifier")
				.comment("Maximum value for the potion amplifier for summon")
				.defineInRange("max_summon_potion_amplifier", 100D, 0D, Byte.MAX_VALUE);
		this.maxSummonPotionMinutes = builder
				.translation("gui.worldhandler.config.sliders.max_summon_potion_minutes")
				.comment("Maximum value for the potion duration in minutes for summon")
				.defineInRange("max_summon_potion_minutes", 100D, 0D, 16000);
		this.maxSummonAttributes = builder
				.translation("gui.worldhandler.config.sliders.max_summon_attributes")
				.comment("Maximum value for attributes")
				.defineInRange("max_summon_attributes", 100D, 0D, Double.MAX_VALUE);
		
		this.maxExperience = builder
				.translation("gui.worldhandler.config.sliders.max_experience")
				.comment("Maximum value for experience")
				.defineInRange("max_experience", 100D, 0D, 100000D);
		this.maxPlayerPoints = builder
				.translation("gui.worldhandler.config.sliders.max_player_points")
				.comment("Maximum value for player points")
				.defineInRange("max_player_points", 100D, 0D, 100000D);
		this.maxTriggerValue = builder
				.translation("gui.worldhandler.config.sliders.max_trigger_value")
				.comment("Maximum value for triggers")
				.defineInRange("max_trigger_value", 100D, 0D, 100000D);
		
		builder.pop();
	}
	
	public double getMaxPotionAmplifier()
	{
		return this.maxPotionAmplifier.get();
	}
	
	public void setMaxPotionAmplifier(double max)
	{
		Config.set(this.maxPotionAmplifier, max);
	}
	
	public double getMaxItemEnchantment()
	{
		return this.maxItemEnchantment.get();
	}
	
	public void setMaxItemEnchantment(double max)
	{
		Config.set(this.maxItemEnchantment, max);
	}
	
	public double getMaxItemAttributes()
	{
		return this.maxItemAttributes.get();
	}
	
	public void setMaxItemAttributes(double max)
	{
		Config.set(this.maxItemAttributes, max);
	}
	
	public double getMaxSummonPotionAmplifier()
	{
		return this.maxSummonPotionAmplifier.get();
	}
	
	public void setMaxSummonPotionAmplifier(double max)
	{
		Config.set(this.maxSummonPotionAmplifier, max);
	}
	
	public double getMaxSummonPotionMinutes()
	{
		return this.maxSummonPotionMinutes.get();
	}
	
	public void setMaxSummonPotionMinutes(double max)
	{
		Config.set(this.maxSummonPotionMinutes, max);
	}
	
	public double getMaxSummonAttributes()
	{
		return this.maxSummonAttributes.get();
	}
	
	public void setMaxSummonAttributes(double max)
	{
		Config.set(this.maxSummonAttributes, max);
	}
	
	public double getMaxExperience()
	{
		return this.maxExperience.get();
	}
	
	public void setMaxExperience(double max)
	{
		Config.set(this.maxExperience, max);
	}
	
	public double getMaxPlayerPoints()
	{
		return this.maxPlayerPoints.get();
	}
	
	public void setMaxPlayerPoints(double max)
	{
		Config.set(this.maxPlayerPoints, max);
	}
	
	public double getMaxTriggerValue()
	{
		return this.maxTriggerValue.get();
	}
	
	public void setMaxTriggerValue(double max)
	{
		Config.set(this.maxTriggerValue, max);
	}
}
