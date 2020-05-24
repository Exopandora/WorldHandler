package exopandora.worldhandler.builder.component.impl;

import exopandora.worldhandler.builder.component.IBuilderComponent;
import net.minecraft.nbt.INBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ComponentCustom implements IBuilderComponent
{
	private INBT nbt;
	private String tag;
	
	public void setNBT(INBT nbt)
	{
		this.nbt = nbt;
	}
	
	public void setTag(String tag)
	{
		this.tag = tag;
	}

	public void set(String tag, INBT nbt)
	{
		this.setTag(tag);
		this.setNBT(nbt);
	}
	
	public void reset()
	{
		this.set(null, null);
	}
	
	@Override
	public INBT serialize()
	{
		return this.nbt;
	}
	
	@Override
	public String getTag()
	{
		return this.tag;
	}
}
