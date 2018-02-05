package exopandora.worldhandler.gui.button.storage;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SliderStorage
{
	private final float min;
	private final float max;
	private float value;
	
	public SliderStorage(float min, float max, float value)
	{
		this.min = min;
		this.max = max;
		this.value = value;
	}
	
	public float getMin()
	{
		return this.min;
	}
	
	public float getMax()
	{
		return this.max;
	}
	
	public float getFloat()
	{
		return this.value;
	}
	
	public void setFloat(float value)
	{
		this.value = value;
	}
}