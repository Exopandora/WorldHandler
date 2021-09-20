package exopandora.worldhandler.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import exopandora.worldhandler.event.KeyHandler;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.config.ModConfigEvent.Reloading;

public class Config
{
	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final ClientConfig CLIENT;
	
	static
	{
		Pair<ClientConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
		CLIENT_SPEC = pair.getRight();
		CLIENT = pair.getLeft();
	}
	
	public static class ClientConfig
	{
		private final ConfigCategorySettings settings;
		private final ConfigCategoryButcher butcher;
		private final ConfigCategorySkin skin;
		private final ConfigCategorySliders sliders;
		
		public ClientConfig(ForgeConfigSpec.Builder builder)
		{
			this.settings = new ConfigCategorySettings(builder);
			this.butcher = new ConfigCategoryButcher(builder);
			this.skin = new ConfigCategorySkin(builder);
			this.sliders = new ConfigCategorySliders(builder);
		}
		
		public ConfigCategorySettings getSettings()
		{
			return this.settings;
		}
		
		public ConfigCategoryButcher getButcher()
		{
			return this.butcher;
		}
		
		public ConfigCategorySkin getSkin()
		{
			return this.skin;
		}
		
		public ConfigCategorySliders getSliders()
		{
			return this.sliders;
		}
	}
	
	public static ConfigCategorySettings getSettings()
	{
		return Config.CLIENT.getSettings();
	}
	
	public static ConfigCategoryButcher getButcher()
	{
		return Config.CLIENT.getButcher();
	}
	
	public static ConfigCategorySkin getSkin()
	{
		return Config.CLIENT.getSkin();
	}
	
	public static ConfigCategorySliders getSliders()
	{
		return Config.CLIENT.getSliders();
	}
	
	protected static <T> void set(ForgeConfigSpec.ConfigValue<T> configValue, T value)
	{
		if(configValue != null && value != null && (!value.equals(configValue.get()) || configValue.get() instanceof List<?>))
		{
			configValue.set(value);
		}
	}
	
	@SubscribeEvent
	public static void configReload(Reloading event)
	{
		if(event.getConfig().getType().equals(Type.CLIENT))
		{
			KeyHandler.updatePosKeys();
		}
	}
	
	public static void setupDirectories(Path path)
	{
		try
		{
			if(!Files.exists(path))
			{
				Files.createDirectories(path);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
