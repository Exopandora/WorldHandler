package exopandora.worldhandler.builder.argument;

import java.util.function.Function;

import javax.annotation.Nullable;

import exopandora.worldhandler.util.Util;
import net.minecraft.advancements.critereon.MinMaxBounds;

public class RangeArgument<T extends Number> implements IDeserializableArgument
{
	private final Function<String, MinMaxBounds<T>> parser;
	private T min;
	private T max;
	
	protected RangeArgument(Function<String, MinMaxBounds<T>> parser)
	{
		this.parser = parser;
	}
	
	public void setExact(@Nullable T value)
	{
		this.min = value;
		this.max = value;
	}
	
	public void setRange(@Nullable T min, @Nullable T max)
	{
		this.min = min;
		this.max = max;
	}
	
	public void setMin(@Nullable T min)
	{
		this.min = min;
	}
	
	public void setMax(@Nullable T max)
	{
		this.max = max;
	}
	
	public T getMin()
	{
		return this.min;
	}
	
	public T getMax()
	{
		return this.max;
	}
	
	@Override
	public void deserialize(@Nullable String string)
	{
		if(string != null)
		{
			MinMaxBounds<T> bounds = this.parser.apply(string);
			
			if(bounds != null)
			{
				this.min = bounds.getMin();
				this.max = bounds.getMax();
			}
			else
			{
				this.min = null;
				this.max = null;
			}
		}
		else
		{
			this.min = null;
			this.max = null;
		}
	}
	
	@Override
	@Nullable
	public String serialize()
	{
		return Util.serializeBounds(this.min, this.max);
	}
	
	@Override
	public boolean isDefault()
	{
		return this.min == null && this.max == null;
	}
}
