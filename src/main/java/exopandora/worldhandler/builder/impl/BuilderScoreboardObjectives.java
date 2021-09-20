package exopandora.worldhandler.builder.impl;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.types.ArgumentType;
import exopandora.worldhandler.builder.types.GreedyString;
import exopandora.worldhandler.util.EnumHelper;

public class BuilderScoreboardObjectives extends BuilderScoreboard
{
	public BuilderScoreboardObjectives()
	{
		this.init();
	}
	
	private void init()
	{
		this.setNode(0, "objectives");
	}
	
	public void setMode(EnumMode mode)
	{
		String objective = this.getObjective();
		
		this.updateSyntax(this.getSyntax(mode));
		this.setNode(1, mode.toString());
		
		if(objective != null)
		{
			this.setObjective(objective);
		}
		
		this.init();
	}
	
	@Nullable
	public EnumMode getMode()
	{
		return EnumHelper.valueOf(this.getNodeAsString(1), EnumMode.class);
	}
	
	public void setObjective(String name)
	{
		String objective = name != null ? name.replaceAll(" ", "_") : null;
		
		EnumMode mode = this.getMode();
		
		if(mode != null)
		{
			switch(mode)
			{
				case ADD:
					this.setNode(4, new GreedyString(name));
				case REMOVE:
					this.setNode(2, objective);
					break;
				case SETDISPLAY:
					this.setNode(3, objective);
					break;
				default:
					break;
			}
		}
	}
	
	public void setCriteria(String criteria)
	{
		if(this.getMode() == null || !this.getMode().equals(EnumMode.ADD))
		{
			this.setMode(EnumMode.ADD);
		}
		
		this.setNode(3, criteria);
	}
	
	public void setSlot(String slot)
	{
		if(this.getMode() == null || !this.getMode().equals(EnumMode.SETDISPLAY))
		{
			this.setMode(EnumMode.SETDISPLAY);
		}
		
		this.setNode(2, slot);
	}
	
	@Nullable
	public String getSlot()
	{
		if(this.getMode() != null && this.getMode().equals(EnumMode.SETDISPLAY))
		{
			return this.getNodeAsString(2);
		}
		
		return null;
	}
	
	@Nullable
	public String getObjective()
	{
		EnumMode mode = this.getMode();
		
		if(mode != null)
		{
			switch(mode)
			{
				case ADD: case REMOVE:
					return this.getNodeAsString(2);
				case SETDISPLAY:
					return this.getNodeAsString(3);
				default:
					break;
			}
		}
		
		return null;
	}
	
	@Nullable
	private CommandSyntax getSyntax(EnumMode mode)
	{
		CommandSyntax syntax = new CommandSyntax();
		
		switch(mode)
		{
			case ADD:
				syntax.addRequired("objectives", ArgumentType.STRING);
				syntax.addRequired("add", ArgumentType.STRING);
				syntax.addRequired("name", ArgumentType.STRING);
				syntax.addRequired("criteria_type", ArgumentType.STRING);
				syntax.addOptional("display_name...", ArgumentType.GREEDY_STRING);
				return syntax;
			case REMOVE:
				syntax.addRequired("objectives", ArgumentType.STRING);
				syntax.addRequired("remove", ArgumentType.STRING);
				syntax.addRequired("name", ArgumentType.STRING);
				return syntax;
			case SETDISPLAY:
				syntax.addRequired("objectives", ArgumentType.STRING);
				syntax.addRequired("setdisplay", ArgumentType.STRING);
				syntax.addRequired("slot", ArgumentType.STRING);
				syntax.addOptional("objective", ArgumentType.STRING);
				return syntax;
			default:
				return null;
		}
	}
	
	@Override
	public final CommandSyntax getSyntax()
	{
		CommandSyntax syntax = new CommandSyntax();
		
		syntax.addRequired("objectives", ArgumentType.STRING);
		syntax.addRequired("list|add|remove|setdisplay", ArgumentType.STRING);
		syntax.addOptional("...", ArgumentType.STRING);
		
		return syntax;
	}
	
	public static enum EnumMode
	{
		ADD,
		REMOVE,
		SETDISPLAY;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
}
