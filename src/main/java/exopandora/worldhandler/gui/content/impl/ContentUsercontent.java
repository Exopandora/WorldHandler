package exopandora.worldhandler.gui.content.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderMultiCommand;
import exopandora.worldhandler.builder.impl.BuilderUsercontent;
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
import exopandora.worldhandler.usercontent.model.JsonModel;
import exopandora.worldhandler.usercontent.model.JsonUsercontent;
import exopandora.worldhandler.usercontent.model.JsonWidget;
import exopandora.worldhandler.util.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;

public class ContentUsercontent extends Content
{
	private final String id;
	private final JsonUsercontent content;
	private final ScriptEngineAdapter engineAdapter;
	private final List<VisibleObject<BuilderUsercontent>> builders;
	private final Map<String, VisibleActiveObject<EditBox>> textfields = new HashMap<String, VisibleActiveObject<EditBox>>();
	private final List<VisibleActiveObject<AbstractWidget>> buttons = new ArrayList<VisibleActiveObject<AbstractWidget>>();
	private final UsercontentAPI api;
	private final WidgetFactory buttonFactory;
	private final MenuFactory menuFactory;
	
	public ContentUsercontent(UsercontentConfig config) throws Exception
	{
		this.id = config.getId();
		this.content = config.getContent();
		this.engineAdapter = new ScriptEngineAdapter(config.getScriptEngine());
		this.builders = this.createBuilders(this.content.getModel());
		this.api = new UsercontentAPI(this.builders.stream().map(VisibleObject::getObject).collect(Collectors.toList()));
		ActionHandlerFactory actionHandlerFactory = new ActionHandlerFactory(this.api, this.builders, this.engineAdapter);
		this.buttonFactory = new WidgetFactory(this.api, actionHandlerFactory);
		this.menuFactory = new MenuFactory(this.api, actionHandlerFactory);
		this.engineAdapter.addObject("api", this.api);
		this.engineAdapter.eval(config.getJs());
	}
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		ICommandBuilder[] builders = this.builders.stream()
				.filter(builder -> builder.isVisible(this.engineAdapter))
				.map(VisibleObject::getObject)
				.toArray(ICommandBuilder[]::new);
		
		return builders.length > 0 ? new BuilderMultiCommand(builders) : null;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.textfields.clear();
		this.buttons.clear();
		
		for(JsonWidget json : this.getWidgets(this.content.getGui().getWidgets(), AbstractJsonWidget.Type.BUTTON))
		{
			AbstractWidget widget = this.buttonFactory.createWidget(json, this, container, x, y);
			
			if(JsonWidget.Type.TEXTFIELD.equals(json.getType()))
			{
				VisibleActiveObject<EditBox> visObj = new VisibleActiveObject<EditBox>(json, (EditBox) widget);
				this.textfields.put(json.getAttributes().getId(), visObj);
			}
			else
			{
				this.buttons.add(new VisibleActiveObject<AbstractWidget>(json, widget));
			}
		}
		
		for(JsonMenu menu : this.getWidgets(this.content.getGui().getMenus(), AbstractJsonWidget.Type.MENU))
		{
			container.add(this.menuFactory.createMenu(menu, this, container, x, y));
		}
		
		this.updateTextfields();
		this.updateButtons();
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		Stream.concat(this.textfields.values().stream(), this.buttons.stream()).map(VisibleObject::getObject).forEach(container::add);
	}
	
	@Override
	public void tick(Container container)
	{
		for(VisibleObject<EditBox> textfield : this.textfields.values())
		{
			if(textfield.isVisible(this.engineAdapter))
			{
				textfield.getObject().tick();
			}
		}
		
		this.updateButtons();
		this.updateTextfields();
	}
	
	@Override
	public void drawScreen(PoseStack matrix, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		for(VisibleObject<EditBox> textfield : this.textfields.values())
		{
			if(textfield.getObject().visible)
			{
				textfield.getObject().renderButton(matrix, mouseX, mouseY, partialTicks);
			}
		}
		
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
		for(VisibleObject<BuilderUsercontent> visObj : this.builders)
		{
			visObj.getObject().setPlayerName(username);
		}
	}
	
	private List<VisibleObject<BuilderUsercontent>> createBuilders(JsonModel model)
	{
		List<VisibleObject<BuilderUsercontent>> builders = new ArrayList<VisibleObject<BuilderUsercontent>>();
		
		if(model != null && model.getCommands() != null)
		{
			for(JsonCommand command : model.getCommands())
			{
				if(command.getName() != null && command.getSyntax() != null)
				{
					BuilderUsercontent builder = new BuilderUsercontent(command.getName(), new CommandSyntax(command.getSyntax()));
					builders.add(new VisibleObject<BuilderUsercontent>(command.getVisible(), builder));
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
		Component message = new TextComponent(ChatFormatting.RED + "<" + Main.NAME + ":" + this.id + ":" + type + ":" + index + "> " + e.getMessage());
		Minecraft.getInstance().gui.handleChat(ChatType.CHAT, message, Util.NIL_UUID);
	}
	
	private void updateTextfields()
	{
		for(VisibleActiveObject<EditBox> visObj : this.textfields.values())
		{
			visObj.getObject().setEditable(visObj.isEnabled(this.engineAdapter));
			visObj.getObject().setVisible(visObj.isVisible(this.engineAdapter));
		}
	}
	
	private void updateButtons()
	{
		for(VisibleActiveObject<AbstractWidget> visObj : this.buttons)
		{
			visObj.getObject().active = visObj.isEnabled(this.engineAdapter);
			visObj.getObject().visible = visObj.isVisible(this.engineAdapter);
		}
	}
}
