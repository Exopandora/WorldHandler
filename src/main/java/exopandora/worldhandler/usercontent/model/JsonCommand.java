package exopandora.worldhandler.usercontent.model;

import com.google.gson.annotations.SerializedName;

public class JsonCommand
{
	@SerializedName("label")
	private String label;
	
	@SerializedName("syntax")
	private JsonArgument syntax = null;
	
	@SerializedName("visible")
	private BooleanExpression visible;
	
	public JsonCommand(String label, JsonArgument syntax, BooleanExpression visible)
	{
		this.label = label;
		this.syntax = syntax;
		this.visible = visible;
	}
	
	public String getLabel()
	{
		return this.label;
	}
	
	public void setLabel(String label)
	{
		this.label = label;
	}
	
	public JsonArgument getSyntax()
	{
		return this.syntax;
	}
	
	public void setSyntax(JsonArgument syntax)
	{
		this.syntax = syntax;
	}
	
	public BooleanExpression getVisible()
	{
		return this.visible;
	}
	
	public void setVisible(BooleanExpression visible)
	{
		this.visible = visible;
	}
}
