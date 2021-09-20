package exopandora.worldhandler.usercontent;

import exopandora.worldhandler.usercontent.model.BooleanExpression;
import exopandora.worldhandler.usercontent.model.JsonWidget;

public class VisibleActiveObject<T> extends VisibleObject<T>
{
	private final BooleanExpression active;
	
	public VisibleActiveObject(BooleanExpression visible, BooleanExpression active, T object)
	{
		super(visible, object);
		this.active = active;
	}
	
	public VisibleActiveObject(JsonWidget widget, T object)
	{
		this(widget.getAttributes() != null ? widget.getAttributes().getVisible() : null, widget.getAttributes() != null ? widget.getAttributes().getEnabled() : null, object);
	}
	
	public boolean isEnabled(ScriptEngineAdapter engine)
	{
		if(this.active != null)
		{
			return this.active.eval(engine);
		}
		
		return true;
	}
}