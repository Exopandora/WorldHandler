package exopandora.worldhandler.gui.button.storage;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ButtonStorage<T>
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
	
	public void decrementIndex()
	{
		this.index--;
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
