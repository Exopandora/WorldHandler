package exopandora.worldhandler.gui.content.impl;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderExperience;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.button.GuiSlider;
import exopandora.worldhandler.gui.button.LogicSliderSimple;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
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
		container.add(new GuiButtonBase(x, y + 96, 114, 20, new TranslationTextComponent("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, new TranslationTextComponent("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(new GuiSlider(x + 116 / 2, y, 114, 20, 0, Config.getSliders().getMaxExperience(), 0, container, new LogicSliderSimple("experience", new TranslationTextComponent("gui.worldhandler.title.player.experience"), value -> 
		{
			this.builderExperience.setLevel(value);
		})));
		
		container.add(this.buttonAdd = new GuiButtonBase(x + 116 / 2, y + 24, 114, 20, new TranslationTextComponent("gui.worldhandler.actions.add"), () ->
		{
			CommandHelper.sendCommand(container.getPlayer(), this.builderExperience.getBuilderForAddLevels());
			container.init();
		}));
		container.add(this.buttonRemove = new GuiButtonBase(x + 116 / 2, y + 48, 114, 20, new TranslationTextComponent("gui.worldhandler.actions.remove"), () ->
		{
			CommandHelper.sendCommand(container.getPlayer(), this.builderExperience.getBuilderForRemoveLevels());
		}));
		container.add(new GuiButtonTooltip(x + 116 / 2, y + 72, 114, 20, new TranslationTextComponent("gui.worldhandler.actions.reset"), new TranslationTextComponent("gui.worldhandler.actions.set_to_0"), () ->
		{
			CommandHelper.sendCommand(container.getPlayer(), this.builderExperience.getBuilderForResetLevels());
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
	public IFormattableTextComponent getTitle()
	{
		return new TranslationTextComponent("gui.worldhandler.title.player.experience");
	}
	
	@Override
	public IFormattableTextComponent getTabTitle()
	{
		return new TranslationTextComponent("gui.worldhandler.tab.player.experience");
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
