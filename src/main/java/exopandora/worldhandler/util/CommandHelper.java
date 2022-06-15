package exopandora.worldhandler.util;

import com.mojang.brigadier.CommandDispatcher;

import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.command.CommandWH;
import exopandora.worldhandler.command.CommandWorldHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class CommandHelper
{
	public static final int MAX_COMMAND_LENGTH = 256;
	
	public static void sendFeedback(CommandSourceStack source, String message)
	{
		source.sendSuccess(Component.literal(message), false);
	}
	
	public static boolean canPlayerIssueCommand()
	{
		if(Minecraft.getInstance().player != null)
		{
			return Minecraft.getInstance().player.hasPermissions(1);
		}
		
		return false;
	}
	
	public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext)
	{
		CommandWorldHandler.register(dispatcher);
		CommandWH.register(dispatcher, buildContext);
	}
	
	public static void sendCommand(String player, ICommandBuilder builder, Object label)
	{
		CommandHelper.sendCommand(player, builder, label, false);
	}
	
	public static void sendCommand(String player, ICommandBuilder builder, Object label, boolean special)
	{
		String command = builder.toCommand(label, false);
		
		if(builder.needsCommandBlock(label, false) || special)
		{
			BlockHelper.setCommandBlockNearPlayer(player, builder, label);
		}
		else if(Minecraft.getInstance().player != null)
		{
			Minecraft.getInstance().player.command(command.substring(1));
		}
		
		WorldHandler.LOGGER.info("Command: " + command);
	}
}
