package exopandora.worldhandler.builder.impl;

import java.util.Set;

import exopandora.worldhandler.builder.component.impl.ComponentPotionItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
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
	
	public void setAmplifier(Potion potion, byte amplifier)
	{
		this.potion.setAmplifier(potion, amplifier);
	}
	
	public void setSeconds(Potion potion, int seconds)
	{
		this.potion.setSeconds(potion, seconds);
	}
	
	public void setMinutes(Potion potion, int minutes)
	{
		this.potion.setMinutes(potion, minutes);
	}
	
	public void setHours(Potion potion, int hours)
	{
		this.potion.setHours(potion, hours);
	}
	
	public void setShowParticles(Potion potion, boolean showParticles)
	{
		this.potion.setShowParticles(potion, showParticles);
	}
	
	public void setAmbient(Potion potion, boolean ambient)
	{
		this.potion.setAmbient(potion, ambient);
	}
	
	public byte getAmplifier(Potion potion)
	{
		return this.potion.getAmplifier(potion);
	}
	
	public int getSeconds(Potion potion)
	{
		return this.potion.getSeconds(potion);
	}
	
	public int getMinutes(Potion potion)
	{
		return this.potion.getMinutes(potion);
	}
	
	public int getHours(Potion potion)
	{
		return this.potion.getHours(potion);
	}
	
	public boolean getShowParticles(Potion potion)
	{
		return this.potion.getShowParticles(potion);
	}
	
	public boolean getAmbient(Potion potion)
	{
		return this.potion.getAmbient(potion);
	}
	
	public Set<Potion> getPotions()
	{
		return this.potion.getPotions();
	}
	
	public BuilderPotionItem getBuilderForPotion(Item item)
	{
		return new BuilderPotionItem(item.getRegistryName(), this.getPlayer(), this.potion);
	}
}
