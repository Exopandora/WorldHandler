package exopandora.worldhandler;

import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.event.ClientEventHandler;
import exopandora.worldhandler.event.KeyHandler;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.usercontent.UsercontentLoader;
import exopandora.worldhandler.util.AdvancementHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.DistExecutor.SafeRunnable;
import net.minecraftforge.fml.IExtensionPoint.DisplayTest;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(Main.MODID)
public class WorldHandler
{
	public static final Logger LOGGER = LogManager.getLogger();
	public static final Path USERCONTENT_PATH = FMLPaths.CONFIGDIR.get().resolve(Main.MODID).resolve("usercontent");
	
	@SuppressWarnings("serial")
	public WorldHandler()
	{
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext modLoadingContext = ModLoadingContext.get();
		MinecraftForge.EVENT_BUS.addListener(this::registerCommands);
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> new SafeRunnable()
		{
			@Override
			public void run()
			{
				Config.setupDirectories(WorldHandler.USERCONTENT_PATH);
				modLoadingContext.registerConfig(Type.CLIENT, Config.CLIENT_SPEC, Main.MODID + "/" + Main.MODID + ".toml");
				UsercontentLoader.load(WorldHandler.USERCONTENT_PATH);
				modEventBus.register(Config.class);
				modEventBus.addListener(WorldHandler.this::clientSetup);
				modEventBus.addListener(WorldHandler.this::registerClientReloadListeners);
				modEventBus.addListener(Content::createRegistry);
				modEventBus.addListener(Category::createRegistry);
				modEventBus.addListener(Content::register);
				modEventBus.addListener(Category::register);
			}
		});
		modLoadingContext.registerExtensionPoint(DisplayTest.class, () -> new DisplayTest(() -> "ANY", (remote, isServer) -> true));
	}
	
	@SubscribeEvent
	public void clientSetup(FMLClientSetupEvent event)
	{
		MinecraftForge.EVENT_BUS.addListener(KeyHandler::keyInputEvent);
		MinecraftForge.EVENT_BUS.addListener(ClientEventHandler::renderLevelLastEvent);
		MinecraftForge.EVENT_BUS.addListener(ClientEventHandler::clientTickEvent);
		
		ClientRegistry.registerKeyBinding(KeyHandler.KEY_WORLD_HANDLER);
		KeyHandler.updatePosKeys();
	}
	
	@SubscribeEvent
	public void registerCommands(RegisterClientCommandsEvent event)
	{
		CommandHelper.registerCommands(event.getDispatcher(), event.getBuildContext());
	}
	
	@SubscribeEvent
	public void registerClientReloadListeners(RegisterClientReloadListenersEvent event)
	{
		event.registerReloadListener(AdvancementHelper.getInstance());
	}
}