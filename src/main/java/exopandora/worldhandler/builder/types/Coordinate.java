package exopandora.worldhandler.builder.types;

import java.util.function.Function;

import javax.annotation.Nullable;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class Coordinate<T extends Number> implements ICoordinate<T>
{
	protected T value;
	protected EnumType type;
	
	public Coordinate(T value)
	{
		this(value, EnumType.ABSOLUTE);
	}
	
	public Coordinate(T value, EnumType type)
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
	
	public void setType(EnumType type)
	{
		this.type = type;
	}
	
	public EnumType getType()
	{
		return this.type;
	}
	
	@Override
	public String toString()
	{
		return this.type.format(this.value, this.zero());
	}
	
	@Nullable
	public static <S extends Number, U extends Coordinate<S>> U parse(U coordiante, String input, Function<String, S> parser)
	{
		for(EnumType type : EnumType.values())
		{
			if(!type.prefix.isEmpty() && input.startsWith(type.prefix))
			{
				String numbers = input.substring(type.prefix.length());
				
				coordiante.setType(type);
				coordiante.setValue(numbers.isEmpty() ? coordiante.zero() : parser.apply(numbers));
				
				return coordiante;
			}
		}
		
		coordiante.setType(EnumType.ABSOLUTE);
		coordiante.setValue(parser.apply(input));
		
		return coordiante;
	}
	
	@OnlyIn(Dist.CLIENT)
	public static enum EnumType
	{
		ABSOLUTE(""),
		GLOBAL("~"),
		LOCAL("^");
		
		private final String prefix;
		
		private EnumType(String prefix)
		{
			this.prefix = prefix;
		}
		
		public <T extends Number> String format(T value, T zero)
		{
			if(value == null || value.equals(zero))
			{
				if(this.prefix.isEmpty())
				{
					return zero.toString();
				}
				
				return this.prefix;
			}
			
			return this.prefix + value;
		}
	}
}
