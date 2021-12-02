package exopandora.worldhandler.builder;

public class CommandNodeLiteral extends CommandNode<CommandNodeLiteral>
{
	protected CommandNodeLiteral(String literal)
	{
		super(literal);
	}
	
	@Override
	public boolean isDefault(Object label)
	{
		return false;
	}
	
	@Override
	public String toKey(Object label)
	{
		return this.getName();
	}
	
	@Override
	public String toValue(Object label)
	{
		return this.getName();
	}
	
	@Override
	protected CommandNodeLiteral getThis()
	{
		return this;
	}
}