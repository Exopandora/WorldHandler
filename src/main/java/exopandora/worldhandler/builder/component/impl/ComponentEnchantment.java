package exopandora.worldhandler.builder.component.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import exopandora.worldhandler.builder.component.IBuilderComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
public class ComponentEnchantment implements IBuilderComponent
{
	private Map<Enchantment, Short> enchantments = new HashMap<Enchantment, Short>();
	
	@Override
	@Nullable
	public INBT serialize()
	{
		ListNBT enchantments = new ListNBT();
		
		for(Entry<Enchantment, Short> entry : this.enchantments.entrySet())
		{
			if(entry.getValue() > 0)
			{
				CompoundNBT enchantment = new CompoundNBT();
				
				enchantment.putString("id", ForgeRegistries.ENCHANTMENTS.getKey(entry.getKey()).toString());
				enchantment.putShort("lvl", entry.getValue());
				
				enchantments.add(enchantment);
			}
		}
		
		if(enchantments.isEmpty())
		{
			return null;
		}
		
		return enchantments;
	}
	
	public void setLevel(Enchantment enchantment, short level)
	{
		if(level == 0)
		{
			this.enchantments.remove(enchantment);
		}
		else
		{
			this.enchantments.put(enchantment, level);
		}
	}
	
	public short getLevel(Enchantment enchantment)
	{
		return this.enchantments.get(enchantment);
	}
	
	public Set<Enchantment> getEnchantments()
	{
		return this.enchantments.keySet();
	}
	
	@Override
	public String getTag()
	{
		return "Enchantments";
	}
}
