package exopandora.worldhandler.usercontent.model;

import com.google.gson.annotations.SerializedName;

import exopandora.worldhandler.usercontent.model.JsonMenu.Type;

public class JsonMenu extends AbstractJsonWidget<Type>
{
	@SerializedName("type")
	private Type type;
	
	public JsonMenu(Type type, Action action, JsonLayout layout, Attributes attributes)
	{
		super(action, layout, attributes);
		this.type = type;
	}
	
	public Type getType()
	{
		return this.type;
	}
	
	public void setType(Type type)
	{
		this.type = type;
	}
	
	public static enum Type
	{
		PAGE_LIST;
	}
	
	@Override
	public void validate() throws IllegalStateException
	{
		if(this.type == null)
		{
			throw new IllegalStateException("menu.type is null");
		}
		
		if(this.type == Type.PAGE_LIST)
		{
			if(this.getAttributes() == null)
			{
				throw new IllegalStateException("menu.attributes is null");
			}
			else if(this.getAttributes().getId() == null)
			{
				throw new IllegalStateException("menu.attributes.id is null");
			}
			else if(this.getAttributes().getId().isEmpty())
			{
				throw new IllegalStateException("menu.attributes.id is empty");
			}
			else if(this.getAttributes().getItems() == null)
			{
				throw new IllegalStateException("menu.attributes.items is null");
			}
			else if(this.getAttributes().getItems().isEmpty())
			{
				throw new IllegalStateException("menu.attributes.items is empty");
			}
			
			this.validateAction(Action.Type.SET, Action.Type.JS);
		}
	}
}
