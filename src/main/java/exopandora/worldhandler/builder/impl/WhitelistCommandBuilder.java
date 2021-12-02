package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.TargetArgument;

public class WhitelistCommandBuilder extends CommandBuilder
{
	private final TargetArgument targets = Arguments.target();
	
	private final CommandNodeLiteral root = CommandNode.literal("whitelist")
			.then(CommandNode.literal("add")
					.then(CommandNode.argument("target", this.targets)
							.label(Label.ADD)))
			.then(CommandNode.literal("remove")
					.then(CommandNode.argument("target", this.targets)
							.label(Label.REMOVE)))
			.then(CommandNode.literal("reload")
					.label(Label.RELOAD))
			.then(CommandNode.literal("on")
					.label(Label.ON))
			.then(CommandNode.literal("off")
					.label(Label.OFF))
			.then(CommandNode.literal("list")
					.label(Label.LIST));
	
	public TargetArgument targets()
	{
		return this.targets;
	}
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
	
	public static enum Label
	{
		ADD,
		REMOVE,
		RELOAD,
		ON,
		OFF,
		LIST;
	}
}
