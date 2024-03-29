package exopandora.worldhandler.builder.argument.tag;

import java.util.Map.Entry;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.registries.ForgeRegistries;

public class AttributesTag extends AbstractAttributeTag
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
	public String key()
	{
		return "Attributes";
	}
}
