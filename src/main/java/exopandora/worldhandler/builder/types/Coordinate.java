package exopandora.worldhandler.builder.types;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Coordinate
{
	private double value;
	private boolean relative;
	
	public Coordinate()
	{
		this(0, true);
	}
	
	public Coordinate(double value)
	{
		this(value, false);
	}
	
	public Coordinate(double value, boolean relative)
	{
		this.relative = relative;
		this.value = value;
	}
	
	public void setValue(double value)
	{
		this.value = value;
	}
	
	public double getValue()
	{
		return this.value;
	}
	
	public void setRelative(boolean relative)
	{
		this.relative = relative;
	}
	
	public boolean isRelative()
	{
		return this.relative;
	}
	
	public static Coordinate valueOf(String value)
	{
		if(value.startsWith("~"))
		{
			return new Coordinate(Double.parseDouble(value.substring(1)), true);
		}
		
		return new Coordinate(Double.parseDouble(value), false);
	}
	
	@Override
	public String toString()
	{
		return String.valueOf(this.relative ? (this.value != 0 ? "~" + this.value : "~") : this.value);
	}
}
