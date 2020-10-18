package exopandora.worldhandler.util;

import com.mojang.brigadier.CommandDispatcher;

import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.ICommandBuilderSyntax;
import exopandora.worldhandler.command.CommandWH;
import exopandora.worldhandler.command.CommandWorldHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CommandHelper
{
	public static void sendFeedback(CommandSource source, String message)
	{
		source.sendFeedback(new StringTextComponent(message), false);
	}
	
	public static boolean canPlayerIssueCommand()
	{
		if(Minecraft.getInstance().player != null)
		{
			return Minecraft.getInstance().player.hasPermissionLevel(1);
		}
		
		return false;
	}
	
	public static void registerCommands(CommandDispatcher<CommandSource> dispatcher)
	{
		CommandWorldHandler.register(dispatcher);
		CommandWH.register(dispatcher);
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void sendCommand(String player, ICommandBuilder builder)
	{
		CommandHelper.sendCommand(player, builder, false);
	}
	
	@OnlyIn(Dist.CLIENT)
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
				Minecraft.getInstance().player.sendChatMessage(command);
			}
		}
	}
}
