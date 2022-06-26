package exopandora.worldhandler.command;

import org.apache.maven.artifact.versioning.ComparableVersion;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.event.ClientEventHandler;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;

public class CommandWorldHandler
{
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
	{
		dispatcher.register(Commands.literal("worldhandler")
				.then(Commands.literal("help")
					.executes(context -> help(context.getSource())))
				.then(Commands.literal("display")
					.executes(context -> display(context.getSource())))
				.then(Commands.literal("version")
					.executes(context -> version(context.getSource()))));
	}
	
	private static int help(CommandSourceStack source) throws CommandSyntaxException
	{
		CommandHelper.sendFeedback(source, "/worldhandler help");
		CommandHelper.sendFeedback(source, "/worldhandler display");
		CommandHelper.sendFeedback(source, "/worldhandler version");
		return 1;
	}
	
	private static int display(CommandSourceStack source) throws CommandSyntaxException
	{
		ClientEventHandler.openGui = true;
		return 1;
	}
	
	private static int version(CommandSourceStack source) throws CommandSyntaxException
	{
		CommandHelper.sendFeedback(source, "Installed: " + Main.MC_VERSION + "-" + Main.MOD_VERSION);
		ComparableVersion target = VersionChecker.getResult(ModList.get().getModContainerById(Main.MODID).get().getModInfo()).target();
		CommandHelper.sendFeedback(source, "Latest: " + Main.MC_VERSION + "-" + (target != null ? target : Main.MOD_VERSION));
		return 1;
	}
}
