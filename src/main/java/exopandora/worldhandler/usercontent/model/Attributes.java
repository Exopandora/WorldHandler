package exopandora.worldhandler.usercontent.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import exopandora.worldhandler.gui.widget.button.EnumIcon;

public class Attributes
{
	@SerializedName("id")
	private String id;
	
	@SerializedName("visible")
	private BooleanExpression visible;
	
	@SerializedName("enabled")
	private BooleanExpression enabled;
	
	@SerializedName("tooltip")
	private String tooltip;
	
	@SerializedName("item")
	private String item;
	
	@SerializedName("icon")
	private EnumIcon icon;
	
	@SerializedName("items")
	private List<JsonItem> items = null;

	@SerializedName("length")
	private int length;
	
	@SerializedName("min")
	private double min;
	
	@SerializedName("max")
	private double max;
	
	@SerializedName("start")
	private double start;
	
	public Attributes(String id, BooleanExpression visible, BooleanExpression enabled, String tooltip, String item, EnumIcon icon, List<JsonItem> items, int length, double min, double max, double start)
	{
		this.id = id;
		this.visible = visible;
		this.enabled = enabled;
		this.tooltip = tooltip;
		this.item = item;
		this.icon = icon;
		this.items = items;
		this.length = length;
		this.min = min;
		this.max = max;
		this.start = start;
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public BooleanExpression getVisible()
	{
		return this.visible;
	}
	
	public void setVisible(BooleanExpression visible)
	{
		this.visible = visible;
	}
	
	public BooleanExpression getEnabled()
	{
		return this.enabled;
	}
	
	public void setEnabled(BooleanExpression enabled)
	{
		this.enabled = enabled;
	}
	
	public String getTooltip()
	{
		return this.tooltip;
	}
	
	public void setTooltip(String tooltip)
	{
		this.tooltip = tooltip;
	}
	
	public String getItem()
	{
		return this.item;
	}
	
	public void setItem(String item)
	{
		this.item = item;
	}
	
	public EnumIcon getIcon()
	{
		return this.icon;
	}
	
	public void setIcon(EnumIcon icon)
	{
		this.icon = icon;
	}
	
	public List<JsonItem> getItems()
	{
		return this.items;
	}
	
	public void setItems(List<JsonItem> items)
	{
		this.items = items;
	}
	
	public int getLength()
	{
		return this.length;
	}
	
	public void setLength(int length)
	{
		this.length = length;
	}
	
	public double getMin()
	{
		return this.min;
	}
	
	public void setMin(double min)
	{
		this.min = min;
	}
	
	public double getMax()
	{
		return this.max;
	}
	
	public void setMax(double max)
	{
		this.max = max;
	}
	
	public double getStart()
	{
		return this.start;
	}
	
	public void setStart(double start)
	{
		this.start = start;
	}
}
