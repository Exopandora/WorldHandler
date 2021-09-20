package exopandora.worldhandler.builder.impl;

import java.util.Set;

import exopandora.worldhandler.builder.component.impl.ComponentPotionItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;

public class BuilderPotionItem extends BuilderGive
{
	private final ComponentPotionItem potion;
	
	public BuilderPotionItem()
	{
		this(null, null, new ComponentPotionItem());
	}
	
	public BuilderPotionItem(ResourceLocation item, String player, ComponentPotionItem potion)
	{
		super(player, item);
		this.potion = this.registerNBTComponent(potion);
	}
	
	public void setAmplifier(MobEffect potion, byte amplifier)
	{
		this.potion.setAmplifier(potion, amplifier);
	}
	
	public void setSeconds(MobEffect potion, int seconds)
	{
		this.potion.setSeconds(potion, seconds);
	}
	
	public void setMinutes(MobEffect potion, int minutes)
	{
		this.potion.setMinutes(potion, minutes);
	}
	
	public void setHours(MobEffect potion, int hours)
	{
		this.potion.setHours(potion, hours);
	}
	
	public void setShowParticles(MobEffect potion, boolean showParticles)
	{
		this.potion.setShowParticles(potion, showParticles);
	}
	
	public void setAmbient(MobEffect potion, boolean ambient)
	{
		this.potion.setAmbient(potion, ambient);
	}
	
	public byte getAmplifier(MobEffect potion)
	{
		return this.potion.getAmplifier(potion);
	}
	
	public int getSeconds(MobEffect potion)
	{
		return this.potion.getSeconds(potion);
	}
	
	public int getMinutes(MobEffect potion)
	{
		return this.potion.getMinutes(potion);
	}
	
	public int getHours(MobEffect potion)
	{
		return this.potion.getHours(potion);
	}
	
	public boolean getShowParticles(MobEffect potion)
	{
		return this.potion.getShowParticles(potion);
	}
	
	public boolean getAmbient(MobEffect potion)
	{
		return this.potion.getAmbient(potion);
	}
	
	public Set<MobEffect> getMobEffects()
	{
		return this.potion.getMobEffects();
	}
	
	public BuilderPotionItem build(Item item)
	{
		return new BuilderPotionItem(item.getRegistryName(), this.getPlayer(), this.potion);
	}
}
