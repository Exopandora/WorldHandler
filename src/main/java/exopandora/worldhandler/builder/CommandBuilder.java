package exopandora.worldhandler.builder;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

public abstract class CommandBuilder implements ICommandBuilder
{
	protected abstract CommandNodeLiteral root();
	
	public String toCommand(Object label, boolean preview)
	{
		CommandNode<?> node = this.root().find(label).orElse(this.root());
		
		if(preview)
		{
			return this.buildForward(node, label, CommandNode::toValue).toString();
		}
		
		return this.buildReverse(node, label, CommandNode::toValue, false).toString();
	}
	
	protected StringBuilder buildForward(CommandNode<?> node, Object label, BiFunction<CommandNode<?>, Object, String> valueMapper)
	{
		StringBuilder builder = this.buildReverse(node, label, valueMapper, true);
		
		if(builder.isEmpty())
		{
			return builder;
		}
		
		if(node != null)
		{
			List<CommandNode<?>> children = node.getChildren();
			
			while(children != null && children.size() == 1)
			{
				node = children.get(0);
				builder.append(" ");
				builder.append(valueMapper.apply(children.get(0), label));
				children = node.getChildren();
			}
			
			if(children != null && children.size() > 0)
			{
				builder.append(children.stream().map(child -> valueMapper.apply(child, label)).collect(Collectors.joining("|", " [", "]")));
			}
		}
		
		return builder;
	}
	
	protected StringBuilder buildReverse(@Nullable CommandNode<?> node, Object label, BiFunction<CommandNode<?>, Object, String> valueMapper, boolean includeOptionals)
	{
		StringBuilder builder = new StringBuilder();
		
		while(node != null)
		{
			CommandNode<?> parent = node.getParent();
			
			if(parent == null)
			{
				builder.insert(0, "/" + valueMapper.apply(node, label));
			}
			else if(includeOptionals || !builder.isEmpty() || !node.isOptional(label) || !node.isDefault(label))
			{
				builder.insert(0, " " + valueMapper.apply(node, label));
			}
			
			node = parent;
		}
		
		return builder;
	}
}
