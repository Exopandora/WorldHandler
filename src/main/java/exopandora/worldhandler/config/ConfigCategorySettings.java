package exopandora.worldhandler.config;

import java.util.Arrays;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

@OnlyIn(Dist.CLIENT)
public class ConfigCategorySettings
{
	private boolean commandSyntax;
	private boolean shortcuts;
	private boolean shortcutKeys;
	private boolean tooltips;
	private boolean watch;
	private boolean smoothWatch;
	private boolean pause;
	private boolean customTimes;
	private boolean permissionQuery;
	private boolean highlightBlocks;	
	private int dawn;
	private int noon;
	private int sunset;
	private int midnight;
	private String blockPlacingMode;
	
	private final BooleanValue valueCommandSyntax;
	private final BooleanValue valueShortcuts;
	private final BooleanValue valueShortcutKeys;
	private final BooleanValue valueTooltips;
	private final BooleanValue valueWatch;
	private final BooleanValue valueSmoothWatch;
	private final BooleanValue valuePause;
	private final BooleanValue valueCustomTimes;
	private final BooleanValue valuePermissionQuery;
	private final BooleanValue valueHighlightBlocks;
	private final IntValue valueDawn;
	private final IntValue valueNoon;
	private final IntValue valueSunset;
	private final IntValue valueMidnight;
	private final ConfigValue<String> valueBlockPlacingMode;
	
	public ConfigCategorySettings(ForgeConfigSpec.Builder builder)
	{
		builder.push("settings");
		
		this.valueCommandSyntax = builder
				.translation("gui.worldhandler.config.settings.command_syntax")
				.comment("Whether or not to display the current command at the bottom")
				.define("command_syntax", false);
		this.valueShortcuts = builder
				.translation("gui.worldhandler.config.settings.shortcuts")
				.comment("Whether or not to display a row of quick access buttons at the top")
				.define("shortcuts", false);
		this.valueShortcutKeys = builder
				.translation("gui.worldhandler.config.settings.key_shortcuts")
				.comment("Whether or not to enable button keys for pos1 and pos2")
				.define("key_shortcuts", false);
		this.valueTooltips = builder
				.translation("gui.worldhandler.config.settings.tooltips")
				.comment("Whether or not to display tooltips for buttons")
				.define("tooltips", true);
		this.valueWatch = builder
				.translation("gui.worldhandler.config.settings.watch")
				.comment("Whether or not to display a watch")
				.define("watch", true);
		this.valueSmoothWatch = builder
				.translation("gui.worldhandler.config.settings.smooth_watch")
				.comment("Whether or not the watch pointers move smoothly")
				.define("smooth_watch", true);
		this.valuePause = builder
				.translation("gui.worldhandler.config.settings.pause_game")
				.comment("Whether or not to pause the game when the gui is opened")
				.define("pause_game", false);
		this.valueCustomTimes = builder
				.translation("gui.worldhandler.config.settings.custom_times")
				.comment("Whether or not to use the custom times")
				.define("custom_times", false);
		this.valuePermissionQuery = builder
				.translation("gui.worldhandler.config.settings.permission_query")
				.comment("Whether or not the permission query is enabled")
				.define("permission_query", true);
		this.valueHighlightBlocks = builder
				.translation("gui.worldhandler.config.settings.highlight_blocks")
				.comment("Whether or not selected blocks will be highlighted")
				.define("highlight_blocks", true);
		
		this.valueDawn = builder
				.translation("gui.worldhandler.config.settings.custom_time_dawn")
				.comment("Ticks upon dawn")
				.defineInRange("custom_time_dawn", 1000, 0, 24000);
		this.valueNoon = builder
				.translation("gui.worldhandler.config.settings.custom_time_noon")
				.comment("Ticks upon noon")
				.defineInRange("custom_time_noon", 6000, 0, 24000);
		this.valueSunset = builder
				.translation("gui.worldhandler.config.settings.custom_time_sunset")
				.comment("Ticks upon sunset")
				.defineInRange("custom_time_sunset", 12500, 0, 24000);
		this.valueMidnight = builder
				.translation("gui.worldhandler.config.settings.custom_time_midnight")
				.comment("Ticks upon midnight")
				.defineInRange("custom_time_midnight", 18000, 0, 24000);
		
		this.valueBlockPlacingMode = builder
				.translation("gui.worldhandler.config.settings.block_placing_mode")
				.comment("Block placing mode (keep, replace, destroy)")
				.defineInList("block_placing_mode", "keep", Arrays.asList("keep", "replace", "destroy"));
		
		builder.pop();
	}
	
	public void read()
	{
		this.commandSyntax = this.valueCommandSyntax.get();
		this.shortcuts = this.valueShortcuts.get();
		this.shortcutKeys = this.valueShortcutKeys.get();
		this.tooltips = this.valueTooltips.get();
		this.watch = this.valueWatch.get();
		this.smoothWatch = this.valueSmoothWatch.get();
		this.pause = this.valuePause.get();
		this.customTimes = this.valueCustomTimes.get();
		this.permissionQuery = this.valuePermissionQuery.get();
		this.highlightBlocks = this.valueHighlightBlocks.get();
		this.dawn = this.valueDawn.get();
		this.noon = this.valueNoon.get();
		this.sunset = this.valueSunset.get();
		this.midnight = this.valueMidnight.get();
		this.blockPlacingMode = this.valueBlockPlacingMode.get();
	}
	
	private void write()
	{
		Config.set(this.valueCommandSyntax, this.commandSyntax);
		Config.set(this.valueShortcuts, this.shortcuts);
		Config.set(this.valueShortcutKeys, this.shortcutKeys);
		Config.set(this.valueTooltips, this.tooltips);
		Config.set(this.valueWatch, this.watch);
		Config.set(this.valueSmoothWatch, this.smoothWatch);
		Config.set(this.valuePause, this.pause);
		Config.set(this.valueCustomTimes, this.customTimes);
		Config.set(this.valuePermissionQuery, this.permissionQuery);
		Config.set(this.valueHighlightBlocks, this.highlightBlocks);
		Config.set(this.valueDawn, this.dawn);
		Config.set(this.valueNoon, this.noon);
		Config.set(this.valueSunset, this.sunset);
		Config.set(this.valueMidnight, this.midnight);
		Config.set(this.valueBlockPlacingMode, this.blockPlacingMode);
	}
	
	public boolean commandSyntax()
	{
		return this.commandSyntax;
	}
	
	public void setCommandSyntax(boolean enabled)
	{
		this.commandSyntax = enabled;
		this.write();
	}
	
	public boolean shortcuts()
	{
		return this.shortcuts;
	}
	
	public void setShortcuts(boolean enabled)
	{
		this.shortcuts = enabled;
		this.write();
	}
	
	public boolean shortcutKeys()
	{
		return this.shortcutKeys;
	}
	
	public void setShortcutKeys(boolean enabled)
	{
		this.shortcutKeys = enabled;
		this.write();
	}
	
	public boolean tooltips()
	{
		return this.tooltips;
	}
	
	public void setTooltips(boolean enabled)
	{
		this.tooltips = enabled;
		this.write();
	}
	
	public boolean watch()
	{
		return this.watch;
	}
	
	public void setWatch(boolean enabled)
	{
		this.watch = enabled;
		this.write();
	}
	
	public boolean smoothWatch()
	{
		return this.smoothWatch;
	}
	
	public void setSmoothWatch(boolean enabled)
	{
		this.smoothWatch = enabled;
		this.write();
	}
	
	public boolean pause()
	{
		return this.pause;
	}
	
	public void setPause(boolean enabled)
	{
		this.pause = enabled;
		this.write();
	}
	
	public boolean customTimes()
	{
		return this.customTimes;
	}
	
	public void setCustomTimes(boolean enabled)
	{
		this.customTimes = enabled;
		this.write();
	}
	
	public boolean permissionQuery()
	{
		return this.permissionQuery;
	}
	
	public void setPermissionQuery(boolean enabled)
	{
		this.permissionQuery = enabled;
		this.write();
	}
	
	public boolean highlightBlocks()
	{
		return this.highlightBlocks;
	}
	
	public void setHighlightBlocks(boolean enabled)
	{
		this.highlightBlocks = enabled;
		this.write();
	}
	
	public int getDawn()
	{
		return this.dawn;
	}
	
	public void setDawn(int ticks)
	{
		this.dawn = ticks;
		this.write();
	}
	
	public int getNoon()
	{
		return this.noon;
	}
	
	public void setNoon(int ticks)
	{
		this.noon = ticks;
		this.write();
	}
	
	public int getSunset()
	{
		return this.sunset;
	}
	
	public void setSunset(int ticks)
	{
		this.sunset = ticks;
		this.write();
	}
	
	public int getMidnight()
	{
		return this.midnight;
	}
	
	public void setMidnight(int ticks)
	{
		this.midnight = ticks;
		this.write();
	}
	
	public String getBlockPlacingMode()
	{
		return this.blockPlacingMode;
	}
	
	public void setBlockPlacingMode(String mode)
	{
		this.blockPlacingMode = mode;
		this.write();
	}
}