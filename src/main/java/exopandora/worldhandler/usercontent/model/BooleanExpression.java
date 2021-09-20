package exopandora.worldhandler.usercontent.model;

import com.google.gson.annotations.SerializedName;

import exopandora.worldhandler.usercontent.ScriptEngineAdapter;

public class BooleanExpression
{
	@SerializedName("type")
	private Type type;
	
	@SerializedName("bool")
	private boolean bool;
	
	@SerializedName("function")
	private String function;
	
	public BooleanExpression(Type type, boolean bool, String function)
	{
		this.type = type;
		this.bool = bool;
		this.function = function;
	}
	
	public Type getType()
	{
		return this.type;
	}
	
	public void setType(Type type)
	{
		this.type = type;
	}
	
	public boolean isBool()
	{
		return this.bool;
	}
	
	public void setBool(boolean bool)
	{
		this.bool = bool;
	}
	
	public String getFunction()
	{
		return this.function;
	}
	
	public void setFunction(String function)
	{
		this.function = function;
	}
	
	public boolean eval(ScriptEngineAdapter engine)
	{
		if(this.type == Type.BOOL)
		{
			return this.bool;
		}
		else if(this.type == Type.JS)
		{
			Object result = engine.invokeFunction(this.function);
			
			if(result != null && result instanceof Boolean)
			{
				return (boolean) result;
			}
		}
		
		return true;
	}
	
	public static enum Type
	{
		BOOL,
		JS;
	}
}
