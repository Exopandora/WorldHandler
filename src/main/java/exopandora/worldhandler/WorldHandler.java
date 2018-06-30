package exopandora.worldhandler;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.ICommandBuilderSyntax;
import exopandora.worldhandler.command.CommandWH;
import exopandora.worldhandler.command.CommandWorldHandler;
import exopandora.worldhandler.config.ConfigButcher;
import exopandora.worldhandler.config.ConfigSettings;
import exopandora.worldhandler.config.ConfigSkin;
import exopandora.worldhandler.config.ConfigSliders;
import exopandora.worldhandler.event.EventListener;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.helper.BlockHelper;
import exopandora.worldhandler.proxy.CommonProxy;
import exopandora.worldhandler.util.UtilKeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.ICommand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.ProgressManager.ProgressBar;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLModDisabledEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod(modid = Main.MODID, name = Main.NAME, acceptedMinecraftVersions = "[$compatible,)", version = "$version", canBeDeactivated = true, guiFactory = "exopandora.worldhandler.gui.config.GuiFactoryWorldHandler", updateJSON = "$update_url", clientSideOnly = true, certificateFingerprint = "$certificate")
public class WorldHandler
{
	@Instance(Main.MODID)
	private static WorldHandler INSTANCE;
	
	public static KeyBinding KEY_WORLD_HANDLER = new KeyBinding(Main.NAME, Keyboard.KEY_V, "key.categories.misc");
	public static KeyBinding KEY_WORLD_HANDLER_POS1 = new KeyBinding(Main.NAME + " Pos1", Keyboard.KEY_O, "key.categories.misc");
	public static KeyBinding KEY_WORLD_HANDLER_POS2 = new KeyBinding(Main.NAME + " Pos2", Keyboard.KEY_P, "key.categories.misc");
	
	public static final ICommand COMMAND_WORLD_HANDLER = new CommandWorldHandler();
	public static final ICommand COMMAND_WH = new CommandWH();
	
	public static Configuration CONFIG;
	public static Logger LOGGER;
	public static String USERNAME = null;
	
	@SidedProxy(clientSide = "exopandora.worldhandler.proxy.ClientProxy", serverSide = "exopandora.worldhandler.proxy.CommonProxy")
	private static CommonProxy PROXY;
	private EventListener eventListener = new EventListener();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		LOGGER = event.getModLog();
		LOGGER.info("Pre-Initialization");
		LOGGER.info("First Release on March 28 2013 - 02:29 PM CET by Exopandora");
		LOGGER.info("Latest Version: $url");
		CONFIG = new Configuration(event.getSuggestedConfigurationFile());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		LOGGER.info("Initialization");
		USERNAME = Minecraft.getMinecraft().getSession().getUsername();
		
		MinecraftForge.EVENT_BUS.register(this.eventListener);
		ClientRegistry.registerKeyBinding(KEY_WORLD_HANDLER);
		UtilKeyBinding.updatePosKeys();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		LOGGER.info("Post-Initialization");
	}
	
	@EventHandler
	public void loadComplete(FMLLoadCompleteEvent event)
	{
		LOGGER.info("Load-Complete");
		ProgressBar bar = ProgressManager.push(Main.NAME, 2);
		bar.step("Loading Configuration Files");
		
		ConfigSettings.load(CONFIG);
		ConfigSkin.load(CONFIG);
		ConfigSliders.load(CONFIG);
		ConfigButcher.load(CONFIG);
		
		bar.step("Initializing User Interface");
		
		Content.registerContents();
		Category.registerCategories();
		
		ProgressManager.pop(bar);
	}
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
		event.registerServerCommand(COMMAND_WORLD_HANDLER);
		event.registerServerCommand(COMMAND_WH);
	}
	
	@EventHandler
	public void disable(FMLModDisabledEvent event)
	{
		MinecraftForge.EVENT_BUS.register(this.eventListener);
		Minecraft.getMinecraft().gameSettings.keyBindings = ArrayUtils.removeElement(Minecraft.getMinecraft().gameSettings.keyBindings, KEY_WORLD_HANDLER);
		
		if(UtilKeyBinding.arePosKeysRegistered() && ConfigSettings.arePosShortcutsEnabled())
		{
			UtilKeyBinding.removePosKeys();
		}
	}
	
	public static void updateConfig()
	{
		ConfigSettings.load(CONFIG);
		ConfigSkin.load(CONFIG);
		ConfigButcher.load(CONFIG);
		ConfigSliders.load(CONFIG);
		UtilKeyBinding.updatePosKeys();
	}
	
	public static void sendCommand(ICommandBuilder builder)
	{
		sendCommand(builder, false);
	}
	
	public static void sendCommand(ICommandBuilder builder, boolean special)
	{
		if(builder != null)
		{
			String command;
			
			if(builder instanceof ICommandBuilderSyntax)
			{
				command = ((ICommandBuilderSyntax) builder).toActualCommand();
			}
			else
			{
				command = builder.toCommand();
			}
			
			LOGGER.info("Command: " + command);
			
			if(builder.needsCommandBlock() || special)
			{
				BlockHelper.setCommandBlockNearPlayer(command);
			}
			else
			{
				Minecraft.getMinecraft().player.sendChatMessage(command);
			}
		}
	}
}