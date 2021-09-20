package exopandora.worldhandler.util;

import com.mojang.brigadier.CommandDispatcher;

import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.ICommandBuilderSyntax;
import exopandora.worldhandler.command.CommandWH;
import exopandora.worldhandler.command.CommandWorldHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;

public class CommandHelper
{
	public static void sendFeedback(CommandSourceStack source, String message)
	{
		source.sendSuccess(new TextComponent(message), false);
	}
	
	public static boolean canPlayerIssueCommand()
	{
		if(Minecraft.getInstance().player != null)
		{
			return Minecraft.getInstance().player.hasPermissions(1);
		}
		
		return false;
	}
	
	public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher)
	{
		CommandWorldHandler.register(dispatcher);
		CommandWH.register(dispatcher);
	}
	
	public static void sendCommand(String player, ICommandBuilder builder)
	{
		CommandHelper.sendCommand(player, builder, false);
	}
	
	public static void sendCommand(String player, ICommandBuilder builder, boolean special)
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
			
			WorldHandler.LOGGER.info("Command: " + command);
			
			if(builder.needsCommandBlock() || special)
			{
				BlockHelper.setCommandBlockNearPlayer(player, command);
			}
			else if(Minecraft.getInstance().player != null)
			{
				Minecraft.getInstance().player.chat(command);
			}
		}
	}
}
