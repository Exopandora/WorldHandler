package exopandora.worldhandler;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import com.google.common.base.Predicates;

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
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
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
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this::commonSetup);
		modEventBus.addListener(this::clientSetup);
		modEventBus.addListener(this::loadComplete);
		MinecraftForge.EVENT_BUS.addListener(this::serverStarting);
		ModLoadingContext.get().registerConfig(Type.CLIENT, Config.CLIENT_SPEC, Main.MODID + ".toml");
		modEventBus.register(Config.class);
	}
	
	@SubscribeEvent
	public void commonSetup(FMLCommonSetupEvent event)
	{
		SIDEPROXY.setup();
	}
	
	@SubscribeEvent
	public void clientSetup(FMLClientSetupEvent event)
	{
		USERNAME = Minecraft.getInstance().getSession().getUsername();
		ClientRegistry.registerKeyBinding(KEY_WORLD_HANDLER);
		UtilKeyBinding.updatePosKeys();
	}
	
	@SubscribeEvent
	public void loadComplete(FMLLoadCompleteEvent event)
	{
		Content.registerContents();
		Category.register();
		InterModComms.getMessages(Main.MODID, Predicates.equalTo("register"))
			.map(imc -> (Runnable) imc.getMessageSupplier().get())
			.forEach(Runnable::run);
	}
	
	@SubscribeEvent
	public void serverStarting(FMLServerStartingEvent event)
	{
		CommandHelper.registerCommands(event.getCommandDispatcher());
	}
	
	public static void registerIMC(Runnable registrationEvent)
	{
		Objects.requireNonNull(registrationEvent);
		InterModComms.sendTo(Main.MODID, "register", () -> registrationEvent);
	}
}