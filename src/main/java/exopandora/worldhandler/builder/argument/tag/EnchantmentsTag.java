package exopandora.worldhandler.builder.argument.tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Nullable;

import exopandora.worldhandler.util.RegistryHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public class EnchantmentsTag implements ITagProvider, IItemComponentProvider
{
	private final Map<Enchantment, Short> enchantments = new HashMap<Enchantment, Short>();
	
	@Override
	@Nullable
	public Tag value()
	{
		ListTag enchantments = new ListTag();
		
		for(Entry<Enchantment, Short> entry : this.enchantments.entrySet())
		{
			if(entry.getValue() > 0)
			{
				CompoundTag enchantment = new CompoundTag();
				
				enchantment.putString("id", RegistryHelper.getEnchantmentKey(entry.getKey()).toString());
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
	
	public void set(Enchantment enchantment, short level)
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
	
	public short get(Enchantment enchantment)
	{
		return this.enchantments.get(enchantment);
	}
	
	public Set<Enchantment> getEnchantments()
	{
		return this.enchantments.keySet();
	}
	
	@Override
	public String key()
	{
		return "Enchantments";
	}

	@Override
	public void addItemComponents(DataComponentPatch.Builder components)
	{
		ItemEnchantments.Mutable enchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);

		for(Entry<Enchantment, Short> entry : this.enchantments.entrySet())
		{
			if(entry.getValue() > 0)
			{
				Holder<Enchantment> holder = RegistryHelper.enchantments().wrapAsHolder(entry.getKey());
				enchantments.set(holder, entry.getValue());
			}
		}

		ItemEnchantments itemEnchantments = enchantments.toImmutable();

		if(!itemEnchantments.isEmpty())
		{
			components.set(DataComponents.ENCHANTMENTS, itemEnchantments);
		}
	}
}
