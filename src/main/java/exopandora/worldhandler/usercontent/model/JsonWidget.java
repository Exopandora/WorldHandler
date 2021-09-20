package exopandora.worldhandler.usercontent.model;

import com.google.gson.annotations.SerializedName;

import exopandora.worldhandler.usercontent.model.JsonWidget.Type;

public class JsonWidget extends AbstractJsonWidget<Type>
{
	@SerializedName("text")
	private String text;
	
	@SerializedName("type")
	private Type type;
	
	public JsonWidget(String text, Type type, Action action, JsonLayout layout, Attributes attributes)
	{
		super(action, layout, attributes);
		this.text = text;
		this.type = type;
	}
	
	public String getText()
	{
		return this.text;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public Type getType()
	{
		return this.type;
	}
	
	public void setType(Type type)
	{
		this.type = type;
	}
	
	@Override
	public void validate() throws IllegalStateException
	{
		if(this.type == null)
		{
			throw new IllegalStateException("widget.type is null");
		}
		
		if(this.type == Type.TEXTFIELD)
		{
			if(this.getAttributes() == null)
			{
				throw new IllegalStateException("widget.attributes is null");
			}
			else if(this.getAttributes().getId() == null)
			{
				throw new IllegalStateException("widget.attributes.id is null");
			}
			else if(this.getAttributes().getId().isEmpty())
			{
				throw new IllegalStateException("widget.attributes.id is empty");
			}
			
			this.validateAction(Action.Type.SET, Action.Type.JS);
		}
		else if(this.type == Type.ITEM_BUTTON)
		{
			if(this.getAttributes() == null)
			{
				throw new IllegalStateException("widget.attributes is null");
			}
			else if(this.getAttributes().getItem() == null)
			{
				throw new IllegalStateException("widget.attributes.item is null");
			}
			
			this.validateAction(Action.Type.OPEN, Action.Type.SET, Action.Type.RUN, Action.Type.BACK, Action.Type.BACK_TO_GAME, Action.Type.JS);
		}
		else if(this.type == Type.ICON_BUTTON)
		{
			if(this.getAttributes() == null)
			{
				throw new IllegalStateException("widget.attributes is null");
			}
			else if(this.getAttributes().getIcon() == null)
			{
				throw new IllegalStateException("widget.attributes.icon is null");
			}
			
			this.validateAction(Action.Type.OPEN, Action.Type.SET, Action.Type.RUN, Action.Type.BACK, Action.Type.BACK_TO_GAME, Action.Type.JS);
		}
		else if(this.type == Type.LIST_BUTTON)
		{
			if(this.getAttributes() == null)
			{
				throw new IllegalStateException("widget.attributes is null");
			}
			else if(this.getAttributes().getItems() == null)
			{
				throw new IllegalStateException("widget.attributes.items is null");
			}
			else if(this.getAttributes().getItems().isEmpty())
			{
				throw new IllegalStateException("widget.attributes.items is empty");
			}
			
			this.validateAction(Action.Type.SET, Action.Type.JS);
		}
		else if(this.type == Type.SLIDER)
		{
			if(this.getAttributes() == null)
			{
				throw new IllegalStateException("widget.attributes is null");
			}
			
			this.validateAction(Action.Type.SET, Action.Type.JS);
		}
	}
	
	public static enum Type
	{
		BUTTON,
		TEXTFIELD,
		ITEM_BUTTON,
		ICON_BUTTON,
		LIST_BUTTON,
		SLIDER;
	}
}
