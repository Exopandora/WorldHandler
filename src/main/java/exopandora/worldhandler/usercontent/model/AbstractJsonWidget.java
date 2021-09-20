package exopandora.worldhandler.usercontent.model;

import java.util.Arrays;

import com.google.gson.annotations.SerializedName;

public abstract class AbstractJsonWidget<T extends Enum<T>>
{
	@SerializedName("action")
	private Action action;
	
	@SerializedName("layout")
	private JsonLayout layout;
	
	@SerializedName("attributes")
	private Attributes attributes;
	
	public AbstractJsonWidget(Action action, JsonLayout layout, Attributes attributes)
	{
		this.action = action;
		this.layout = layout;
		this.attributes = attributes;
	}
	
	public Action getAction()
	{
		return this.action;
	}
	
	public void setAction(Action action)
	{
		this.action = action;
	}
	
	public JsonLayout getLayout()
	{
		return this.layout;
	}
	
	public void setDimensions(JsonLayout layout)
	{
		this.layout = layout;
	}
	
	public Attributes getAttributes()
	{
		return this.attributes;
	}
	
	public void setAttributes(Attributes attributes)
	{
		this.attributes = attributes;
	}
	
	public abstract T getType();
	public abstract void setType(T type);
	public abstract void validate() throws IllegalStateException;
	
	protected void validateAction(Action.Type... allowedTypes) throws IllegalStateException
	{
		if(this.getAction() != null)
		{
			this.getAction().validate();
			
			if(Arrays.stream(allowedTypes).noneMatch(type -> type.equals(this.getAction().getType())))
			{
				throw new IllegalStateException("Illegal action for type " + this.getType().toString().toLowerCase());
			}
		}
	}
	
	public static enum Type
	{
		BUTTON,
		MENU
	}
}
