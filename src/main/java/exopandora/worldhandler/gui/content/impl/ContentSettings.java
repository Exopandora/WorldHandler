package exopandora.worldhandler.gui.content.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.element.impl.ElementPageList;
import exopandora.worldhandler.gui.content.impl.ContentSettings.Setting.BooleanSetting;
import exopandora.worldhandler.gui.content.impl.ContentSettings.Setting.IntegerSetting;
import exopandora.worldhandler.gui.content.impl.abstr.ContentChild;
import exopandora.worldhandler.gui.logic.ILogicPageList;
import exopandora.worldhandler.helper.ActionHelper;
import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ContentSettings extends ContentChild
{
	private static final List<Setting<?>> SETTINGS = new ArrayList<Setting<?>>();
	
	static
	{
		SETTINGS.add(new BooleanSetting("command_syntax", Config.CLIENT.getSettings()::commandSyntax, Config.CLIENT.getSettings()::setCommandSyntax));
		SETTINGS.add(new BooleanSetting("shortcuts", Config.CLIENT.getSettings()::shortcuts, Config.CLIENT.getSettings()::setShortcuts));
		SETTINGS.add(new BooleanSetting("key_shortcuts", Config.CLIENT.getSettings()::shortcutKeys, Config.CLIENT.getSettings()::setShortcutKeys));
		SETTINGS.add(new BooleanSetting("tooltips", Config.CLIENT.getSettings()::tooltips, Config.CLIENT.getSettings()::setTooltips));
		SETTINGS.add(new BooleanSetting("watch", Config.CLIENT.getSettings()::watch, Config.CLIENT.getSettings()::setWatch));
		SETTINGS.add(new BooleanSetting("smooth_watch", Config.CLIENT.getSettings()::smoothWatch, Config.CLIENT.getSettings()::setSmoothWatch));
		SETTINGS.add(new BooleanSetting("pause_game", Config.CLIENT.getSettings()::pause, Config.CLIENT.getSettings()::setPause));
		SETTINGS.add(new BooleanSetting("custom_times", Config.CLIENT.getSettings()::customTimes, Config.CLIENT.getSettings()::setCustomTimes));
		SETTINGS.add(new BooleanSetting("permission_query", Config.CLIENT.getSettings()::permissionQuery, Config.CLIENT.getSettings()::setPermissionQuery));
		SETTINGS.add(new BooleanSetting("highlight_blocks", Config.CLIENT.getSettings()::highlightBlocks, Config.CLIENT.getSettings()::setHighlightBlocks));
		SETTINGS.add(new IntegerSetting("custom_time_dawn", Config.CLIENT.getSettings()::getDawn, Config.CLIENT.getSettings()::setDawn));
		SETTINGS.add(new IntegerSetting("custom_time_noon", Config.CLIENT.getSettings()::getNoon, Config.CLIENT.getSettings()::setNoon));
		SETTINGS.add(new IntegerSetting("custom_time_sunset", Config.CLIENT.getSettings()::getSunset, Config.CLIENT.getSettings()::setSunset));
		SETTINGS.add(new IntegerSetting("custom_time_midnight", Config.CLIENT.getSettings()::getMidnight, Config.CLIENT.getSettings()::setMidnight));
	}
	
	private Setting<?> setting;
	private GuiTextFieldTooltip valueField;
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		ElementPageList<Setting<?>> settings = new ElementPageList<Setting<?>>(x, y, SETTINGS, 114, 20, 3, container, new ILogicPageList<Setting<?>>()
		{
			@Override
			public String translate(Setting<?> item)
			{
				return I18n.format("gui.worldhandler.config.key.settings." + item.getKey());
			}
			
			@Override
			public String toTooltip(Setting<?> item)
			{
				return I18n.format("gui.worldhandler.config.comment.settings." + item.getKey());
			}
			
			@Override
			public void onClick(Setting<?> item)
			{
				ContentSettings.this.setting = item;
				container.initButtons();
			}
			
			@Override
			public GuiButtonBase onRegister(int x, int y, int width, int height, String text, Setting<?> item, ActionHandler actionHandler)
			{
				return new GuiButtonTooltip(x, y, width, height, text, this.toTooltip(item), actionHandler);
			}
			
			@Override
			public String getId()
			{
				return "settings";
			}
		});
		
		container.add(settings);

		this.valueField = new GuiTextFieldTooltip(x + 118, y + 24, 114, 20, I18n.format("gui.worldhandler.generic.value"));
		this.valueField.setValidator(string ->
		{
			if(string == null)
			{
				return false;
			}
			
			if(!string.isEmpty())
			{
				try
				{
					Integer.parseInt(string);
				}
				catch(Exception e)
				{
					return false;
				}
			}
			
			return true;
		});
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonBase button1;
		GuiButtonBase button2;
		
		container.add(new GuiButtonBase(x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		if(this.setting instanceof BooleanSetting)
		{
			BooleanSetting setting = (BooleanSetting) this.setting;
			
			container.add(button1 = new GuiButtonBase(x + 118, y + 24, 114, 20, I18n.format("gui.worldhandler.generic.enable"), () ->
			{
				setting.set(true);
				container.initButtons();
			}));
			container.add(button2 = new GuiButtonBase(x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.generic.disable"), () ->
			{
				setting.set(false);
				container.initButtons();
			}));
			
			boolean enabled = setting.get();
			
			button1.enabled = !enabled;
			button2.enabled = enabled;
		}
		else if(this.setting instanceof IntegerSetting)
		{
			IntegerSetting setting = (IntegerSetting) this.setting;
			this.valueField.setText(String.valueOf(setting.get()));
			
			container.add(this.valueField);
			container.add(new GuiButtonBase(x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.actions.set"), () ->
			{
				String text = this.valueField.getText();
				
				if(text.isEmpty())
				{
					setting.set(0);
				}
				else
				{
					setting.set(Integer.parseInt(text));
				}
				
				container.initButtons();
			}));
		}
	}
	
	@Override
	public void tick(Container container)
	{
		if(this.setting instanceof IntegerSetting)
		{
			this.valueField.tick();
		}
	}
	
	@Override
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(this.setting instanceof IntegerSetting)
		{
			this.valueField.drawTextField(mouseX, mouseY, partialTicks);
		}
	}
	
	@Override
	public String getTitle()
	{
		return I18n.format("gui.worldhandler.shortcuts.tooltip.settings");
	}
	
	public abstract static class Setting<T>
	{
		private final String key;
		private final Supplier<T> getter;
		private final Consumer<T> setter;
		
		public Setting(String key, Supplier<T> getter, Consumer<T> setter)
		{
			this.key = key;
			this.getter = getter;
			this.setter = setter;
		}
		
		public String getKey()
		{
			return this.key;
		}

		public T get()
		{
			return this.getter.get();
		}
		
		public void set(T value)
		{
			this.setter.accept(value);
		}
		
		public static class BooleanSetting extends Setting<Boolean>
		{
			public BooleanSetting(String key, Supplier<Boolean> getter, Consumer<Boolean> setter)
			{
				super(key, getter, setter);
			}
		}
		
		public static class IntegerSetting extends Setting<Integer>
		{
			public IntegerSetting(String key, Supplier<Integer> getter, Consumer<Integer> setter)
			{
				super(key, getter, setter);
			}
		}
	}
}
