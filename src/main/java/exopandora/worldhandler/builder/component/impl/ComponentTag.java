package exopandora.worldhandler.builder.component.impl;

import java.util.Collection;
import java.util.function.Function;

import javax.annotation.Nullable;

import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.builder.component.IBuilderComponent;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;

public class ComponentTag<T> implements IBuilderComponent
{
	private final Function<T, Tag> serializer;
	private final String tag;
	private T value;
	
	public ComponentTag(String tag, T value, Function<T, Tag> serializer)
	{
		this.tag = tag;
		this.value = value;
		this.serializer = serializer;
	}
	
	public ComponentTag(String tag, Function<T, Tag> serializer)
	{
		this(tag, null, serializer);
	}
	
	public ComponentTag(String tag, T value)
	{
		this(tag, value, null);
	}
	
	public ComponentTag(String tag)
	{
		this(tag, null, null);
	}
	
	public void setValue(T value)
	{
		this.value = value;
	}
	
	@Nullable
	public T getValue()
	{
		return this.value;
	}
	
	@Override
	@Nullable
	public Tag serialize()
	{
		if(this.value != null)
		{
			if(this.serializer != null)
			{
				return this.serializer.apply(this.value);
			}
			else if(this.value instanceof String)
			{
				String string = (String) this.value;
				
				if(string.isEmpty())
				{
					return null;
				}
				
				return StringTag.valueOf(string);
			}
			else if(this.value instanceof Tag)
			{
				if(this.value instanceof Collection<?>)
				{
					Collection<?> collection = (Collection<?>) this.value;
					
					if(collection.isEmpty())
					{
						return null;
					}
				}
				
				return (Tag) this.value;
			}
			else if(this.value instanceof Integer)
			{
				return IntTag.valueOf((Integer) this.value);
			}
			else if(this.value instanceof Byte)
			{
				return ByteTag.valueOf((Byte) this.value);
			}
			else if(this.value instanceof Float)
			{
				return FloatTag.valueOf((Float) this.value);
			}
			else if(this.value instanceof Double)
			{
				return DoubleTag.valueOf((Double) this.value);
			}
			else if(this.value instanceof Long)
			{
				return LongTag.valueOf((Long) this.value);
			}
			else if(this.value instanceof Short)
			{
				return ShortTag.valueOf((Short) this.value);
			}
			else if(this.value instanceof Byte[])
			{
				return new ByteArrayTag((byte[]) this.value);
			}
			else if(this.value instanceof Integer[])
			{
				return new IntArrayTag((int[]) this.value);
			}
			else if(this.value instanceof Long[])
			{
				return new LongArrayTag((long[]) this.value);
			}
			else
			{
				WorldHandler.LOGGER.warn("Tag \"" + this.tag + "\" has no serializer");
			}
		}
		
		return null;
	}
	
	@Override
	public String getTag()
	{
		return this.tag;
	}
}
