package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.PrimitiveArgument;
import exopandora.worldhandler.builder.argument.TargetArgument;
import net.minecraft.resources.ResourceLocation;

public class AdvancementCommandBuilder extends CommandBuilder
{
	private final TargetArgument targets = Arguments.target();
	private final PrimitiveArgument<ResourceLocation> advancement = Arguments.resourceLocation();
	private final PrimitiveArgument<String> criterion = Arguments.greedyString();
	
	private final CommandNodeLiteral root = CommandNode.literal("advancement")
			.then(CommandNode.literal("grant")
					.then(CommandNode.argument("targets", this.targets)
							.then(CommandNode.literal("only")
									.then(CommandNode.argument("advancement", this.advancement)
											.label(Label.GRANT_ONLY)
											.then(CommandNode.argument("criterion", this.criterion)
													.label(Label.GRANT_ONLY_CRITERION))))
							.then(CommandNode.literal("from")
									.then(CommandNode.argument("advancement", this.advancement)
											.label(Label.GRANT_FROM)))
							.then(CommandNode.literal("until")
									.then(CommandNode.argument("advancement", this.advancement)
											.label(Label.GRANT_UNTIL)))
							.then(CommandNode.literal("through")
									.then(CommandNode.argument("advancement", this.advancement)
											.label(Label.GRANT_THROUGH)))
							.then(CommandNode.literal("everything")
									.label(Label.GRANT_EVERYTHING))))
			.then(CommandNode.literal("revoke")
					.then(CommandNode.argument("targets", this.targets)
							.then(CommandNode.literal("only")
									.then(CommandNode.argument("advancement", this.advancement)
											.label(Label.REVOKE_ONLY)
											.then(CommandNode.argument("criterion", this.criterion)
													.label(Label.REVOKE_ONLY_CRITERION))))
							.then(CommandNode.literal("from")
									.then(CommandNode.argument("advancement", this.advancement)
											.label(Label.REVOKE_FROM)))
							.then(CommandNode.literal("until")
									.then(CommandNode.argument("advancement", this.advancement)
											.label(Label.REVOKE_UNTIL)))
							.then(CommandNode.literal("through")
									.then(CommandNode.argument("advancement", this.advancement)
											.label(Label.REVOKE_THROUGH)))
							.then(CommandNode.literal("everything")
									.label(Label.REVOKE_EVERYTHING))));
	
	public TargetArgument targets()
	{
		return this.targets;
	}
	
	public PrimitiveArgument<ResourceLocation> advancement()
	{
		return this.advancement;
	}
	
	public PrimitiveArgument<String> criterion()
	{
		return this.criterion;
	}
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
	
	public static enum Label
	{
		GRANT_ONLY,
		GRANT_ONLY_CRITERION,
		GRANT_FROM,
		GRANT_UNTIL,
		GRANT_THROUGH,
		GRANT_EVERYTHING,
		REVOKE_ONLY,
		REVOKE_ONLY_CRITERION,
		REVOKE_FROM,
		REVOKE_UNTIL,
		REVOKE_THROUGH,
		REVOKE_EVERYTHING;
	}
}
