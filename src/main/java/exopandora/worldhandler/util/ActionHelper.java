package exopandora.worldhandler.util;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.builder.impl.BuilderDifficulty;
import exopandora.worldhandler.builder.impl.BuilderGamemode;
import exopandora.worldhandler.builder.impl.BuilderGamemode.EnumGamemode;
import exopandora.worldhandler.builder.impl.BuilderTime;
import exopandora.worldhandler.builder.impl.BuilderTime.EnumMode;
import exopandora.worldhandler.builder.impl.BuilderWeather;
import exopandora.worldhandler.builder.impl.BuilderWeather.EnumWeather;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.container.impl.GuiWorldHandler;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.content.impl.ContentChild;
import exopandora.worldhandler.gui.content.impl.ContentContinue;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.NoteBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Difficulty;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ActionHelper
{
	public static void backToGame()
	{
		Minecraft.getInstance().displayGuiScreen(null);
		Minecraft.getInstance().mouseHelper.grabMouse();
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
		if(content != null && !(content instanceof ContentContinue))
		{
			if(content instanceof ContentChild && Minecraft.getInstance().currentScreen != null && Minecraft.getInstance().currentScreen instanceof GuiWorldHandler)
			{
				Minecraft.getInstance().displayGuiScreen(new GuiWorldHandler(((ContentChild) content).withParent(((GuiWorldHandler) Minecraft.getInstance().currentScreen).getContent())));
			}
			else
			{
				Minecraft.getInstance().displayGuiScreen(new GuiWorldHandler(content));
			}
		}
	}
	
	public static void timeDawn()
	{
		CommandHelper.sendCommand(Minecraft.getInstance().getSession().getUsername(), new BuilderTime(EnumMode.SET, Config.getSettings().getDawn()));
	}
	
	public static void timeNoon()
	{
		CommandHelper.sendCommand(Minecraft.getInstance().getSession().getUsername(), new BuilderTime(EnumMode.SET, Config.getSettings().getNoon()));
	}
	
	public static void timeSunset()
	{
		CommandHelper.sendCommand(Minecraft.getInstance().getSession().getUsername(), new BuilderTime(EnumMode.SET, Config.getSettings().getSunset()));
	}
	
	public static void timeMidnight()
	{
		CommandHelper.sendCommand(Minecraft.getInstance().getSession().getUsername(), new BuilderTime(EnumMode.SET, Config.getSettings().getMidnight()));
	}
	
	public static void weatherClear()
	{
		CommandHelper.sendCommand(Minecraft.getInstance().getSession().getUsername(), new BuilderWeather(EnumWeather.CLEAR));
	}
	
	public static void weatherRain()
	{
		CommandHelper.sendCommand(Minecraft.getInstance().getSession().getUsername(), new BuilderWeather(EnumWeather.RAIN));
	}
	
	public static void weatherThunder()
	{
		CommandHelper.sendCommand(Minecraft.getInstance().getSession().getUsername(), new BuilderWeather(EnumWeather.THUNDER));
	}
	
	public static void difficultyPeaceful()
	{
		CommandHelper.sendCommand(Minecraft.getInstance().getSession().getUsername(), new BuilderDifficulty(Difficulty.PEACEFUL));
	}
	
	public static void difficultyEasy()
	{
		CommandHelper.sendCommand(Minecraft.getInstance().getSession().getUsername(), new BuilderDifficulty(Difficulty.EASY));
	}
	
	public static void difficultyNormal()
	{
		CommandHelper.sendCommand(Minecraft.getInstance().getSession().getUsername(), new BuilderDifficulty(Difficulty.NORMAL));
	}
	
	public static void difficultyHard()
	{
		CommandHelper.sendCommand(Minecraft.getInstance().getSession().getUsername(), new BuilderDifficulty(Difficulty.HARD));
	}
	
	public static void gamemodeSurvival()
	{
		CommandHelper.sendCommand(Minecraft.getInstance().getSession().getUsername(), new BuilderGamemode(EnumGamemode.SURVIVAL));
	}
	
	public static void gamemodeCreative()
	{
		CommandHelper.sendCommand(Minecraft.getInstance().getSession().getUsername(), new BuilderGamemode(EnumGamemode.CREATIVE));
	}
	
	public static void gamemodeAdventure()
	{
		CommandHelper.sendCommand(Minecraft.getInstance().getSession().getUsername(), new BuilderGamemode(EnumGamemode.ADVENTURE));
	}
	
	public static void gamemodeSpectator()
	{
		CommandHelper.sendCommand(Minecraft.getInstance().getSession().getUsername(), new BuilderGamemode(EnumGamemode.SPECTATOR));
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
			Minecraft.getInstance().displayGuiScreen(null);
			Minecraft.getInstance().mouseHelper.grabMouse();
			
			ITextComponent message = new TranslationTextComponent("<" + Main.NAME + "> %s", new TranslationTextComponent("worldhandler.error.gui")).mergeStyle(TextFormatting.RED);
			ITextComponent cause = new StringTextComponent(" " + e.getClass().getCanonicalName() + ": " + e.getMessage()).mergeStyle(TextFormatting.RED);
			
			Minecraft.getInstance().ingameGUI.func_238450_a_(ChatType.SYSTEM, message, Util.DUMMY_UUID);
			Minecraft.getInstance().ingameGUI.func_238450_a_(ChatType.SYSTEM, cause, Util.DUMMY_UUID);
			
			WorldHandler.LOGGER.throwing(e);
		}
	}
	
	public static void displayGui()
	{
		if(!CommandHelper.canPlayerIssueCommand() && Config.getSettings().permissionQuery())
		{
			Minecraft.getInstance().ingameGUI.func_238450_a_(ChatType.SYSTEM, new StringTextComponent(TextFormatting.RED + I18n.format("worldhandler.permission.refused")), Util.DUMMY_UUID);
			Minecraft.getInstance().ingameGUI.func_238450_a_(ChatType.SYSTEM, new StringTextComponent(TextFormatting.RED + I18n.format("worldhandler.permission.refused.change", I18n.format("gui.worldhandler.config.settings.permission_query"))), Util.DUMMY_UUID);
		}
		else
		{
			ActionHelper.tryRun(() ->
			{
				if(BlockHelper.getFocusedBlock() instanceof AbstractSignBlock)
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
