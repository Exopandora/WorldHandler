package exopandora.worldhandler.event;

import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.helper.ActionHelper;
import exopandora.worldhandler.helper.BlockHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

@OnlyIn(Dist.CLIENT)
public class KeyHandler
{
	//TODO FORGE switch to KeyInputEvent
	public static void keyInputEvent(ClientTickEvent event)
	{
		if(Minecraft.getInstance().isGameFocused())
		{
			if(WorldHandler.KEY_WORLD_HANDLER.isPressed())
			{
				ActionHelper.displayGui();
			}
			else if(WorldHandler.KEY_WORLD_HANDLER_POS1.isPressed() && Config.getSettings().shortcutKeys())
			{
				BlockHelper.setPos1(BlockHelper.getFocusedBlockPos());
			}
			else if(WorldHandler.KEY_WORLD_HANDLER_POS2.isPressed() && Config.getSettings().shortcutKeys())
			{
				BlockHelper.setPos2(BlockHelper.getFocusedBlockPos());
			}
		}
	}
}
