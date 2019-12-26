package exopandora.worldhandler.builder.component.impl;

import java.util.Collection;
import java.util.function.Function;

import javax.annotation.Nullable;

import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.builder.component.IBuilderComponent;
import net.minecraft.nbt.ByteArrayNBT;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.LongArrayNBT;
import net.minecraft.nbt.LongNBT;
import net.minecraft.nbt.ShortNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ComponentTag<T> implements IBuilderComponent
{
	private final Function<T, INBT> serializer;
	private final String tag;
	private T value;
	
	public ComponentTag(String tag, T value, Function<T, INBT> serializer)
	{
		this.tag = tag;
		this.value = value;
		this.serializer = serializer;
	}
	
	public ComponentTag(String tag, Function<T, INBT> serializer)
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
	public INBT serialize()
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
				
				return StringNBT.func_229705_a_(string);
			}
			else if(this.value instanceof INBT)
			{
				if(this.value instanceof Collection<?>)
				{
					Collection<?> collection = (Collection<?>) this.value;
					
					if(collection.isEmpty())
					{
						return null;
					}
				}
				
				return (INBT) this.value;
			}
			else if(this.value instanceof Integer)
			{
				return IntNBT.func_229692_a_((Integer) this.value);
			}
			else if(this.value instanceof Byte)
			{
				return ByteNBT.func_229671_a_((Byte) this.value);
			}
			else if(this.value instanceof Float)
			{
				return FloatNBT.func_229689_a_((Float) this.value);
			}
			else if(this.value instanceof Double)
			{
				return DoubleNBT.func_229684_a_((Double) this.value);
			}
			else if(this.value instanceof Long)
			{
				return LongNBT.func_229698_a_((Long) this.value);
			}
			else if(this.value instanceof Short)
			{
				return ShortNBT.func_229701_a_((Short) this.value);
			}
			else if(this.value instanceof Byte[])
			{
				return new ByteArrayNBT((byte[]) this.value);
			}
			else if(this.value instanceof Integer[])
			{
				return new IntArrayNBT((int[]) this.value);
			}
			else if(this.value instanceof Long[])
			{
				return new LongArrayNBT((long[]) this.value);
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
