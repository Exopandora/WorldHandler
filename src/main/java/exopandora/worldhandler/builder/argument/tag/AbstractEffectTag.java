package exopandora.worldhandler.builder.argument.tag;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.effect.MobEffect;

public abstract class AbstractEffectTag implements ITagProvider 
{
	private final Map<MobEffect, EffectInstance> effects = new HashMap<MobEffect, EffectInstance>();
	
	@Nullable
	@Override
	public Tag value()
	{
		ListTag list = new ListTag();
		
		for(Entry<MobEffect, EffectInstance> entry : this.effects.entrySet())
		{
			EffectInstance instance = entry.getValue();
			
			if(instance.getAmplifier() > 0)
			{
				CompoundTag compound = new CompoundTag();
				int ticks = instance.toTicks();
				
				compound.putByte("Id", (byte) MobEffect.getId(entry.getKey()));
				compound.putByte("Amplifier", (byte) (instance.getAmplifier() - 1));
				compound.putInt("Duration", ticks > 0 ? ticks : 1000000);
				compound.putBoolean("Ambient", instance.isAmbient());
				compound.putBoolean("ShowParticles", instance.doShowParticles());
				
				list.add(compound);
			}
		}
		
		if(list.isEmpty())
		{
			return null;
		}
		
		return list;
	}
	
	public EffectInstance getOrCreate(MobEffect effect)
	{
		return this.effects.computeIfAbsent(effect, key -> new EffectInstance());
	}
	
	public Set<MobEffect> getMobEffects()
	{
		return this.effects.keySet();
	}
	
	public void remove(MobEffect potion)
	{
		this.effects.remove(potion);
	}
	
	public Map<MobEffect, EffectInstance> getEffects()
	{
		return Collections.unmodifiableMap(this.effects);
	}
	
	public void clear()
	{
		this.effects.clear();
	}
}