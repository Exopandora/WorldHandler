package exopandora.worldhandler.usercontent.factory;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.impl.BuilderUsercontent;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.helper.ActionHelper;
import exopandora.worldhandler.helper.CommandHelper;
import exopandora.worldhandler.usercontent.ScriptEngineAdapter;
import exopandora.worldhandler.usercontent.UsercontentAPI;
import exopandora.worldhandler.usercontent.VisibleObject;
import exopandora.worldhandler.usercontent.model.Action;
import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ActionHandlerFactory
{
	private final UsercontentAPI api;
	private final List<VisibleObject<BuilderUsercontent>> builders;
	private final ScriptEngineAdapter engine;
	
	public ActionHandlerFactory(UsercontentAPI api, List<VisibleObject<BuilderUsercontent>> builders, ScriptEngineAdapter engine)
	{
		this.api = api;
		this.builders = builders;
		this.engine = engine;
	}
	
	@Nullable
	public ActionHandler createActionHandler(Content content, Action action)
	{
		return this.createActionHandler(content, action, null);
	}
	
	@Nullable
	public ActionHandler createActionHandler(Content content, Action action, String value)
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
					VisibleObject<BuilderUsercontent> visObj = this.builders.get(action.getAttributes().getCommand());
					
					if(visObj != null && visObj.getObject() != null)
					{
						visObj.getObject().set(action.getAttributes().getIndex(), value != null ? value : action.getAttributes().getValue());
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
					if(action.getAttributes().getValue() == null)
					{
						CommandHelper.sendCommand(this.builders.get(action.getAttributes().getCommand()).getObject());
					}
					else if(!action.getAttributes().getValue().isEmpty())
					{
						Minecraft.getInstance().player.sendChatMessage(action.getAttributes().getValue());
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
					this.api.updateValue(id, value);
					this.builders.get(action.getAttributes().getCommand()).getObject().set(action.getAttributes().getIndex(), value);
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
					this.api.updateValue(id, value);
					this.engine.invokeFunction(action.getAttributes().getFunction(), value);
				};
			}
		}
		
		return string -> this.api.updateValue(id, toStringMapper.apply(string));
	}
}
