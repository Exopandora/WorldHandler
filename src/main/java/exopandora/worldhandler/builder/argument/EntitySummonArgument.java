package exopandora.worldhandler.builder.argument;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import exopandora.worldhandler.util.ResourceHelper;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.core.registries.BuiltInRegistries;

public class EntitySummonArgument implements IDeserializableArgument
{
	private static final Map<String, ResourceLocation> ALIASES = Util.make(new HashMap<String, ResourceLocation>(), map ->
	{
		map.put("RedCow", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.MOOSHROOM));
		map.put("ChickenJockey", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.CHICKEN));
		map.put("Pigman", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.PIGLIN));
		map.put("ZombiePig", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.PIGLIN));
		map.put("ZombiePigman", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.PIGLIN));
		map.put("Dog", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.WOLF));
		map.put("Dragon", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.ENDER_DRAGON));
		map.put("SnowMan", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.SNOW_GOLEM));
		map.put("LavaCube", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.MAGMA_CUBE));
		map.put("MagmaSlime", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.MAGMA_CUBE));
		map.put("LavaSlime", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.MAGMA_CUBE));
		map.put("SpiderJockey", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.SPIDER));
		map.put("VillagerGolem", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.IRON_GOLEM));
		map.put("Ozelot", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.OCELOT));
		map.put("Kitty", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.CAT));
		map.put("Kitten", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.CAT));
		map.put("TESTIFICATE", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.VILLAGER));
		map.put("Octopus", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.SQUID));
		map.put("GlowingOctopus", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.SQUID));
		map.put("Exwife", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.GHAST));
		map.put("CommandMinecart", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.COMMAND_BLOCK_MINECART));
		map.put("Wizard", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.EVOKER));
		map.put("Johnny", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.VINDICATOR));
		map.put("BabyZombie", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.ZOMBIE));
		
		BuiltInRegistries.VILLAGER_PROFESSION.entrySet().stream().forEach(profession -> map.put(profession.getKey().location().getPath(), profession.getKey().location()));
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
			EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(entity);
			ResourceLocation location = BuiltInRegistries.ENTITY_TYPE.getKey(type);
			
			if(!BuiltInRegistries.ENTITY_TYPE.getDefaultKey().equals(location) || location.equals(entity))
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
		
		return BuiltInRegistries.ENTITY_TYPE.getKey(this.entity).toString();
	}
	
	@Override
	public boolean isDefault()
	{
		return this.entity == null;
	}
}
