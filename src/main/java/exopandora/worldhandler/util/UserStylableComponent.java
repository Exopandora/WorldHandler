package exopandora.worldhandler.util;

import java.util.Objects;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.ClickEvent.Action;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.LiteralContents;

public class UserStylableComponent extends MutableComponent
{
	private String text = "";
	
	public UserStylableComponent()
	{
		super(ComponentContents.EMPTY, Lists.newArrayList(), Style.EMPTY);
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public String getText()
	{
		return this.text;
	}
	
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
		return this.getStyle().getClickEvent() != null && Action.RUN_COMMAND.equals(this.getStyle().getClickEvent().getAction()) && this.getStyle().getClickEvent().getValue() != null;
	}
	
	@Override
	public ComponentContents getContents()
	{
		if(this.isStyled())
		{
			return new LiteralContents(applyStyleFormatting(this.text));
		}
		
		return new LiteralContents(this.text);
	}
	
	public boolean isStyled()
	{
		return this.text != null && !this.text.isEmpty() && applyStyleFormatting(this.text).contains("\u00A7");
	}
	
	private static String applyStyleFormatting(String text)
	{
		String result = text.replaceAll("\u0026", "\u00A7").replaceAll("\u00A7\u00A7", "\u0026");
		
		if(result.contains("\u00A7"))
		{
			result += ChatFormatting.RESET;
		}
		
		return result;
	}
	
	@Override
	public boolean equals(Object object)
	{
		if(this == object)
		{
			return true;
		}
		else if(object instanceof UserStylableComponent component)
		{
			return this.text.equals(component.text) && this.getStyle().equals(component.getStyle()) && this.getSiblings().equals(component.getSiblings());
		}
		
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(this.text, this.getStyle(), this.getSiblings());
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder(this.text);
		boolean hasStyle = !this.getStyle().isEmpty();
		boolean hasSiblings = !this.getSiblings().isEmpty();
		
		if(hasStyle || hasSiblings)
		{
			builder.append('[');
			
			if(hasStyle)
			{
				builder.append("style=");
				builder.append(this.getStyle());
			}
			
			if(hasStyle && hasSiblings)
			{
				builder.append(", ");
			}
			
			if(hasSiblings)
			{
				builder.append("siblings=");
				builder.append(this.getSiblings());
			}
			
			builder.append(']');
		}
		
		return builder.toString();
	}
}
