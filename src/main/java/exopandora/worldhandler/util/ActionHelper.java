package exopandora.worldhandler.util;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.builder.argument.Gamemode;
import exopandora.worldhandler.builder.impl.DifficultyCommandBuilder;
import exopandora.worldhandler.builder.impl.GamemodeCommandBuilder;
import exopandora.worldhandler.builder.impl.TimeCommandBuilder;
import exopandora.worldhandler.builder.impl.WeatherCommandBuilder;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.container.impl.GuiWorldHandler;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.content.impl.ContentChild;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.SignBlock;

public class ActionHelper
{
	public static void backToGame()
	{
		Minecraft.getInstance().setScreen(null);
		Minecraft.getInstance().mouseHandler.grabMouse();
	}
	
	public static void back(Content content) throws Exception
	{
		if(content.getBackContent() != null)
		{
			ActionHelper.open(content.getBackContent());
		}
	}
	
	public static void open(String id) throws Exception
	{
		if(id != null)
		{
			ActionHelper.open(Contents.getRegisteredContent(id));
		}
	}
	
	public static void open(Content content) throws Exception
	{
		if(content != null)
		{
			if(content instanceof ContentChild && Minecraft.getInstance().screen != null && Minecraft.getInstance().screen instanceof GuiWorldHandler)
			{
				Minecraft.getInstance().setScreen(new GuiWorldHandler(((ContentChild) content).withParent(((GuiWorldHandler) Minecraft.getInstance().screen).getContent())));
			}
			else
			{
				Minecraft.getInstance().setScreen(new GuiWorldHandler(content));
			}
		}
	}
	
	public static void timeDawn()
	{
		ActionHelper.setDayTime(Config.getSettings().getDawn());
	}
	
	public static void timeNoon()
	{
		ActionHelper.setDayTime(Config.getSettings().getNoon());
	}
	
	public static void timeSunset()
	{
		ActionHelper.setDayTime(Config.getSettings().getSunset());
	}
	
	public static void timeMidnight()
	{
		ActionHelper.setDayTime(Config.getSettings().getMidnight());
	}
	
	public static void setDayTime(int time)
	{
		TimeCommandBuilder builder = new TimeCommandBuilder();
		builder.time().set((float) time);
		CommandHelper.sendCommand(Minecraft.getInstance().getUser().getName(), builder, TimeCommandBuilder.Label.SET);
	}
	
	public static void weatherClear()
	{
		CommandHelper.sendCommand(Minecraft.getInstance().getUser().getName(), new WeatherCommandBuilder(), WeatherCommandBuilder.Label.CLEAR);
	}
	
	public static void weatherRain()
	{
		CommandHelper.sendCommand(Minecraft.getInstance().getUser().getName(), new WeatherCommandBuilder(), WeatherCommandBuilder.Label.RAIN);
	}
	
	public static void weatherThunder()
	{
		CommandHelper.sendCommand(Minecraft.getInstance().getUser().getName(), new WeatherCommandBuilder(), WeatherCommandBuilder.Label.THUNDER);
	}
	
	public static void difficultyPeaceful()
	{
		ActionHelper.setDifficulty(Difficulty.PEACEFUL);
	}
	
	public static void difficultyEasy()
	{
		ActionHelper.setDifficulty(Difficulty.EASY);
	}
	
	public static void difficultyNormal()
	{
		ActionHelper.setDifficulty(Difficulty.NORMAL);
	}
	
	public static void difficultyHard()
	{
		ActionHelper.setDifficulty(Difficulty.HARD);
	}
	
	private static void setDifficulty(Difficulty difficulty)
	{
		DifficultyCommandBuilder builder = new DifficultyCommandBuilder();
		builder.difficulty().set(difficulty);
		CommandHelper.sendCommand(Minecraft.getInstance().getUser().getName(), builder, DifficultyCommandBuilder.Label.DIFFICULTY);
	}
	
	public static void gamemodeSurvival()
	{
		ActionHelper.setGamemode(Gamemode.SURVIVAL);
	}
	
	public static void gamemodeCreative()
	{
		ActionHelper.setGamemode(Gamemode.CREATIVE);
	}
	
	public static void gamemodeAdventure()
	{
		ActionHelper.setGamemode(Gamemode.ADVENTURE);
	}
	
	public static void gamemodeSpectator()
	{
		ActionHelper.setGamemode(Gamemode.SPECTATOR);
	}
	
	private static void setGamemode(Gamemode gamemode)
	{
		GamemodeCommandBuilder builder = new GamemodeCommandBuilder();
		builder.gamemode().set(gamemode);
		CommandHelper.sendCommand(Minecraft.getInstance().getUser().getName(), builder, GamemodeCommandBuilder.Label.GAMEMODE);
	}
	
	public static void tryRun(ActionHandler action)
	{
		try
		{
			if(action != null)
			{
				action.run();
			}
		}
		catch(Exception e)
		{
			Minecraft.getInstance().setScreen(null);
			Minecraft.getInstance().mouseHandler.grabMouse();
			
			Component message = new TranslatableComponent("<" + Main.NAME + "> %s", new TranslatableComponent("worldhandler.error.gui")).withStyle(ChatFormatting.RED);
			Component cause = new TextComponent(" " + e.getClass().getCanonicalName() + ": " + e.getMessage()).withStyle(ChatFormatting.RED);
			
			Minecraft.getInstance().gui.handleChat(ChatType.SYSTEM, message, Util.NIL_UUID);
			Minecraft.getInstance().gui.handleChat(ChatType.SYSTEM, cause, Util.NIL_UUID);
			
			WorldHandler.LOGGER.throwing(e);
		}
	}
	
	public static void displayGui()
	{
		if(!CommandHelper.canPlayerIssueCommand() && Config.getSettings().permissionQuery())
		{
			Minecraft.getInstance().gui.handleChat(ChatType.SYSTEM, new TextComponent(ChatFormatting.RED + I18n.get("worldhandler.permission.refused")), Util.NIL_UUID);
			Minecraft.getInstance().gui.handleChat(ChatType.SYSTEM, new TextComponent(ChatFormatting.RED + I18n.get("worldhandler.permission.refused.change", I18n.get("gui.worldhandler.config.settings.permission_query"))), Util.NIL_UUID);
		}
		else
		{
			ActionHelper.tryRun(() ->
			{
				if(BlockHelper.getFocusedBlock() instanceof SignBlock)
				{
					ActionHelper.open(Contents.SIGN_EDITOR);
				}
				else if(BlockHelper.getFocusedBlock() instanceof NoteBlock)
				{
					ActionHelper.open(Contents.NOTE_EDITOR);
				}
				else
				{
					ActionHelper.open(Contents.MAIN);
				}
			});
		}
	}
}
