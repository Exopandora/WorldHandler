package exopandora.worldhandler.event;

import org.lwjgl.glfw.GLFW;

import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.BlockHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class KeyHandler
{
	public static final KeyMapping KEY_WORLD_HANDLER = new KeyMapping("key.worldhandler", GLFW.GLFW_KEY_V, "key.categories.worldhandler");
	public static final KeyMapping KEY_WORLD_HANDLER_POS1 = new KeyMapping("key.worldhandler.pos1", GLFW.GLFW_KEY_UNKNOWN, "key.categories.worldhandler");
	public static final KeyMapping KEY_WORLD_HANDLER_POS2 = new KeyMapping("key.worldhandler.pos2", GLFW.GLFW_KEY_UNKNOWN, "key.categories.worldhandler");
	
	@SubscribeEvent
	public static void keyInputEvent(InputEvent.Key event)
	{
		if(Minecraft.getInstance() != null && Minecraft.getInstance().screen == null)
		{
			if(KEY_WORLD_HANDLER.consumeClick())
			{
				ActionHelper.displayGui();
			}
			else if(KEY_WORLD_HANDLER_POS1.consumeClick())
			{
				BlockHelper.pos1().set(BlockHelper.getFocusedBlockPos());
			}
			else if(KEY_WORLD_HANDLER_POS2.consumeClick())
			{
				BlockHelper.pos2().set(BlockHelper.getFocusedBlockPos());
			}
		}
	}
}
