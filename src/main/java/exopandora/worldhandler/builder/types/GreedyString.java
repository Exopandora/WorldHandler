package exopandora.worldhandler.builder.types;

import javax.annotation.Nullable;

public class GreedyString
{
	private String string;
	
	public GreedyString(String string)
	{
		this.string = string;
	}
	
	public String getString()
	{
		return this.string;
	}
	
	public void setString(String string)
	{
		this.string = string;
	}
	
	public boolean isEmpty()
	{
		if(this.string == null)
		{
			return true;
		}
		
		return this.string.isEmpty();
	}
	
	@Nullable
	public static GreedyString valueOf(String string)
	{
		if(string != null && string.matches("\"(.*)\""))
		{
			return new GreedyString(string.substring(1, string.length() - 1));
		}
		
		return null;
	}
	
	@Override
	public String toString()
	{
		return "\"" + this.string + "\"";
	}
}
