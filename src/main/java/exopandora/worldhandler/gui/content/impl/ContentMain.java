package exopandora.worldhandler.gui.content.impl;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.widget.button.EnumIcon;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiButtonIcon;
import exopandora.worldhandler.util.ActionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.OptionsScreen;
import net.minecraft.client.gui.screen.PackScreen;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ContentMain extends Content
{
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(new GuiButtonIcon(x, y, 22, 20, EnumIcon.TIME_DAWN, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.time", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.time.dawn", Config.getSettings().getDawn())), ActionHelper::timeDawn));
		container.add(new GuiButtonIcon(x + 26, y, 22, 20, EnumIcon.TIME_NOON, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.time", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.time.noon", Config.getSettings().getNoon())), ActionHelper::timeNoon));
		container.add(new GuiButtonIcon(x + 26 * 2, y, 22, 20, EnumIcon.TIME_SUNSET, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.time", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.time.sunset", Config.getSettings().getSunset())), ActionHelper::timeSunset));
		container.add(new GuiButtonIcon(x + 26 * 3, y, 23, 20, EnumIcon.TIME_MIDNIGHT, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.time", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.time.midnight", Config.getSettings().getMidnight())), ActionHelper::timeMidnight));
		container.add(new GuiButtonIcon(x + 26 * 4, y, 24, 20, EnumIcon.DIFFICULTY_PEACEFUL, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.difficulty", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.difficulty.peaceful")), ActionHelper::difficultyPeaceful));
		container.add(new GuiButtonIcon(x + 26 * 5 + 2, y, 23, 20, EnumIcon.DIFFICULTY_EASY, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.difficulty", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.difficulty.easy")), ActionHelper::difficultyEasy));
		container.add(new GuiButtonIcon(x + 26 * 6 + 2, y, 22, 20, EnumIcon.DIFFICULTY_NORMAL, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.difficulty", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.difficulty.normal")), ActionHelper::difficultyNormal));
		container.add(new GuiButtonIcon(x + 26 * 7 + 2, y, 22, 20, EnumIcon.DIFFICULTY_HARD, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.difficulty", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.difficulty.hard")), ActionHelper::difficultyHard));
		container.add(new GuiButtonIcon(x + 26 * 8 + 2, y, 22, 20, EnumIcon.SETTINGS, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.settings"), () -> ActionHelper.open(Contents.SETTINGS)));
		
		container.add(new GuiButtonIcon(x, y + 24, 22, 20, EnumIcon.WEATHER_SUN, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.weather", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.weather.clear")), ActionHelper::weatherClear));
		container.add(new GuiButtonIcon(x + 26, y + 24, 22, 20, EnumIcon.WEATHER_RAIN, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.weather", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.weather.rainy")), ActionHelper::weatherRain));
		container.add(new GuiButtonIcon(x + 26 * 2, y + 24, 22, 20, EnumIcon.WEATHER_STORM, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.weather", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.weather.thunder")), ActionHelper::weatherThunder));
		container.add(new GuiButtonIcon(x + 26 * 3, y + 24, 23, 20, EnumIcon.POTION, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.potions"), () -> ActionHelper.open(Contents.POTIONS)));
		container.add(new GuiButtonIcon(x + 26 * 4, y + 24, 24, 20, EnumIcon.COMMAND_STACK, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.command_stack"), () -> ActionHelper.open(Contents.COMMAND_STACK)));
		container.add(new GuiButtonIcon(x + 26 * 5 + 2, y + 24, 23, 20, EnumIcon.GAMEMODE_SURVIVAL, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.gamemode", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.gamemode.survival")), ActionHelper::gamemodeSurvival));
		container.add(new GuiButtonIcon(x + 26 * 6 + 2, y + 24, 22, 20, EnumIcon.GAMEMODE_CREATIVE, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.gamemode", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.gamemode.creative")), ActionHelper::gamemodeCreative));
		container.add(new GuiButtonIcon(x + 26 * 7 + 2, y + 24, 22, 20, EnumIcon.GAMEMODE_ADVENTURE, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.gamemode", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.gamemode.adventure")), ActionHelper::gamemodeAdventure));
		container.add(new GuiButtonIcon(x + 26 * 8 + 2, y + 24, 22, 20, EnumIcon.GAMEMODE_SPECTATOR, new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.gamemode", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.gamemode.spectator")), ActionHelper::gamemodeSpectator));		
		
		container.add(new GuiButtonBase(x, y + 48, 74, 20, new TranslationTextComponent("gui.worldhandler.items"), () -> ActionHelper.open(Contents.CUSTOM_ITEM)));
		container.add(new GuiButtonBase(x + 78, y + 48, 76, 20, new TranslationTextComponent("gui.worldhandler.blocks"), () -> ActionHelper.open(Contents.EDIT_BLOCKS)));
		container.add(new GuiButtonBase(x + 158, y + 48, 74, 20, new TranslationTextComponent("gui.worldhandler.entities"), () -> ActionHelper.open(Contents.SUMMON)));
		
		container.add(new GuiButtonBase(x, y + 72, 74, 20, new TranslationTextComponent("gui.worldhandler.world"), () -> ActionHelper.open(Contents.WORLD_INFO)));
		container.add(new GuiButtonBase(x + 78, y + 72, 76, 20, new TranslationTextComponent("gui.worldhandler.player"), () -> ActionHelper.open(Contents.PLAYER)));
		container.add(new GuiButtonBase(x + 158, y + 72, 74, 20, new TranslationTextComponent("gui.worldhandler.scoreboard"), () -> ActionHelper.open(Contents.SCOREBOARD_OBJECTIVES)));
		
		container.add(new GuiButtonBase(x, y + 96, 74, 20, new TranslationTextComponent("gui.worldhandler.change_world"), () -> ActionHelper.open(Contents.CHANGE_WORLD)));
		container.add(new GuiButtonBase(x + 78, y + 96, 76, 20, new TranslationTextComponent("gui.worldhandler.resourcepack"), () -> 
		{
			Minecraft.getInstance().gameSettings.saveOptions();
			Minecraft.getInstance().displayGuiScreen(new PackScreen(container, Minecraft.getInstance().getResourcePackList(), resourcePackList ->
			{
				OptionsScreen optionsScreen = new OptionsScreen(container, Minecraft.getInstance().gameSettings);
				optionsScreen.init(Minecraft.getInstance(), 0, 0);
				optionsScreen.func_241584_a_(resourcePackList);
			}, Minecraft.getInstance().getFileResourcePacks(), new TranslationTextComponent("resourcePack.title")));
		}));
		container.add(new GuiButtonBase(x + 158, y + 96, 74, 20, new TranslationTextComponent("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.MAIN;
	}
	
	@Override
	public IFormattableTextComponent getTitle()
	{
		return new StringTextComponent(Main.NAME);
	}
	
	@Override
	public IFormattableTextComponent getTabTitle()
	{
		return new StringTextComponent(Main.NAME);
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.MAIN;
	}

	@Override
	public Content getBackContent()
	{
		return null;
	}
}
