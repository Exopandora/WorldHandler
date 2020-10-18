package exopandora.worldhandler.util;

import java.util.Arrays;
import java.util.List;

import exopandora.worldhandler.builder.INBTWritable;
import exopandora.worldhandler.builder.component.IBuilderComponent;
import exopandora.worldhandler.builder.component.impl.EntityNBT;
import net.minecraft.block.BlockState;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NBTHelper
{
	public static INBT serialize(BlockState blockState)
	{
		return blockState != null ? NBTUtil.writeBlockState(blockState) : null;
	}
	
	public static INBT serialize(double[] vector)
	{
		if(vector.length == 3 && (vector[0] != 0D || vector[1] != 0D || vector[2] != 0D))
		{
			ListNBT list = new ListNBT();
			
			list.add(DoubleNBT.valueOf(vector[0]));
			list.add(DoubleNBT.valueOf(vector[1]));
			list.add(DoubleNBT.valueOf(vector[2]));
			
			return list;
		}
		
		return null;
	}
	
	public static INBT serialize(ResourceLocation resource)
	{
		if(resource != null)
		{
			return StringNBT.valueOf(resource.toString());
		}
		
		return null;
	}
	
	public static INBT serialize(ResourceLocation[] itemArray)
	{
		if(Arrays.stream(itemArray).allMatch(resource -> resource != null && resource.equals(Items.AIR.getRegistryName())))
		{
			return null;
		}
		
		ListNBT list = new ListNBT();
		
		for(ResourceLocation item : itemArray)
		{
			CompoundNBT compound = new CompoundNBT();
			compound.putString("id", item.toString());
			compound.putInt("Count", 1);
			list.add(compound);
		}
		
		return list;
	}
	
	public static INBT serialize(List<EntityNBT> entities)
	{
		ListNBT list = new ListNBT();
		
		for(EntityNBT entity : entities)
		{
			INBT nbt = entity.serialize();
			
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
	
	public static void append(CompoundNBT compound, String tag, INBTWritable writable)
	{
		NBTHelper.append(compound, tag, writable.serialize());
	}
	
	public static void append(CompoundNBT compound, IBuilderComponent component)
	{
		NBTHelper.append(compound, component.getTag(), component.serialize());
	}
	
	public static void append(CompoundNBT compound, String tag, INBT nbt)
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
