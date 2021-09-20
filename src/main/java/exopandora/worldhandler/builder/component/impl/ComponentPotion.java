package exopandora.worldhandler.builder.component.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.component.IBuilderComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.effect.MobEffect;

public abstract class ComponentPotion implements IBuilderComponent 
{
	protected final Map<MobEffect, EffectNBT> potions = new HashMap<MobEffect, EffectNBT>();
	
	@Override
	@Nullable
	public Tag serialize()
	{
		ListTag list = new ListTag();
		
		for(Entry<MobEffect, EffectNBT> entry : this.potions.entrySet())
		{
			EffectNBT effect = entry.getValue();
			
			if(effect.getAmplifier() > 0)
			{
				CompoundTag compound = effect.serialize();
				compound.putByte("Id", (byte) MobEffect.getId(entry.getKey()));
				list.add(compound);
			}
		}
		
		if(list.isEmpty())
		{
			return null;
		}
		
		return list;
	}
	
	public void setAmplifier(MobEffect potion, byte amplifier)
	{
		this.get(potion).setAmplifier(amplifier);
	}
	
	public byte getAmplifier(MobEffect potion)
	{
		return this.get(potion).getAmplifier();
	}
	
	public void setSeconds(MobEffect potion, int seconds)
	{
		this.get(potion).setSeconds(seconds);
	}
	
	public int getSeconds(MobEffect potion)
	{
		return this.get(potion).getSeconds();
	}
	
	public void setMinutes(MobEffect potion, int minutes)
	{
		this.get(potion).setMinutes(minutes);
	}
	
	public int getMinutes(MobEffect potion)
	{
		return this.get(potion).getMinutes();
	}
	
	public void setHours(MobEffect potion, int hours)
	{
		this.get(potion).setHours(hours);
	}
	
	public int getHours(MobEffect potion)
	{
		return this.get(potion).getHours();
	}
	
	public void setShowParticles(MobEffect potion, boolean showParticles)
	{
		this.get(potion).setShowParticles(showParticles);
	}
	
	public boolean getShowParticles(MobEffect potion)
	{
		return this.get(potion).getShowParticles();
	}
	
	public void setAmbient(MobEffect potion, boolean ambient)
	{
		this.get(potion).setAmbient(ambient);
	}
	
	public boolean getAmbient(MobEffect potion)
	{
		return this.get(potion).getAmbient();
	}
	
	private EffectNBT get(MobEffect potion)
	{
		return this.potions.computeIfAbsent(potion, key -> new EffectNBT());
	}
	
	public Set<MobEffect> getMobEffects()
	{
		return this.potions.keySet();
	}
	
	public void remove(MobEffect potion)
	{
		this.potions.remove(potion);
	}
}
