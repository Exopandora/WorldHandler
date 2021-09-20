package exopandora.worldhandler.builder.impl;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.types.ArgumentType;
import exopandora.worldhandler.util.EnumHelper;

public class BuilderTrigger extends CommandBuilder
{
	public BuilderTrigger()
	{
		this.setValue(0);
	}
	
	public BuilderTrigger(String objective, EnumMode mode, int value)
	{
		this.setObjective(objective);
		this.setMode(mode);
		this.setValue(value);
	}
	
	public void setObjective(String name)
	{
		this.setNode(0, name != null ? name.replaceAll(" ", "_") : null);
	}
	
	@Nullable
	public String getObjective()
	{
		return this.getNodeAsString(0);
	}
	
	public void setMode(EnumMode mode)
	{
		if(mode != null)
		{
			this.setNode(1, mode.toString());
		}
	}
	
	@Nullable
	public EnumMode getMode()
	{
		return EnumHelper.valueOf(this.getNodeAsString(1), EnumMode.class);
	}
	
	public void setValue(int value)
	{
		this.setNode(2, value);
	}
	
	public int getValue()
	{
		return this.getNodeAsInt(2);
	}
	
	@Override
	public String getCommandName()
	{
		return "trigger";
	}
	
	@Override
	public CommandSyntax getSyntax()
	{
		CommandSyntax syntax = new CommandSyntax();
		
		syntax.addRequired("objective", ArgumentType.STRING);
		syntax.addRequired("add|set", ArgumentType.STRING);
		syntax.addRequired("value", ArgumentType.INT);
		
		return syntax;
	}
	
	public BuilderTrigger build(EnumMode mode)
	{
		return new BuilderTrigger(this.getObjective(), mode, this.getValue());
	}
	
	public static enum EnumMode
	{
		ADD,
		SET;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
}
