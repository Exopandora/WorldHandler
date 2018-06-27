package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.types.Type;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BuilderWeather extends CommandBuilder
{
	public BuilderWeather()
	{
		
	}
	
	public BuilderWeather(EnumWeather weather)
	{
		this.setWeather(weather);
	}
	
	public BuilderWeather(EnumWeather weather, int value)
	{
		this(weather);
		this.setValue(value);
	}
	
	public void setWeather(EnumWeather weather)
	{
		this.setNode(0, weather.toString());
	}
	
	public void setValue(int value)
	{
		this.setNode(1, value);
	}
	
	@Override
	public String getCommandName()
	{
		return "weather";
	}

	@Override
	public final Syntax getSyntax()
	{
		Syntax syntax = new Syntax();
		
		syntax.addRequired("clear|rain|thunde", Type.STRING);
		syntax.addOptional("duration", Type.INT);
		
		return syntax;
	}
	
	@SideOnly(Side.CLIENT)
	public static enum EnumWeather
	{
		CLEAR,
		RAIN,
		THUNDER;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
}
