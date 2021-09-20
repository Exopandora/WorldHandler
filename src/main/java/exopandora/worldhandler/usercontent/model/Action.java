package exopandora.worldhandler.usercontent.model;

import com.google.gson.annotations.SerializedName;

public class Action
{
	@SerializedName("type")
	private Type type;
	
	@SerializedName("attributes")
	private ActionAttributes attributes;
	
	public Action(Type type, ActionAttributes attributes)
	{
		this.type = type;
		this.attributes = attributes;
	}
	
	public Type getType()
	{
		return this.type;
	}
	
	public void setType(Type type)
	{
		this.type = type;
	}
	
	public ActionAttributes getAttributes()
	{
		return this.attributes;
	}
	
	public void setAttributes(ActionAttributes attributes)
	{
		this.attributes = attributes;
	}
	
	public void validate() throws IllegalStateException
	{
		if(this.type == null)
		{
			throw new IllegalStateException("action.type type is null");
		}
		
		if(this.type == Type.OPEN)
		{
			if(this.getAttributes() == null)
			{
				throw new IllegalStateException("action.attributes is null");
			}
			else if(this.getAttributes().getValue() == null)
			{
				throw new IllegalStateException("action.attributes.value is null");
			}
		}
		else if(this.type == Type.SET)
		{
			if(this.getAttributes() == null)
			{
				throw new IllegalStateException("action.attributes is null");
			}
		}
		else if(this.type == Type.RUN)
		{
			if(this.getAttributes() == null)
			{
				throw new IllegalStateException("action.attributes is null");
			}
		}
		else if(this.type == Type.JS)
		{
			if(this.getAttributes() == null)
			{
				throw new IllegalStateException("action.attributes is null");
			}
			else if(this.getAttributes().getFunction() == null)
			{
				throw new IllegalStateException("action.attributes.function is null");
			}
		}
	}
	
	public static enum Type
	{
		OPEN,
		SET,
		RUN,
		BACK,
		BACK_TO_GAME,
		JS;
	}
}
