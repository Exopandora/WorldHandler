package exopandora.worldhandler.builder.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.IDeserializableArgument;
import exopandora.worldhandler.usercontent.factory.ArgumentFactory;
import exopandora.worldhandler.usercontent.model.ArgumentType;
import exopandora.worldhandler.usercontent.model.JsonArgument;

public class UsercontentCommandBuilder extends CommandBuilder
{
	private final Map<String, IDeserializableArgument> arguments = new HashMap<String, IDeserializableArgument>();
	private final Set<IDeserializableArgument> player = new HashSet<IDeserializableArgument>();
	private final CommandNodeLiteral root;
	private final String label;
	
	public UsercontentCommandBuilder(JsonArgument argument, String label)
	{
		this.root = CommandNode.literal(argument.getName()).label(argument.getLabel());
		this.label = label;
		this.createChildren(this.root, argument.getChildren());
	}
	
	private void createChildren(CommandNode<?> root, List<JsonArgument> children)
	{
		if(children != null)
		{
			for(JsonArgument child : children)
			{
				CommandNode<?> node = this.createNode(child);
				this.createChildren(node, child.getChildren());
				root.then(node);
			}
		}
	}
	
	private CommandNode<?> createNode(JsonArgument json)
	{
		if(json.getType() == null)
		{
			return CommandNode.literal(json.getName()).label(json.getLabel());
		}
		
		IDeserializableArgument argument = this.arguments.computeIfAbsent(json.getName(), key -> ArgumentFactory.createArgument(json));
		
		if(ArgumentType.PLAYER.equals(json.getType()))
		{
			this.player.add(argument);
		}
		
		return CommandNode.argument(json.getName(), argument).label(json.getLabel());
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public void setPlayerName(String username)
	{
		this.player.forEach(argument -> argument.deserialize(username));
	}
	
	@Nullable
	public IDeserializableArgument getArgument(String name)
	{
		return this.arguments.get(name);
	}
	
	public void setArgument(String key, String value)
	{
		IDeserializableArgument argument = this.arguments.get(key);
		
		if(argument != null)
		{
			argument.deserialize(value);
		}
	}
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
}
