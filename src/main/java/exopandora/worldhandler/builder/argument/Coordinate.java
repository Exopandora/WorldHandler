package exopandora.worldhandler.builder.argument;

import javax.annotation.Nullable;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.arguments.coordinates.LocalCoordinates;
import net.minecraft.commands.arguments.coordinates.WorldCoordinate;

public abstract class Coordinate<T extends Number>
{
	protected T value;
	protected Type type;
	
	protected Coordinate(T value)
	{
		this(value, Type.ABSOLUTE);
	}
	
	protected Coordinate(T value, Type type)
	{
		this.value = value;
		this.type = type;
	}
	
	public void setValue(T value)
	{
		this.value = value;
	}
	
	public T getValue()
	{
		return this.value;
	}
	
	public void setType(Type type)
	{
		this.type = type;
	}
	
	public Type getType()
	{
		return this.type;
	}
	
	@Nullable
	public String serialize()
	{
		if(this.value == null)
		{
			return null;
		}
		
		if(this.zero().equals(this.value) && !Type.ABSOLUTE.equals(this.type))
		{
			return this.type.getPrefix();
		}
		
		return this.type.getPrefix() + this.value.toString();
	}
	
	public abstract T zero();
	
	public static class Ints extends Coordinate<Integer>
	{
		public static final Ints ZERO = new Ints();
		
		public Ints()
		{
			super(0);
		}
		
		public Ints(Integer value)
		{
			super(value);
		}
		
		public Ints(Type type)
		{
			super(0, type);
		}
		
		public Ints(Integer value, Type type)
		{
			super(value, type);
		}
		
		@Override
		public Integer zero()
		{
			return 0;
		}
		
		@Nullable
		public static Ints parse(String string)
		{
			try
			{
				StringReader reader = new StringReader(string);
				
				if(reader.canRead() && reader.peek() == '^')
				{
					return new Coordinate.Ints((int) LocalCoordinates.readDouble(reader, 0), Coordinate.Type.LOCAL);
				}
				else
				{
					WorldCoordinate wc = WorldCoordinate.parseInt(reader);
					return new Coordinate.Ints((int) wc.get(0), wc.isRelative() ? Coordinate.Type.RELATIVE : Coordinate.Type.ABSOLUTE);
				}
			}
			catch(CommandSyntaxException e)
			{
				return null;
			}
		}
	}
	
	public static class Doubles extends Coordinate<Double>
	{
		public static final Doubles ZERO = new Doubles();
		
		public Doubles()
		{
			super(0.0D);
		}
		
		public Doubles(Double value)
		{
			super(value);
		}
		
		public Doubles(Type type)
		{
			super(0.0D, type);
		}
		
		public Doubles(Double value, Type type)
		{
			super(value, type);
		}
		
		@Override
		public Double zero()
		{
			return 0.0D;
		}
		
		@Nullable
		public static Doubles parse(String string)
		{
			try
			{
				StringReader reader = new StringReader(string);
				
				if(reader.canRead() && reader.peek() == '^')
				{
					return new Coordinate.Doubles(LocalCoordinates.readDouble(reader, 0), Coordinate.Type.LOCAL);
				}
				else
				{
					WorldCoordinate wc = WorldCoordinate.parseInt(reader);
					return new Coordinate.Doubles(wc.get(0), wc.isRelative() ? Coordinate.Type.RELATIVE : Coordinate.Type.ABSOLUTE);
				}
			}
			catch(CommandSyntaxException e)
			{
				return null;
			}
		}
	}
	
	public static enum Type
	{
		ABSOLUTE(""),
		RELATIVE("~"),
		LOCAL("^");
		
		private final String prefix;
		
		private Type(String prefix)
		{
			this.prefix = prefix;
		}
		
		public String getPrefix()
		{
			return this.prefix;
		}
	}
}
