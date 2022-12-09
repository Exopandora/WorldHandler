package exopandora.worldhandler.command;

import org.apache.maven.artifact.versioning.ComparableVersion;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.event.ClientEventHandler;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;

public class CommandWorldHandler
{
	private static final SimpleCommandExceptionType NOT_IN_SINGLEPLAYER = new SimpleCommandExceptionType(Component.translatable("commands.worldhandler.allow_commands.not_in_singleplayer"));
	private static final SimpleCommandExceptionType COMMANDS_ALREADY_ALLOWED = new SimpleCommandExceptionType(Component.translatable("commands.worldhandler.allow_commands.commands_already_allowed"));
	
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
	{
		dispatcher.register(Commands.literal("worldhandler")
				.then(Commands.literal("help")
					.executes(context -> help(context.getSource())))
				.then(Commands.literal("display")
					.executes(context -> display(context.getSource())))
				.then(Commands.literal("version")
					.executes(context -> version(context.getSource())))
				.then(Commands.literal("allow_commands")
						.executes(context -> allowCommands(context.getSource()))));
	}
	
	private static int help(CommandSourceStack source) throws CommandSyntaxException
	{
		CommandHelper.sendFeedback(source, "/worldhandler help");
		CommandHelper.sendFeedback(source, "/worldhandler display");
		CommandHelper.sendFeedback(source, "/worldhandler version");
		CommandHelper.sendFeedback(source, "/worldhandler allow_commands");
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
	
	private static int allowCommands(CommandSourceStack source) throws CommandSyntaxException
	{
		if(!Minecraft.getInstance().hasSingleplayerServer())
		{
			throw NOT_IN_SINGLEPLAYER.create();
		}
		
		MinecraftServer server = Minecraft.getInstance().getSingleplayerServer();
		PrimaryLevelData worldData = (PrimaryLevelData) server.getWorldData();
		LevelSettings settings = worldData.settings;
		
		if(settings.allowCommands())
		{
			throw COMMANDS_ALREADY_ALLOWED.create();
		}
		
		worldData.settings = new LevelSettings(settings.levelName(), settings.gameType(), settings.hardcore(), settings.difficulty(), true, settings.gameRules(), settings.getDataConfiguration(), settings.getLifecycle());
		int operatorPermissionLevel = server.getOperatorUserPermissionLevel();
		Minecraft.getInstance().player.setPermissionLevel(operatorPermissionLevel);
		
		for(ServerPlayer player : server.getPlayerList().getPlayers())
		{
			server.getCommands().sendCommands(player);
		}
		
		source.sendSuccess(Component.translatable("commands.worldhandler.allow_commands.success"), false);
		return operatorPermissionLevel;
	}
}
