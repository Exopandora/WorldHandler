package exopandora.worldhandler.util;

import org.apache.commons.lang3.ArrayUtils;

import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.config.ConfigSettings;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UtilKeyBinding
{
	public static void updatePosKeys()
	{
		boolean isRegistered = arePosKeysRegistered();
		
		if(ConfigSettings.arePosShortcutsEnabled() && !isRegistered)
		{
			ClientRegistry.registerKeyBinding(WorldHandler.KEY_WORLD_HANDLER_POS1);
			ClientRegistry.registerKeyBinding(WorldHandler.KEY_WORLD_HANDLER_POS2);
		}
		else if(!ConfigSettings.arePosShortcutsEnabled() && isRegistered)
		{
			removePosKeys();
		}
	}
	
	public static boolean arePosKeysRegistered()
	{
		return ArrayUtils.contains(Minecraft.getMinecraft().gameSettings.keyBindings, WorldHandler.KEY_WORLD_HANDLER_POS1) || ArrayUtils.contains(Minecraft.getMinecraft().gameSettings.keyBindings, WorldHandler.KEY_WORLD_HANDLER_POS2);
	}
	
	public static void removePosKeys()
	{
		Minecraft.getMinecraft().gameSettings.keyBindings = ArrayUtils.removeElements(Minecraft.getMinecraft().gameSettings.keyBindings, WorldHandler.KEY_WORLD_HANDLER_POS1, WorldHandler.KEY_WORLD_HANDLER_POS2);
	}
}