package exopandora.worldhandler.event;

import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.helper.ActionHelper;
import exopandora.worldhandler.helper.BlockHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class KeyHandler
{
	@SubscribeEvent
	public static void keyInputEvent(KeyInputEvent event)
	{
		if(Minecraft.getInstance() != null && Minecraft.getInstance().field_71462_r == null)
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
