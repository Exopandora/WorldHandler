package exopandora.worldhandler.builder.component.impl;

import java.util.Map.Entry;
import java.util.UUID;
import java.util.function.Function;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.component.abstr.ComponentAttribute;
import exopandora.worldhandler.builder.impl.abstr.EnumAttributes;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ComponentAttributeItem extends ComponentAttribute
{
	public ComponentAttributeItem(Function<EnumAttributes, Boolean> applyable)
	{
		super(applyable);
	}
	
	@Override
	@Nullable
	public INBT serialize()
	{
		ListNBT attributes = new ListNBT();
		
		for(Entry<EnumAttributes, Double> entry : this.attributes.entrySet())
		{
			if(this.applyable.apply(entry.getKey()) && entry.getValue() != 0)
			{
				CompoundNBT attribute = new CompoundNBT();
				
				attribute.putString("AttributeName", entry.getKey().getAttribute());
				attribute.putString("Name", entry.getKey().getAttribute());
				attribute.putDouble("Amount", entry.getKey().calculate(entry.getValue()));
				attribute.putInt("Operation", entry.getKey().getOperation().ordinal());
				attribute.putLong("UUIDLeast", UUID.nameUUIDFromBytes(entry.getKey().getAttribute().getBytes()).getLeastSignificantBits());
				attribute.putLong("UUIDMost", UUID.nameUUIDFromBytes(entry.getKey().getAttribute().getBytes()).getMostSignificantBits());
				
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
