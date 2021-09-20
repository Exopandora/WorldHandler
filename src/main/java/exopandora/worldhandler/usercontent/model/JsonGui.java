package exopandora.worldhandler.usercontent.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class JsonGui
{
	@SerializedName("title")
	private String title;
	
	@SerializedName("tab")
	private JsonTab tab;
	
	@SerializedName("widgets")
	private List<JsonWidget> widgets = null;
	
	@SerializedName("menus")
	private List<JsonMenu> menus = null;
	
	@SerializedName("labels")
	private List<JsonLabel> labels = null;
	
	public JsonGui(String title, JsonTab tab, List<JsonWidget> widgets, List<JsonMenu> menus, List<JsonLabel> labels)
	{
		this.title = title;
		this.tab = tab;
		this.widgets = widgets;
		this.menus = menus;
		this.labels = labels;
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public JsonTab getTab()
	{
		return this.tab;
	}
	
	public void setTitle(JsonTab tab)
	{
		this.tab = tab;
	}
	
	public List<JsonWidget> getWidgets()
	{
		return this.widgets;
	}
	
	public void setButtons(List<JsonWidget> widgets)
	{
		this.widgets = widgets;
	}
	
	public List<JsonMenu> getMenus()
	{
		return this.menus;
	}
	
	public void setMenus(List<JsonMenu> menus)
	{
		this.menus = menus;
	}
	
	public List<JsonLabel> getLabels()
	{
		return this.labels;
	}
	
	public void setTexts(List<JsonLabel> labels)
	{
		this.labels = labels;
	}
	
	public void validate() throws IllegalStateException
	{
		if(this.tab == null)
		{
			throw new IllegalStateException("gui.tab is null");
		}
		
		this.tab.validate();
	}
}
