package exopandora.worldhandler.usercontent.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class JsonGui
{
	@SerializedName("title")
	private String title;
	
	@SerializedName("tab")
	private JsonTab tab;
	
	@SerializedName("buttons")
	private List<JsonButton> buttons = null;
	
	@SerializedName("menus")
	private List<JsonMenu> menus = null;
	
	@SerializedName("texts")
	private List<JsonText> texts = null;
	
	public JsonGui(String title, JsonTab tab, List<JsonButton> buttons, List<JsonMenu> menus, List<JsonText> texts)
	{
		this.title = title;
		this.tab = tab;
		this.buttons = buttons;
		this.menus = menus;
		this.texts = texts;
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
	
	public List<JsonButton> getButtons()
	{
		return this.buttons;
	}
	
	public void setButtons(List<JsonButton> buttons)
	{
		this.buttons = buttons;
	}
	
	public List<JsonMenu> getMenus()
	{
		return this.menus;
	}
	
	public void setMenus(List<JsonMenu> menus)
	{
		this.menus = menus;
	}
	
	public List<JsonText> getTexts()
	{
		return this.texts;
	}
	
	public void setTexts(List<JsonText> texts)
	{
		this.texts = texts;
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
