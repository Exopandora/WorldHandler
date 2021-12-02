package exopandora.worldhandler.builder;

import exopandora.worldhandler.builder.argument.IArgument;

public class CommandNodeArgument extends CommandNode<CommandNodeArgument>
{
	private final IArgument argument;
	
	protected CommandNodeArgument(String name, IArgument value)
	{
		super(name);
		this.argument = value;
	}
	
	@Override
	public String toKey(Object label)
	{
		if(this.isOptional(label))
		{
			return "[" + this.getName() + "]";
		}
		
		return "<" + this.getName() + ">";
	}
	
	@Override
	public String toValue(Object label)
	{
		if(this.argument == null || this.argument.isDefault())
		{
			return this.toKey(label);
		}
		
		String argument = this.argument.serialize();
		
		if(argument != null)
		{
			return argument;
		}
		
		return this.toKey(label);
	}
	
	@Override
	public boolean isDefault(Object label)
	{
		if(this.argument != null)
		{
			return this.argument.isDefault();
		}
		
		return true;
	}
	
	@Override
	protected CommandNodeArgument getThis()
	{
		return this;
	}
}