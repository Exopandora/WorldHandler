package exopandora.worldhandler.builder.component.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import exopandora.worldhandler.builder.component.IBuilderComponent;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class ComponentAttribute implements IBuilderComponent
{	
	public static final List<Attribute> ATTRIBUTES = ForgeRegistries.ATTRIBUTES.getValues().stream().filter(attribute -> !attribute.getDescriptionId().equals(I18n.get(attribute.getDescriptionId()))).collect(Collectors.toList());
	
	protected Map<Attribute, Double> attributes = new HashMap<Attribute, Double>();
	
	public void set(Attribute attribute, double ammount)
	{
		this.attributes.put(attribute, ammount);
	}
	
	public double getAmmount(Attribute attribute)
	{
		if(this.attributes.containsKey(attribute))
		{
			return this.attributes.get(attribute);
		}
		
		return 0;
	}
	
	public void remove(Attribute attribute)
	{
		this.attributes.remove(attribute);
	}
	
	public Set<Attribute> getAttributes()
	{
		return this.attributes.keySet();
	}
}
