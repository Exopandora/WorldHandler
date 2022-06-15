package exopandora.worldhandler.builder.argument;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import exopandora.worldhandler.util.BlockPredicateParser;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockPredicateArgument extends TagArgument
{
	private ResourceLocation resource;
	private boolean isTag;
	private Map<String, String> properties;
	
	protected BlockPredicateArgument()
	{
		super();
	}
	
	public void set(@Nullable ResourceLocation resource)
	{
		this.resource = resource;
	}
	
	public void set(@Nullable BlockState state)
	{
		if(state != null)
		{
			this.resource = ForgeRegistries.BLOCKS.getKey(state.getBlock());
			this.properties = propertiesToString(state.getValues());
		}
		else
		{
			this.resource = null;
			this.properties = null;
		}
		
		this.isTag = false;
	}
	
	public void set(@Nullable Map<Property<?>, Comparable<?>> properties)
	{
		if(properties != null)
		{
			this.properties = propertiesToString(properties);
		}
		else
		{
			this.properties = null;
		}
	}
	
	public void set(Property<?> key, @Nullable Comparable<?> value)
	{
		String name = key.getName();
		
		if(value == null)
		{
			if(this.properties != null && this.properties.containsKey(name))
			{
				this.properties.remove(name);
			}
		}
		else
		{
			if(this.properties == null)
			{
				this.properties = new HashMap<String, String>();
			}
			
			this.properties.put(name, getName(key, value));
		}
	}
	
	public void set(@Nullable BlockState state, CompoundTag tag)
	{
		this.set(state);
		this.setTag(tag);
		this.isTag = false;
	}
	
	public void set(boolean isTag)
	{
		this.isTag = isTag;
	}
	
	@Nullable
	public ResourceLocation getResourceLocation()
	{
		return this.resource;
	}
	
	public boolean isTag()
	{
		return this.isTag;
	}
	
	public Map<String, String> getProperties()
	{
		if(this.properties == null)
		{
			return Collections.emptyMap();
		}
		
		return Collections.unmodifiableMap(this.properties);
	}
	
	@Override
	public void deserialize(@Nullable String predicate)
	{
		if(predicate == null)
		{
			this.reset();
		}
		else
		{
			try
			{
				BlockPredicateParser parser = new BlockPredicateParser(predicate);
				parser.parse(true);
				this.resource = parser.getResourceLocation();
				this.isTag = parser.isTag();
				this.properties = parser.getVagueProperties();
				this.setTag(parser.getNbt());
			}
			catch(CommandSyntaxException e)
			{
				this.reset();
			}
		}
	}
    
	private void reset()
	{
		this.resource = null;
		this.properties = null;
		this.isTag = false;
		this.setTag(null);
	}
	
	@Override
	@Nullable
	public String serialize()
	{
		if(this.resource == null || this.resource.getPath().isEmpty())
		{
			return null;
		}
		
		StringBuilder builder = new StringBuilder();
		
		if(this.isTag)
		{
			builder.append('#');
		}
		
		builder.append(this.resource.toString());
		
		if(this.properties != null && !this.properties.isEmpty())
		{
			builder.append('[');
			builder.append(this.properties.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining(",")));
			builder.append(']');
		}
		
		String nbt = super.serialize();
		
		if(nbt != null)
		{
			builder.append(nbt);
		}
		
		return builder.toString();
	}
	
	@Override
	public boolean isDefault()
	{
		return super.isDefault() && this.resource == null && (this.properties == null || this.properties.isEmpty());
	}
	
	private static Map<String, String> propertiesToString(Map<Property<?>, Comparable<?>> properties)
	{
		return properties.entrySet().stream().map(entry ->
		{
			Property<?> property = entry.getKey();
			return new SimpleEntry<String, String>(property.getName(), getName(property, entry.getValue()));
		}).collect(Collectors.toMap(Entry::getKey, Entry::getValue));
	}
	
	@SuppressWarnings("unchecked")
	private static <T extends Comparable<T>> String getName(Property<T> key, Comparable<?> value)
	{
		return key.getName((T) value);
	}
}
