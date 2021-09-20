package exopandora.worldhandler.gui.content.impl;

import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import net.minecraft.network.chat.MutableComponent;

public abstract class ContentChild extends Content
{
	private Content parent;
	
	public ContentChild withParent(Content parent)
	{
		this.parent = parent;
		return this;
	}
	
	@Override
	public Category getCategory()
	{
		if(this.parent.getCategory() != null)
		{
			return this.parent.getCategory();
		}
		
		return Contents.MAIN.getCategory();
	}
	
	@Override
	public MutableComponent getTitle()
	{
		if(this.parent != null)
		{
			return this.parent.getTitle();
		}
		
		return null;
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		if(this.parent != null)
		{
			return this.parent.getTabTitle();
		}
		
		return null;
	}
	
	@Override
	public Content getActiveContent()
	{
		if(this.parent != null)
		{
			return this.parent.getActiveContent();
		}
		
		return null;
	}
	
	@Override
	public Content getBackContent()
	{
		return this.parent;
	}
	
	public Content getParentContent()
	{
		return this.parent;
	}
}
