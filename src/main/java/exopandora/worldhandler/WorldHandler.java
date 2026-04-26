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
import exopandora.worldhandler.util.CommandHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLPaths;

@Mod(Main.MODID)
public class WorldHandler
{
	public static final Logger LOGGER = LogManager.getLogger();
	public static final Path USERCONTENT_PATH = FMLPaths.CONFIGDIR.get().resolve(Main.MODID).resolve("usercontent");
	
	public WorldHandler(IEventBus modEventBus, ModContainer modContainer)
	{
		if(Dist.CLIENT.equals(FMLEnvironment.dist))
		{
			Config.setupDirectories(WorldHandler.USERCONTENT_PATH);
			modContainer.registerConfig(Type.CLIENT, Config.CLIENT_SPEC, Main.MODID + "/" + Main.MODID + ".toml");
			UsercontentLoader.load(WorldHandler.USERCONTENT_PATH);
			modEventBus.addListener(WorldHandler.this::registerKeyMappingsEvent);
			modEventBus.addListener(Content::createRegistry);
			modEventBus.addListener(Category::createRegistry);
			modEventBus.addListener(Content::register);
			modEventBus.addListener(Category::register);
		}
		modEventBus.addListener(this::clientSetup);
		modEventBus.addListener(this::commonSetup);
	}
	
	@SubscribeEvent
	public void clientSetup(FMLClientSetupEvent event)
	{
		NeoForge.EVENT_BUS.addListener(KeyHandler::keyInputEvent);
		NeoForge.EVENT_BUS.addListener(ClientEventHandler::renderLevelStageEvent);
		NeoForge.EVENT_BUS.addListener(ClientEventHandler::clientTickEvent);
	}
	
	@SubscribeEvent
	public void commonSetup(FMLCommonSetupEvent event)
	{
		NeoForge.EVENT_BUS.addListener(this::registerCommands);
	}
	
	@SubscribeEvent
	public void registerKeyMappingsEvent(RegisterKeyMappingsEvent event)
	{
		event.register(KeyHandler.KEY_WORLD_HANDLER);
		event.register(KeyHandler.KEY_WORLD_HANDLER_POS1);
		event.register(KeyHandler.KEY_WORLD_HANDLER_POS2);
	}
	
	@SubscribeEvent
	public void registerCommands(RegisterClientCommandsEvent event)
	{
		CommandHelper.registerCommands(event.getDispatcher(), event.getBuildContext());
	}
}
