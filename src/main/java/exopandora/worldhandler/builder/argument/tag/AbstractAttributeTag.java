package exopandora.worldhandler.builder.argument.tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class AbstractAttributeTag implements ITagProvider
{	
	public static final List<Attribute> ATTRIBUTES = ForgeRegistries.ATTRIBUTES.getValues().stream()
			.filter(attribute -> !attribute.getDescriptionId().equals(I18n.get(attribute.getDescriptionId())))
			.collect(Collectors.toList());
	
	protected final Map<Attribute, Double> attributes = new HashMap<Attribute, Double>();
	
	public void set(Attribute attribute, double value)
	{
		this.attributes.put(attribute, value);
	}
	
	public double get(Attribute value)
	{
		if(this.attributes.containsKey(value))
		{
			return this.attributes.get(value);
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
