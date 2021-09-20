package exopandora.worldhandler.usercontent;

import exopandora.worldhandler.usercontent.model.BooleanExpression;

public class VisibleObject<T>
{
	private final BooleanExpression visible;
	private final T object;
	
	public VisibleObject(BooleanExpression visible, T object)
	{
		this.visible = visible;
		this.object = object;
	}
	
	public boolean isVisible(ScriptEngineAdapter engine)
	{
		if(this.visible != null)
		{
			return this.visible.eval(engine);
		}
		
		return true;
	}
	
	public T getObject()
	{
		return this.object;
	}
}
