package exopandora.worldhandler.builder.component.impl;

import java.util.Map.Entry;

import javax.annotation.Nullable;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ComponentAttributeMob extends ComponentAttribute
{
	@Override
	@Nullable
	public INBT serialize()
	{
		ListNBT attributes = new ListNBT();
		
		for(Entry<Attribute, Double> entry : this.attributes.entrySet())
		{
			if(entry.getValue() != 0)
			{
				CompoundNBT attribute = new CompoundNBT();
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
