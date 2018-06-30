package exopandora.worldhandler.builder.impl;

import java.util.Set;

import exopandora.worldhandler.builder.component.impl.ComponentPotionItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
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
		this.potion.get(potion).setAmplifier(amplifier);
	}
	
	public void setSeconds(Potion potion, int seconds)
	{
		this.potion.get(potion).setSeconds(seconds);
	}
	
	public void setMinutes(Potion potion, int minutes)
	{
		this.potion.get(potion).setMinutes(minutes);
	}
	
	public void setHours(Potion potion, int hours)
	{
		this.potion.get(potion).setHours(hours);
	}
	
	public void setShowParticles(Potion potion, boolean showParticles)
	{
		this.potion.get(potion).setShowParticles(showParticles);
	}
	
	public void setAmbient(Potion potion, boolean ambient)
	{
		this.potion.get(potion).setAmbient(ambient);
	}
	
	public byte getAmplifier(Potion potion)
	{
		return this.potion.get(potion).getAmplifier();
	}
	
	public int getSeconds(Potion potion)
	{
		return this.potion.get(potion).getSeconds();
	}
	
	public int getMinutes(Potion potion)
	{
		return this.potion.get(potion).getMinutes();
	}
	
	public int getHours(Potion potion)
	{
		return this.potion.get(potion).getHours();
	}
	
	public boolean getShowParticles(Potion potion)
	{
		return this.potion.get(potion).getShowParticles();
	}
	
	public boolean getAmbient(Potion potion)
	{
		return this.potion.get(potion).getAmbient();
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
