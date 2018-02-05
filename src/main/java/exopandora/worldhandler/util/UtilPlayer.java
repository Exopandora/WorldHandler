package exopandora.worldhandler.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;

public class UtilPlayer
{
	public static boolean canIssueCommand()
	{
		return Minecraft.getMinecraft().player.getPermissionLevel() > 0;
	}
	
	public static int getPlayerDirection()
	{
		return MathHelper.floor((double) (Minecraft.getMinecraft().player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
	}
}
