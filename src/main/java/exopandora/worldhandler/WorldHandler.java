package exopandora.worldhandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.helper.CommandHelper;
import exopandora.worldhandler.proxy.ClientProxy;
import exopandora.worldhandler.proxy.CommonProxy;
import exopandora.worldhandler.util.UtilKeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Main.MODID)
public class WorldHandler
{
	public static final Logger LOGGER = LogManager.getLogger();
	
	public static final KeyBinding KEY_WORLD_HANDLER = new KeyBinding(Main.NAME, GLFW.GLFW_KEY_V, "key.categories.misc");
	public static final KeyBinding KEY_WORLD_HANDLER_POS1 = new KeyBinding(Main.NAME + " Pos1", GLFW.GLFW_KEY_O, "key.categories.misc");
	public static final KeyBinding KEY_WORLD_HANDLER_POS2 = new KeyBinding(Main.NAME + " Pos2", GLFW.GLFW_KEY_P, "key.categories.misc");
	
	public static String USERNAME = null;
	
	private static CommonProxy SIDEPROXY;
	
	public WorldHandler()
	{
		SIDEPROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadComplete);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(Config::configLoad);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(Config::configReload);
		ModLoadingContext.get().registerConfig(Type.CLIENT, Config.CLIENT_SPEC, Main.MODID + ".toml");
		MinecraftForge.EVENT_BUS.addListener(this::serverStarting);
	}
	
	private void commonSetup(FMLCommonSetupEvent event)
	{
		SIDEPROXY.setup();
	}
	
	private void clientSetup(FMLClientSetupEvent event)
	{
		USERNAME = Minecraft.getInstance().getSession().getUsername();
		ClientRegistry.registerKeyBinding(KEY_WORLD_HANDLER);
		UtilKeyBinding.updatePosKeys();
	}
	
	private void loadComplete(FMLLoadCompleteEvent event)
	{
		Content.registerContents();
		Category.registerCategories();
	}
	
	private void serverStarting(FMLServerStartingEvent event)
	{
		CommandHelper.registerCommands(event.getCommandDispatcher());
	}
}