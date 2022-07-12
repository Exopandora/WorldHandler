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
		map.put("RedCow", ForgeRegistries.ENTITY_TYPES.getKey(EntityType.MOOSHROOM));
		map.put("ChickenJockey", ForgeRegistries.ENTITY_TYPES.getKey(EntityType.CHICKEN));
		map.put("Pigman", ForgeRegistries.ENTITY_TYPES.getKey(EntityType.PIGLIN));
		map.put("ZombiePig", ForgeRegistries.ENTITY_TYPES.getKey(EntityType.PIGLIN));
		map.put("ZombiePigman", ForgeRegistries.ENTITY_TYPES.getKey(EntityType.PIGLIN));
		map.put("Dog", ForgeRegistries.ENTITY_TYPES.getKey(EntityType.WOLF));
		map.put("Dragon", ForgeRegistries.ENTITY_TYPES.getKey(EntityType.ENDER_DRAGON));
		map.put("SnowMan", ForgeRegistries.ENTITY_TYPES.getKey(EntityType.SNOW_GOLEM));
		map.put("LavaCube", ForgeRegistries.ENTITY_TYPES.getKey(EntityType.MAGMA_CUBE));
		map.put("MagmaSlime", ForgeRegistries.ENTITY_TYPES.getKey(EntityType.MAGMA_CUBE));
		map.put("LavaSlime", ForgeRegistries.ENTITY_TYPES.getKey(EntityType.MAGMA_CUBE));
		map.put("SpiderJockey", ForgeRegistries.ENTITY_TYPES.getKey(EntityType.SPIDER));
		map.put("VillagerGolem", ForgeRegistries.ENTITY_TYPES.getKey(EntityType.IRON_GOLEM));
		map.put("Ozelot", ForgeRegistries.ENTITY_TYPES.getKey(EntityType.OCELOT));
		map.put("Kitty", ForgeRegistries.ENTITY_TYPES.getKey(EntityType.CAT));
		map.put("Kitten", ForgeRegistries.ENTITY_TYPES.getKey(EntityType.CAT));
		map.put("TESTIFICATE", ForgeRegistries.ENTITY_TYPES.getKey(EntityType.VILLAGER));
		map.put("Octopus", ForgeRegistries.ENTITY_TYPES.getKey(EntityType.SQUID));
		map.put("GlowingOctopus", ForgeRegistries.ENTITY_TYPES.getKey(EntityType.SQUID));
		map.put("Exwife", ForgeRegistries.ENTITY_TYPES.getKey(EntityType.GHAST));
		map.put("CommandMinecart", ForgeRegistries.ENTITY_TYPES.getKey(EntityType.COMMAND_BLOCK_MINECART));
		map.put("Wizard", ForgeRegistries.ENTITY_TYPES.getKey(EntityType.EVOKER));
		map.put("Johnny", ForgeRegistries.ENTITY_TYPES.getKey(EntityType.VINDICATOR));
		map.put("BabyZombie", ForgeRegistries.ENTITY_TYPES.getKey(EntityType.ZOMBIE));
		
		ForgeRegistries.VILLAGER_PROFESSIONS.getEntries().stream().forEach(profession -> map.put(profession.getKey().location().getPath(), profession.getKey().location()));
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
			EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(entity);
			ResourceLocation location = ForgeRegistries.ENTITY_TYPES.getKey(type);
			
			if(!ForgeRegistries.ENTITY_TYPES.getDefaultKey().equals(location) || location.equals(entity))
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
		
		return ForgeRegistries.ENTITY_TYPES.getKey(this.entity).toString();
	}
	
	@Override
	public boolean isDefault()
	{
		return this.entity == null;
	}
}
