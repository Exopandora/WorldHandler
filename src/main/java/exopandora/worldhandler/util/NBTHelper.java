package exopandora.worldhandler.util;

import java.util.Arrays;
import java.util.List;

import exopandora.worldhandler.builder.INBTWritable;
import exopandora.worldhandler.builder.component.IBuilderComponent;
import exopandora.worldhandler.builder.component.impl.EntityNBT;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
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
	
	public static Tag serialize(ResourceLocation[] itemArray)
	{
		if(Arrays.stream(itemArray).allMatch(resource -> resource != null && resource.equals(Items.AIR.getRegistryName())))
		{
			return null;
		}
		
		ListTag list = new ListTag();
		
		for(ResourceLocation item : itemArray)
		{
			CompoundTag compound = new CompoundTag();
			compound.putString("id", item.toString());
			compound.putInt("Count", 1);
			list.add(compound);
		}
		
		return list;
	}
	
	public static Tag serialize(List<EntityNBT> entities)
	{
		ListTag list = new ListTag();
		
		for(EntityNBT entity : entities)
		{
			Tag nbt = entity.serialize();
			
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
	
	public static void append(CompoundTag compound, String tag, INBTWritable writable)
	{
		NBTHelper.append(compound, tag, writable.serialize());
	}
	
	public static void append(CompoundTag compound, IBuilderComponent component)
	{
		NBTHelper.append(compound, component.getTag(), component.serialize());
	}
	
	public static void append(CompoundTag compound, String tag, Tag nbt)
	{
		if(nbt != null)
		{
			if(!compound.contains(tag))
			{
				compound.put(tag, nbt);
			}
		}
	}
}
