package exopandora.worldhandler.usercontent.factory;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.impl.LiteralCommandBuilder;
import exopandora.worldhandler.builder.impl.UsercontentCommandBuilder;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.usercontent.ScriptEngineAdapter;
import exopandora.worldhandler.usercontent.UsercontentAPI;
import exopandora.worldhandler.usercontent.VisibleObject;
import exopandora.worldhandler.usercontent.model.Action;
import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.CommandHelper;

public class ActionHandlerFactory
{
	private final UsercontentAPI api;
	private final Map<String, VisibleObject<UsercontentCommandBuilder>> builders;
	private final ScriptEngineAdapter engine;
	
	public ActionHandlerFactory(UsercontentAPI api, Map<String, VisibleObject<UsercontentCommandBuilder>> builders, ScriptEngineAdapter engine)
	{
		this.api = api;
		this.builders = builders;
		this.engine = engine;
	}
	
	@Nullable
	public ActionHandler createActionHandler(Content content, Action action, Supplier<String> player)
	{
		return this.createActionHandler(content, action, player, null);
	}
	
	@Nullable
	public ActionHandler createActionHandler(Content content, Action action, Supplier<String> player, String value)
	{
		if(action == null)
		{
			return null;
		}
		else if(Action.Type.OPEN.equals(action.getType()))
		{
			if(action.getAttributes() != null && action.getAttributes().getValue() != null)
			{
				return () -> ActionHelper.open(action.getAttributes().getValue());
			}
		}
		else if(Action.Type.SET.equals(action.getType()))
		{
			if(action.getAttributes() != null && (action.getAttributes().getValue() != null || value != null))
			{
				return () ->
				{
					VisibleObject<UsercontentCommandBuilder> visObj = this.builders.get(action.getAttributes().getCommand());
					
					if(visObj != null && visObj.get() != null)
					{
						visObj.get().setArgument(action.getAttributes().getArgument(), value != null ? value : action.getAttributes().getValue());
					}
				};
			}
		}
		else if(Action.Type.RUN.equals(action.getType()))
		{
			if(action.getAttributes() != null)
			{
				return () ->
				{
					if(action.getAttributes().getValue() != null && !action.getAttributes().getValue().isEmpty() && action.getAttributes().getCommand() != null)
					{
						CommandHelper.sendCommand(player.get(), this.builders.get(action.getAttributes().getCommand()).get(), action.getAttributes().getValue());
					}
				};
			}
		}
		else if(Action.Type.RUN_STRING.equals(action.getType()))
		{
			if(action.getAttributes() != null)
			{
				return () ->
				{
					if(action.getAttributes().getValue() != null && !action.getAttributes().getValue().isEmpty())
					{
						String command = action.getAttributes().getValue();
						
						if(command.startsWith("/"))
						{
							command = command.substring(1);
						}
						
						CommandHelper.sendCommand(player.get(), new LiteralCommandBuilder(command), LiteralCommandBuilder.Label.ROOT);
					}
				};
			}
		}
		else if(Action.Type.BACK.equals(action.getType()))
		{
			return () -> ActionHelper.back(content);
		}
		else if(Action.Type.BACK_TO_GAME.equals(action.getType()))
		{
			return ActionHelper::backToGame;
		}
		else if(Action.Type.JS.equals(action.getType()))
		{
			if(action.getAttributes() != null && action.getAttributes().getFunction() != null && !action.getAttributes().getFunction().isEmpty())
			{
				return () -> this.engine.invokeFunction(action.getAttributes().getFunction(), value != null ? value : action.getAttributes().getValue());
			}
		}
		
		return null;
	}
	
	public <T> Consumer<T> createResponder(Function<T, String> toStringMapper, String id, Action action)
	{
		if(Action.Type.SET.equals(action.getType()))
		{
			if(action.getAttributes() != null)
			{
				return string ->
				{
					String value = toStringMapper.apply(string);
					this.api.setValue(id, value);
					this.builders.get(action.getAttributes().getCommand()).get().setArgument(action.getAttributes().getArgument(), value);
				};
			}
		}
		else if(Action.Type.JS.equals(action.getType()))
		{
			if(action.getAttributes() != null && action.getAttributes().getFunction() != null)
			{
				return string ->
				{
					String value = toStringMapper.apply(string);
					this.api.setValue(id, value);
					this.engine.invokeFunction(action.getAttributes().getFunction(), value);
				};
			}
		}
		
		return string -> this.api.setValue(id, toStringMapper.apply(string));
	}
}
