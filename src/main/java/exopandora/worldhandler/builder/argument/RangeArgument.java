package exopandora.worldhandler.builder.argument;

import java.util.Optional;
import java.util.function.Function;

import javax.annotation.Nullable;

import exopandora.worldhandler.util.Util;
import net.minecraft.advancements.critereon.MinMaxBounds;

public class RangeArgument<T extends Number> implements IDeserializableArgument
{
	private final Function<String, MinMaxBounds<T>> parser;
	private Optional<T> min;
	private Optional<T> max;
	
	protected RangeArgument(Function<String, MinMaxBounds<T>> parser)
	{
		this.parser = parser;
	}
	
	public void setExact(@Nullable T value)
	{
		this.min = Optional.of(value);
		this.max = Optional.of(value);
	}
	
	public void setRange(@Nullable T min, @Nullable T max)
	{
		this.min = Optional.of(min);
		this.max = Optional.of(max);
	}
	
	public void setMin(@Nullable T min)
	{
		this.min = Optional.of(min);
	}
	
	public void setMax(@Nullable T max)
	{
		this.max = Optional.of(max);
	}
	
	public T getMin()
	{
		return this.min.orElse(null);
	}
	
	public T getMax()
	{
		return this.max.orElse(null);
	}
	
	@Override
	public void deserialize(@Nullable String string)
	{
		if(string != null)
		{
			MinMaxBounds<T> bounds = this.parser.apply(string);
			this.min = bounds.min();
			this.max = bounds.max();
		}
		else
		{
			this.min = Optional.empty();
			this.max = Optional.empty();
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
