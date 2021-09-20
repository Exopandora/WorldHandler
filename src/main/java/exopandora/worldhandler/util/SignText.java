package exopandora.worldhandler.util;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.INBTWritable;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.ClickEvent.Action;

public class SignText implements INBTWritable
{
	private MutableTextComponent text = new MutableTextComponent();
	private final int line;
	
	public SignText(int line)
	{
		this.line = line;
	}
	
	public int getLine()
	{
		return this.line;
	}
	
	public MutableTextComponent getString()
	{
		return this.text;
	}
	
	public void setString(MutableTextComponent string)
	{
		this.text = string;
	}
	
	public void setCommand(String command)
	{
		if(command != null && !command.isEmpty())
		{
			this.text.getStyle().withClickEvent(new ClickEvent(Action.RUN_COMMAND, command));
		}
		else
		{
			this.text.getStyle().withClickEvent(null);
		}
	}
	
	@Nullable
	public String getCommand()
	{
		if(this.hasCommand())
		{
			return this.text.getStyle().getClickEvent().getValue();
		}
		
		return null;
	}
	
	public boolean hasCommand()
	{
		return this.text.getStyle().getClickEvent() != null && this.text.getStyle().getClickEvent().getAction() == Action.RUN_COMMAND && this.text.getStyle().getClickEvent().getValue() != null;
	}
	
	@Override
	public Tag serialize()
	{
		return StringTag.valueOf(this.toString());
	}
	
	@Override
	public String toString()
	{
		if(this.text.getContents().isEmpty())
		{
			return this.text.getContents();
		}
		
		if(this.text.getStyle().isEmpty() && !this.hasCommand())
		{
			return this.text.getString();
		}
		
		return this.text.toString();
	}
}
