package exopandora.worldhandler.gui.content.impl;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderButcher;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.container.impl.GuiWorldHandler;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ContentButcher extends Content
{
	private GuiTextFieldTooltip radiusField;
	private String radius;
	private final BuilderButcher builderButcher = new BuilderButcher();
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		return this.builderButcher;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.radiusField = new GuiTextFieldTooltip(x + 116 / 2, y + 12, 116, 20, I18n.format("gui.worldhandler.butcher.radius"));
		this.radiusField.setValidator(string ->
		{
			if(string == null)
			{
				return false;
			}
			
			if(!string.isEmpty())
			{
				try
				{
					Integer.parseInt(string);
				}
				catch(Exception e)
				{
					return false;
				}
			}
			
			return true;
		});
		this.radiusField.setText(this.radius);
		this.radiusField.setResponder(text ->
		{
			this.radius = text;
			
			if(!this.radius.isEmpty())
			{
				this.builderButcher.setDistance(Integer.valueOf(this.radius));
			}
			else
			{
				this.builderButcher.setDistance(0);
			}
			
			container.initButtons();
		});
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonBase slaughter;
		
		container.add(new GuiButtonBase(x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(this.radiusField);
		container.add(new GuiButtonBase(x + 116 / 2, y + 36, 232 / 2, 20, I18n.format("gui.worldhandler.butcher.configure"), () ->
		{
			Minecraft.getInstance().displayGuiScreen(new GuiWorldHandler(Contents.BUTCHER_SETTINGS.withParent(Contents.BUTCHER)));
		}));
		
		container.add(slaughter = new GuiButtonBase(x + 116 / 2, y + 60, 232 / 2, 20, I18n.format("gui.worldhandler.butcher.slaughter"), () ->
		{
			for(ResourceLocation entry : Config.getButcher().getEntities())
			{
				CommandHelper.sendCommand(new BuilderButcher(entry, Integer.valueOf(this.radius)));
			}
		}));
		
		slaughter.active = this.radius != null && !this.radius.isEmpty() && !Config.CLIENT.getButcher().getEntities().isEmpty();
	}
	
	@Override
	public void tick(Container container)
	{
		this.radiusField.tick();
	}
	
	@Override
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		this.radiusField.renderButton(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.ENTITIES;
	}
	
	@Override
	public String getTitle()
	{
		return I18n.format("gui.worldhandler.title.entities.butcher");
	}
	
	@Override
	public String getTabTitle()
	{
		return I18n.format("gui.worldhandler.tab.entities.butcher");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.BUTCHER;
	}
}
