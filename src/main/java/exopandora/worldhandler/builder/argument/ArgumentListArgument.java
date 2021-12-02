package exopandora.worldhandler.builder.argument;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.CommandBuilder;

public class ArgumentListArgument implements IArgument
{
	private final List<OptionalCommandBuilder<?>> arguments = new ArrayList<OptionalCommandBuilder<?>>();
	
	public void add(OptionalCommandBuilder<?> argument)
	{
		this.arguments.add(argument);
	}
	
	@Override
	@Nullable
	public String serialize()
	{
		if(this.arguments.isEmpty())
		{
			return null;
		}
		
		return this.arguments.stream().map(builder -> builder.toCommand(builder.getLabel(), false)).collect(Collectors.joining(" "));
	}
	
	@Override
	public boolean isDefault()
	{
		return this.arguments.isEmpty();
	}
	
	public abstract static class OptionalCommandBuilder<T> extends CommandBuilder
	{
		private T label;
		
		public OptionalCommandBuilder(T label)
		{
			this.label = label;
		}
		
		public void setLabel(T label)
		{
			this.label = label;
		}
		
		public T getLabel()
		{
			return this.label;
		}
		
		@Override
		public String toCommand(Object label, boolean preview)
		{
			return super.toCommand(label, preview).substring(1);
		}
	}
}
