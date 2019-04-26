package exopandora.worldhandler.config;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;

import exopandora.worldhandler.util.UtilKeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.config.ModConfig.Type;

public class Config
{
	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final ClientConfig CLIENT;
	
	private static ModConfig MOD_CONFIG;
	private static CommentedFileConfig CONFIG_DATA;
	
	static
	{
		Pair<ClientConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
		CLIENT_SPEC = pair.getRight();
		CLIENT = pair.getLeft();
	}
	
	@OnlyIn(Dist.CLIENT)
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
		
		public void read()
		{
			this.getSettings().read();
			this.getButcher().read();
			this.getSkin().read();
			this.getSliders().read();
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
			Config.CONFIG_DATA.set(configValue.getPath(), value);
		}
	}
	
	@SubscribeEvent
	public static void configLoad(ModConfig.Loading event)
	{
		if(event.getConfig().getType().equals(Type.CLIENT))
		{
			Config.MOD_CONFIG = event.getConfig();
			Config.CONFIG_DATA = (CommentedFileConfig) Config.MOD_CONFIG.getConfigData();
			Config.CLIENT.read();
		}
	}
	
	@SubscribeEvent
	public static void configReload(ModConfig.ConfigReloading event)
	{
		if(event.getConfig().getType().equals(Type.CLIENT) && Config.CONFIG_DATA != null)
		{
			Config.CONFIG_DATA.load();
			Config.CLIENT.read();
			
			UtilKeyBinding.updatePosKeys();
		}
	}
}
