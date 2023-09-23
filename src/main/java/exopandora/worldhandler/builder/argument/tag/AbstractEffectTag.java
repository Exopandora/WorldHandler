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
import net.minecraftforge.registries.ForgeRegistries;

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
				
				compound.putString("id", ForgeRegistries.MOB_EFFECTS.getKey(entry.getKey()).toString());
				compound.putByte("amplifier", (byte) (instance.getAmplifier() - 1));
				compound.putInt("duration", ticks > 0 ? ticks : 1000000);
				compound.putBoolean("ambient", instance.isAmbient());
				compound.putBoolean("show_particles", instance.doShowParticles());
				
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