package exopandora.worldhandler.builder.types;

public class CoordinateInt extends Coordinate<Integer>
{
	public CoordinateInt()
	{
		super(0);
	}
	
	public CoordinateInt(Integer value)
	{
		super(value);
	}
	
	public CoordinateInt(EnumType type)
	{
		super(0, type);
	}
	
	public CoordinateInt(Integer value, EnumType type)
	{
		super(value, type);
	}
	
	public static CoordinateInt valueOf(String value)
	{
		return Coordinate.parse(new CoordinateInt(), value, Integer::parseInt);
	}
	
	@Override
	public Integer zero()
	{
		return 0;
	}
}
