package exopandora.worldhandler.builder.argument.tag;

import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class AttributeModifiersTag extends AbstractAttributeTag implements IItemComponentProvider
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
				String id = BuiltInRegistries.ATTRIBUTE.getKey(entry.getKey()).toString();
				
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

	@Override
	public void addItemComponents(DataComponentPatch.Builder components)
	{
		ItemAttributeModifiers.Builder modifiers = ItemAttributeModifiers.builder();
		boolean hasModifiers = false;

		for(Entry<Attribute, Double> entry : this.attributes.entrySet())
		{
			if(entry.getValue() != 0)
			{
				ResourceLocation id = BuiltInRegistries.ATTRIBUTE.getKey(entry.getKey());

				modifiers.add(BuiltInRegistries.ATTRIBUTE.wrapAsHolder(entry.getKey()), new AttributeModifier(id, entry.getValue() / 100, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), EquipmentSlotGroup.ANY);
				hasModifiers = true;
			}
		}

		if(hasModifiers)
		{
			components.set(DataComponents.ATTRIBUTE_MODIFIERS, modifiers.build());
		}
	}
}
