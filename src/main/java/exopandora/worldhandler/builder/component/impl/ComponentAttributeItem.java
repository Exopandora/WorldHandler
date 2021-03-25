package exopandora.worldhandler.builder.component.impl;

import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ComponentAttributeItem extends ComponentAttribute
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
				
				attribute.putString("AttributeName", id);
				attribute.putDouble("Amount", entry.getValue() / 100);
				attribute.putInt("Operation", 1); // 0 = additive, 1 = percentage
				attribute.putUUID("UUID", UUID.nameUUIDFromBytes(id.getBytes()));
				
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
		return "AttributeModifiers";
	}
}
