package exopandora.worldhandler.usercontent.model;

import java.util.Arrays;

import com.google.gson.annotations.SerializedName;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class JsonWidget<T extends Enum<T>>
{
	@SerializedName("action")
	private Action action;
	
	@SerializedName("dimensions")
	private JsonDimensions dimensions;
	
	@SerializedName("attributes")
	private Attributes attributes;
	
	public JsonWidget(Action action, JsonDimensions dimensions, Attributes attributes)
	{
		this.action = action;
		this.dimensions = dimensions;
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
	
	public JsonDimensions getDimensions()
	{
		return this.dimensions;
	}
	
	public void setDimensions(JsonDimensions dimensions)
	{
		this.dimensions = dimensions;
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
	
	@OnlyIn(Dist.CLIENT)
	public static enum Type
	{
		BUTTON,
		MENU
	}
}
