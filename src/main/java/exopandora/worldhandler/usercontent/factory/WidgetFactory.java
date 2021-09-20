package exopandora.worldhandler.usercontent.factory;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.common.base.Predicates;

import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.widget.button.GuiButtonIcon;
import exopandora.worldhandler.gui.widget.button.GuiButtonItem;
import exopandora.worldhandler.gui.widget.button.GuiButtonList;
import exopandora.worldhandler.gui.widget.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.widget.button.GuiSlider;
import exopandora.worldhandler.gui.widget.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.widget.button.LogicSliderSimple;
import exopandora.worldhandler.usercontent.UsercontentAPI;
import exopandora.worldhandler.usercontent.model.JsonItem;
import exopandora.worldhandler.usercontent.model.JsonWidget;
import exopandora.worldhandler.util.TextUtils;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class WidgetFactory extends AbstractWidgetFactory
{
	public WidgetFactory(UsercontentAPI api, ActionHandlerFactory actionHandlerFactory)
	{
		super(api, actionHandlerFactory);
	}
	
	@Nullable
	public AbstractWidget createWidget(JsonWidget widget, Content content, Container container, int x, int y)
	{
		if(JsonWidget.Type.BUTTON.equals(widget.getType()))
		{
			return new GuiButtonTooltip
			(
				widget.getLayout().getX() + x,
				widget.getLayout().getY() + y,
				widget.getLayout().getWidth(),
				widget.getLayout().getHeight(),
				TextUtils.formatNonnull(widget.getText()),
				TextUtils.formatNonnull(widget.getAttributes() != null ? widget.getAttributes().getTooltip() : null),
				this.getActionHandlerFactory().createActionHandler(content, widget.getAction(), container::getPlayer)
			);
		}
		else if(JsonWidget.Type.ITEM_BUTTON.equals(widget.getType()))
		{
			return new GuiButtonItem
			(
				widget.getLayout().getX() + x,
				widget.getLayout().getY() + y,
				widget.getLayout().getWidth(),
				widget.getLayout().getHeight(),
				ForgeRegistries.ITEMS.getValue(new ResourceLocation(widget.getAttributes().getItem())),
				this.getActionHandlerFactory().createActionHandler(content, widget.getAction(), container::getPlayer)
			);
		}
		else if(JsonWidget.Type.ICON_BUTTON.equals(widget.getType()))
		{
			return new GuiButtonIcon
			(
				widget.getLayout().getX() + x,
				widget.getLayout().getY() + y,
				widget.getLayout().getWidth(),
				widget.getLayout().getHeight(),
				widget.getAttributes().getIcon(),
				TextUtils.formatNonnull(widget.getAttributes().getTooltip()),
				this.getActionHandlerFactory().createActionHandler(content, widget.getAction(), container::getPlayer)
			);
		}
		else if(JsonWidget.Type.LIST_BUTTON.equals(widget.getType()))
		{
			return new GuiButtonList<JsonItem>
			(
				widget.getLayout().getX() + x,
				widget.getLayout().getY() + y,
				widget.getAttributes().getItems(),
				widget.getLayout().getWidth(),
				widget.getLayout().getHeight(),
				container,
				new UsercontentLogicMapped<JsonWidget.Type>(this.getApi(), this.getActionHandlerFactory(), content, widget, container::getPlayer)
			);
		}
		else if(JsonWidget.Type.SLIDER.equals(widget.getType()))
		{
			Consumer<Integer> responder = this.getActionHandlerFactory().createResponder(integer -> integer.toString(), widget.getAttributes().getId(), widget.getAction());
			return new GuiSlider
			(
				widget.getLayout().getX() + x,
				widget.getLayout().getY() + y,
				widget.getLayout().getWidth(),
				widget.getLayout().getHeight(),
				widget.getAttributes().getMin(),
				widget.getAttributes().getMax(),
				widget.getAttributes().getStart(),
				container,
				new LogicSliderSimple(widget.getAttributes().getId(), TextUtils.formatNonnull(widget.getText()), responder)
			);
		}
		else if(JsonWidget.Type.TEXTFIELD.equals(widget.getType()))
		{
			GuiTextFieldTooltip textfield = new GuiTextFieldTooltip
			(
				widget.getLayout().getX() + x,
				widget.getLayout().getY() + y,
				widget.getLayout().getWidth(),
				widget.getLayout().getHeight(),
				TextUtils.formatNonnull(widget.getText())
			);
			textfield.setFilter(Predicates.notNull());
			textfield.setValue(this.getApi().getValue(widget.getAttributes().getId()));
			textfield.setResponder(this.getActionHandlerFactory().createResponder(string -> textfield.getValue(), widget.getAttributes().getId(), widget.getAction()));
			
			return textfield;
		}
		
		return null;
	}
}
