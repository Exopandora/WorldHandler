package exopandora.worldhandler.config;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;

@OnlyIn(Dist.CLIENT)
public class ConfigCategorySliders
{
	private double maxPotionAmplifier;
	private double maxItemEnchantment;
	private double maxItemAttributes;
	private double maxSummonPotionAmplifier;
	private double maxSummonPotionMinutes;
	private double maxSummonAttributes;
	private double maxExperience;
	private double maxPlayerPoints;
	private double maxTriggerValue;
	
	private final DoubleValue valueMaxPotionAmplifier;
	private final DoubleValue valueMaxItemEnchantment;
	private final DoubleValue valueMaxItemAttributes;
	private final DoubleValue valueMaxSummonPotionAmplifier;
	private final DoubleValue valueMaxSummonPotionMinutes;
	private final DoubleValue valueMaxSummonAttributes;
	private final DoubleValue valueMaxExperience;
	private final DoubleValue valueMaxPlayerPoints;
	private final DoubleValue valueMaxTriggerValue;
	
	public ConfigCategorySliders(ForgeConfigSpec.Builder builder)
	{
		builder.push("sliders");
		
		this.valueMaxPotionAmplifier = builder
				.comment(I18n.format("gui.worldhandler.config.comment.sliders.max_potion_amplifier"))
				.translation("gui.worldhandler.config.key.sliders.max_potion_amplifier")
				.defineInRange("max_potion_amplifier", 100D, 0D, Byte.MAX_VALUE);
		
		this.valueMaxItemEnchantment = builder
				.comment(I18n.format("gui.worldhandler.config.comment.sliders.max_item_enchantment"))
				.translation("gui.worldhandler.config.key.sliders.max_item_enchantment")
				.defineInRange("max_item_enchantment", 100D, 0D, Integer.MAX_VALUE);
		this.valueMaxItemAttributes = builder
				.comment(I18n.format("gui.worldhandler.config.comment.sliders.max_item_attributes"))
				.translation("gui.worldhandler.config.key.sliders.max_item_attributes")
				.defineInRange("max_item_attributes", 100D, 0D, Double.MAX_VALUE);
		
		this.valueMaxSummonPotionAmplifier = builder
				.comment(I18n.format("gui.worldhandler.config.comment.sliders.max_summon_potion_amplifier"))
				.translation("gui.worldhandler.config.key.sliders.max_summon_potion_amplifier")
				.defineInRange("max_summon_potion_amplifier", 100D, 0D, Byte.MAX_VALUE);
		this.valueMaxSummonPotionMinutes = builder
				.comment(I18n.format("gui.worldhandler.config.comment.sliders.max_summon_potion_minutes"))
				.translation("gui.worldhandler.config.key.sliders.max_summon_potion_minutes")
				.defineInRange("max_summon_potion_minutes", 100D, 0D, 16000);
		this.valueMaxSummonAttributes = builder
				.comment(I18n.format("gui.worldhandler.config.comment.sliders.max_summon_attributes"))
				.translation("gui.worldhandler.config.key.sliders.max_summon_attributes")
				.defineInRange("max_summon_attributes", 100D, 0D, Double.MAX_VALUE);
		
		this.valueMaxExperience = builder
				.comment(I18n.format("gui.worldhandler.config.comment.sliders.max_experience"))
				.translation("gui.worldhandler.config.key.sliders.max_experience")
				.defineInRange("max_experience", 100D, 0D, 100000D);
		this.valueMaxPlayerPoints = builder
				.comment(I18n.format("gui.worldhandler.config.comment.sliders.max_player_points"))
				.translation("gui.worldhandler.config.key.sliders.max_player_points")
				.defineInRange("max_player_points", 100D, 0D, 100000D);
		this.valueMaxTriggerValue = builder
				.comment(I18n.format("gui.worldhandler.config.comment.sliders.max_trigger_value"))
				.translation("gui.worldhandler.config.key.sliders.max_trigger_value")
				.defineInRange("max_trigger_value", 100D, 0D, 100000D);
		
		builder.pop();
	}
	
	public void read()
	{
		this.maxPotionAmplifier = this.valueMaxPotionAmplifier.get();
		this.maxItemEnchantment = this.valueMaxItemEnchantment.get();
		this.maxItemAttributes = this.valueMaxItemAttributes.get();
		this.maxSummonPotionAmplifier = this.valueMaxSummonPotionAmplifier.get();
		this.maxSummonPotionMinutes = this.valueMaxSummonPotionMinutes.get();
		this.maxSummonAttributes = this.valueMaxSummonAttributes.get();
		this.maxExperience = this.valueMaxExperience.get();
		this.maxPlayerPoints = this.valueMaxPlayerPoints.get();
		this.maxTriggerValue = this.valueMaxTriggerValue.get();
	}
	
	private void write()
	{
		Config.set(this.valueMaxPotionAmplifier, this.maxPotionAmplifier);
		Config.set(this.valueMaxItemEnchantment, this.maxItemEnchantment);
		Config.set(this.valueMaxItemAttributes, this.maxItemAttributes);
		Config.set(this.valueMaxSummonPotionAmplifier, this.maxSummonPotionAmplifier);
		Config.set(this.valueMaxSummonPotionMinutes, this.maxSummonPotionMinutes);
		Config.set(this.valueMaxSummonAttributes, this.maxSummonAttributes);
		Config.set(this.valueMaxExperience, this.maxExperience);
		Config.set(this.valueMaxPlayerPoints, this.maxPlayerPoints);
		Config.set(this.valueMaxTriggerValue, this.maxTriggerValue);
	}
	
	public double getMaxPotionAmplifier()
	{
		return this.maxPotionAmplifier;
	}
	
	public void setMaxPotionAmplifier(double max)
	{
		this.maxPotionAmplifier = max;
		this.write();
	}
	
	public double getMaxItemEnchantment()
	{
		return this.maxItemEnchantment;
	}
	
	public void setMaxItemEnchantment(double max)
	{
		this.maxItemEnchantment = max;
		this.write();
	}
	
	public double getMaxItemAttributes()
	{
		return this.maxItemAttributes;
	}
	
	public void setMaxItemAttributes(double max)
	{
		this.maxItemAttributes = max;
		this.write();
	}
	
	public double getMaxSummonPotionAmplifier()
	{
		return this.maxSummonPotionAmplifier;
	}
	
	public void setMaxSummonPotionAmplifier(double max)
	{
		this.maxSummonPotionAmplifier = max;
		this.write();
	}
	
	public double getMaxSummonPotionMinutes()
	{
		return this.maxSummonPotionMinutes;
	}
	
	public void setMaxSummonPotionMinutes(double max)
	{
		this.maxSummonPotionMinutes = max;
		this.write();
	}
	
	public double getMaxSummonAttributes()
	{
		return this.maxSummonAttributes;
	}
	
	public void setMaxSummonAttributes(double max)
	{
		this.maxSummonAttributes = max;
		this.write();
	}
	
	public double getMaxExperience()
	{
		return this.maxExperience;
	}
	
	public void setMaxExperience(double max)
	{
		this.maxExperience = max;
		this.write();
	}
	
	public double getMaxPlayerPoints()
	{
		return this.maxPlayerPoints;
	}
	
	public void setMaxPlayerPoints(double max)
	{
		this.maxPlayerPoints = max;
		this.write();
	}
	
	public double getMaxTriggerValue()
	{
		return this.maxTriggerValue;
	}
	
	public void setMaxTriggerValue(double max)
	{
		this.maxTriggerValue = max;
		this.write();
	}
}
