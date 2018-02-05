package exopandora.worldhandler.builder.component.impl;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.component.IBuilderComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ComponentEnchantment implements IBuilderComponent
{
	private Map<Enchantment, Short> enchantments = Enchantment.REGISTRY.getKeys().stream().collect(Collectors.toMap(Enchantment.REGISTRY::getObject, key -> (short) 0));
	
	@Override
	@Nullable
	public NBTBase serialize()
	{
		NBTTagList enchantments = new NBTTagList();
		
		for(Entry<Enchantment, Short> entry : this.enchantments.entrySet())
		{
			if(entry.getValue() > 0)
			{
				NBTTagCompound enchantment = new NBTTagCompound();
				
				enchantment.setShort("id", (short) Enchantment.getEnchantmentID(entry.getKey()));
				enchantment.setShort("lvl", entry.getValue());
				
				enchantments.appendTag(enchantment);
			}
		}
		
		if(!enchantments.hasNoTags())
		{
			return enchantments;
		}
		
		return null;
	}
	
	public void setLevel(Enchantment enchantment, short level)
	{
		this.enchantments.put(enchantment, level);
	}
	
	public int getLevel(Enchantment enchantment)
	{
		return this.enchantments.get(enchantment);
	}
	
	@Override
	public String getTag()
	{
		return "ench";
	}
}
