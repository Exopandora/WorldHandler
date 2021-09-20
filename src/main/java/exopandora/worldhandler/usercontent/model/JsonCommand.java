package exopandora.worldhandler.usercontent.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import exopandora.worldhandler.builder.CommandSyntax.Argument;

public class JsonCommand
{
	@SerializedName("name")
	private String name;
	
	@SerializedName("syntax")
	private List<Argument> syntax = null;
	
	@SerializedName("visible")
	private BooleanExpression visible;
	
	public JsonCommand(String name, List<Argument> syntax, BooleanExpression visible)
	{
		this.name = name;
		this.syntax = syntax;
		this.visible = visible;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public List<Argument> getSyntax()
	{
		return this.syntax;
	}
	
	public void setSyntax(List<Argument> syntax)
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
