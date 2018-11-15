package exopandora.worldhandler.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityHelper
{
	private static final Map<ResourceLocation, String> RESOURCELOCATION_TO_NAME = new HashMap<ResourceLocation, String>();
	private static final Map<Class<? extends Entity>, ResourceLocation> CLASS_TO_RESOURCELOCATION = new HashMap<Class<? extends Entity>, ResourceLocation>();
	
	static
	{
		if(ForgeRegistries.ENTITIES.getEntries().isEmpty())
		{
			throw new RuntimeException("Accessed Entities before register!");
		}
		
		for(Entry<ResourceLocation, EntityEntry> entity : ForgeRegistries.ENTITIES.getEntries())
		{
			RESOURCELOCATION_TO_NAME.put(entity.getKey(), entity.getValue().getName());
			CLASS_TO_RESOURCELOCATION.put(entity.getValue().getEntityClass(), entity.getKey());
		}
	}
	
	@Nullable
	public static String getUnifiedEntityName(String name)
	{
		for(ResourceLocation location : RESOURCELOCATION_TO_NAME.keySet())
		{
			if(RESOURCELOCATION_TO_NAME.get(location).equals(name))
			{
				return location.getResourcePath();
			}
		}
		
		return null;
	}
	
	public static String getEntityName(ResourceLocation location)
	{
		return RESOURCELOCATION_TO_NAME.get(location);
	}
	
	@Nullable
	public static String getEntityName(String name)
	{
		for(ResourceLocation location : RESOURCELOCATION_TO_NAME.keySet())
		{
			if(location.getResourcePath().equals(name))
			{
				return RESOURCELOCATION_TO_NAME.get(location);
			}
		}
		
		return null;
	}
	
	public static boolean doesExist(ResourceLocation key)
	{
		return RESOURCELOCATION_TO_NAME.containsKey(key);
	}
	
	public static boolean doesExist(String value)
	{
		return RESOURCELOCATION_TO_NAME.containsValue(value);
	}
	
	@Nullable
	public static ResourceLocation getResourceLocation(Class<? extends Entity> entity)
	{
		return CLASS_TO_RESOURCELOCATION.get(entity);
	}
}
