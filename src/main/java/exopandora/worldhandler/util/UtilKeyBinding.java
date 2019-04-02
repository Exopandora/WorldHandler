package exopandora.worldhandler.util;

import org.apache.commons.lang3.ArrayUtils;

import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;

@OnlyIn(Dist.CLIENT)
public class UtilKeyBinding
{
	public static void updatePosKeys()
	{
		boolean isRegistered = arePosKeysRegistered();
		
		if(Config.getSettings().shortcutKeys() && !isRegistered)
		{
			ClientRegistry.registerKeyBinding(WorldHandler.KEY_WORLD_HANDLER_POS1);
			ClientRegistry.registerKeyBinding(WorldHandler.KEY_WORLD_HANDLER_POS2);
		}
		else if(!Config.getSettings().shortcutKeys() && isRegistered)
		{
			removePosKeys();
		}
	}
	
	public static boolean arePosKeysRegistered()
	{
		return ArrayUtils.contains(Minecraft.getInstance().gameSettings.keyBindings, WorldHandler.KEY_WORLD_HANDLER_POS1) || ArrayUtils.contains(Minecraft.getInstance().gameSettings.keyBindings, WorldHandler.KEY_WORLD_HANDLER_POS2);
	}
	
	public static void removePosKeys()
	{
		Minecraft.getInstance().gameSettings.keyBindings = ArrayUtils.removeElements(Minecraft.getInstance().gameSettings.keyBindings, WorldHandler.KEY_WORLD_HANDLER_POS1, WorldHandler.KEY_WORLD_HANDLER_POS2);
	}
}