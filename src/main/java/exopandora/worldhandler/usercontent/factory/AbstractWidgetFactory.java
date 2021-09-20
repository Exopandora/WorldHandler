package exopandora.worldhandler.usercontent.factory;

import java.util.function.Supplier;

import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.menu.impl.ILogicMapped;
import exopandora.worldhandler.usercontent.UsercontentAPI;
import exopandora.worldhandler.usercontent.model.AbstractJsonWidget;
import exopandora.worldhandler.usercontent.model.JsonItem;
import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public abstract class AbstractWidgetFactory
{
	private final ActionHandlerFactory actionHandlerFactory;
	private final UsercontentAPI api;
	
	public AbstractWidgetFactory(UsercontentAPI api, ActionHandlerFactory actionHandlerFactory)
	{
		this.api = api;
		this.actionHandlerFactory = actionHandlerFactory;
	}
	
	public ActionHandlerFactory getActionHandlerFactory()
	{
		return this.actionHandlerFactory;
	}
	
	public UsercontentAPI getApi()
	{
		return this.api;
	}
	
	public static class UsercontentLogicMapped<T extends Enum<T>> implements ILogicMapped<JsonItem>
	{
		private final ActionHandlerFactory actionHandlerFactory;
		private final UsercontentAPI api;
		private final Content content;
		private final AbstractJsonWidget<T> widget;
		private final Supplier<String> player;
		
		public UsercontentLogicMapped(UsercontentAPI api, ActionHandlerFactory actionHandlerFactory, Content content, AbstractJsonWidget<T> widget, Supplier<String> player)
		{
			this.api = api;
			this.actionHandlerFactory = actionHandlerFactory;
			this.content = content;
			this.widget = widget;
			this.player = player;
		}
		
		@Override
		public MutableComponent translate(JsonItem item)
		{
			if(item.getTranslation() != null)
			{
				return new TranslatableComponent(item.getTranslation());
			}
			
			return new TextComponent(item.getId());
		}
		
		@Override
		public MutableComponent toTooltip(JsonItem item)
		{
			return new TextComponent(item.getId());
		}
		
		@Override
		public void onClick(JsonItem item)
		{
			try
			{
				this.api.updateValue(this.widget.getAttributes().getId(), item.getId());
				ActionHandler action = this.actionHandlerFactory.createActionHandler(this.content, this.widget.getAction(), this.player, item.getId());
				
				if(action != null)
				{
					action.run();
				}
			}
			catch(Exception e)
			{
				WorldHandler.LOGGER.error("Error executing action for widget");
			}
		}
		
		@Override
		public String getId()
		{
			return this.widget.getAttributes().getId();
		}
		
		@Override
		public void onInit(JsonItem item)
		{
			this.onClick(item);
		}
	}
}
