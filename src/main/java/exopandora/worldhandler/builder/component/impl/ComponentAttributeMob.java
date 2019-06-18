package exopandora.worldhandler.builder.component.impl;

import java.util.Map.Entry;
import java.util.function.Function;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.component.abstr.ComponentAttribute;
import exopandora.worldhandler.builder.impl.abstr.EnumAttributes;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ComponentAttributeMob extends ComponentAttribute
{
	public ComponentAttributeMob(Function<EnumAttributes, Boolean> applyable)
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
				
				attribute.putString("Name", entry.getKey().getAttribute());
				attribute.putDouble("Base", entry.getKey().calculate(entry.getValue()));
				
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
