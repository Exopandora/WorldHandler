package exopandora.worldhandler.gui.content.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import exopandora.worldhandler.usercontent.factory.ButtonFactory;
import exopandora.worldhandler.usercontent.factory.ElementFactory;
import exopandora.worldhandler.usercontent.model.JsonButton;
import exopandora.worldhandler.usercontent.model.JsonCommand;
import exopandora.worldhandler.usercontent.model.JsonElement;
import exopandora.worldhandler.usercontent.model.JsonModel;
import exopandora.worldhandler.usercontent.model.JsonText;
import exopandora.worldhandler.usercontent.model.JsonUsercontent;
import exopandora.worldhandler.usercontent.model.JsonWidget;
import exopandora.worldhandler.util.TextFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ContentUsercontent extends Content
{
	private final String id;
	private final JsonUsercontent content;
	private final ScriptEngineAdapter engineAdapter;
	private final List<VisibleObject<BuilderUsercontent>> builders;
	private final Map<String, VisibleActiveObject<TextFieldWidget>> textfields = new HashMap<String, VisibleActiveObject<TextFieldWidget>>();
	private final List<VisibleActiveObject<Widget>> buttons = new ArrayList<VisibleActiveObject<Widget>>();
	private UsercontentAPI api;
	private ButtonFactory buttonFactory;
	private ElementFactory elementFactory;
	
	public ContentUsercontent(UsercontentConfig config) throws Exception
	{
		this.id = config.getId();
		this.content = config.getContent();
		this.engineAdapter = new ScriptEngineAdapter(config.getScriptEngine());
		this.builders = this.createBuilders(this.content.getModel());
		this.api = new UsercontentAPI(this.builders.stream().map(VisibleObject::getObject).collect(Collectors.toList()));
		ActionHandlerFactory actionHandlerFactory = new ActionHandlerFactory(this.api,this. builders, this.engineAdapter);
		this.buttonFactory = new ButtonFactory(this.api, actionHandlerFactory);
		this.elementFactory = new ElementFactory(this.api, actionHandlerFactory);
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
		
		for(JsonButton button : this.getWidgets(this.content.getGui().getButtons(), JsonWidget.Type.BUTTON))
		{
			Widget widget = this.buttonFactory.createButton(button, this, container, x, y);
			
			if(JsonButton.Type.TEXTFIELD.equals(button.getType()))
			{
				VisibleActiveObject<TextFieldWidget> visObj = new VisibleActiveObject<TextFieldWidget>(button, (TextFieldWidget) widget);
				this.textfields.put(button.getAttributes().getId(), visObj);
			}
			else
			{
				this.buttons.add(new VisibleActiveObject<Widget>(button, widget));
			}
		}
		
		for(JsonElement element : this.getWidgets(this.content.getGui().getElements(), JsonWidget.Type.ELEMENT))
		{
			container.add(this.elementFactory.createElement(element, this, container, x, y));
		}
		
		this.updateTextfields();
		this.updateButtons();
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		this.textfields.values().stream().map(VisibleObject::getObject).forEach(container::add);
		this.buttons.stream().map(VisibleObject::getObject).forEach(container::add);
	}
	
	@Override
	public void tick(Container container)
	{
		for(VisibleObject<TextFieldWidget> textfield : this.textfields.values())
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
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		for(VisibleObject<TextFieldWidget> textfield : this.textfields.values())
		{
			if(textfield.getObject().visible)
			{
				textfield.getObject().renderButton(mouseX, mouseY, partialTicks);
			}
		}
		
		if(this.content.getGui() != null && this.content.getGui().getTexts() != null)
		{
			for(JsonText text : this.content.getGui().getTexts())
			{
				container.getMinecraft().fontRenderer.drawString(TextFormatting.formatNullable(text.getText()), text.getX() + x, text.getY() + y, text.getColor());
			}
		}
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.getRegisteredCategory(this.content.getGui().getTab().getCategory());
	}
	
	@Override
	public String getTitle()
	{
		return TextFormatting.formatNullable(this.content.getGui().getTitle());
	}
	
	@Override
	public String getTabTitle()
	{
		return TextFormatting.formatNullable(this.content.getGui().getTab().getTitle());
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
	
	private <T extends JsonWidget<?>> List<T> getWidgets(List<T> list, JsonWidget.Type type)
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
		ITextComponent message = new StringTextComponent(net.minecraft.util.text.TextFormatting.RED + "<" + Main.NAME + ":" + this.id + ":" + type + ":" + index + "> " + e.getMessage());
		Minecraft.getInstance().ingameGUI.addChatMessage(ChatType.CHAT, message);
	}
	
	private void updateTextfields()
	{
		for(VisibleActiveObject<TextFieldWidget> visObj : this.textfields.values())
		{
			visObj.getObject().setEnabled(visObj.isEnabled(this.engineAdapter));
			visObj.getObject().visible = visObj.isVisible(this.engineAdapter);
		}
	}
	
	private void updateButtons()
	{
		for(VisibleActiveObject<Widget> visObj : this.buttons)
		{
			visObj.getObject().active = visObj.isEnabled(this.engineAdapter);
			visObj.getObject().visible = visObj.isVisible(this.engineAdapter);
		}
	}
}
