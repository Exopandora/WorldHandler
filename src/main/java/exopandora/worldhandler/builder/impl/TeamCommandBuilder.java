package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.PrimitiveArgument;
import exopandora.worldhandler.builder.argument.TargetArgument;
import net.minecraft.network.chat.Component;

public class TeamCommandBuilder extends CommandBuilder
{
	private final PrimitiveArgument<String> team = Arguments.word();
	private final PrimitiveArgument<Component> displayName = Arguments.textComponent();
	private final TargetArgument members = Arguments.target();
	private final PrimitiveArgument<String> option = Arguments.word();
	private final PrimitiveArgument<String> value = Arguments.string();
	
	private final CommandNodeLiteral root = CommandNode.literal("team")
			.then(CommandNode.literal("add")
					.then(CommandNode.argument("team", this.team)
							.label(Label.ADD)
							.then(CommandNode.argument("displayName", this.displayName)
									.label(Label.ADD_DISPLAYNAME))))
			.then(CommandNode.literal("empty")
					.then(CommandNode.argument("team", this.team)
							.label(Label.EMPTY)))
			.then(CommandNode.literal("join")
					.then(CommandNode.argument("team", this.team)
							.then(CommandNode.argument("members", this.members)
									.label(Label.JOIN))))
			.then(CommandNode.literal("leave")
					.then(CommandNode.argument("members", this.members)
							.label(Label.LEAVE)))
			.then(CommandNode.literal("list")
					.label(Label.LIST)
					.then(CommandNode.argument("team", this.team)
							.label(Label.LIST_TEAM)))
			.then(CommandNode.literal("modify")
					.then(CommandNode.argument("team", this.team)
							.then(CommandNode.argument("option", this.option)
									.then(CommandNode.argument("value", this.value)
											.label(Label.MODIFY)))))
			.then(CommandNode.literal("remove")
					.then(CommandNode.argument("team", this.team)
							.label(Label.REMOVE)));
	
	public PrimitiveArgument<String> team()
	{
		return this.team;
	}
	
	public PrimitiveArgument<Component> displayName()
	{
		return this.displayName;
	}
	
	public TargetArgument members()
	{
		return this.members;
	}
	
	public PrimitiveArgument<String> option()
	{
		return this.option;
	}
	
	public PrimitiveArgument<String> value()
	{
		return this.value;
	}
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
	
	public static enum Label
	{
		ADD,
		ADD_DISPLAYNAME,
		EMPTY,
		JOIN,
		LEAVE,
		LIST,
		LIST_TEAM,
		MODIFY,
		REMOVE;
	}
}
