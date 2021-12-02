package exopandora.worldhandler.builder.argument;

import javax.annotation.Nullable;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.arguments.coordinates.WorldCoordinate;

public class AngleArgument implements IDeserializableArgument
{
	private Float angle;
	private boolean relative;
	
	protected AngleArgument()
	{
		super();
	}
	
	public void setAngle(@Nullable Float angle)
	{
		this.angle = angle;
	}
	
	public void setRelative(boolean relative)
	{
		this.relative = relative;
	}
	
	public void setAngle(@Nullable Float angle, boolean relative)
	{
		this.angle = angle;
		this.relative = relative;
	}
	
	@Nullable
	public Float getAngle()
	{
		return this.angle;
	}
	
	public boolean isRelative()
	{
		return this.relative;
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
				boolean relative = WorldCoordinate.isRelative(reader);
				float angle = reader.canRead() && reader.peek() != ' ' ? reader.readFloat() : 0.0F;
				
				if(!Float.isNaN(angle) && !Float.isInfinite(angle))
				{
					this.angle = angle;
					this.relative = relative;
				}
				else
				{
					this.reset();
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
		this.angle = null;
		this.relative = false;
	}
	
	@Override
	@Nullable
	public String serialize()
	{
		if(this.angle == null)
		{
			return null;
		}
		
		return this.relative ? "~" : "" + Float.toString(this.angle);
	}
	
	@Override
	public boolean isDefault()
	{
		return this.angle == null;
	}
}
