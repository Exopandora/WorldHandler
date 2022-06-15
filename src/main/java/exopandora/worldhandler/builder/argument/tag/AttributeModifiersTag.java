package exopandora.worldhandler.builder.argument.tag;

import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.registries.ForgeRegistries;

public class AttributeModifiersTag extends AbstractAttributeTag
{
	@Override
	@Nullable
	public Tag value()
	{
		ListTag attributes = new ListTag();
		
		for(Entry<Attribute, Double> entry : this.attributes.entrySet())
		{
			if(entry.getValue() != 0)
			{
				CompoundTag attribute = new CompoundTag();
				String id = ForgeRegistries.ATTRIBUTES.getKey(entry.getKey()).toString();
				
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
	public String key()
	{
		return "AttributeModifiers";
	}
}
