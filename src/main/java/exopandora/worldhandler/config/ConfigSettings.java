package exopandora.worldhandler.config;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ConfigSettings
{
	private static boolean BIOME_INDICATOR;
	private static boolean COMMAND_SYNTAX;
	private static boolean SHORTCUTS;
	private static boolean SHORTCUT_KEYS;
	private static boolean TOOLTIPS;
	private static boolean WATCH;
	private static boolean SMOOTH_WATCH;
	private static boolean PAUSE;
	private static boolean CUSTOM_TIMES;
	private static boolean PERMISSION_QUERY;
	private static boolean HIGHLIGHT_BLOCKS;
	
	private static int DAWN;
	private static int NOON;
	private static int SUNSET;
	private static int MIDNIGHT;
	
	private static String BLOCK_PLACING_MODE;
	
	public static final String CATEGORY = "settings";
	
	public static void load(Configuration config)
	{
		BIOME_INDICATOR = config.getBoolean("biome_indicator", CATEGORY, false, I18n.format("gui.worldhandler.config.comment.settings.biome_indicator"), "gui.worldhandler.config.key.settings.biome_indicator");
		COMMAND_SYNTAX = config.getBoolean("command_syntax", CATEGORY, false, I18n.format("gui.worldhandler.config.comment.settings.command_syntax"), "gui.worldhandler.config.key.settings.command_syntax");
		SHORTCUTS = config.getBoolean("shortcuts", CATEGORY, false, I18n.format("gui.worldhandler.config.comment.settings.shortcuts"), "gui.worldhandler.config.key.settings.shortcuts");
		SHORTCUT_KEYS = config.getBoolean("key_shortcuts", CATEGORY, false, I18n.format("gui.worldhandler.config.comment.settings.key_shortcuts"), "gui.worldhandler.config.key.settings.key_shortcuts");
		TOOLTIPS = config.getBoolean("tooltips", CATEGORY, true, I18n.format("gui.worldhandler.config.comment.settings.tooltips"), "gui.worldhandler.config.key.settings.tooltips");
		WATCH = config.getBoolean("watch", CATEGORY, true, I18n.format("gui.worldhandler.config.comment.settings.watch"), "gui.worldhandler.config.key.settings.watch");
		SMOOTH_WATCH = config.getBoolean("smooth_watch", CATEGORY, true, I18n.format("gui.worldhandler.config.comment.settings.smooth_watch"), "gui.worldhandler.config.key.settings.smooth_watch");
		PAUSE = config.getBoolean("pause_game", CATEGORY, false, I18n.format("gui.worldhandler.config.comment.settings.pause_game"), "gui.worldhandler.config.key.settings.pause_game");
		CUSTOM_TIMES = config.getBoolean("custom_times", CATEGORY, false, I18n.format("gui.worldhandler.config.comment.settings.custom_times"), "gui.worldhandler.config.key.settings.custom_times");
		PERMISSION_QUERY = config.getBoolean("permission_query", CATEGORY, true, I18n.format("gui.worldhandler.config.comment.settings.permission_query"), "gui.worldhandler.config.key.settings.permission_query");
		HIGHLIGHT_BLOCKS = config.getBoolean("highlight_blocks", CATEGORY, true, I18n.format("gui.worldhandler.config.comment.settings.highlight_blocks"), "gui.worldhandler.config.key.settings.highlight_blocks");
		DAWN = config.getInt("custom_time_dawn", CATEGORY, 1000, 0, 24000, I18n.format("gui.worldhandler.config.comment.settings.custom_time_dawn"), "gui.worldhandler.config.key.settings.custom_time_dawn");
		NOON = config.getInt("custom_time_noon", CATEGORY, 6000, 0, 24000, I18n.format("gui.worldhandler.config.comment.settings.custom_time_noon"), "gui.worldhandler.config.key.settings.custom_time_noon");
		SUNSET = config.getInt("custom_time_sunset", CATEGORY, 12500, 0, 24000, I18n.format("gui.worldhandler.config.comment.settings.custom_time_sunset"), "gui.worldhandler.config.key.settings.custom_time_sunset");
		MIDNIGHT = config.getInt("custom_time_midnight", CATEGORY, 18000, 0, 24000, I18n.format("gui.worldhandler.config.comment.settings.custom_time_midnight"), "gui.worldhandler.config.key.settings.custom_time_midnight");
		BLOCK_PLACING_MODE = config.getString("block_placing_mode", CATEGORY, "keep", I18n.format("gui.worldhandler.config.comment.settings.block_placing_mode"), new String[]{"keep", "replace", "destroy"}, "gui.worldhandler.config.key.settings.block_placing_mode");
		
		if(config.hasChanged())
		{
			config.save();
		}
	}
	
	public static boolean isBiomeIndicatorEnabled()
	{
		return BIOME_INDICATOR;
	}

	public static boolean isCommandSyntaxEnabled()
	{
		return COMMAND_SYNTAX;
	}

	public static boolean areShortcutsEnabled()
	{
		return SHORTCUTS;
	}

	public static boolean arePosShortcutsEnabled()
	{
		return SHORTCUT_KEYS;
	}

	public static boolean areTooltipsEnabled()
	{
		return TOOLTIPS;
	}

	public static boolean isWatchEnabled()
	{
		return WATCH;
	}

	public static boolean isSmoothWatchEnabled()
	{
		return SMOOTH_WATCH;
	}

	public static boolean isPauseEnabled()
	{
		return PAUSE;
	}

	public static boolean isCustomTimeEnabled()
	{
		return CUSTOM_TIMES;
	}

	public static boolean isPermissionQueryEnabled()
	{
		return PERMISSION_QUERY;
	}
	
	public static boolean isHighlightBlocksEnabled()
	{
		return HIGHLIGHT_BLOCKS;
	}
	
	public static int getDawn()
	{
		return DAWN;
	}

	public static int getNoon()
	{
		return NOON;
	}

	public static int getSunset()
	{
		return SUNSET;
	}

	public static int getMidnight()
	{
		return MIDNIGHT;
	}
	
	public static String getMode()
	{
		return BLOCK_PLACING_MODE;
	}
}