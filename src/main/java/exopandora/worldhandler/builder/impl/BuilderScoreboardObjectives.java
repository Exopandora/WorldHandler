package exopandora.worldhandler.builder.impl;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.impl.abstr.BuilderScoreboard;
import exopandora.worldhandler.builder.types.Type;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
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
	
	public void setMode(String mode)
	{
		String objective = this.getObjective();
		
		if(mode.equals("add") || mode.equals("remove") || mode.equals("setdisplay"))
		{
			this.updateSyntax(this.getSyntax(mode));
			this.setNode(1, mode);
			
			if(objective != null)
			{
				this.setObjective(objective);
			}
			
			this.init();
		}
	}
	
	public String getMode()
	{
		return this.getNodeAsString(1);
	}
	
	public void setObjective(String name)
	{
		String mode = this.getMode();
		String objective = name != null ? name.replaceAll(" ", "_") : null;
		
		if(mode != null)
		{
			if(mode.equals("add") || mode.equals("remove"))
			{
				this.setNode(2, objective);
				
				if(mode.equals("add"))
				{
					this.setNode(4, name);
				}
			}
			else if(mode.equals("setdisplay"))
			{
				this.setNode(3, objective);
			}
		}
	}
	
	public void setCriteria(String criteria)
	{
		if(this.getMode() == null || !this.getMode().equals("add"))
		{
			this.setMode("add");
		}
		this.setNode(3, criteria.replaceAll("[:]", "."));
	}
	
	public void setSlot(String slot)
	{
		if(this.getMode() == null || !this.getMode().equals("setdisplay"))
		{
			this.setMode("setdisplay");
		}
		this.setNode(2, slot);
	}
	
	@Nullable
	public String getSlot()
	{
		if(this.getMode() != null && this.getMode().equals("setdisplay"))
		{
			return this.getNodeAsString(2);
		}
		
		return null;
	}
	
	@Nullable
	public String getObjective()
	{
		String mode = this.getMode();
		
		if(mode != null)
		{
			if(mode.equals("add") || mode.equals("remove"))
			{
				return this.getNodeAsString(2);
			}
			else if(mode.equals("setdisplay"))
			{
				return this.getNodeAsString(3);
			}
		}
		
		return null;
	}
	
	@Nullable
	private Syntax getSyntax(String mode)
	{
		if(mode.equals("add"))
		{
			Syntax syntax = new Syntax();
			
			syntax.addRequired("objectives", Type.STRING);
			syntax.addRequired("add", Type.STRING);
			syntax.addRequired("name", Type.STRING);
			syntax.addRequired("criteria_type", Type.STRING);
			syntax.addOptional("display_name...", Type.STRING);
			
			return syntax;
		}
		else if(mode.equals("remove"))
		{
			Syntax syntax = new Syntax();
			
			syntax.addRequired("objectives", Type.STRING);
			syntax.addRequired("remove", Type.STRING);
			syntax.addRequired("name", Type.STRING);
			
			return syntax;
		}
		else if(mode.equals("setdisplay"))
		{
			Syntax syntax = new Syntax();
			
			syntax.addRequired("objectives", Type.STRING);
			syntax.addRequired("setdisplay", Type.STRING);
			syntax.addRequired("slot", Type.STRING);
			syntax.addOptional("objective", Type.STRING);
			
			return syntax;
		}
		
		return null;
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
}
