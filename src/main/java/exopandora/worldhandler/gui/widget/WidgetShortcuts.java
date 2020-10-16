package exopandora.worldhandler.gui.widget;

import java.util.function.Supplier;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.widget.button.EnumIcon;
import exopandora.worldhandler.gui.widget.button.GuiButtonIcon;
import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.ActionHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WidgetShortcuts implements IContainerWidget
{
	@Override
	public void initGui(Container container, int x, int y)
	{
		int start = container.width / 2 - 157;
		EnumShortcuts shortcuts[] = EnumShortcuts.values();
		
		for(int i = 0; i < shortcuts.length; i++)
		{
			EnumShortcuts shortcut = shortcuts[i];
			container.addWidget(new GuiButtonIcon(start + i * 21, 0, 20, 20, shortcut.getIcon(), shortcut.getTextSupplier().get(), shortcut.getActionHandler()));
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
	
	@OnlyIn(Dist.CLIENT)
	public static enum EnumShortcuts
	{
		TIME_DAWN(EnumIcon.TIME_DAWN, ActionHelper::timeDawn, () ->
		{
			return new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.time", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.time.dawn", Config.getSettings().getDawn()));
		}),
		TIME_NOON(EnumIcon.TIME_NOON, ActionHelper::timeNoon, () ->
		{
			return new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.time", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.time.noon", Config.getSettings().getNoon()));
		}),
		TIME_SUNSET(EnumIcon.TIME_SUNSET, ActionHelper::timeSunset, () ->
		{
			return new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.time", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.time.sunset", Config.getSettings().getSunset()));
		}),
		TIME_MIDNIGHT(EnumIcon.TIME_MIDNIGHT, ActionHelper::timeMidnight, () ->
		{
			return new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.time", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.time.midnight", Config.getSettings().getMidnight()));
		}),
		WEATHER_SUN(EnumIcon.WEATHER_SUN, ActionHelper::weatherClear, () ->
		{
			return new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.weather", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.weather.clear"));
		}),
		WEATHER_RAIN(EnumIcon.WEATHER_RAIN, ActionHelper::weatherRain, () ->
		{
			return new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.weather", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.weather.rainy"));
		}),
		WEATHER_STORM(EnumIcon.WEATHER_STORM, ActionHelper::weatherThunder, () ->
		{
			return new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.weather", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.weather.thunder"));
		}),
		DIFFICULTY_PEACEFUL(EnumIcon.DIFFICULTY_PEACEFUL, ActionHelper::difficultyPeaceful, () ->
		{
			return new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.difficulty", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.difficulty.peaceful"));
		}),
		DIFFICULTY_EASY(EnumIcon.DIFFICULTY_EASY, ActionHelper::difficultyEasy, () ->
		{
			return new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.difficulty", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.difficulty.easy"));
		}),
		DIFFICULTY_NORMAL(EnumIcon.DIFFICULTY_NORMAL, ActionHelper::difficultyNormal, () ->
		{
			return new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.difficulty", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.difficulty.normal"));
		}),
		DIFFICULTY_HARD(EnumIcon.DIFFICULTY_HARD, ActionHelper::difficultyHard, () ->
		{
			return new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.difficulty", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.difficulty.hard"));
		}),
		GAMEMODE_SURVIVAL(EnumIcon.GAMEMODE_SURVIVAL, ActionHelper::gamemodeSurvival, () ->
		{
			return new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.gamemode", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.gamemode.survival"));
		}),
		GAMEMODE_CREATIVE(EnumIcon.GAMEMODE_CREATIVE, ActionHelper::gamemodeCreative, () ->
		{
			return new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.gamemode", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.gamemode.creative"));
		}),
		GAMEMODE_ADVENTURE(EnumIcon.GAMEMODE_ADVENTURE, ActionHelper::gamemodeAdventure, () ->
		{
			return new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.gamemode", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.gamemode.adventure"));
		}),
		GAMEMODE_SPECTATOR(EnumIcon.GAMEMODE_SPECTATOR, ActionHelper::gamemodeSpectator, () ->
		{
			return new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.gamemode", new TranslationTextComponent("gui.worldhandler.shortcuts.tooltip.gamemode.spectator"));
		});
		
		private final EnumIcon icon;
		private final Supplier<ITextComponent> text;
		private final ActionHandler actionHandler;
		
		private EnumShortcuts(EnumIcon icon, ActionHandler actionHandler, Supplier<ITextComponent> text)
		{
			this.icon = icon;
			this.text = text;
			this.actionHandler = actionHandler;
		}
		
		public EnumIcon getIcon()
		{
			return this.icon;
		}
		
		public Supplier<ITextComponent> getTextSupplier()
		{
			return this.text;
		}
		
		public ActionHandler getActionHandler()
		{
			return this.actionHandler;
		}
	}
}
