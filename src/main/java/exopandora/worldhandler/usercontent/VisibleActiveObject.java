package exopandora.worldhandler.usercontent;

import exopandora.worldhandler.usercontent.model.BooleanExpression;
import exopandora.worldhandler.usercontent.model.JsonButton;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VisibleActiveObject<T> extends VisibleObject<T>
{
	private final BooleanExpression active;
	
	public VisibleActiveObject(BooleanExpression visible, BooleanExpression active, T object)
	{
		super(visible, object);
		this.active = active;
	}
	
	public VisibleActiveObject(JsonButton button, T object)
	{
		this(button.getAttributes() != null ? button.getAttributes().getVisible() : null, button.getAttributes() != null ? button.getAttributes().getEnabled() : null, object);
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