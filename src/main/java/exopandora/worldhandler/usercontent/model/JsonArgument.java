package exopandora.worldhandler.usercontent.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class JsonArgument
{
	@SerializedName("name")
	private String name;
	
	@SerializedName("label")
	private String label;
	
	@SerializedName("type")
	private ArgumentType type;
	
	@SerializedName("children")
	private List<JsonArgument> children;
	
	public JsonArgument(String name, String label, ArgumentType type, List<JsonArgument> children)
	{
		this.name = name;
		this.label = label;
		this.type = type;
		this.children = children;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getLabel()
	{
		return this.label;
	}
	
	public void setLabel(String label)
	{
		this.label = label;
	}
	
	public ArgumentType getType()
	{
		return this.type;
	}
	
	public void setType(ArgumentType type)
	{
		this.type = type;
	}
	
	public List<JsonArgument> getChildren()
	{
		return this.children;
	}
	
	public void setChildren(List<JsonArgument> children)
	{
		this.children = children;
	}
	
	public void validate()
	{
		this.validate(this.name, new HashMap<String, ArgumentType>());
	}
	
	private void validate(String path, Map<String, ArgumentType> typeMap)
	{
		if(this.name == null)
		{
			throw new IllegalStateException("Argument in path \"" + path + "\" has no name");
		}
		
		if(this.children == null)
		{
			return;
		}
		
		List<ArgumentType> types = new ArrayList<ArgumentType>();
		List<String> names = new ArrayList<String>();
		
		for(JsonArgument child : this.children)
		{
			if(child.getType() != null)
			{
				if(types.contains(child.getType()))
				{
					throw new IllegalStateException("\"" + path + "\" contains two or more branches with the same argument type \"" + child.getType() + "\"");
				}
				
				types.add(child.getType());
			}
			
			if(names.contains(child.getName()))
			{
				throw new IllegalStateException("\"" + path + "\" contains two or more branches with the same argument name \"" + child.getName() + "\"");
			}
			
			if(child.getType() != null)
			{
				if(typeMap.containsKey(child.getName()) && !child.getType().equals(typeMap.get(child.getName())))
				{
					throw new IllegalStateException("\"" + path + "\" expects a different type for argument \"" + child.getName() + "\"");
				}
				
				typeMap.put(child.getName(), child.getType());
			}
			
			child.validate(path + "/" + child.getName(), typeMap);
		}
	}
}
