package exopandora.worldhandler.command;

import org.apache.maven.artifact.versioning.ComparableVersion;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.helper.ActionHelper;
import exopandora.worldhandler.helper.CommandHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;

public class CommandWorldHandler
{
	public static void register(CommandDispatcher<CommandSource> dispatcher)
	{
		dispatcher.register(Commands.literal("worldhandler")
				.then(Commands.literal("help")
					.executes(context -> help(context.getSource())))
				.then(Commands.literal("display")
					.executes(context -> display()))
				.then(Commands.literal("version")
					.executes(context -> version(context.getSource()))));
	}
	
	private static int help(CommandSource source) throws CommandSyntaxException
	{
		CommandHelper.sendFeedback(source, "/worldhandler help");
		CommandHelper.sendFeedback(source, "/worldhandler display");
		CommandHelper.sendFeedback(source, "/worldhandler version");
		return 1;
	}
	
	private static int display() throws CommandSyntaxException
	{
		Minecraft.getInstance().addScheduledTask(ActionHelper::displayGui);
		return 1;
	}
	
	private static int version(CommandSource source) throws CommandSyntaxException
	{
		CommandHelper.sendFeedback(source, "Installed: " + Main.MC_VERSION + "-" + Main.MOD_VERSION);
		ComparableVersion target = VersionChecker.getResult(ModList.get().getModContainerById(Main.MODID).get().getModInfo()).target;
		CommandHelper.sendFeedback(source, "Latest: " + Main.MC_VERSION + "-" + (target != null ? target : Main.MOD_VERSION));
		return 1;
	}
}
