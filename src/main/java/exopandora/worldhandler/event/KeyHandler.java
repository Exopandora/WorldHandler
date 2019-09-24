package exopandora.worldhandler.event;

import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.glfw.GLFW;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.helper.ActionHelper;
import exopandora.worldhandler.helper.BlockHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

@OnlyIn(Dist.CLIENT)
public class KeyHandler
{
	public static final KeyBinding KEY_WORLD_HANDLER = new KeyBinding(Main.NAME, GLFW.GLFW_KEY_V, Main.NAME);
	public static final KeyBinding KEY_WORLD_HANDLER_POS1 = new KeyBinding(Main.NAME + " Pos1", GLFW.GLFW_KEY_O, Main.NAME);
	public static final KeyBinding KEY_WORLD_HANDLER_POS2 = new KeyBinding(Main.NAME + " Pos2", GLFW.GLFW_KEY_P, Main.NAME);
	
	@SubscribeEvent
	public static void keyInputEvent(KeyInputEvent event)
	{
		if(Minecraft.getInstance() != null && Minecraft.getInstance().currentScreen == null)
		{
			if(KeyHandler.KEY_WORLD_HANDLER.isPressed())
			{
				ActionHelper.displayGui();
			}
			else if(KeyHandler.KEY_WORLD_HANDLER_POS1.isPressed() && Config.getSettings().shortcutKeys())
			{
				BlockHelper.setPos1(BlockHelper.getFocusedBlockPos());
			}
			else if(KeyHandler.KEY_WORLD_HANDLER_POS2.isPressed() && Config.getSettings().shortcutKeys())
			{
				BlockHelper.setPos2(BlockHelper.getFocusedBlockPos());
			}
		}
	}
	
	public static void updatePosKeys()
	{
		boolean isRegistered = KeyHandler.arePosKeysRegistered();
		
		if(Config.getSettings().shortcutKeys() && !isRegistered)
		{
			ClientRegistry.registerKeyBinding(KEY_WORLD_HANDLER_POS1);
			ClientRegistry.registerKeyBinding(KEY_WORLD_HANDLER_POS2);
		}
		else if(!Config.getSettings().shortcutKeys() && isRegistered)
		{
			KeyHandler.removePosKeys();
		}
	}
	
	public static boolean arePosKeysRegistered()
	{
		return ArrayUtils.contains(Minecraft.getInstance().gameSettings.keyBindings, KEY_WORLD_HANDLER_POS1) || ArrayUtils.contains(Minecraft.getInstance().gameSettings.keyBindings, KEY_WORLD_HANDLER_POS2);
	}
	
	public static void removePosKeys()
	{
		Minecraft.getInstance().gameSettings.keyBindings = ArrayUtils.removeElements(Minecraft.getInstance().gameSettings.keyBindings, KEY_WORLD_HANDLER_POS1, KEY_WORLD_HANDLER_POS2);
	}
}
