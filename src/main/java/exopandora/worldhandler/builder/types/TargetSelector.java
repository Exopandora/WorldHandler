package exopandora.worldhandler.builder.types;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TargetSelector
{
	private final Map<String, Object> values = new HashMap<String, Object>();
	private static final String REGEX = "@e\\[(.*)\\]";
	
	public void set(String id, Object value)
	{
		this.values.put(id.toLowerCase(), value);
	}
	
	@Nullable
	@SuppressWarnings("unchecked")
	public <T> T get(String id)
	{
		return (T) this.values.get(id);
	}
	
	public Object remove(String id)
	{
		return this.values.remove(id.toLowerCase());
	}
	
	@Nonnull
	public static TargetSelector valueOf(String input)
	{
		if(input.matches(REGEX));
		{
			TargetSelector result = new TargetSelector();
			
			for(String keys : input.replaceFirst(REGEX, "$1").split(","))
			{
				String[] pair = keys.split("=");
				
				if(pair.length > 1)
				{
					result.set(pair[0], pair[1]);
				}
			}
		}
		
		return new TargetSelector();
	}
	
	@Override
	public String toString()
	{
		return "@e[" + String.join(",", this.values.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue().toString()).collect(Collectors.toList())) + "]";
	}
}
