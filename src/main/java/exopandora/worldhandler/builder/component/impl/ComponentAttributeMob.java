package exopandora.worldhandler.builder.component.impl;

import java.util.Map.Entry;
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
public class ComponentAttributeMob extends ComponentAttribute
{
	public ComponentAttributeMob(Function<EnumAttributes, Boolean> applyable)
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
				
				attribute.setString("Name", entry.getKey().getAttribute());
				attribute.setDouble("Base", entry.getKey().calculate(entry.getValue()));
				
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
		return "Attributes";
	}
}
