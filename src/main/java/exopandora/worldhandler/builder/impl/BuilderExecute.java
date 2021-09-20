package exopandora.worldhandler.builder.impl;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.types.ArgumentType;
import exopandora.worldhandler.util.EnumHelper;

public class BuilderExecute extends CommandBuilder
{
	public void setMode1(EnumMode mode)
	{
		this.setNode(0, mode.toString());
	}
	
	@Nullable
	public EnumMode getMode1()
	{
		return EnumHelper.valueOf(this.getNodeAsString(0), EnumMode.class);
	}
	
	public void setTarget(String target)
	{
		this.setNode(1, target);
	}
	
	@Nullable
	public String getTarget()
	{
		return this.getNodeAsString(1);
	}
	
	public void setMode2(EnumMode mode)
	{
		this.setNode(2, mode.toString());
	}
	
	@Nullable
	public EnumMode getMode2()
	{
		return EnumHelper.valueOf(this.getNodeAsString(2), EnumMode.class);
	}
	
	public void setCommand(String command)
	{
		if(command != null && command.startsWith("/"))
		{
			this.setNode(3, command.substring(1));
		}
		else
		{
			this.setNode(3, command);
		}
	}
	
	@Nullable
	public String getCommand()
	{
		return this.getNodeAsString(3);
	}
	
	@Override
	public String getCommandName()
	{
		return "execute";
	}
	
	@Override
	public CommandSyntax getSyntax()
	{
		CommandSyntax syntax = new CommandSyntax();
		
		syntax.addRequired("modifier", ArgumentType.STRING);
		syntax.addRequired("targets", ArgumentType.STRING);
		syntax.addRequired("action", ArgumentType.STRING);
		syntax.addRequired("command", ArgumentType.STRING);
		
		return syntax;
	}
	
	public static enum EnumMode
	{
		ALIGN,
		ANCHORED,
		AS,
		AT,
		IN,
		RUN;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
}
