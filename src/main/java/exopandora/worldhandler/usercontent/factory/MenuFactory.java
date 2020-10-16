package exopandora.worldhandler.usercontent.factory;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.menu.Menu;
import exopandora.worldhandler.gui.menu.impl.ILogicPageList;
import exopandora.worldhandler.gui.menu.impl.MenuPageList;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiButtonTooltip;
import exopandora.worldhandler.usercontent.UsercontentAPI;
import exopandora.worldhandler.usercontent.model.JsonItem;
import exopandora.worldhandler.usercontent.model.JsonMenu;
import exopandora.worldhandler.usercontent.model.JsonWidget;
import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MenuFactory extends WidgetFactory
{
	public MenuFactory(UsercontentAPI api, ActionHandlerFactory actionHandlerFactory)
	{
		super(api, actionHandlerFactory);
	}
	
	@Nullable
	public Menu createMenu(JsonMenu menu, Content content, Container container, int x, int y)
	{
		if(JsonMenu.Type.PAGE_LIST.equals(menu.getType()))
		{
			return new MenuPageList<JsonItem>
			(
				menu.getDimensions().getX() + x,
				menu.getDimensions().getY() + y,
				menu.getAttributes().getItems(),
				menu.getDimensions().getWidth(),
				menu.getDimensions().getHeight(),
				menu.getAttributes().getLength(),
				container,
				new UsercontentLogicPageList<JsonMenu.Type>(this.getApi(), this.getActionHandlerFactory(), content, container, menu, container::getPlayer)
			);
		}
		
		return null;
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class UsercontentLogicPageList<T extends Enum<T>> extends UsercontentLogicMapped<T> implements ILogicPageList<JsonItem>
	{
		private final Container container;
		
		public UsercontentLogicPageList(UsercontentAPI api, ActionHandlerFactory actionHandlerFactory, Content content, Container container, JsonWidget<T> widget, Supplier<String> player)
		{
			super(api, actionHandlerFactory, content, widget, player);
			this.container = container;
		}
		
		@Override
		public void onClick(JsonItem item)
		{
			super.onClick(item);
			this.container.initButtons();
		}
		
		@Override
		public GuiButtonBase onRegister(int x, int y, int width, int height, IFormattableTextComponent text, JsonItem item, ActionHandler actionHandler)
		{
			return new GuiButtonTooltip(x, y, width, height, text, this.toTooltip(item), actionHandler);
		}
		
		@Override
		public void onInit(JsonItem item)
		{
			
		}
	}
}
