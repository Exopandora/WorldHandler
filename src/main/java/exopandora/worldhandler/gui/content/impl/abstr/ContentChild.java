package exopandora.worldhandler.gui.content.impl.abstr;

import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class ContentChild extends Content
{
	protected Content parent;
	
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
	public String getTabTitle()
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
}
