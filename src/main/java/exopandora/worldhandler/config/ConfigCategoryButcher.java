package exopandora.worldhandler.config;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Predicates;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.registries.ForgeRegistries;

public class ConfigCategoryButcher
{
	private final ConfigValue<List<? extends String>> entities;
	
	public ConfigCategoryButcher(ForgeConfigSpec.Builder builder)
	{
		builder.push("butcher");
		
		this.entities = builder.defineList("entities", Collections.<String>emptyList(), this::isValid);
		
		builder.pop();
	}
	
	public List<ResourceLocation> getEntities()
	{
		return this.entities.get().stream().map(ResourceLocation::tryParse).filter(Predicates.notNull()).collect(Collectors.toList());
	}
	
	public boolean containsEntity(ResourceLocation entity)
	{
		return this.getEntities().contains(entity);
	}
	
	public void addEntity(ResourceLocation entity)
	{
		if(this.isValid(entity))
		{
			@SuppressWarnings("unchecked")
			List<String> entities = (List<String>) this.entities.get();
			entities.add(entity.toString());
			Config.set(this.entities, entities);
		}
	}
	
	public boolean removeEntity(ResourceLocation entity)
	{
		@SuppressWarnings("unchecked")
		List<String> entities = (List<String>) this.entities.get();
		boolean removed = entities.remove(entity.toString());
		
		if(removed)
		{
			Config.set(this.entities, entities);
		}
		
		return removed;
	}
	
	private boolean isValid(Object string)
	{
		if(string != null)
		{
			return ForgeRegistries.ENTITIES.containsKey(ResourceLocation.tryParse(string.toString()));
		}
		
		return false;
	}
}
