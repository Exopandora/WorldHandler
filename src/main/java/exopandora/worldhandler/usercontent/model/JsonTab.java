package exopandora.worldhandler.usercontent.model;

import com.google.gson.annotations.SerializedName;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class JsonTab
{
	@SerializedName("title")
	private String title;
	
	@SerializedName("category")
	private String category;
	
	@SerializedName("category_index")
	private int categoryIndex;
	
	@SerializedName("active_content")
	private String activeContent;
	
	public JsonTab(String title, String category, int categoryIndex, String activeContent)
	{
		this.title = title;
		this.category = category;
		this.categoryIndex = categoryIndex;
		this.activeContent = activeContent;
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getCategory()
	{
		return this.category;
	}
	
	public void setCategory(String category)
	{
		this.category = category;
	}
	
	public int getCategoryIndex()
	{
		return this.categoryIndex;
	}
	
	public void setCategoryIndex(int categoryIndex)
	{
		this.categoryIndex = categoryIndex;
	}
	
	public String getActiveContent()
	{
		return this.activeContent;
	}
	
	public void setActiveContent(String activeContent)
	{
		this.activeContent = activeContent;
	}
	
	public void validate() throws IllegalStateException
	{
		if(this.category == null)
		{
			throw new IllegalStateException("tab.category is null");
		}
	}
}
