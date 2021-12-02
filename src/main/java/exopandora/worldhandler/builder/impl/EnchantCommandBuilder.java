package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.EnchantmentArgument;
import exopandora.worldhandler.builder.argument.PrimitiveArgument;
import exopandora.worldhandler.builder.argument.TargetArgument;

public class EnchantCommandBuilder extends CommandBuilder
{
	private final TargetArgument target = Arguments.target();
	private final EnchantmentArgument enchantment = Arguments.enchantment();
	private final PrimitiveArgument<Integer> level = Arguments.intArg();
	
	private final CommandNodeLiteral root = CommandNode.literal("enchant")
			.then(CommandNode.argument("target", this.target)
					.then(CommandNode.argument("enchantment", this.enchantment)
							.label(Label.ENCHANT)
							.then(CommandNode.argument("level", this.level)
									.label(Label.ENCHANT_LEVEL))));
	
	public TargetArgument target()
	{
		return this.target;
	}
	
	public EnchantmentArgument enchantment()
	{
		return this.enchantment;
	}
	
	public PrimitiveArgument<Integer> level()
	{
		return this.level;
	}
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
	
	public static enum Label
	{
		ENCHANT,
		ENCHANT_LEVEL;
	}
}
