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
		this.min = Optional.ofNullable(value);
		this.max = Optional.ofNullable(value);
	}
	
	public void setRange(@Nullable T min, @Nullable T max)
	{
		this.min = Optional.ofNullable(min);
		this.max = Optional.ofNullable(max);
	}
	
	public void setMin(@Nullable T min)
	{
		this.min = Optional.ofNullable(min);
	}
	
	public void setMax(@Nullable T max)
	{
		this.max = Optional.ofNullable(max);
	}
	
	@Nullable
	public T getMin()
	{
		return this.min.orElse(null);
	}
	
	@Nullable
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
		return Util.serializeBounds(this.getMin(), this.getMax());
	}
	
	@Override
	public boolean isDefault()
	{
		return this.min.isEmpty() && this.max.isEmpty();
	}
}
