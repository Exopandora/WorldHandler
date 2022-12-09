package exopandora.worldhandler.gui.widget;

import java.util.function.Supplier;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.widget.button.EnumIcon;
import exopandora.worldhandler.gui.widget.button.GuiButtonIcon;
import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.ActionHelper;
import net.minecraft.network.chat.Component;

public class WidgetShortcuts implements IContainerWidget
{
	@Override
	public void initButtons(Container container, int x, int y)
	{
		int start = container.width / 2 - 157;
		EnumShortcuts shortcuts[] = EnumShortcuts.values();
		
		for(int i = 0; i < shortcuts.length; i++)
		{
			EnumShortcuts shortcut = shortcuts[i];
			container.addRenderableWidget(new GuiButtonIcon(start + i * 21, 0, 20, 20, shortcut.getIcon(), shortcut.getTextSupplier().get(), shortcut.getActionHandler()));
		}
	}
	
	@Override
	public boolean isEnabled()
	{
		return Config.getSettings().shortcuts();
	}
	
	@Override
	public EnumLayer getLayer()
	{
		return EnumLayer.FOREGROUND;
	}
	
	public static enum EnumShortcuts
	{
		TIME_DAWN(EnumIcon.TIME_DAWN, ActionHelper::timeDawn, () ->
		{
			return Component.translatable("gui.worldhandler.shortcuts.tooltip.time", Component.translatable("gui.worldhandler.shortcuts.tooltip.time.dawn", Config.getSettings().getDawn()));
		}),
		TIME_NOON(EnumIcon.TIME_NOON, ActionHelper::timeNoon, () ->
		{
			return Component.translatable("gui.worldhandler.shortcuts.tooltip.time", Component.translatable("gui.worldhandler.shortcuts.tooltip.time.noon", Config.getSettings().getNoon()));
		}),
		TIME_SUNSET(EnumIcon.TIME_SUNSET, ActionHelper::timeSunset, () ->
		{
			return Component.translatable("gui.worldhandler.shortcuts.tooltip.time", Component.translatable("gui.worldhandler.shortcuts.tooltip.time.sunset", Config.getSettings().getSunset()));
		}),
		TIME_MIDNIGHT(EnumIcon.TIME_MIDNIGHT, ActionHelper::timeMidnight, () ->
		{
			return Component.translatable("gui.worldhandler.shortcuts.tooltip.time", Component.translatable("gui.worldhandler.shortcuts.tooltip.time.midnight", Config.getSettings().getMidnight()));
		}),
		WEATHER_SUN(EnumIcon.WEATHER_SUN, ActionHelper::weatherClear, () ->
		{
			return Component.translatable("gui.worldhandler.shortcuts.tooltip.weather", Component.translatable("gui.worldhandler.shortcuts.tooltip.weather.clear"));
		}),
		WEATHER_RAIN(EnumIcon.WEATHER_RAIN, ActionHelper::weatherRain, () ->
		{
			return Component.translatable("gui.worldhandler.shortcuts.tooltip.weather", Component.translatable("gui.worldhandler.shortcuts.tooltip.weather.rainy"));
		}),
		WEATHER_STORM(EnumIcon.WEATHER_STORM, ActionHelper::weatherThunder, () ->
		{
			return Component.translatable("gui.worldhandler.shortcuts.tooltip.weather", Component.translatable("gui.worldhandler.shortcuts.tooltip.weather.thunder"));
		}),
		DIFFICULTY_PEACEFUL(EnumIcon.DIFFICULTY_PEACEFUL, ActionHelper::difficultyPeaceful, () ->
		{
			return Component.translatable("gui.worldhandler.shortcuts.tooltip.difficulty", Component.translatable("gui.worldhandler.shortcuts.tooltip.difficulty.peaceful"));
		}),
		DIFFICULTY_EASY(EnumIcon.DIFFICULTY_EASY, ActionHelper::difficultyEasy, () ->
		{
			return Component.translatable("gui.worldhandler.shortcuts.tooltip.difficulty", Component.translatable("gui.worldhandler.shortcuts.tooltip.difficulty.easy"));
		}),
		DIFFICULTY_NORMAL(EnumIcon.DIFFICULTY_NORMAL, ActionHelper::difficultyNormal, () ->
		{
			return Component.translatable("gui.worldhandler.shortcuts.tooltip.difficulty", Component.translatable("gui.worldhandler.shortcuts.tooltip.difficulty.normal"));
		}),
		DIFFICULTY_HARD(EnumIcon.DIFFICULTY_HARD, ActionHelper::difficultyHard, () ->
		{
			return Component.translatable("gui.worldhandler.shortcuts.tooltip.difficulty", Component.translatable("gui.worldhandler.shortcuts.tooltip.difficulty.hard"));
		}),
		GAMEMODE_SURVIVAL(EnumIcon.GAMEMODE_SURVIVAL, ActionHelper::gamemodeSurvival, () ->
		{
			return Component.translatable("gui.worldhandler.shortcuts.tooltip.gamemode", Component.translatable("gui.worldhandler.shortcuts.tooltip.gamemode.survival"));
		}),
		GAMEMODE_CREATIVE(EnumIcon.GAMEMODE_CREATIVE, ActionHelper::gamemodeCreative, () ->
		{
			return Component.translatable("gui.worldhandler.shortcuts.tooltip.gamemode", Component.translatable("gui.worldhandler.shortcuts.tooltip.gamemode.creative"));
		}),
		GAMEMODE_ADVENTURE(EnumIcon.GAMEMODE_ADVENTURE, ActionHelper::gamemodeAdventure, () ->
		{
			return Component.translatable("gui.worldhandler.shortcuts.tooltip.gamemode", Component.translatable("gui.worldhandler.shortcuts.tooltip.gamemode.adventure"));
		}),
		GAMEMODE_SPECTATOR(EnumIcon.GAMEMODE_SPECTATOR, ActionHelper::gamemodeSpectator, () ->
		{
			return Component.translatable("gui.worldhandler.shortcuts.tooltip.gamemode", Component.translatable("gui.worldhandler.shortcuts.tooltip.gamemode.spectator"));
		});
		
		private final EnumIcon icon;
		private final Supplier<Component> text;
		private final ActionHandler actionHandler;
		
		private EnumShortcuts(EnumIcon icon, ActionHandler actionHandler, Supplier<Component> text)
		{
			this.icon = icon;
			this.text = text;
			this.actionHandler = actionHandler;
		}
		
		public EnumIcon getIcon()
		{
			return this.icon;
		}
		
		public Supplier<Component> getTextSupplier()
		{
			return this.text;
		}
		
		public ActionHandler getActionHandler()
		{
			return this.actionHandler;
		}
	}
}
