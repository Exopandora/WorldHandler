package exopandora.worldhandler.usercontent.factory;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.common.base.Predicates;

import exopandora.worldhandler.gui.button.GuiButtonIcon;
import exopandora.worldhandler.gui.button.GuiButtonItem;
import exopandora.worldhandler.gui.button.GuiButtonList;
import exopandora.worldhandler.gui.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.button.GuiSlider;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.logic.LogicSliderSimple;
import exopandora.worldhandler.usercontent.UsercontentAPI;
import exopandora.worldhandler.usercontent.model.JsonItem;
import exopandora.worldhandler.util.TextFormatting;
import exopandora.worldhandler.usercontent.model.JsonButton;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
public class ButtonFactory extends WidgetFactory
{
	public ButtonFactory(UsercontentAPI api, ActionHandlerFactory actionHandlerFactory)
	{
		super(api, actionHandlerFactory);
	}
	
	@Nullable
	public Widget createButton(JsonButton button, Content content, Container container, int x, int y)
	{
		if(JsonButton.Type.BUTTON.equals(button.getType()))
		{
			return new GuiButtonTooltip
			(
				button.getDimensions().getX() + x,
				button.getDimensions().getY() + y,
				button.getDimensions().getWidth(),
				button.getDimensions().getHeight(),
				TextFormatting.formatNonnull(button.getText()),
				TextFormatting.formatNullable(button.getAttributes() != null ? button.getAttributes().getTooltip() : null),
				this.getActionHandlerFactory().createActionHandler(content, button.getAction())
			);
		}
		else if(JsonButton.Type.ITEM_BUTTON.equals(button.getType()))
		{
			return new GuiButtonItem
			(
				button.getDimensions().getX() + x,
				button.getDimensions().getY() + y,
				button.getDimensions().getWidth(),
				button.getDimensions().getHeight(),
				ForgeRegistries.ITEMS.getValue(new ResourceLocation(button.getAttributes().getItem())),
				this.getActionHandlerFactory().createActionHandler(content, button.getAction())
			);
		}
		else if(JsonButton.Type.ICON_BUTTON.equals(button.getType()))
		{
			return new GuiButtonIcon
			(
				button.getDimensions().getX() + x,
				button.getDimensions().getY() + y,
				button.getDimensions().getWidth(),
				button.getDimensions().getHeight(),
				button.getAttributes().getIcon(),
				TextFormatting.formatNonnull(button.getAttributes().getTooltip()),
				this.getActionHandlerFactory().createActionHandler(content, button.getAction())
			);
		}
		else if(JsonButton.Type.LIST_BUTTON.equals(button.getType()))
		{
			return new GuiButtonList<JsonItem>
			(
				button.getDimensions().getX() + x,
				button.getDimensions().getY() + y,
				button.getAttributes().getItems(),
				button.getDimensions().getWidth(),
				button.getDimensions().getHeight(),
				container,
				new UsercontentLogicMapped<JsonButton.Type>(this.getApi(), this.getActionHandlerFactory(), content, button)
			);
		}
		else if(JsonButton.Type.SLIDER.equals(button.getType()))
		{
			Consumer<Integer> responder = this.getActionHandlerFactory().createResponder(integer -> integer.toString(), button.getAttributes().getId(), button.getAction());
			return new GuiSlider
			(
				button.getDimensions().getX() + x,
				button.getDimensions().getY() + y,
				button.getDimensions().getWidth(),
				button.getDimensions().getHeight(),
				button.getAttributes().getMin(),
				button.getAttributes().getMax(),
				button.getAttributes().getStart(),
				container,
				new LogicSliderSimple(button.getAttributes().getId(), TextFormatting.formatNullable(button.getText()), responder)
			);
		}
		else if(JsonButton.Type.TEXTFIELD.equals(button.getType()))
		{
			GuiTextFieldTooltip textfield = new GuiTextFieldTooltip
			(
				button.getDimensions().getX() + x,
				button.getDimensions().getY() + y,
				button.getDimensions().getWidth(),
				button.getDimensions().getHeight(),
				TextFormatting.formatNullable(button.getText())
			);
			textfield.setValidator(Predicates.notNull());
			textfield.setText(this.getApi().getValue(button.getAttributes().getId()));
			textfield.setResponder(this.getActionHandlerFactory().createResponder(string -> textfield.getText(), button.getAttributes().getId(), button.getAction()));
			
			return textfield;
		}
		
		return null;
	}
}
