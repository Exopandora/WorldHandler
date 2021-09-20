package exopandora.worldhandler.builder.types;

public class CoordinateDouble extends Coordinate<Double>
{
	public CoordinateDouble()
	{
		super(0.0);
	}
	
	public CoordinateDouble(Double value)
	{
		super(value);
	}
	
	public CoordinateDouble(EnumType type)
	{
		super(0.0, type);
	}
	
	public CoordinateDouble(Double value, EnumType type)
	{
		super(value, type);
	}
	
	public static CoordinateDouble valueOf(String value)
	{
		return Coordinate.parse(new CoordinateDouble(), value, Double::parseDouble);
	}
	
	@Override
	public Double zero()
	{
		return 0.0;
	}
}
