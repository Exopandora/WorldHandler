package exopandora.worldhandler.gui.button.storage;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SliderStorage
{
	private final double min;
	private final double max;
	private double position;
	
	public SliderStorage(double min, double max, double position)
	{
		this.min = min;
		this.max = max;
		this.position = position;
	}
	
	public SliderStorage(double min, double max, int value)
	{
		this.min = min;
		this.max = max;
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
		return (value - this.min) / (this.max - this.min);
	}
}