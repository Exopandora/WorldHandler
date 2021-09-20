package exopandora.worldhandler.builder.impl;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.component.impl.ComponentTag;
import exopandora.worldhandler.util.MutableTextComponent;
import exopandora.worldhandler.util.SignText;

public class BuilderSignEditor extends BuilderData
{
	@SuppressWarnings("unchecked")
	private final ComponentTag<SignText>[] sign = new ComponentTag[4];
	
	public BuilderSignEditor()
	{
		this.setMode(EnumMode.MERGE);
		this.setTarget(EnumTarget.BLOCK);
		
		for(int x = 0; x < 4; x++)
		{
			this.sign[x] = this.registerNBTComponent(new ComponentTag<SignText>("Text" + (x + 1), new SignText(x), SignText::serialize));
		}
	}
	
	public boolean isSpecial()
	{
		boolean special = false;
		
		for(int x = 0; x < this.sign.length; x++)
		{
			special = special || this.getColoredString(x).isSpecial();
		}
		
		return special;
	}
	
	@Nullable
	public MutableTextComponent getColoredString(int line)
	{
		if(this.checkBounds(line))
		{
			return this.sign[line].getValue().getString();
		}
		
		return null;
	}
	
	@Nullable
	public String getCommand(int line)
	{
		if(this.checkBounds(line))
		{
			return this.sign[line].getValue().getCommand();
		}
		
		return null;
	}
	
	@Nullable
	public void setCommand(int line, String command)
	{
		if(this.checkBounds(line))
		{
			this.sign[line].getValue().setCommand(command);
		}
	}
	
	private boolean checkBounds(int line)
	{
		return line >= 0 && line < this.sign.length;
	}
}
