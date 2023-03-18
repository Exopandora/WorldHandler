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
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ContentMain extends Content
{
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.addRenderableWidget(new GuiButtonIcon(x, y, 22, 20, EnumIcon.TIME_DAWN, Component.translatable("gui.worldhandler.shortcuts.tooltip.time", Component.translatable("gui.worldhandler.shortcuts.tooltip.time.dawn", Config.getSettings().getDawn())), ActionHelper::timeDawn));
		container.addRenderableWidget(new GuiButtonIcon(x + 26, y, 22, 20, EnumIcon.TIME_NOON, Component.translatable("gui.worldhandler.shortcuts.tooltip.time", Component.translatable("gui.worldhandler.shortcuts.tooltip.time.noon", Config.getSettings().getNoon())), ActionHelper::timeNoon));
		container.addRenderableWidget(new GuiButtonIcon(x + 26 * 2, y, 22, 20, EnumIcon.TIME_SUNSET, Component.translatable("gui.worldhandler.shortcuts.tooltip.time", Component.translatable("gui.worldhandler.shortcuts.tooltip.time.sunset", Config.getSettings().getSunset())), ActionHelper::timeSunset));
		container.addRenderableWidget(new GuiButtonIcon(x + 26 * 3, y, 23, 20, EnumIcon.TIME_MIDNIGHT, Component.translatable("gui.worldhandler.shortcuts.tooltip.time", Component.translatable("gui.worldhandler.shortcuts.tooltip.time.midnight", Config.getSettings().getMidnight())), ActionHelper::timeMidnight));
		container.addRenderableWidget(new GuiButtonIcon(x + 26 * 4, y, 24, 20, EnumIcon.DIFFICULTY_PEACEFUL, Component.translatable("gui.worldhandler.shortcuts.tooltip.difficulty", Component.translatable("gui.worldhandler.shortcuts.tooltip.difficulty.peaceful")), ActionHelper::difficultyPeaceful));
		container.addRenderableWidget(new GuiButtonIcon(x + 26 * 5 + 2, y, 23, 20, EnumIcon.DIFFICULTY_EASY, Component.translatable("gui.worldhandler.shortcuts.tooltip.difficulty", Component.translatable("gui.worldhandler.shortcuts.tooltip.difficulty.easy")), ActionHelper::difficultyEasy));
		container.addRenderableWidget(new GuiButtonIcon(x + 26 * 6 + 2, y, 22, 20, EnumIcon.DIFFICULTY_NORMAL, Component.translatable("gui.worldhandler.shortcuts.tooltip.difficulty", Component.translatable("gui.worldhandler.shortcuts.tooltip.difficulty.normal")), ActionHelper::difficultyNormal));
		container.addRenderableWidget(new GuiButtonIcon(x + 26 * 7 + 2, y, 22, 20, EnumIcon.DIFFICULTY_HARD, Component.translatable("gui.worldhandler.shortcuts.tooltip.difficulty", Component.translatable("gui.worldhandler.shortcuts.tooltip.difficulty.hard")), ActionHelper::difficultyHard));
		container.addRenderableWidget(new GuiButtonIcon(x + 26 * 8 + 2, y, 22, 20, EnumIcon.SETTINGS, Component.translatable("gui.worldhandler.shortcuts.tooltip.settings"), () -> ActionHelper.open(Contents.SETTINGS)));
		
		container.addRenderableWidget(new GuiButtonIcon(x, y + 24, 22, 20, EnumIcon.WEATHER_SUN, Component.translatable("gui.worldhandler.shortcuts.tooltip.weather", Component.translatable("gui.worldhandler.shortcuts.tooltip.weather.clear")), ActionHelper::weatherClear));
		container.addRenderableWidget(new GuiButtonIcon(x + 26, y + 24, 22, 20, EnumIcon.WEATHER_RAIN, Component.translatable("gui.worldhandler.shortcuts.tooltip.weather", Component.translatable("gui.worldhandler.shortcuts.tooltip.weather.rainy")), ActionHelper::weatherRain));
		container.addRenderableWidget(new GuiButtonIcon(x + 26 * 2, y + 24, 22, 20, EnumIcon.WEATHER_STORM, Component.translatable("gui.worldhandler.shortcuts.tooltip.weather", Component.translatable("gui.worldhandler.shortcuts.tooltip.weather.thunder")), ActionHelper::weatherThunder));
		container.addRenderableWidget(new GuiButtonIcon(x + 26 * 3, y + 24, 23, 20, EnumIcon.POTION, Component.translatable("gui.worldhandler.shortcuts.tooltip.potions"), () -> ActionHelper.open(Contents.POTIONS)));
		container.addRenderableWidget(new GuiButtonIcon(x + 26 * 4, y + 24, 24, 20, EnumIcon.COMMAND_STACK, Component.translatable("gui.worldhandler.shortcuts.tooltip.command_stack"), () -> ActionHelper.open(Contents.COMMAND_STACK)));
		container.addRenderableWidget(new GuiButtonIcon(x + 26 * 5 + 2, y + 24, 23, 20, EnumIcon.GAMEMODE_SURVIVAL, Component.translatable("gui.worldhandler.shortcuts.tooltip.gamemode", Component.translatable("gui.worldhandler.shortcuts.tooltip.gamemode.survival")), ActionHelper::gamemodeSurvival));
		container.addRenderableWidget(new GuiButtonIcon(x + 26 * 6 + 2, y + 24, 22, 20, EnumIcon.GAMEMODE_CREATIVE, Component.translatable("gui.worldhandler.shortcuts.tooltip.gamemode", Component.translatable("gui.worldhandler.shortcuts.tooltip.gamemode.creative")), ActionHelper::gamemodeCreative));
		container.addRenderableWidget(new GuiButtonIcon(x + 26 * 7 + 2, y + 24, 22, 20, EnumIcon.GAMEMODE_ADVENTURE, Component.translatable("gui.worldhandler.shortcuts.tooltip.gamemode", Component.translatable("gui.worldhandler.shortcuts.tooltip.gamemode.adventure")), ActionHelper::gamemodeAdventure));
		container.addRenderableWidget(new GuiButtonIcon(x + 26 * 8 + 2, y + 24, 22, 20, EnumIcon.GAMEMODE_SPECTATOR, Component.translatable("gui.worldhandler.shortcuts.tooltip.gamemode", Component.translatable("gui.worldhandler.shortcuts.tooltip.gamemode.spectator")), ActionHelper::gamemodeSpectator));		
		
		container.addRenderableWidget(new GuiButtonBase(x, y + 48, 74, 20, Component.translatable("gui.worldhandler.items"), () -> ActionHelper.open(Contents.CUSTOM_ITEM)));
		container.addRenderableWidget(new GuiButtonBase(x + 78, y + 48, 76, 20, Component.translatable("gui.worldhandler.blocks"), () -> ActionHelper.open(Contents.EDIT_BLOCKS)));
		container.addRenderableWidget(new GuiButtonBase(x + 158, y + 48, 74, 20, Component.translatable("gui.worldhandler.entities"), () -> ActionHelper.open(Contents.SUMMON)));
		
		container.addRenderableWidget(new GuiButtonBase(x, y + 72, 74, 20, Component.translatable("gui.worldhandler.world"), () -> ActionHelper.open(Contents.WORLD_INFO)));
		container.addRenderableWidget(new GuiButtonBase(x + 78, y + 72, 76, 20, Component.translatable("gui.worldhandler.player"), () -> ActionHelper.open(Contents.PLAYER)));
		container.addRenderableWidget(new GuiButtonBase(x + 158, y + 72, 74, 20, Component.translatable("gui.worldhandler.scoreboard"), () -> ActionHelper.open(Contents.SCOREBOARD_OBJECTIVES)));
		
		container.addRenderableWidget(new GuiButtonBase(x, y + 96, 74, 20, Component.translatable("gui.worldhandler.change_world"), () -> ActionHelper.open(Contents.CHANGE_WORLD)));
		container.addRenderableWidget(new GuiButtonBase(x + 78, y + 96, 76, 20, Component.translatable("gui.worldhandler.resourcepack"), () -> 
		{
			Minecraft minecraft = Minecraft.getInstance();
			minecraft.options.save();
			minecraft.setScreen(new PackSelectionScreen(Minecraft.getInstance().getResourcePackRepository(), resourcePackList ->
			{
				minecraft.options.updateResourcePacks(resourcePackList);
				ActionHelper.tryRun(() -> ActionHelper.open(Contents.MAIN));
			}, minecraft.getResourcePackDirectory(), Component.translatable("resourcePack.title")));
		}));
		container.addRenderableWidget(new GuiButtonBase(x + 158, y + 96, 74, 20, Component.translatable("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.MAIN;
	}
	
	@Override
	public MutableComponent getTitle()
	{
		return Component.literal(Main.NAME);
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return Component.literal(Main.NAME);
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
