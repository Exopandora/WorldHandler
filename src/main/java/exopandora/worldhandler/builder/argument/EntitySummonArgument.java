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
		map.put("RedCow", ForgeRegistries.ENTITIES.getKey(EntityType.MOOSHROOM));
		map.put("ChickenJockey", ForgeRegistries.ENTITIES.getKey(EntityType.CHICKEN));
		map.put("Pigman", ForgeRegistries.ENTITIES.getKey(EntityType.PIGLIN));
		map.put("ZombiePig", ForgeRegistries.ENTITIES.getKey(EntityType.PIGLIN));
		map.put("ZombiePigman", ForgeRegistries.ENTITIES.getKey(EntityType.PIGLIN));
		map.put("Dog", ForgeRegistries.ENTITIES.getKey(EntityType.WOLF));
		map.put("Dragon", ForgeRegistries.ENTITIES.getKey(EntityType.ENDER_DRAGON));
		map.put("SnowMan", ForgeRegistries.ENTITIES.getKey(EntityType.SNOW_GOLEM));
		map.put("LavaCube", ForgeRegistries.ENTITIES.getKey(EntityType.MAGMA_CUBE));
		map.put("MagmaSlime", ForgeRegistries.ENTITIES.getKey(EntityType.MAGMA_CUBE));
		map.put("LavaSlime", ForgeRegistries.ENTITIES.getKey(EntityType.MAGMA_CUBE));
		map.put("SpiderJockey", ForgeRegistries.ENTITIES.getKey(EntityType.SPIDER));
		map.put("VillagerGolem", ForgeRegistries.ENTITIES.getKey(EntityType.IRON_GOLEM));
		map.put("Ozelot", ForgeRegistries.ENTITIES.getKey(EntityType.OCELOT));
		map.put("Kitty", ForgeRegistries.ENTITIES.getKey(EntityType.CAT));
		map.put("Kitten", ForgeRegistries.ENTITIES.getKey(EntityType.CAT));
		map.put("TESTIFICATE", ForgeRegistries.ENTITIES.getKey(EntityType.VILLAGER));
		map.put("Octopus", ForgeRegistries.ENTITIES.getKey(EntityType.SQUID));
		map.put("GlowingOctopus", ForgeRegistries.ENTITIES.getKey(EntityType.SQUID));
		map.put("Exwife", ForgeRegistries.ENTITIES.getKey(EntityType.GHAST));
		map.put("CommandMinecart", ForgeRegistries.ENTITIES.getKey(EntityType.COMMAND_BLOCK_MINECART));
		map.put("Wizard", ForgeRegistries.ENTITIES.getKey(EntityType.EVOKER));
		map.put("Johnny", ForgeRegistries.ENTITIES.getKey(EntityType.VINDICATOR));
		map.put("BabyZombie", ForgeRegistries.ENTITIES.getKey(EntityType.ZOMBIE));
		
		ForgeRegistries.PROFESSIONS.getEntries().stream().forEach(profession -> map.put(profession.getKey().location().getPath(), profession.getKey().location()));
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
			ResourceLocation location = ForgeRegistries.ENTITIES.getKey(type);
			
			if(!ForgeRegistries.ENTITIES.getDefaultKey().equals(location) || location.equals(entity))
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
		
		return ForgeRegistries.ENTITIES.getKey(this.entity).toString();
	}
	
	@Override
	public boolean isDefault()
	{
		return this.entity == null;
	}
}
