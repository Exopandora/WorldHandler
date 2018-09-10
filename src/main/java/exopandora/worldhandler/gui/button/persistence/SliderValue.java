package exopandora.worldhandler.gui.button.persistence;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SliderValue
{
	private final double min;
	private final double max;
	private double position;
	
	private SliderValue(double min, double max)
	{
		this.min = min;
		this.max = max;
	}
	
	public SliderValue(double min, double max, double position)
	{
		this(min, max);
		this.position = position;
	}
	
	public SliderValue(double min, double max, int value)
	{
		this(min, max);
		this.position = this.valueToPosition(value);
	}
	
	public double getMin()
	{
		return this.min;
	}
	
	public double getMax()
	{
		return this.max;
	}
	
	public double getPosition()
	{
		return this.position;
	}
	
	public void setPosition(double position)
	{
		this.position = position;
	}
	
	public int getValue()
	{
		return (int) (this.min + (this.max - this.min) * this.position);
	}
	
	public void setValue(int value)
	{
		this.position = this.valueToPosition(value);
	}
	
	private double valueToPosition(int value)
	{
		if(this.min == this.max)
		{
			return 0;
		}
		
		return (value - this.min) / (this.max - this.min);
	}
}