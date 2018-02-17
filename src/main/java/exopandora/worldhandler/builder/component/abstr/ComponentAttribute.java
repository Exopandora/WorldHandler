package exopandora.worldhandler.builder.component.abstr;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import exopandora.worldhandler.builder.component.IBuilderComponent;
import exopandora.worldhandler.builder.impl.abstr.EnumAttributes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class ComponentAttribute implements IBuilderComponent
{
	protected Map<EnumAttributes, Double> attributes = new HashMap<EnumAttributes, Double>();
	protected Function<EnumAttributes, Boolean> applyable;
	
	public ComponentAttribute(Function<EnumAttributes, Boolean> applyable)
	{
		this.applyable = applyable;
	}
	
	public void set(EnumAttributes attribute, double ammount)
	{
		this.attributes.put(attribute, ammount);
	}
	
	public double getAmmount(EnumAttributes attribute)
	{
		if(this.attributes.containsKey(attribute))
		{
			return this.attributes.get(attribute);
		}
		
		return 0;
	}
	
	public void remove(EnumAttributes attribute)
	{
		this.attributes.remove(attribute);
	}
	
	public Set<EnumAttributes> getAttributes()
	{
		return this.attributes.keySet();
	}
}
