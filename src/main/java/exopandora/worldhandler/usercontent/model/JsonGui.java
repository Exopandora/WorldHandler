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
	
	@SerializedName("elements")
	private List<JsonElement> elements = null;
	
	@SerializedName("texts")
	private List<JsonText> texts = null;
	
	public JsonGui(String title, JsonTab tab, List<JsonButton> buttons, List<JsonElement> elements, List<JsonText> texts)
	{
		this.title = title;
		this.tab = tab;
		this.buttons = buttons;
		this.elements = elements;
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
	
	public List<JsonElement> getElements()
	{
		return this.elements;
	}
	
	public void setElements(List<JsonElement> elements)
	{
		this.elements = elements;
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
