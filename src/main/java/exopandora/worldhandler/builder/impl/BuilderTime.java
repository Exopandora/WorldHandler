package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.types.Type;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BuilderTime extends CommandBuilder
{
	public BuilderTime()
	{
		
	}
	
	public BuilderTime(EnumMode mode)
	{
		this.setMode(mode);
	}
	
	public BuilderTime(EnumMode mode, int value)
	{
		this(mode);
		this.setValue(value);
	}
	
	public void setMode(EnumMode mode)
	{
		this.setNode(0, mode.toString());
	}
	
	public void setValue(int value)
	{
		this.setNode(1, value);
	}
	
	@Override
	public String getCommandName()
	{
		return "time";
	}
	
	@Override
	public final Syntax getSyntax()
	{
		Syntax syntax = new Syntax();
		
		syntax.addRequired("set|add|query", Type.STRING);
		syntax.addOptional("value", Type.INT);
		
		return syntax;
	}
	
	@SideOnly(Side.CLIENT)
	public static enum EnumMode
	{
		ADD,
		SET,
		QUERY;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
}
