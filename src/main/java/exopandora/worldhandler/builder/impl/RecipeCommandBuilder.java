package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.PrimitiveArgument;
import exopandora.worldhandler.builder.argument.TargetArgument;
import net.minecraft.resources.ResourceLocation;

public class RecipeCommandBuilder extends CommandBuilder
{
	private final TargetArgument targets = Arguments.target();
	private final PrimitiveArgument<ResourceLocation> recipe = Arguments.resourceLocation();
	
	private final CommandNodeLiteral root = CommandNode.literal("recipe")
			.then(CommandNode.literal("give")
					.then(CommandNode.argument("targets", this.targets)
							.then(CommandNode.argument("recipe", this.recipe)
									.label(Label.GIVE))
							.then(CommandNode.literal("*")
									.label(Label.GIVE_ALL))))
			.then(CommandNode.literal("take")
					.then(CommandNode.argument("targets", this.targets)
							.then(CommandNode.argument("recipe", this.recipe)
									.label(Label.TAKE))
							.then(CommandNode.literal("*")
									.label(Label.TAKE_ALL))));
	
	public TargetArgument targets()
	{
		return this.targets;
	}
	
	public PrimitiveArgument<ResourceLocation> recipe()
	{
		return this.recipe;
	}
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
	
	public static enum Label
	{
		GIVE,
		GIVE_ALL,
		TAKE,
		TAKE_ALL;
	}
}
