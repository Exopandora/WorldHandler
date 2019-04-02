package exopandora.worldhandler.builder;

import java.util.ArrayList;
import java.util.List;

import exopandora.worldhandler.builder.types.Type;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Syntax
{
	private List<SyntaxEntry> syntax = new ArrayList<SyntaxEntry>();
	
	public Syntax addRequired(String key, Type type)
	{
		this.syntax.add(new SyntaxEntry(key, type, true, null));
		return this;
	}
	
	public Syntax addRequired(String key, Type type, Object def)
	{
		this.syntax.add(new SyntaxEntry(key, type, true, def));
		return this;
	}
	
	public Syntax addOptional(String key, Type type)
	{
		this.syntax.add(new SyntaxEntry(key, type, false, null));
		return this;
	}
	
	public Syntax addOptional(String key, Type type, Object def)
	{
		this.syntax.add(new SyntaxEntry(key, type, false, def));
		return this;
	}
	
	public List<SyntaxEntry> getSyntaxEntries()
	{
		return this.syntax;
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class SyntaxEntry
	{
		private final String key;
		private final Type type;
		private final boolean required;
		private final Object def;
		
		public SyntaxEntry(String key, Type type, boolean required, Object def)
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
		
		public Type getType()
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
