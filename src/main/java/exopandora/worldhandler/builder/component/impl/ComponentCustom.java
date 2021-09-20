package exopandora.worldhandler.builder.component.impl;

import exopandora.worldhandler.builder.component.IBuilderComponent;
import net.minecraft.nbt.Tag;

public class ComponentCustom implements IBuilderComponent
{
	private Tag nbt;
	private String tag;
	
	public void setNBT(Tag nbt)
	{
		this.nbt = nbt;
	}
	
	public void setTag(String tag)
	{
		this.tag = tag;
	}

	public void set(String tag, Tag nbt)
	{
		this.setTag(tag);
		this.setNBT(nbt);
	}
	
	public void reset()
	{
		this.set(null, null);
	}
	
	@Override
	public Tag serialize()
	{
		return this.nbt;
	}
	
	@Override
	public String getTag()
	{
		return this.tag;
	}
}
