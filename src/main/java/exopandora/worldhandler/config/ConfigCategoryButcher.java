package exopandora.worldhandler.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
public class ConfigCategoryButcher
{
	private final List<String> entities = new ArrayList<String>();
	
	private final ConfigValue<List<? extends String>> valueEntities;
	
	public ConfigCategoryButcher(ForgeConfigSpec.Builder builder)
	{
		builder.push("butcher");
		
		this.valueEntities = builder.defineList("entities", Collections.<String>emptyList(), this::isValid);
		
		builder.pop();
	}
	
	public void read()
	{
		this.entities.clear();
		this.entities.addAll(this.valueEntities.get());
	}
	
	private void write()
	{
		Config.set(this.valueEntities, this.entities);
	}
	
	public List<ResourceLocation> getEntities()
	{
		List<ResourceLocation> list = new ArrayList<ResourceLocation>();
		
		for(String entity : this.entities)
		{
			ResourceLocation resource = ResourceLocation.tryCreate(entity);
			
			if(resource != null)
			{
				list.add(resource);
			}
		}
		
		return list;
	}
	
	public boolean containsEntity(ResourceLocation entity)
	{
		return this.entities.contains(entity.toString());
	}
	
	public void addEntity(ResourceLocation entity)
	{
		if(this.isValid(entity))
		{
			this.entities.add(entity.toString());
			this.write();
		}
	}
	
	public boolean removeEntity(ResourceLocation entity)
	{
		boolean removed = this.entities.remove(entity.toString());
		
		if(removed)
		{
			this.write();
		}
		
		return removed;
	}
	
	private boolean isValid(Object string)
	{
		if(string != null)
		{
			return ForgeRegistries.ENTITIES.containsKey(ResourceLocation.tryCreate(string.toString()));
		}
		
		return false;
	}
}
