package exopandora.worldhandler.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.argument.IArgument;

public abstract class CommandNode<T extends CommandNode<T>>
{
	private final String name;
	private CommandNode<?> parent;
	private List<CommandNode<?>> children;
	private Object label;
	
	protected CommandNode(String name)
	{
		if(name == null || name.isBlank())
		{
			throw new IllegalArgumentException("Name cannot be null or blank");
		}
		
		this.name = name;
	}
	
	public T then(CommandNode<?> node)
	{
		if(this.children == null)
		{
			this.children = new ArrayList<CommandNode<?>>();
		}
		
		node.parent = this.getThis();
		this.children.add(node);
		return this.getThis();
	}
	
	public T label(Object label)
	{
		this.label = label;
		return this.getThis();
	}
	
	public Optional<CommandNode<?>> find(Object label)
	{
		if(label != null && label.equals(this.label))
		{
			return Optional.of(this.getThis());
		}
		
		if(this.hasChildren())
		{
			for(CommandNode<?> child : this.children)
			{
				Optional<CommandNode<?>> result = child.find(label);
				
				if(result.isPresent())
				{
					return result;
				}
			}
		}
		
		return Optional.empty();
	}
	
	public String getName()
	{
		return this.name;
	}
	
	@Nullable
	public Object getLabel()
	{
		return this.label;
	}
	
	public boolean isOptional(Object label)
	{
		return this.parent.label != null && (this.label != null && this.label.equals(label) || this.parent.label.equals(label));
	}
	
	@Nullable
	public CommandNode<?> getParent()
	{
		return this.parent;
	}
	
	@Nullable
	public List<CommandNode<?>> getChildren()
	{
		return this.children;
	}
	
	public boolean hasChildren()
	{
		return this.children != null && !this.children.isEmpty();
	}
	
	public abstract boolean isDefault(Object label);
	
	public abstract String toKey(Object label);
	
	public abstract String toValue(Object label);
	
	protected abstract T getThis();
	
	public static CommandNodeLiteral literal(String label)
	{
		return new CommandNodeLiteral(label);
	}
	
	public static CommandNodeArgument argument(String label, IArgument argument)
	{
		return new CommandNodeArgument(label, argument);
	}
}