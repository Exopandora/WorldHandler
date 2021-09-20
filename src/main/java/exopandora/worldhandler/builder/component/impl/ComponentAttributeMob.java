package exopandora.worldhandler.builder.component.impl;

import java.util.Map.Entry;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class ComponentAttributeMob extends ComponentAttribute
{
	@Override
	@Nullable
	public Tag serialize()
	{
		ListTag attributes = new ListTag();
		
		for(Entry<Attribute, Double> entry : this.attributes.entrySet())
		{
			if(entry.getValue() != 0)
			{
				CompoundTag attribute = new CompoundTag();
				String id = entry.getKey().getRegistryName().toString();
				
				attribute.putString("Name", id);
				attribute.putDouble("Base", entry.getValue() / 100);
				
				attributes.add(attribute);
			}
		}
		
		if(attributes.isEmpty())
		{
			return null;
		}
		
		return attributes;
	}
	
	@Override
	public String getTag()
	{
		return "Attributes";
	}
}
