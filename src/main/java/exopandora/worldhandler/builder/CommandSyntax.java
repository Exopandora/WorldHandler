package exopandora.worldhandler.builder;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import exopandora.worldhandler.builder.types.ArgumentType;

public class CommandSyntax
{
	private List<Argument> syntax = new ArrayList<Argument>();
	
	public CommandSyntax()
	{
		super();
	}
	
	public CommandSyntax(List<Argument> syntax)
	{
		this.syntax = syntax;
	}
	
	public CommandSyntax addRequired(String key, ArgumentType type)
	{
		this.syntax.add(new Argument(key, type, true, null));
		return this;
	}
	
	public CommandSyntax addRequired(String key, ArgumentType type, Object def)
	{
		this.syntax.add(new Argument(key, type, true, def));
		return this;
	}
	
	public CommandSyntax addOptional(String key, ArgumentType type)
	{
		this.syntax.add(new Argument(key, type, false, null));
		return this;
	}
	
	public CommandSyntax addOptional(String key, ArgumentType type, Object def)
	{
		this.syntax.add(new Argument(key, type, false, def));
		return this;
	}
	
	public List<Argument> getArguments()
	{
		return this.syntax;
	}
	
	public static class Argument
	{
		@SerializedName("name")
		private final String key;
		
		@SerializedName("type")
		private final ArgumentType type;
		
		@SerializedName("required")
		private final boolean required;
		
		@SerializedName("default")
		private final Object def;
		
		public Argument(String key, ArgumentType type, boolean required, Object def)
		{
			this.key = key;
			this.type = type;
			this.required = required;
			this.def = def;
		}
		
		public String getKey()
		{
			return this.key;
		}
		
		public ArgumentType getType()
		{
			return this.type;
		}
		
		public boolean isRequired()
		{
			return this.required;
		}
		
		public Object getDefault()
		{
			return this.def;
		}
		
		@Override
		public String toString()
		{
			if(this.required)
			{
				return "<" + this.key + ">";
			}
			else
			{
				return "[" + this.key + "]";
			}
		}
	}
}
