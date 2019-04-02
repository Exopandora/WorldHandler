package exopandora.worldhandler.helper;

import com.mojang.brigadier.CommandDispatcher;

import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.ICommandBuilderSyntax;
import exopandora.worldhandler.command.CommandWH;
import exopandora.worldhandler.command.CommandWorldHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CommandHelper
{
	public static void sendFeedback(CommandSource source, String message)
	{
		source.sendFeedback(new TextComponentString(message), false);
	}
	
	public static boolean canPlayerIssueCommand()
	{
		return Minecraft.getInstance().player.hasPermissionLevel(1);
	}
	
	public static void registerCommands(CommandDispatcher<CommandSource> dispatcher)
	{
		CommandWorldHandler.register(dispatcher);
		CommandWH.register(dispatcher);
	}
	
	public static void sendCommand(ICommandBuilder builder)
	{
		CommandHelper.sendCommand(builder, false);
	}
	
	public static void sendCommand(ICommandBuilder builder, boolean special)
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
				BlockHelper.setCommandBlockNearPlayer(command);
			}
			else
			{
				Minecraft.getInstance().player.sendChatMessage(command);
			}
		}
	}
}
