package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.PrimitiveArgument;
import exopandora.worldhandler.builder.argument.PrimitiveArgument.Operation;
import exopandora.worldhandler.builder.argument.TargetArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.scores.criteria.ObjectiveCriteria.RenderType;

public class ScoreboardCommandBuilder extends CommandBuilder
{
	private final PrimitiveArgument<String> objective = Arguments.word();
	private final PrimitiveArgument<String> criteria = Arguments.criteria();
	private final PrimitiveArgument<Component> displayName = Arguments.textComponent();
	private final PrimitiveArgument<String> slot = Arguments.word();
	private final PrimitiveArgument<RenderType> renderType = Arguments.renderType();
	private final PrimitiveArgument<Boolean> displayAutoUpdate = Arguments.boolArg();
	private final TargetArgument target = Arguments.target();
	private final TargetArgument targets = Arguments.target();
	private final PrimitiveArgument<Integer> score = Arguments.intArg();
	private final PrimitiveArgument<Operation> operation = Arguments.operation();
	private final PrimitiveArgument<String> sourceObjective = Arguments.word();
	private final PrimitiveArgument<Style> style = Arguments.style();
	
	private final CommandNodeLiteral root = CommandNode.literal("scoreboard")
		.then(CommandNode.literal("objectives")
			.label(Label.OBJECTIVES)
			.then(CommandNode.literal("add")
				.then(CommandNode.argument("objective", this.objective)
					.then(CommandNode.argument("criteria", this.criteria)
						.label(Label.OBJECTIVES_ADD)
						.then(CommandNode.argument("displayName", this.displayName)
							.label(Label.OBJECTIVES_ADD_DISPLAYNAME)))))
			.then(CommandNode.literal("modify")
				.then(CommandNode.argument("objective", this.objective)
					.then(CommandNode.literal("displayname")
						.then(CommandNode.argument("displayName", this.displayName)
							.label(Label.OBJECTIVES_MODIFY_DISPLAYNAME)))
					.then(CommandNode.literal("rendertype")
						.then(CommandNode.argument("renderType", this.renderType)
							.label(Label.OBJECTIVES_MODIFY_RENDERTYPE)))
					.then(CommandNode.literal("displayautoupdate")
						.then(CommandNode.argument("displayAutoUpdate", this.displayAutoUpdate)
							.label(Label.OBJECTIVES_MODIFY_DISPLAYAUTOUPDATE)))
					.then(CommandNode.literal("numberformat")
						.label(Label.OBJECTIVES_MODIFY_NUMBERFORMAT)
						.then(CommandNode.argument("styled", this.style)
							.label(Label.OBJECTIVES_MODIFY_NUMBERFORMAT_STYLED))
						.then(CommandNode.argument("fixed", this.displayName)
							.label(Label.OBJECTIVES_MODIFY_NUMBERFORMAT_FIXED))
						.then(CommandNode.literal("blank")
							.label(Label.OBJECTIVES_MODIFY_NUMBERFORMAT_BLANK)))))
			.then(CommandNode.literal("remove")
				.then(CommandNode.argument("objective", this.objective)
					.label(Label.OBJECTIVES_REMOVE)))
			.then(CommandNode.literal("setdisplay")
				.then(CommandNode.argument("slot", this.slot)
					.label(Label.OBJECTIVES_SETDISPLAY_SLOT)
					.then(CommandNode.argument("objective", this.objective)
						.label(Label.OBJECTIVES_SETDISPLAY_SLOT_OBJECTIVE)))))
		.then(CommandNode.literal("players")
			.label(Label.PLAYERS)
			.then(CommandNode.literal("list")
				.label(Label.PLAYERS_LIST)
				.then(CommandNode.argument("target", this.target)
					.label(Label.PLAYERS_LIST_TARGET)))
			.then(CommandNode.literal("set")
				.then(CommandNode.argument("targets", this.targets)
					.then(CommandNode.argument("objective", this.objective)
						.then(CommandNode.argument("score", this.score)
							.label(Label.PLAYERS_SET_SCORE)))))
			.then(CommandNode.literal("get")
				.then(CommandNode.argument("target", this.target)
					.then(CommandNode.argument("objective", this.objective)
						.label(Label.PLAYERS_GET_SCORE))))
			.then(CommandNode.literal("set")
				.then(CommandNode.argument("targets", this.targets)
					.then(CommandNode.argument("objective", this.objective)
						.then(CommandNode.argument("score", this.score)
							.label(Label.PLAYERS_SET_SCORE)))))
			.then(CommandNode.literal("add")
				.then(CommandNode.argument("targets", this.targets)
					.then(CommandNode.argument("objective", this.objective)
						.then(CommandNode.argument("score", this.score)
							.label(Label.PLAYERS_ADD_SCORE)))))
			.then(CommandNode.literal("remove")
				.then(CommandNode.argument("targets", this.targets)
					.then(CommandNode.argument("objective", this.objective)
						.then(CommandNode.argument("score", this.score)
							.label(Label.PLAYERS_REMOVE_SCORE)))))
			.then(CommandNode.literal("reset")
				.then(CommandNode.argument("targets", this.targets)
					.then(CommandNode.argument("objective", this.objective)
						.label(Label.PLAYERS_RESET_SCORE))))
			.then(CommandNode.literal("enable")
				.then(CommandNode.argument("targets", this.targets)
					.label(Label.PLAYERS_ENABLE)
					.then(CommandNode.argument("objective", this.objective)
						.label(Label.PLAYERS_ENABLE_OBJECTIVE))))
			.then(CommandNode.literal("display")
				.then(CommandNode.literal("name")
					.then(CommandNode.argument("targets", this.targets)
						.then(CommandNode.argument("objective", this.objective)
							.label(Label.PLAYERS_DISPLAY)
							.then(CommandNode.argument("name", this.displayName)
								.label(Label.PLAYERS_DISPLAY_NAME))))
				.then(CommandNode.literal("numberformat")
					.then(CommandNode.argument("targets", this.targets)
						.then(CommandNode.argument("objective", this.objective)
							.label(Label.PLAYERS_DISPLAY_NUMBERFORMAT)
							.then(CommandNode.argument("styled", this.style)
								.label(Label.PLAYERS_DISPLAY_NUMBERFORMAT_STYLED))
							.then(CommandNode.argument("fixed", this.displayName)
								.label(Label.PLAYERS_DISPLAY_NUMBERFORMAT_FIXED))
							.then(CommandNode.literal("blank")
								.label(Label.PLAYERS_DISPLAY_NUMBERFORMAT_BLANK)))))))
			.then(CommandNode.literal("operation")
				.then(CommandNode.argument("targetObjective", this.objective)
					.then(CommandNode.argument("operation", this.operation)
						.then(CommandNode.argument("source", this.targets)
							.then(CommandNode.argument("sourceObjective", this.sourceObjective)
								.label(Label.PLAYERS_OPERATION)))))));
	
	public PrimitiveArgument<String> objective()
	{
		return this.objective;
	}
	
	public PrimitiveArgument<String> criteria()
	{
		return this.criteria;
	}
	
	public PrimitiveArgument<Component> displayName()
	{
		return this.displayName;
	}
	
	public PrimitiveArgument<String> slot()
	{
		return this.slot;
	}
	
	public PrimitiveArgument<RenderType> renderType()
	{
		return this.renderType;
	}
	
	public TargetArgument target()
	{
		return this.target;
	}
	
	public TargetArgument targets()
	{
		return this.targets;
	}
	
	public PrimitiveArgument<Integer> score()
	{
		return this.score;
	}
	
	public PrimitiveArgument<Operation> operation()
	{
		return this.operation;
	}
	
	public PrimitiveArgument<String> sourceObjective()
	{
		return this.sourceObjective;
	}
	
	public PrimitiveArgument<Style> style()
	{
		return this.style;
	}
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
	
	public static enum Label
	{
		OBJECTIVES,
		OBJECTIVES_ADD,
		OBJECTIVES_ADD_DISPLAYNAME,
		OBJECTIVES_MODIFY_DISPLAYNAME,
		OBJECTIVES_MODIFY_RENDERTYPE,
		OBJECTIVES_MODIFY_DISPLAYAUTOUPDATE,
		OBJECTIVES_MODIFY_NUMBERFORMAT,
		OBJECTIVES_MODIFY_NUMBERFORMAT_STYLED,
		OBJECTIVES_MODIFY_NUMBERFORMAT_FIXED,
		OBJECTIVES_MODIFY_NUMBERFORMAT_BLANK,
		OBJECTIVES_REMOVE,
		OBJECTIVES_SETDISPLAY_SLOT,
		OBJECTIVES_SETDISPLAY_SLOT_OBJECTIVE,
		PLAYERS,
		PLAYERS_LIST,
		PLAYERS_LIST_TARGET,
		PLAYERS_SET_SCORE,
		PLAYERS_GET_SCORE,
		PLAYERS_ADD_SCORE,
		PLAYERS_REMOVE_SCORE,
		PLAYERS_RESET_SCORE,
		PLAYERS_ENABLE,
		PLAYERS_ENABLE_OBJECTIVE,
		PLAYERS_OPERATION,
		PLAYERS_DISPLAY,
		PLAYERS_DISPLAY_NAME,
		PLAYERS_DISPLAY_NUMBERFORMAT,
		PLAYERS_DISPLAY_NUMBERFORMAT_STYLED,
		PLAYERS_DISPLAY_NUMBERFORMAT_FIXED,
		PLAYERS_DISPLAY_NUMBERFORMAT_BLANK;
	}
}
