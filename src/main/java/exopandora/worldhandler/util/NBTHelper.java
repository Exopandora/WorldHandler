package exopandora.worldhandler.util;

import java.util.Arrays;
import java.util.List;

import exopandora.worldhandler.builder.argument.tag.EntityTag;
import exopandora.worldhandler.builder.argument.tag.ITagProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;

public class NBTHelper
{
	public static Tag serialize(BlockState blockState)
	{
		return blockState != null ? NbtUtils.writeBlockState(blockState) : null;
	}
	
	public static Tag serialize(double[] vector)
	{
		if(vector.length == 3 && (vector[0] != 0D || vector[1] != 0D || vector[2] != 0D))
		{
			ListTag list = new ListTag();
			
			list.add(DoubleTag.valueOf(vector[0]));
			list.add(DoubleTag.valueOf(vector[1]));
			list.add(DoubleTag.valueOf(vector[2]));
			
			return list;
		}
		
		return null;
	}
	
	public static Tag serialize(ResourceLocation resource)
	{
		if(resource != null)
		{
			return StringTag.valueOf(resource.toString());
		}
		
		return null;
	}
	
	public static Tag serialize(Item[] itemArray)
	{
		if(Arrays.stream(itemArray).allMatch(Items.AIR::equals))
		{
			return null;
		}
		
		ListTag list = new ListTag();
		
		for(Item item : itemArray)
		{
			CompoundTag compound = new CompoundTag();
			compound.putString("id", item.getRegistryName().toString());
			compound.putInt("Count", 1);
			list.add(compound);
		}
		
		return list;
	}
	
	public static Tag serialize(List<EntityTag> entities)
	{
		ListTag list = new ListTag();
		
		for(EntityTag entity : entities)
		{
			Tag nbt = entity.value();
			
			if(nbt != null)
			{
				list.add(nbt);
			}
		}
		
		if(list.isEmpty())
		{
			return null;
		}
		
		return list;
	}
	
	public static void append(CompoundTag compound, ITagProvider component)
	{
		NBTHelper.append(compound, component.key(), component.value());
	}
	
	public static void append(CompoundTag compound, String tag, Tag nbt)
	{
		if(nbt != null && !compound.contains(tag))
		{
			compound.put(tag, nbt);
		}
	}
}
