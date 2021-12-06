package exopandora.worldhandler.util;

import javax.annotation.Nullable;

import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.ClickEvent.Action;

public class SignText extends MutableTextComponent
{
	public void setCommand(String command)
	{
		if(command != null && !command.isEmpty())
		{
			this.setStyle(this.getStyle().withClickEvent(new ClickEvent(Action.RUN_COMMAND, command)));
		}
		else
		{
			this.setStyle(this.getStyle().withClickEvent(null));
		}
	}
	
	@Nullable
	public String getCommand()
	{
		if(this.hasCommand())
		{
			return this.getStyle().getClickEvent().getValue();
		}
		
		return null;
	}
	
	public boolean hasCommand()
	{
		return this.getStyle().getClickEvent() != null && this.getStyle().getClickEvent().getAction() == Action.RUN_COMMAND && this.getStyle().getClickEvent().getValue() != null;
	}
	
	@Override
	public Tag serialize()
	{
		return StringTag.valueOf(this.toString());
	}
}
