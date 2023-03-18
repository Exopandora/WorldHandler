package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.EffectArgument;
import exopandora.worldhandler.builder.argument.PrimitiveArgument;
import exopandora.worldhandler.builder.argument.TargetArgument;

public class EffectCommandBuilder extends CommandBuilder
{
	private final TargetArgument targets = Arguments.target();
	private final EffectArgument effect = Arguments.effect();
	private final PrimitiveArgument<Integer> seconds = Arguments.intArg();
	private final PrimitiveArgument<Byte> amplifier = Arguments.byteArg();
	private final PrimitiveArgument<Boolean> hideParticles = Arguments.boolArg();
	
	private final CommandNodeLiteral root = CommandNode.literal("effect")
			.then(CommandNode.literal("give")
					.then(CommandNode.argument("targets", this.targets)
							.then(CommandNode.argument("effect", this.effect)
									.label(Label.GIVE)
									.then(CommandNode.argument("seconds", this.seconds)
											.label(Label.GIVE_SECONDS)
											.then(CommandNode.argument("amplifier", this.amplifier)
													.label(Label.GIVE_SECONDS_AMPLIFIER)
													.then(CommandNode.argument("hideParticles", this.hideParticles)
															.label(Label.GIVE_SECONDS_AMPLIFIER_HIDEPARTICLES))))
									.then(CommandNode.literal("infinite")
											.label(Label.GIVE_INFINITE)
											.then(CommandNode.argument("amplifier", this.amplifier)
													.label(Label.GIVE_INFINITE_AMPLIFIER)
													.then(CommandNode.argument("hideParticles", this.hideParticles)
															.label(Label.GIVE_INFINITE_AMPLIFIER_HIDEPARTICLES)))))))
			.then(CommandNode.literal("clear")
					.label(Label.CLEAR)
					.then(CommandNode.argument("targets", this.targets)
							.label(Label.CLEAR_TARGETS)
							.then(CommandNode.argument("effect", this.effect)
									.label(Label.CLEAR_TARGETS_EFFECT))));
	
	public TargetArgument targets()
	{
		return this.targets;
	}
	
	public EffectArgument effect()
	{
		return this.effect;
	}
	
	public PrimitiveArgument<Integer> seconds()
	{
		return this.seconds;
	}
	
	public PrimitiveArgument<Byte> amplifier()
	{
		return this.amplifier;
	}
	
	public PrimitiveArgument<Boolean> hideParticles()
	{
		return this.hideParticles;
	}
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
	
	public static enum Label
	{
		GIVE,
		GIVE_SECONDS,
		GIVE_SECONDS_AMPLIFIER,
		GIVE_SECONDS_AMPLIFIER_HIDEPARTICLES,
		GIVE_INFINITE,
		GIVE_INFINITE_AMPLIFIER,
		GIVE_INFINITE_AMPLIFIER_HIDEPARTICLES,
		CLEAR,
		CLEAR_TARGETS,
		CLEAR_TARGETS_EFFECT;
	}
}
