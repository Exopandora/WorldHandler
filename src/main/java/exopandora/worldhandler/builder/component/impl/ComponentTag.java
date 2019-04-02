package exopandora.worldhandler.builder.component.impl;

import java.util.Collection;
import java.util.function.Function;

import javax.annotation.Nullable;

import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.builder.component.IBuilderComponent;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagLongArray;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ComponentTag<T> implements IBuilderComponent
{
	private final Function<T, INBTBase> serializer;
	private final String tag;
	private T value;
	
	public ComponentTag(String tag, T value, Function<T, INBTBase> serializer)
	{
		this.tag = tag;
		this.value = value;
		this.serializer = serializer;
	}
	
	public ComponentTag(String tag, Function<T, INBTBase> serializer)
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
	public INBTBase serialize()
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
				
				return new NBTTagString(string);
			}
			else if(this.value instanceof INBTBase)
			{
				if(this.value instanceof Collection<?>)
				{
					Collection<?> collection = (Collection<?>) this.value;
					
					if(collection.isEmpty())
					{
						return null;
					}
				}
				
				return (INBTBase) this.value;
			}
			else if(this.value instanceof Integer)
			{
				return new NBTTagInt((Integer) this.value);
			}
			else if(this.value instanceof Byte)
			{
				return new NBTTagByte((Byte) this.value);
			}
			else if(this.value instanceof Float)
			{
				return new NBTTagFloat((Float) this.value);
			}
			else if(this.value instanceof Double)
			{
				return new NBTTagDouble((Double) this.value);
			}
			else if(this.value instanceof Long)
			{
				return new NBTTagLong((Long) this.value);
			}
			else if(this.value instanceof Short)
			{
				return new NBTTagLong((Short) this.value);
			}
			else if(this.value instanceof Byte[])
			{
				return new NBTTagByteArray((byte[]) this.value);
			}
			else if(this.value instanceof Integer[])
			{
				return new NBTTagIntArray((int[]) this.value);
			}
			else if(this.value instanceof Long[])
			{
				return new NBTTagLongArray((long[]) this.value);
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
