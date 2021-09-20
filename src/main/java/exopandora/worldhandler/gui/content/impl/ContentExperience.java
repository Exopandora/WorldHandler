package exopandora.worldhandler.gui.content.impl;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderExperience;
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
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class ContentExperience extends Content
{
	private final BuilderExperience builderExperience = new BuilderExperience();
	
	private GuiButtonBase buttonAdd;
	private GuiButtonBase buttonRemove;
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		return this.builderExperience;
	}
	
	@Override
	public void init(Container container)
	{
		if(this.builderExperience.getLevel() > Config.getSliders().getMaxExperience())
		{
			this.builderExperience.setLevel((int) Config.getSliders().getMaxExperience());
		}
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(new GuiButtonBase(x, y + 96, 114, 20, new TranslatableComponent("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, new TranslatableComponent("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(new GuiSlider(x + 116 / 2, y, 114, 20, 0, Config.getSliders().getMaxExperience(), 0, container, new LogicSliderSimple("experience", new TranslatableComponent("gui.worldhandler.title.player.experience"), value -> 
		{
			this.builderExperience.setLevel(value);
		})));
		
		container.add(this.buttonAdd = new GuiButtonBase(x + 116 / 2, y + 24, 114, 20, new TranslatableComponent("gui.worldhandler.actions.add"), () ->
		{
			CommandHelper.sendCommand(container.getPlayer(), this.builderExperience.buildAdd());
			container.init();
		}));
		container.add(this.buttonRemove = new GuiButtonBase(x + 116 / 2, y + 48, 114, 20, new TranslatableComponent("gui.worldhandler.actions.remove"), () ->
		{
			CommandHelper.sendCommand(container.getPlayer(), this.builderExperience.buildRemove());
		}));
		container.add(new GuiButtonTooltip(x + 116 / 2, y + 72, 114, 20, new TranslatableComponent("gui.worldhandler.actions.reset"), new TranslatableComponent("gui.worldhandler.actions.set_to_0"), () ->
		{
			CommandHelper.sendCommand(container.getPlayer(), this.builderExperience.buildReset());
			container.init();
		}));
		
		boolean enabled = this.builderExperience.getLevel() > 0;
		
		this.buttonAdd.active = enabled;
		this.buttonRemove.active = enabled;
	}
	
	@Override
	public void tick(Container container)
	{
		boolean enabled = this.builderExperience.getLevel() > 0;
		
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
		return new TranslatableComponent("gui.worldhandler.title.player.experience");
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return new TranslatableComponent("gui.worldhandler.tab.player.experience");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.EXPERIENCE;
	}
	
	@Override
	public void onPlayerNameChanged(String username)
	{
		this.builderExperience.setPlayer(username);
	}
}
