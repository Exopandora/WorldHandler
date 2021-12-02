package exopandora.worldhandler.builder.argument;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import exopandora.worldhandler.util.ResourceHelper;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

public class EntitySummonArgument implements IDeserializableArgument
{
	private static final Map<String, ResourceLocation> ALIASES = Util.make(new HashMap<String, ResourceLocation>(), map ->
	{
		map.put("RedCow", EntityType.MOOSHROOM.getRegistryName());
		map.put("ChickenJockey", EntityType.CHICKEN.getRegistryName());
		map.put("Pigman", EntityType.PIGLIN.getRegistryName());
		map.put("ZombiePig", EntityType.PIGLIN.getRegistryName());
		map.put("ZombiePigman", EntityType.PIGLIN.getRegistryName());
		map.put("Dog", EntityType.WOLF.getRegistryName());
		map.put("Dragon", EntityType.ENDER_DRAGON.getRegistryName());
		map.put("SnowMan", EntityType.SNOW_GOLEM.getRegistryName());
		map.put("LavaCube", EntityType.MAGMA_CUBE.getRegistryName());
		map.put("MagmaSlime", EntityType.MAGMA_CUBE.getRegistryName());
		map.put("LavaSlime", EntityType.MAGMA_CUBE.getRegistryName());
		map.put("SpiderJockey", EntityType.SPIDER.getRegistryName());
		map.put("VillagerGolem", EntityType.IRON_GOLEM.getRegistryName());
		map.put("Ozelot", EntityType.OCELOT.getRegistryName());
		map.put("Kitty", EntityType.CAT.getRegistryName());
		map.put("Kitten", EntityType.CAT.getRegistryName());
		map.put("TESTIFICATE", EntityType.VILLAGER.getRegistryName());
		map.put("Octopus", EntityType.SQUID.getRegistryName());
		map.put("GlowingOctopus", EntityType.SQUID.getRegistryName());
		map.put("Exwife", EntityType.GHAST.getRegistryName());
		map.put("CommandMinecart", EntityType.COMMAND_BLOCK_MINECART.getRegistryName());
		map.put("Wizard", EntityType.EVOKER.getRegistryName());
		map.put("Johnny", EntityType.VINDICATOR.getRegistryName());
		map.put("BabyZombie", EntityType.ZOMBIE.getRegistryName());
		
		ForgeRegistries.PROFESSIONS.getKeys().stream().forEach(profession -> map.put(profession.getPath(), EntityType.VILLAGER.getRegistryName()));
	});
	
	private EntityType<?> entity;
	
	protected EntitySummonArgument()
	{
		super();
	}
	
	public void set(@Nullable EntityType<?> entity)
	{
		if(entity != null && entity.canSummon())
		{
			this.entity = entity;
		}
		else
		{
			this.entity = null;
		}
	}
	
	public void set(@Nullable ResourceLocation entity)
	{
		if(entity != null)
		{
			EntityType<?> type = ForgeRegistries.ENTITIES.getValue(entity);
			
			if(!ForgeRegistries.ENTITIES.getDefaultKey().equals(type.getRegistryName()) || type.getRegistryName().equals(entity))
			{
				this.set(type);
			}
			else
			{
				this.entity = null;
			}
		}
		else
		{
			this.entity = null;
		}
	}
	
	@Nullable
	public EntityType<?> getEntity()
	{
		return this.entity;
	}
	
	@Override
	public void deserialize(@Nullable String entity)
	{
		if(entity == null)
		{
			this.entity = null;
		}
		else
		{
			String stripped = entity.replace(" ", "");
			
			for(Entry<String, ResourceLocation> entry : ALIASES.entrySet())
			{
				if(entry.getKey().equalsIgnoreCase(stripped))
				{
					this.set(entry.getValue());
					return;
				}
			}
			
			this.set(ResourceHelper.stringToResourceLocation(entity));
		}
	}
	
	@Override
	@Nullable
	public String serialize()
	{
		if(this.entity == null)
		{
			return null;
		}
		
		return this.entity.getRegistryName().toString();
	}
	
	@Override
	public boolean isDefault()
	{
		return this.entity == null;
	}
}
