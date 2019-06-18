package exopandora.worldhandler.gui.content.impl;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.button.EnumIcon;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiButtonIcon;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.container.impl.GuiWorldHandler;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.helper.ActionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ResourcePacksScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ContentMain extends Content
{
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(new GuiButtonIcon(x, y, 22, 20, EnumIcon.TIME_DAWN, I18n.format("gui.worldhandler.shortcuts.tooltip.time", I18n.format("gui.worldhandler.shortcuts.tooltip.time.dawn", Config.getSettings().getDawn())), ActionHelper::timeDawn));
		container.add(new GuiButtonIcon(x + 26, y, 22, 20, EnumIcon.TIME_NOON, I18n.format("gui.worldhandler.shortcuts.tooltip.time", I18n.format("gui.worldhandler.shortcuts.tooltip.time.noon", Config.getSettings().getNoon())), ActionHelper::timeNoon));
		container.add(new GuiButtonIcon(x + 26 * 2, y, 22, 20, EnumIcon.TIME_SUNSET, I18n.format("gui.worldhandler.shortcuts.tooltip.time", I18n.format("gui.worldhandler.shortcuts.tooltip.time.sunset", Config.getSettings().getSunset())), ActionHelper::timeSunset));
		container.add(new GuiButtonIcon(x + 26 * 3, y, 23, 20, EnumIcon.TIME_MIDNIGHT, I18n.format("gui.worldhandler.shortcuts.tooltip.time", I18n.format("gui.worldhandler.shortcuts.tooltip.time.midnight", Config.getSettings().getMidnight())), ActionHelper::timeMidnight));
		container.add(new GuiButtonIcon(x + 26 * 4, y, 24, 20, EnumIcon.DIFFICULTY_PEACEFUL, I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty", I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty.peaceful")), ActionHelper::difficultyPeaceful));
		container.add(new GuiButtonIcon(x + 26 * 5 + 2, y, 23, 20, EnumIcon.DIFFICULTY_EASY, I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty", I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty.easy")), ActionHelper::difficultyEasy));
		container.add(new GuiButtonIcon(x + 26 * 6 + 2, y, 22, 20, EnumIcon.DIFFICULTY_NORMAL, I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty", I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty.normal")), ActionHelper::difficultyNormal));
		container.add(new GuiButtonIcon(x + 26 * 7 + 2, y, 22, 20, EnumIcon.DIFFICULTY_HARD, I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty", I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty.hard")), ActionHelper::difficultyHard));
		container.add(new GuiButtonIcon(x + 26 * 8 + 2, y, 22, 20, EnumIcon.SETTINGS, I18n.format("gui.worldhandler.shortcuts.tooltip.settings"), () ->
		{
			Minecraft.getInstance().displayGuiScreen(new GuiWorldHandler(Contents.SETTINGS.withParent(Contents.MAIN)));
		}));
		
		container.add(new GuiButtonIcon(x, y + 24, 22, 20, EnumIcon.WEATHER_SUN, I18n.format("gui.worldhandler.shortcuts.tooltip.weather", I18n.format("gui.worldhandler.shortcuts.tooltip.weather.clear")), ActionHelper::weatherClear));
		container.add(new GuiButtonIcon(x + 26, y + 24, 22, 20, EnumIcon.WEATHER_RAIN, I18n.format("gui.worldhandler.shortcuts.tooltip.weather", I18n.format("gui.worldhandler.shortcuts.tooltip.weather.rainy")), ActionHelper::weatherRain));
		container.add(new GuiButtonIcon(x + 26 * 2, y + 24, 22, 20, EnumIcon.WEATHER_STORM, I18n.format("gui.worldhandler.shortcuts.tooltip.weather", I18n.format("gui.worldhandler.shortcuts.tooltip.weather.thunder")), ActionHelper::weatherThunder));
		container.add(new GuiButtonIcon(x + 26 * 3, y + 24, 23, 20, EnumIcon.BUTCHER, I18n.format("gui.worldhandler.shortcuts.tooltip.butcher"), () -> 
		{
			Minecraft.getInstance().displayGuiScreen(new GuiWorldHandler(Contents.BUTCHER.withParent(Contents.MAIN)));
		}));
		container.add(new GuiButtonIcon(x + 26 * 4, y + 24, 24, 20, EnumIcon.POTION, I18n.format("gui.worldhandler.shortcuts.tooltip.potions"), () -> 
		{
			Minecraft.getInstance().displayGuiScreen(new GuiWorldHandler(Contents.POTIONS.withParent(Contents.MAIN)));
		}));
		container.add(new GuiButtonIcon(x + 26 * 5 + 2, y + 24, 23, 20, EnumIcon.GAMEMODE_SURVIVAL, I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode", I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode.survival")), ActionHelper::gamemodeSurvival));
		container.add(new GuiButtonIcon(x + 26 * 6 + 2, y + 24, 22, 20, EnumIcon.GAMEMODE_CREATIVE, I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode", I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode.creative")), ActionHelper::gamemodeCreative));
		container.add(new GuiButtonIcon(x + 26 * 7 + 2, y + 24, 22, 20, EnumIcon.GAMEMODE_ADVENTURE, I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode", I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode.adventure")), ActionHelper::gamemodeAdventure));
		container.add(new GuiButtonIcon(x + 26 * 8 + 2, y + 24, 22, 20, EnumIcon.GAMEMODE_SPECTATOR, I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode", I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode.spectator")), ActionHelper::gamemodeSpectator));		
		
		container.add(new GuiButtonBase(x, y + 48, 74, 20, I18n.format("gui.worldhandler.items"), () -> 
		{
			Minecraft.getInstance().displayGuiScreen(new GuiWorldHandler(Contents.CUSTOM_ITEM));
		}));
		container.add(new GuiButtonBase(x + 78, y + 48, 76, 20, I18n.format("gui.worldhandler.blocks"), () -> 
		{
			Minecraft.getInstance().displayGuiScreen(new GuiWorldHandler(Contents.EDIT_BLOCKS));
		}));
		container.add(new GuiButtonBase(x + 158, y + 48, 74, 20, I18n.format("gui.worldhandler.entities"), () -> 
		{
			Minecraft.getInstance().displayGuiScreen(new GuiWorldHandler(Contents.SUMMON));
		}));
		
		container.add(new GuiButtonBase(x, y + 72, 74, 20, I18n.format("gui.worldhandler.world"), () -> 
		{
			Minecraft.getInstance().displayGuiScreen(new GuiWorldHandler(Contents.WORLD_INFO));
		}));
		container.add(new GuiButtonBase(x + 78, y + 72, 76, 20, I18n.format("gui.worldhandler.player"), () -> 
		{
			Minecraft.getInstance().displayGuiScreen(new GuiWorldHandler(Contents.PLAYER));
		}));
		container.add(new GuiButtonBase(x + 158, y + 72, 74, 20, I18n.format("gui.worldhandler.scoreboard"), () -> 
		{
			Minecraft.getInstance().displayGuiScreen(new GuiWorldHandler(Contents.SCOREBOARD_OBJECTIVES));
		}));
		
		container.add(new GuiButtonBase(x, y + 96, 74, 20, I18n.format("gui.worldhandler.change_world"), () -> 
		{
			Minecraft.getInstance().displayGuiScreen(new GuiWorldHandler(Contents.CHANGE_WORLD.withParent(Contents.MAIN)));
		}));
		container.add(new GuiButtonBase(x + 78, y + 96, 76, 20, I18n.format("gui.worldhandler.resourcepack"), () -> 
		{
			Minecraft.getInstance().gameSettings.saveOptions();
			Minecraft.getInstance().displayGuiScreen(new ResourcePacksScreen(container));
		}));
		container.add(new GuiButtonBase(x + 158, y + 96, 74, 20, I18n.format("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.MAIN;
	}
	
	@Override
	public String getTitle()
	{
		return Main.NAME;
	}
	
	@Override
	public String getTabTitle()
	{
		return Main.NAME;
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
