package exopandora.worldhandler.builder.impl;

import java.util.Set;

import exopandora.worldhandler.builder.component.impl.ComponentPotionItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
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
	
	public void setAmplifier(Effect potion, byte amplifier)
	{
		this.potion.setAmplifier(potion, amplifier);
	}
	
	public void setSeconds(Effect potion, int seconds)
	{
		this.potion.setSeconds(potion, seconds);
	}
	
	public void setMinutes(Effect potion, int minutes)
	{
		this.potion.setMinutes(potion, minutes);
	}
	
	public void setHours(Effect potion, int hours)
	{
		this.potion.setHours(potion, hours);
	}
	
	public void setShowParticles(Effect potion, boolean showParticles)
	{
		this.potion.setShowParticles(potion, showParticles);
	}
	
	public void setAmbient(Effect potion, boolean ambient)
	{
		this.potion.setAmbient(potion, ambient);
	}
	
	public byte getAmplifier(Effect potion)
	{
		return this.potion.getAmplifier(potion);
	}
	
	public int getSeconds(Effect potion)
	{
		return this.potion.getSeconds(potion);
	}
	
	public int getMinutes(Effect potion)
	{
		return this.potion.getMinutes(potion);
	}
	
	public int getHours(Effect potion)
	{
		return this.potion.getHours(potion);
	}
	
	public boolean getShowParticles(Effect potion)
	{
		return this.potion.getShowParticles(potion);
	}
	
	public boolean getAmbient(Effect potion)
	{
		return this.potion.getAmbient(potion);
	}
	
	public Set<Effect> getEffects()
	{
		return this.potion.getEffects();
	}
	
	public BuilderPotionItem build(Item item)
	{
		return new BuilderPotionItem(item.getRegistryName(), this.getPlayer(), this.potion);
	}
}
