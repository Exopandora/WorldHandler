package exopandora.worldhandler.usercontent.factory;

import javax.annotation.Nullable;

import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.element.Element;
import exopandora.worldhandler.gui.element.impl.ElementPageList;
import exopandora.worldhandler.gui.logic.ILogicPageList;
import exopandora.worldhandler.usercontent.UsercontentAPI;
import exopandora.worldhandler.usercontent.model.JsonElement;
import exopandora.worldhandler.usercontent.model.JsonItem;
import exopandora.worldhandler.usercontent.model.JsonWidget;
import exopandora.worldhandler.util.ActionHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElementFactory extends WidgetFactory
{
	public ElementFactory(UsercontentAPI api, ActionHandlerFactory actionHandlerFactory)
	{
		super(api, actionHandlerFactory);
	}
	
	@Nullable
	public Element createElement(JsonElement element, Content content, Container container, int x, int y)
	{
		if(JsonElement.Type.PAGE_LIST.equals(element.getType()))
		{
			return new ElementPageList<JsonItem>
			(
				element.getDimensions().getX() + x,
				element.getDimensions().getY() + y,
				element.getAttributes().getItems(),
				element.getDimensions().getWidth(),
				element.getDimensions().getHeight(),
				element.getAttributes().getLength(),
				container,
				new UsercontentLogicPageList<JsonElement.Type>(this.getApi(), this.getActionHandlerFactory(), content, container, element)
			);
		}
		
		return null;
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class UsercontentLogicPageList<T extends Enum<T>> extends UsercontentLogicMapped<T> implements ILogicPageList<JsonItem>
	{
		private final Container container;
		
		public UsercontentLogicPageList(UsercontentAPI api, ActionHandlerFactory actionHandlerFactory, Content content, Container container, JsonWidget<T> widget)
		{
			super(api, actionHandlerFactory, content, widget);
			this.container = container;
		}
		
		@Override
		public void onClick(JsonItem item)
		{
			super.onClick(item);
			this.container.initButtons();
		}
		
		@Override
		public GuiButtonBase onRegister(int x, int y, int width, int height, String text, JsonItem item, ActionHandler actionHandler)
		{
			return new GuiButtonTooltip(x, y, width, height, text, this.toTooltip(item), actionHandler);
		}
		
		@Override
		public void onInit(JsonItem item)
		{
			
		}
	}
}
