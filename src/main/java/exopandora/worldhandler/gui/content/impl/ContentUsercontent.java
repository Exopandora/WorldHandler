package exopandora.worldhandler.gui.content.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.builder.impl.UsercontentCommandBuilder;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.usercontent.ScriptEngineAdapter;
import exopandora.worldhandler.usercontent.UsercontentAPI;
import exopandora.worldhandler.usercontent.UsercontentConfig;
import exopandora.worldhandler.usercontent.VisibleActiveObject;
import exopandora.worldhandler.usercontent.VisibleObject;
import exopandora.worldhandler.usercontent.factory.ActionHandlerFactory;
import exopandora.worldhandler.usercontent.factory.MenuFactory;
import exopandora.worldhandler.usercontent.factory.WidgetFactory;
import exopandora.worldhandler.usercontent.model.AbstractJsonWidget;
import exopandora.worldhandler.usercontent.model.JsonCommand;
import exopandora.worldhandler.usercontent.model.JsonLabel;
import exopandora.worldhandler.usercontent.model.JsonMenu;
import exopandora.worldhandler.usercontent.model.JsonUsercontent;
import exopandora.worldhandler.usercontent.model.JsonWidget;
import exopandora.worldhandler.util.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

public class ContentUsercontent extends Content
{
	private final String id;
	private final JsonUsercontent content;
	private final ScriptEngineAdapter engineAdapter;
	private final Map<String, VisibleObject<UsercontentCommandBuilder>> builders;
	private final Map<String, VisibleActiveObject<EditBox>> textfields = new HashMap<String, VisibleActiveObject<EditBox>>();
	private final List<VisibleActiveObject<AbstractWidget>> widgets = new ArrayList<VisibleActiveObject<AbstractWidget>>();
	private final UsercontentAPI api;
	private final WidgetFactory widgetFactory;
	private final MenuFactory menuFactory;
	
	public ContentUsercontent(UsercontentConfig config) throws Exception
	{
		this.id = config.getId();
		this.content = config.getContent();
		this.engineAdapter = new ScriptEngineAdapter(config.getScriptEngine());
		this.builders = this.createBuilders(this.content.getCommands());
		this.api = new UsercontentAPI(this.builders.entrySet().stream().collect(Collectors.toMap(Entry::getKey, entry -> entry.getValue().get())));
		ActionHandlerFactory actionHandlerFactory = new ActionHandlerFactory(this.api, this.builders, this.engineAdapter);
		this.widgetFactory = new WidgetFactory(this.api, actionHandlerFactory);
		this.menuFactory = new MenuFactory(this.api, actionHandlerFactory);
		this.engineAdapter.addObject("api", this.api);
		this.engineAdapter.eval(config.getJs());
	}
	
	@Override
	public CommandPreview getCommandPreview()
	{
		List<UsercontentCommandBuilder> builders = this.builders.values().stream()
				.filter(builder -> builder.isVisible(this.engineAdapter))
				.map(VisibleObject::get)
				.collect(Collectors.toList());
		
		if(builders.isEmpty())
		{
			return null;
		}
		
		CommandPreview preview = new CommandPreview();
		builders.forEach(builder -> preview.add(builder, builder.getLabel()));
		return preview;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.textfields.clear();
		this.widgets.clear();
		
		for(JsonWidget json : this.getWidgets(this.content.getGui().getWidgets(), AbstractJsonWidget.Type.BUTTON))
		{
			AbstractWidget widget = this.widgetFactory.createWidget(json, this, container, x, y);
			
			if(JsonWidget.Type.TEXTFIELD.equals(json.getType()))
			{
				VisibleActiveObject<EditBox> visObj = new VisibleActiveObject<EditBox>(json, (EditBox) widget);
				this.textfields.put(json.getAttributes().getId(), visObj);
			}
			else
			{
				this.widgets.add(new VisibleActiveObject<AbstractWidget>(json, widget));
			}
		}
		
		for(JsonMenu menu : this.getWidgets(this.content.getGui().getMenus(), AbstractJsonWidget.Type.MENU))
		{
			container.addMenu(this.menuFactory.createMenu(menu, this, container, x, y));
		}
		
		this.updateTextfields();
		this.updateButtons();
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		Stream.concat(this.textfields.values().stream(), this.widgets.stream()).map(VisibleObject::get).forEach(container::addRenderableWidget);
	}
	
	@Override
	public void tick(Container container)
	{
		for(VisibleObject<EditBox> textfield : this.textfields.values())
		{
			if(textfield.isVisible(this.engineAdapter))
			{
				textfield.get().tick();
			}
		}
		
		this.updateButtons();
		this.updateTextfields();
	}
	
	@Override
	public void drawScreen(PoseStack matrix, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(this.content.getGui() != null && this.content.getGui().getLabels() != null)
		{
			for(JsonLabel label : this.content.getGui().getLabels())
			{
				if(label.getVisible() == null || label.getVisible().eval(this.engineAdapter))
				{
					container.getMinecraft().font.draw(matrix, TextUtils.formatNonnull(label.getText()), label.getX() + x, label.getY() + y, label.getColor());
				}
			}
		}
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.getRegisteredCategory(this.content.getGui().getTab().getCategory());
	}
	
	@Override
	public MutableComponent getTitle()
	{
		return TextUtils.formatNonnull(this.content.getGui().getTitle());
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return TextUtils.formatNonnull(this.content.getGui().getTab().getTitle());
	}
	
	@Override
	public Content getActiveContent()
	{
		if(this.content.getGui().getTab().getActiveContent() == null)
		{
			return this;
		}
		
		return Contents.getRegisteredContent(this.content.getGui().getTab().getActiveContent());
	}
	
	@Override
	public Content getBackContent()
	{
		if(this.content.getGui().getTab().getActiveContent() == null)
		{
			return super.getBackContent();
		}
		
		return Contents.getRegisteredContent(this.content.getGui().getTab().getBackContent());
	}
	
	@Override
	public void onPlayerNameChanged(String username)
	{
		for(VisibleObject<UsercontentCommandBuilder> visObj : this.builders.values())
		{
			visObj.get().setPlayerName(username);
		}
	}
	
	private Map<String, VisibleObject<UsercontentCommandBuilder>> createBuilders(Map<String, JsonCommand> commands)
	{
		Map<String, VisibleObject<UsercontentCommandBuilder>> builders = new TreeMap<String, VisibleObject<UsercontentCommandBuilder>>();
		
		if(commands != null)
		{
			for(Entry<String, JsonCommand> command : commands.entrySet())
			{
				JsonCommand root = command.getValue();
				
				if(root.getSyntax() != null)
				{
					root.getSyntax().validate();
					UsercontentCommandBuilder builder = new UsercontentCommandBuilder(root.getSyntax(), root.getLabel());
					builders.put(command.getKey(), new VisibleObject<UsercontentCommandBuilder>(root.getVisible(), builder));
				}
			}
		}
		
		return builders;
	}
	
	private <T extends AbstractJsonWidget<?>> List<T> getWidgets(List<T> list, AbstractJsonWidget.Type type)
	{
		List<T> result = new ArrayList<T>();
		
		if(list == null)
		{
			return result;
		}
		
		for(int x = 0; x < list.size(); x++)
		{
			T widget = list.get(x);
			
			try
			{
				widget.validate();
				result.add(widget);
			}
			catch(Exception e)
			{
				this.printError(type.toString().toLowerCase(), x, e);
			}
		}
		
		return result;
	}
	
	private void printError(String type, int index, Throwable e)
	{
		Component message = Component.literal("<" + Main.NAME + ":" + this.id + ":" + type + ":" + index + "> " + e.getMessage()).setStyle(Style.EMPTY.withColor(ChatFormatting.RED));
		Minecraft.getInstance().player.sendSystemMessage(message);
	}
	
	private void updateTextfields()
	{
		for(VisibleActiveObject<EditBox> visObj : this.textfields.values())
		{
			visObj.get().setEditable(visObj.isEnabled(this.engineAdapter));
			visObj.get().setVisible(visObj.isVisible(this.engineAdapter));
		}
	}
	
	private void updateButtons()
	{
		for(VisibleActiveObject<AbstractWidget> visObj : this.widgets)
		{
			visObj.get().active = visObj.isEnabled(this.engineAdapter);
			visObj.get().visible = visObj.isVisible(this.engineAdapter);
		}
	}
}
