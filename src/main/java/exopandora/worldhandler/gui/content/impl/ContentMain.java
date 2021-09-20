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
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class ContentMain extends Content
{
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(new GuiButtonIcon(x, y, 22, 20, EnumIcon.TIME_DAWN, new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.time", new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.time.dawn", Config.getSettings().getDawn())), ActionHelper::timeDawn));
		container.add(new GuiButtonIcon(x + 26, y, 22, 20, EnumIcon.TIME_NOON, new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.time", new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.time.noon", Config.getSettings().getNoon())), ActionHelper::timeNoon));
		container.add(new GuiButtonIcon(x + 26 * 2, y, 22, 20, EnumIcon.TIME_SUNSET, new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.time", new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.time.sunset", Config.getSettings().getSunset())), ActionHelper::timeSunset));
		container.add(new GuiButtonIcon(x + 26 * 3, y, 23, 20, EnumIcon.TIME_MIDNIGHT, new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.time", new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.time.midnight", Config.getSettings().getMidnight())), ActionHelper::timeMidnight));
		container.add(new GuiButtonIcon(x + 26 * 4, y, 24, 20, EnumIcon.DIFFICULTY_PEACEFUL, new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.difficulty", new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.difficulty.peaceful")), ActionHelper::difficultyPeaceful));
		container.add(new GuiButtonIcon(x + 26 * 5 + 2, y, 23, 20, EnumIcon.DIFFICULTY_EASY, new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.difficulty", new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.difficulty.easy")), ActionHelper::difficultyEasy));
		container.add(new GuiButtonIcon(x + 26 * 6 + 2, y, 22, 20, EnumIcon.DIFFICULTY_NORMAL, new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.difficulty", new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.difficulty.normal")), ActionHelper::difficultyNormal));
		container.add(new GuiButtonIcon(x + 26 * 7 + 2, y, 22, 20, EnumIcon.DIFFICULTY_HARD, new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.difficulty", new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.difficulty.hard")), ActionHelper::difficultyHard));
		container.add(new GuiButtonIcon(x + 26 * 8 + 2, y, 22, 20, EnumIcon.SETTINGS, new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.settings"), () -> ActionHelper.open(Contents.SETTINGS)));
		
		container.add(new GuiButtonIcon(x, y + 24, 22, 20, EnumIcon.WEATHER_SUN, new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.weather", new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.weather.clear")), ActionHelper::weatherClear));
		container.add(new GuiButtonIcon(x + 26, y + 24, 22, 20, EnumIcon.WEATHER_RAIN, new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.weather", new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.weather.rainy")), ActionHelper::weatherRain));
		container.add(new GuiButtonIcon(x + 26 * 2, y + 24, 22, 20, EnumIcon.WEATHER_STORM, new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.weather", new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.weather.thunder")), ActionHelper::weatherThunder));
		container.add(new GuiButtonIcon(x + 26 * 3, y + 24, 23, 20, EnumIcon.POTION, new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.potions"), () -> ActionHelper.open(Contents.POTIONS)));
		container.add(new GuiButtonIcon(x + 26 * 4, y + 24, 24, 20, EnumIcon.COMMAND_STACK, new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.command_stack"), () -> ActionHelper.open(Contents.COMMAND_STACK)));
		container.add(new GuiButtonIcon(x + 26 * 5 + 2, y + 24, 23, 20, EnumIcon.GAMEMODE_SURVIVAL, new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.gamemode", new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.gamemode.survival")), ActionHelper::gamemodeSurvival));
		container.add(new GuiButtonIcon(x + 26 * 6 + 2, y + 24, 22, 20, EnumIcon.GAMEMODE_CREATIVE, new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.gamemode", new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.gamemode.creative")), ActionHelper::gamemodeCreative));
		container.add(new GuiButtonIcon(x + 26 * 7 + 2, y + 24, 22, 20, EnumIcon.GAMEMODE_ADVENTURE, new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.gamemode", new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.gamemode.adventure")), ActionHelper::gamemodeAdventure));
		container.add(new GuiButtonIcon(x + 26 * 8 + 2, y + 24, 22, 20, EnumIcon.GAMEMODE_SPECTATOR, new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.gamemode", new TranslatableComponent("gui.worldhandler.shortcuts.tooltip.gamemode.spectator")), ActionHelper::gamemodeSpectator));		
		
		container.add(new GuiButtonBase(x, y + 48, 74, 20, new TranslatableComponent("gui.worldhandler.items"), () -> ActionHelper.open(Contents.CUSTOM_ITEM)));
		container.add(new GuiButtonBase(x + 78, y + 48, 76, 20, new TranslatableComponent("gui.worldhandler.blocks"), () -> ActionHelper.open(Contents.EDIT_BLOCKS)));
		container.add(new GuiButtonBase(x + 158, y + 48, 74, 20, new TranslatableComponent("gui.worldhandler.entities"), () -> ActionHelper.open(Contents.SUMMON)));
		
		container.add(new GuiButtonBase(x, y + 72, 74, 20, new TranslatableComponent("gui.worldhandler.world"), () -> ActionHelper.open(Contents.WORLD_INFO)));
		container.add(new GuiButtonBase(x + 78, y + 72, 76, 20, new TranslatableComponent("gui.worldhandler.player"), () -> ActionHelper.open(Contents.PLAYER)));
		container.add(new GuiButtonBase(x + 158, y + 72, 74, 20, new TranslatableComponent("gui.worldhandler.scoreboard"), () -> ActionHelper.open(Contents.SCOREBOARD_OBJECTIVES)));
		
		container.add(new GuiButtonBase(x, y + 96, 74, 20, new TranslatableComponent("gui.worldhandler.change_world"), () -> ActionHelper.open(Contents.CHANGE_WORLD)));
		container.add(new GuiButtonBase(x + 78, y + 96, 76, 20, new TranslatableComponent("gui.worldhandler.resourcepack"), () -> 
		{
			Minecraft.getInstance().options.save();
			Minecraft.getInstance().setScreen(new PackSelectionScreen(container, Minecraft.getInstance().getResourcePackRepository(), resourcePackList ->
			{
				OptionsScreen optionsScreen = new OptionsScreen(container, Minecraft.getInstance().options);
				optionsScreen.init(Minecraft.getInstance(), 0, 0);
				optionsScreen.updatePackList(resourcePackList);
			}, Minecraft.getInstance().getResourcePackDirectory(), new TranslatableComponent("resourcePack.title")));
		}));
		container.add(new GuiButtonBase(x + 158, y + 96, 74, 20, new TranslatableComponent("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.MAIN;
	}
	
	@Override
	public MutableComponent getTitle()
	{
		return new TextComponent(Main.NAME);
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return new TextComponent(Main.NAME);
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
