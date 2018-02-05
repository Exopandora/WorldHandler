package exopandora.worldhandler.builder.component.abstr;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import exopandora.worldhandler.builder.component.IBuilderComponent;
import exopandora.worldhandler.builder.impl.abstr.EnumAttributes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class ComponentAttribute implements IBuilderComponent
{
	protected Map<EnumAttributes, Float> attributes = new HashMap<EnumAttributes, Float>();
	protected Function<EnumAttributes, Boolean> applyable;
	
	public ComponentAttribute(Function<EnumAttributes, Boolean> applyable)
	{
		this.applyable = applyable;
	}
	
	public void set(EnumAttributes attribute, float ammount)
	{
		this.attributes.put(attribute, ammount);
	}
	
	public void remove(EnumAttributes attribute)
	{
		this.attributes.remove(attribute);
	}
}
