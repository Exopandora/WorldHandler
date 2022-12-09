package exopandora.worldhandler.gui.content.impl;

import exopandora.worldhandler.builder.impl.ExperienceCommandBuilder;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.widget.button.GuiSlider;
import exopandora.worldhandler.gui.widget.button.LogicSliderSimple;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ContentExperience extends Content
{
	private final ExperienceCommandBuilder builderExperience = new ExperienceCommandBuilder();
	private final CommandPreview preview = new CommandPreview(this.builderExperience, null);
	
	private GuiButtonBase buttonAdd;
	private GuiButtonBase buttonRemove;
	
	public ContentExperience()
	{
		this.builderExperience.amount().set(0);
	}
	
	@Override
	public CommandPreview getCommandPreview()
	{
		return this.preview;
	}
	
	@Override
	public void init(Container container)
	{
		if(this.builderExperience.amount().get() > Config.getSliders().getMaxExperience())
		{
			this.builderExperience.amount().set((int) Config.getSliders().getMaxExperience());
		}
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.addRenderableWidget(new GuiButtonBase(x, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.addRenderableWidget(new GuiButtonBase(x + 118, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.addRenderableWidget(new GuiSlider(x + 116 / 2, y, 114, 20, 0, Config.getSliders().getMaxExperience(), 0, container, new LogicSliderSimple("experience", Component.translatable("gui.worldhandler.title.player.experience"), value -> 
		{
			this.builderExperience.amount().set(value);
		})));
		
		container.addRenderableWidget(this.buttonAdd = new GuiButtonBase(x + 116 / 2, y + 24, 114, 20, Component.translatable("gui.worldhandler.actions.add"), () ->
		{
			CommandHelper.sendCommand(container.getPlayer(), this.builderExperience, ExperienceCommandBuilder.Label.ADD_LEVELS);
			container.init();
		}));
		container.addRenderableWidget(this.buttonRemove = new GuiButtonBase(x + 116 / 2, y + 48, 114, 20, Component.translatable("gui.worldhandler.actions.remove"), () ->
		{
			ExperienceCommandBuilder builder = new ExperienceCommandBuilder();
			builder.targets().setTarget(this.builderExperience.targets().getTarget());
			builder.amount().set(-this.builderExperience.amount().get());
			CommandHelper.sendCommand(container.getPlayer(), builder, ExperienceCommandBuilder.Label.ADD_LEVELS);
		}));
		container.addRenderableWidget(new GuiButtonTooltip(x + 116 / 2, y + 72, 114, 20, Component.translatable("gui.worldhandler.actions.reset"), Component.translatable("gui.worldhandler.actions.set_to_0"), () ->
		{
			ExperienceCommandBuilder builder = new ExperienceCommandBuilder();
			builder.amount().set(0);
			builder.targets().setTarget(this.builderExperience.targets().getTarget());
			CommandHelper.sendCommand(container.getPlayer(), builder, ExperienceCommandBuilder.Label.SET_POINTS);
		}));
		
		boolean enabled = this.builderExperience.amount().get() > 0;
		
		this.buttonAdd.active = enabled;
		this.buttonRemove.active = enabled;
	}
	
	@Override
	public void tick(Container container)
	{
		boolean enabled = this.builderExperience.amount().get() > 0;
		
		this.buttonAdd.active = enabled;
		this.buttonRemove.active = enabled;
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.PLAYER;
	}
	
	@Override
	public MutableComponent getTitle()
	{
		return Component.translatable("gui.worldhandler.title.player.experience");
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return Component.translatable("gui.worldhandler.tab.player.experience");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.EXPERIENCE;
	}
	
	@Override
	public void onPlayerNameChanged(String username)
	{
		this.builderExperience.targets().setTarget(username);
	}
}
