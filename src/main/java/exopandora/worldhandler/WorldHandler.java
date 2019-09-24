package exopandora.worldhandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.event.ClientEventHandler;
import exopandora.worldhandler.event.KeyHandler;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.helper.CommandHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Main.MODID)
public class WorldHandler
{
	public static final Logger LOGGER = LogManager.getLogger();
	public static String USERNAME = null;
	
	public WorldHandler()
	{
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this::clientSetup);
		MinecraftForge.EVENT_BUS.addListener(this::serverStarting);
		DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
		{
			ModLoadingContext.get().registerConfig(Type.CLIENT, Config.CLIENT_SPEC, Main.MODID + ".toml");
			modEventBus.register(Config.class);
			modEventBus.addListener(Content::createRegistry);
			modEventBus.addListener(Category::createRegistry);
			modEventBus.addGenericListener(Content.class, Content::register);
			modEventBus.addGenericListener(Category.class, Category::register);
		});
	}
	
	@SubscribeEvent
	public void clientSetup(FMLClientSetupEvent event)
	{
		MinecraftForge.EVENT_BUS.addListener(KeyHandler::keyInputEvent);
		MinecraftForge.EVENT_BUS.addListener(ClientEventHandler::renderWorldLastEvent);
		MinecraftForge.EVENT_BUS.addListener(ClientEventHandler::clientChatEvent);
		
		USERNAME = Minecraft.getInstance().getSession().getUsername();
		ClientRegistry.registerKeyBinding(KeyHandler.KEY_WORLD_HANDLER);
		KeyHandler.updatePosKeys();
	}
	
	@SubscribeEvent
	public void serverStarting(FMLServerStartingEvent event)
	{
		CommandHelper.registerCommands(event.getCommandDispatcher());
	}
}