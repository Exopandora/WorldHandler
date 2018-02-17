package exopandora.worldhandler.builder.component.impl;

import java.util.Map.Entry;
import java.util.UUID;
import java.util.function.Function;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.component.abstr.ComponentAttribute;
import exopandora.worldhandler.builder.impl.abstr.EnumAttributes;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ComponentAttributeItem extends ComponentAttribute
{
	public ComponentAttributeItem(Function<EnumAttributes, Boolean> applyable)
	{
		super(applyable);
	}
	
	@Override
	@Nullable
	public NBTBase serialize()
	{
		NBTTagList attributes = new NBTTagList();
		
		for(Entry<EnumAttributes, Double> entry : this.attributes.entrySet())
		{
			if(this.applyable.apply(entry.getKey()) && entry.getValue() != 0)
			{
				NBTTagCompound attribute = new NBTTagCompound();
				
				attribute.setString("AttributeName", entry.getKey().getAttribute());
				attribute.setString("Name", entry.getKey().getAttribute());
				attribute.setDouble("Amount", entry.getKey().calculate(entry.getValue()));
				attribute.setInteger("Operation", entry.getKey().getOperation().ordinal());
				attribute.setLong("UUIDLeast", UUID.nameUUIDFromBytes(entry.getKey().getAttribute().getBytes()).getLeastSignificantBits());
				attribute.setLong("UUIDMost", UUID.nameUUIDFromBytes(entry.getKey().getAttribute().getBytes()).getMostSignificantBits());
				
				attributes.appendTag(attribute);
			}
		}
		
		if(!attributes.hasNoTags())
		{
			return attributes;
		}
		
		return null;
	}
	
	@Override
	public String getTag()
	{
		return "AttributeModifiers";
	}
}
