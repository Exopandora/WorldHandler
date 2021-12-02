package exopandora.worldhandler.builder.argument.tag;

import net.minecraft.nbt.Tag;

public class MutableTag implements ITagProvider
{
	private String key;
	private Tag tag;
	
	public void setKey(String key)
	{
		this.key = key;
	}
	
	public void setTag(Tag tag)
	{
		this.tag = tag;
	}
	
	@Override
	public String key()
	{
		return this.key;
	}
	
	@Override
	public Tag value()
	{
		return this.tag;
	}
	
	public void reset()
	{
		this.key = null;
		this.tag = null;
	}
}
