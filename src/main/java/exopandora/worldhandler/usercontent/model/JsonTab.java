package exopandora.worldhandler.usercontent.model;

import com.google.gson.annotations.SerializedName;

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
	
	@SerializedName("back_content")
	private String backContent;
	
	public JsonTab(String title, String category, int categoryIndex, String activeContent, String backContent)
	{
		this.title = title;
		this.category = category;
		this.categoryIndex = categoryIndex;
		this.activeContent = activeContent;
		this.backContent = backContent;
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
	
	public String getBackContent()
	{
		return this.backContent;
	}
	
	public void setBackContent(String backContent)
	{
		this.backContent = backContent;
	}
	
	public void validate() throws IllegalStateException
	{
		if(this.category == null)
		{
			throw new IllegalStateException("tab.category is null");
		}
		else if(this.activeContent != null && this.activeContent.isEmpty())
		{
			throw new IllegalStateException("tab.active_content is empty");
		}
		else if(this.backContent != null && this.backContent.isEmpty())
		{
			throw new IllegalStateException("tab.back_content is empty");
		}
	}
}
