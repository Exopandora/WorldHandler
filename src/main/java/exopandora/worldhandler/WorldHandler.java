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
import net.minecraft.client.Minecraft;
import net.minecraft.resources.SimpleReloadableResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.DistExecutor.SafeRunnable;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(Main.MODID)
public class WorldHandler
{
	public static final Logger LOGGER = LogManager.getLogger();
	public static final Path USERCONTENT_PATH = FMLPaths.CONFIGDIR.get().resolve(Main.MODID).resolve("usercontent");
	
	public WorldHandler()
	{
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this::clientSetup);
		MinecraftForge.EVENT_BUS.addListener(this::serverStarting);
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> new SafeRunnable()
		{
			private static final long serialVersionUID = 1457410143759855413L;
			
			@Override
			public void run()
			{
				SimpleReloadableResourceManager manager = (SimpleReloadableResourceManager) Minecraft.getInstance().getResourceManager();
				manager.addReloadListener(AdvancementHelper.getInstance());
				Config.setupDirectories(WorldHandler.USERCONTENT_PATH);
				ModLoadingContext.get().registerConfig(Type.CLIENT, Config.CLIENT_SPEC, Main.MODID + "/" + Main.MODID + ".toml");
				UsercontentLoader.load(WorldHandler.USERCONTENT_PATH);
				modEventBus.register(Config.class);
				modEventBus.addListener(Content::createRegistry);
				modEventBus.addListener(Category::createRegistry);
				modEventBus.addGenericListener(Content.class, Content::register);
				modEventBus.addGenericListener(Category.class, Category::register);
			}
		});
//		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () ->
//		{
//			GuiFactoryWorldHandler factory = new GuiFactoryWorldHandler();
//			return (minecraft, parentScreen) -> factory.createConfigGui(parentScreen);
//		});
	}
	
	@SubscribeEvent
	public void clientSetup(FMLClientSetupEvent event)
	{
		MinecraftForge.EVENT_BUS.addListener(KeyHandler::keyInputEvent);
		MinecraftForge.EVENT_BUS.addListener(ClientEventHandler::renderWorldLastEvent);
		MinecraftForge.EVENT_BUS.addListener(ClientEventHandler::clientChatEvent);
		
		ClientRegistry.registerKeyBinding(KeyHandler.KEY_WORLD_HANDLER);
		KeyHandler.updatePosKeys();
	}
	
	@SubscribeEvent
	public void serverStarting(FMLServerStartingEvent event)
	{
		CommandHelper.registerCommands(event.getCommandDispatcher());
	}
}