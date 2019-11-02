package exopandora.worldhandler.usercontent;

import exopandora.worldhandler.usercontent.model.BooleanExpression;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
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
