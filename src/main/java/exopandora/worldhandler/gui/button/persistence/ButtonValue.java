package exopandora.worldhandler.gui.button.persistence;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ButtonValue<T>
{
	private int index;
	private T object;
	
	public void setIndex(int index)
	{
		this.index = index;
	}
	
	public int getIndex()
	{
		return this.index;
	}
	
	public void incrementIndex()
	{
		this.index++;
	}
	
	public void incrementIndex(int amount)
	{
		this.index += amount;
	}
	
	public void decrementIndex()
	{
		this.index--;
	}
	
	public void decrementIndex(int amount)
	{
		this.index -= amount;
	}
	
	public T getObject()
	{
		return this.object;
	}
	
	public void setObject(T object)
	{
		this.object = object;
	}
}
