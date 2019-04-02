package exopandora.worldhandler.config;

import java.util.Arrays;

import net.minecraft.client.resources.I18n;
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
				.comment(I18n.format("gui.worldhandler.config.comment.settings.command_syntax"))
				.translation("gui.worldhandler.config.key.settings.command_syntax")
				.define("command_syntax", false);
		this.valueShortcuts = builder
				.comment(I18n.format("gui.worldhandler.config.comment.settings.shortcuts"))
				.translation("gui.worldhandler.config.key.settings.shortcuts")
				.define("shortcuts", false);
		this.valueShortcutKeys = builder
				.comment(I18n.format("gui.worldhandler.config.comment.settings.key_shortcuts"))
				.translation("gui.worldhandler.config.key.settings.key_shortcuts")
				.define("key_shortcuts", false);
		this.valueTooltips = builder
				.comment(I18n.format("gui.worldhandler.config.comment.settings.tooltips"))
				.translation("gui.worldhandler.config.key.settings.tooltips")
				.define("tooltips", true);
		this.valueWatch = builder
				.comment(I18n.format("gui.worldhandler.config.comment.settings.watch"))
				.translation("gui.worldhandler.config.key.settings.watch")
				.define("watch", true);
		this.valueSmoothWatch = builder
				.comment(I18n.format("gui.worldhandler.config.comment.settings.smooth_watch"))
				.translation("gui.worldhandler.config.key.settings.smooth_watch")
				.define("smooth_watch", true);
		this.valuePause = builder
				.comment(I18n.format("gui.worldhandler.config.comment.settings.pause_game"))
				.translation("gui.worldhandler.config.key.settings.pause_game")
				.define("pause_game", false);
		this.valueCustomTimes = builder
				.comment(I18n.format("gui.worldhandler.config.comment.settings.custom_times"))
				.translation("gui.worldhandler.config.key.settings.custom_times")
				.define("custom_times", false);
		this.valuePermissionQuery = builder
				.comment(I18n.format("gui.worldhandler.config.comment.settings.permission_query"))
				.translation("gui.worldhandler.config.key.settings.permission_query")
				.define("permission_query", true);
		this.valueHighlightBlocks = builder
				.comment(I18n.format("gui.worldhandler.config.comment.settings.highlight_blocks"))
				.translation("gui.worldhandler.config.key.settings.highlight_blocks")
				.define("highlight_blocks", true);
		
		this.valueDawn = builder
				.comment(I18n.format("gui.worldhandler.config.comment.settings.custom_time_dawn"))
				.translation("gui.worldhandler.config.key.settings.custom_time_dawn")
				.defineInRange("custom_time_dawn", 1000, 0, 24000);
		this.valueNoon = builder
				.comment(I18n.format("gui.worldhandler.config.comment.settings.custom_time_noon"))
				.translation("gui.worldhandler.config.key.settings.custom_time_noon")
				.defineInRange("custom_time_noon", 6000, 0, 24000);
		this.valueSunset = builder
				.comment(I18n.format("gui.worldhandler.config.comment.settings.custom_time_sunset"))
				.translation("gui.worldhandler.config.key.settings.custom_time_sunset")
				.defineInRange("custom_time_sunset", 12500, 0, 24000);
		this.valueMidnight = builder
				.comment(I18n.format("gui.worldhandler.config.comment.settings.custom_time_midnight"))
				.translation("gui.worldhandler.config.key.settings.custom_time_midnight")
				.defineInRange("custom_time_midnight", 18000, 0, 24000);
		
		this.valueBlockPlacingMode = builder
				.comment(I18n.format("gui.worldhandler.config.comment.settings.block_placing_mode"))
				.translation("gui.worldhandler.config.key.settings.block_placing_mode")
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