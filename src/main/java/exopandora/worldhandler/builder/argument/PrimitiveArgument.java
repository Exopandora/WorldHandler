package exopandora.worldhandler.builder.argument;

import java.util.function.Function;

import javax.annotation.Nullable;

public class PrimitiveArgument<T> implements IDeserializableArgument
{
	private T value;
	private final Function<T, Boolean> defaultOverride;
	private final Function<String, T> parser;
	private final Function<T, String> serializer;
	
	protected PrimitiveArgument(Function<T, Boolean> defaultOverride, Function<String, T> parser, Function<T, String> serializer)
	{
		this.defaultOverride = defaultOverride;
		this.parser = parser;
		this.serializer = serializer;
	}
	
	public void set(@Nullable T value)
	{
		this.value = value;
	}
	
	@Nullable
	public T get()
	{
		return this.value;
	}
	
	@Override
	public void deserialize(@Nullable String string)
	{
		if(string == null)
		{
			this.value = null;
		}
		else
		{
			this.value = this.parser.apply(string);
		}
	}
	
	@Override
	@Nullable
	public String serialize()
	{
		if(this.value == null)
		{
			return null;
		}
		else if(this.serializer != null)
		{
			return this.serializer.apply(this.value);
		}
		
		return this.value.toString();
	}
	
	@Override
	public boolean isDefault()
	{
		if(this.defaultOverride != null)
		{
			return this.defaultOverride.apply(this.value);
		}
		
		return this.value == null;
	}
	
	public static <T> Builder<T> builder(Function<String, T> deserializer)
	{
		return new Builder<T>(deserializer);
	}
	
	public static class Builder<T>
	{
		private Function<T, Boolean> defaultOverride;
		private Function<String, T> deserializer;
		private Function<T, String> serializer;
		
		private Builder(Function<String, T> deserializer)
		{
			this.deserializer = deserializer;
		}
		
		public Builder<T> defaultOverride(Function<T, Boolean> defaultOverride)
		{
			this.defaultOverride = defaultOverride;
			return this;
		}
		
		public Builder<T> serializer(Function<T, String> serializer)
		{
			this.serializer = serializer;
			return this;
		}
		
		public PrimitiveArgument<T> build()
		{
			return new PrimitiveArgument<T>(this.defaultOverride, this.deserializer, this.serializer);
		}
	}
	
	public static enum Operation
	{
		SET("="),
		ADD("+="),
		SUB("-="),
		MUL("*="),
		DIV("/="),
		MOD("%="),
		LESS_THAN("<"),
		GREATER_THAN(">");
		
		private final String operator;
		
		private Operation(String operator)
		{
			this.operator = operator;
		}
		
		@Override
		public String toString()
		{
			return this.operator;
		}
	}
	
	public static enum Relation
	{
		LT("<"),
		LE("<="),
		EQ("="),
		GE(">="),
		GT(">");
		
		private final String operator;
		
		private Relation(String operator)
		{
			this.operator = operator;
		}
		
		@Override
		public String toString()
		{
			return this.operator;
		}
	}
	
	public static enum Type
	{
		BYTE,
		DOUBLE,
		FLOAT,
		INT,
		LONG,
		SHORT;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
	
	public static enum Linkage
	{
		APPEND,
		INSERT,
		MERGE,
		PREPEND,
		SET;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
}
