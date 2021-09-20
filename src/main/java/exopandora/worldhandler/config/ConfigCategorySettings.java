package exopandora.worldhandler.config;

import exopandora.worldhandler.builder.impl.BuilderSetBlock.EnumMode;
import exopandora.worldhandler.event.KeyHandler;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class ConfigCategorySettings
{
	private final BooleanValue commandSyntax;
	private final BooleanValue shortcuts;
	private final BooleanValue shortcutKeys;
	private final BooleanValue tooltips;
	private final BooleanValue watch;
	private final BooleanValue smoothWatch;
	private final BooleanValue pause;
	private final BooleanValue customTimes;
	private final BooleanValue permissionQuery;
	private final BooleanValue highlightBlocks;
	private final IntValue dawn;
	private final IntValue noon;
	private final IntValue sunset;
	private final IntValue midnight;
	private final ConfigValue<EnumMode> blockPlacingMode;
	
	public ConfigCategorySettings(ForgeConfigSpec.Builder builder)
	{
		builder.push("settings");
		
		this.commandSyntax = builder
				.translation("gui.worldhandler.config.settings.command_syntax")
				.comment("Whether or not to display the current command at the bottom")
				.define("command_syntax", false);
		this.shortcuts = builder
				.translation("gui.worldhandler.config.settings.shortcuts")
				.comment("Whether or not to display a row of quick access buttons at the top")
				.define("shortcuts", false);
		this.shortcutKeys = builder
				.translation("gui.worldhandler.config.settings.key_shortcuts")
				.comment("Whether or not to enable button keys for pos1 and pos2")
				.define("key_shortcuts", false);
		this.tooltips = builder
				.translation("gui.worldhandler.config.settings.tooltips")
				.comment("Whether or not to display tooltips for buttons")
				.define("tooltips", true);
		this.watch = builder
				.translation("gui.worldhandler.config.settings.watch")
				.comment("Whether or not to display a watch")
				.define("watch", false);
		this.smoothWatch = builder
				.translation("gui.worldhandler.config.settings.smooth_watch")
				.comment("Whether or not the watch pointers move smoothly")
				.define("smooth_watch", true);
		this.pause = builder
				.translation("gui.worldhandler.config.settings.pause_game")
				.comment("Whether or not to pause the game when the gui is opened")
				.define("pause_game", false);
		this.customTimes = builder
				.translation("gui.worldhandler.config.settings.custom_times")
				.comment("Whether or not to use the custom times")
				.define("custom_times", false);
		this.permissionQuery = builder
				.translation("gui.worldhandler.config.settings.permission_query")
				.comment("Whether or not the permission query is enabled")
				.define("permission_query", true);
		this.highlightBlocks = builder
				.translation("gui.worldhandler.config.settings.highlight_blocks")
				.comment("Whether or not selected blocks will be highlighted (Incompatible with fabolous graphics)")
				.define("highlight_blocks", true);
		
		this.dawn = builder
				.translation("gui.worldhandler.config.settings.custom_time_dawn")
				.comment("Ticks upon dawn")
				.defineInRange("custom_time_dawn", 1000, 0, 24000);
		this.noon = builder
				.translation("gui.worldhandler.config.settings.custom_time_noon")
				.comment("Ticks upon noon")
				.defineInRange("custom_time_noon", 6000, 0, 24000);
		this.sunset = builder
				.translation("gui.worldhandler.config.settings.custom_time_sunset")
				.comment("Ticks upon sunset")
				.defineInRange("custom_time_sunset", 12500, 0, 24000);
		this.midnight = builder
				.translation("gui.worldhandler.config.settings.custom_time_midnight")
				.comment("Ticks upon midnight")
				.defineInRange("custom_time_midnight", 18000, 0, 24000);
		
		this.blockPlacingMode = builder
				.translation("gui.worldhandler.config.settings.block_placing_mode")
				.comment("Block placing mode (keep, replace, destroy)")
				.defineEnum("block_placing_mode", EnumMode.KEEP, EnumMode.values());
		
		builder.pop();
	}
	
	public boolean commandSyntax()
	{
		return this.commandSyntax.get();
	}
	
	public void setCommandSyntax(boolean enabled)
	{
		Config.set(this.commandSyntax, enabled);
	}
	
	public boolean shortcuts()
	{
		return this.shortcuts.get();
	}
	
	public void setShortcuts(boolean enabled)
	{
		Config.set(this.shortcuts, enabled);
	}
	
	public boolean shortcutKeys()
	{
		return this.shortcutKeys.get();
	}
	
	public void setShortcutKeys(boolean enabled)
	{
		boolean previous = this.shortcutKeys();
		
		Config.set(this.shortcutKeys, enabled);
		
		if(previous != enabled)
		{
			KeyHandler.updatePosKeys();
		}
	}
	
	public boolean tooltips()
	{
		return this.tooltips.get();
	}
	
	public void setTooltips(boolean enabled)
	{
		Config.set(this.tooltips, enabled);
	}
	
	public boolean watch()
	{
		return this.watch.get();
	}
	
	public void setWatch(boolean enabled)
	{
		Config.set(this.watch, enabled);
	}
	
	public boolean smoothWatch()
	{
		return this.smoothWatch.get();
	}
	
	public void setSmoothWatch(boolean enabled)
	{
		Config.set(this.smoothWatch, enabled);
	}
	
	public boolean pause()
	{
		return this.pause.get();
	}
	
	public void setPause(boolean enabled)
	{
		Config.set(this.pause, enabled);
	}
	
	public boolean customTimes()
	{
		return this.customTimes.get();
	}
	
	public void setCustomTimes(boolean enabled)
	{
		Config.set(this.customTimes, enabled);
	}
	
	public boolean permissionQuery()
	{
		return this.permissionQuery.get();
	}
	
	public void setPermissionQuery(boolean enabled)
	{
		Config.set(this.permissionQuery, enabled);
	}
	
	public boolean highlightBlocks()
	{
		return this.highlightBlocks.get();
	}
	
	public void setHighlightBlocks(boolean enabled)
	{
		Config.set(this.highlightBlocks, enabled);
	}
	
	public int getDawn()
	{
		return this.dawn.get();
	}
	
	public void setDawn(int ticks)
	{
		Config.set(this.dawn, ticks);
	}
	
	public int getNoon()
	{
		return this.noon.get();
	}
	
	public void setNoon(int ticks)
	{
		Config.set(this.noon, ticks);
	}
	
	public int getSunset()
	{
		return this.sunset.get();
	}
	
	public void setSunset(int ticks)
	{
		Config.set(this.sunset, ticks);
	}
	
	public int getMidnight()
	{
		return this.midnight.get();
	}
	
	public void setMidnight(int ticks)
	{
		Config.set(this.midnight, ticks);
	}
	
	public EnumMode getBlockPlacingMode()
	{
		return this.blockPlacingMode.get();
	}
	
	public void setBlockPlacingMode(EnumMode mode)
	{
		Config.set(this.blockPlacingMode, mode);
	}
}