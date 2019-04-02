package exopandora.worldhandler.builder.impl;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.impl.abstr.BuilderScoreboard;
import exopandora.worldhandler.builder.types.GreedyString;
import exopandora.worldhandler.builder.types.Type;
import exopandora.worldhandler.helper.EnumHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
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
	private Syntax getSyntax(EnumMode mode)
	{
		Syntax syntax = new Syntax();
		
		switch(mode)
		{
			case ADD:
				syntax.addRequired("objectives", Type.STRING);
				syntax.addRequired("add", Type.STRING);
				syntax.addRequired("name", Type.STRING);
				syntax.addRequired("criteria_type", Type.STRING);
				syntax.addOptional("display_name...", Type.GREEDY_STRING);
				return syntax;
			case REMOVE:
				syntax.addRequired("objectives", Type.STRING);
				syntax.addRequired("remove", Type.STRING);
				syntax.addRequired("name", Type.STRING);
				return syntax;
			case SETDISPLAY:
				syntax.addRequired("objectives", Type.STRING);
				syntax.addRequired("setdisplay", Type.STRING);
				syntax.addRequired("slot", Type.STRING);
				syntax.addOptional("objective", Type.STRING);
				return syntax;
			default:
				return null;
		}
	}
	
	@Override
	public final Syntax getSyntax()
	{
		Syntax syntax = new Syntax();
		
		syntax.addRequired("objectives", Type.STRING);
		syntax.addRequired("list|add|remove|setdisplay", Type.STRING);
		syntax.addOptional("...", Type.STRING);
		
		return syntax;
	}
	
	@OnlyIn(Dist.CLIENT)
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
