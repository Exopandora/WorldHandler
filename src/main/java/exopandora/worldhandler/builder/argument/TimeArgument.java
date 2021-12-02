package exopandora.worldhandler.builder.argument;

import javax.annotation.Nullable;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class TimeArgument implements IDeserializableArgument
{
	private Float time;
	private Unit unit;
	
	protected TimeArgument()
	{
		super();
	}
	
	public void set(@Nullable Float time)
	{
		this.time = time;
	}
	
	public void set(@Nullable Unit unit)
	{
		this.unit = unit;
	}
	
	public void set(@Nullable Float time, @Nullable Unit unit)
	{
		this.time = time;
		this.unit = unit;
	}
	
	@Override
	public void deserialize(@Nullable String string)
	{
		if(string == null)
		{
			this.reset();
		}
		else
		{
	        try
			{
				StringReader reader = new StringReader(string);
				float time = reader.readFloat();
				String unit = reader.readUnquotedString();
				this.time = time;
				
				if(unit.isEmpty())
				{
					this.unit = null;
				}
			}
			catch(CommandSyntaxException e)
			{
				this.reset();
			}
		}
	}
	
	private void reset()
	{
		this.time = 0F;
		this.unit = null;
	}
	
	@Nullable
	public Float getTime()
	{
		return this.time;
	}
	
	@Nullable
	public Unit getUnit()
	{
		return this.unit;
	}
	
	@Override
	@Nullable
	public String serialize()
	{
		if(this.time == null)
		{
			return null;
		}
		
		StringBuilder builder = new StringBuilder();
		builder.append(this.time);
		
		if(this.unit != null)
		{
			builder.append(this.unit.getSuffix());
		}
		
		return builder.toString();
	}
	
	@Override
	public boolean isDefault()
	{
		return this.time == null;
	}
	
	public static enum Unit
	{
		TICKS("t"),
		SECONDS("s"),
		DAYS("d");
		
		private final String suffix;
		
		private Unit(String suffix)
		{
			this.suffix = suffix;
		}
		
		public String getSuffix()
		{
			return this.suffix;
		}
	}
}
