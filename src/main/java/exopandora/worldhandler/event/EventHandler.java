package exopandora.worldhandler.event;

import com.mojang.realmsclient.gui.ChatFormatting;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.command.FakeCommandHandler;
import exopandora.worldhandler.config.ConfigSettings;
import exopandora.worldhandler.gui.container.impl.GuiWorldHandlerContainer;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.helper.BlockHelper;
import exopandora.worldhandler.hud.BiomeIndicator;
import exopandora.worldhandler.util.UtilPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EventHandler
{
	private final FakeCommandHandler commandHandler = new FakeCommandHandler();
	
	@SubscribeEvent
	public void clientTickEvent(TickEvent.ClientTickEvent event)
	{
		if(Minecraft.getMinecraft().inGameHasFocus && event.phase.equals(Phase.START))
		{
			if(ConfigSettings.isBiomeIndicatorEnabled())
			{
				BiomeIndicator.tick();
			}
		}
	}
	
	@SubscribeEvent
	public void keyInputEvent(KeyInputEvent event)
	{
		if(WorldHandler.KEY_WORLD_HANDLER.isPressed())
		{
			displayGui();
		}
		else if(WorldHandler.KEY_WORLD_HANDLER_POS1.isPressed() && ConfigSettings.arePosShortcutsEnabled())
		{
			BlockHelper.setPos1(BlockHelper.getFocusedBlockPos());
		}
		else if(WorldHandler.KEY_WORLD_HANDLER_POS2.isPressed() && ConfigSettings.arePosShortcutsEnabled())
		{
			BlockHelper.setPos2(BlockHelper.getFocusedBlockPos());
		}
	}
	
	@SubscribeEvent
	public void onConfigChanged(OnConfigChangedEvent event)
	{
		if(event.getModID().equals(Main.MODID))
		{
			WorldHandler.updateConfig();
		}
	}
	
	@SubscribeEvent
	public void clientChatEvent(ClientChatEvent event)
	{
		if(!Minecraft.getMinecraft().isSingleplayer())
		{
			this.commandHandler.tryCommand(WorldHandler.COMMAND_WORLD_HANDLER, event);
			this.commandHandler.tryCommand(WorldHandler.COMMAND_WH, event);
		}
	}
	
	public static void displayGui()
	{
		if(!UtilPlayer.canIssueCommand() && ConfigSettings.isPermissionQueryEnabled())
		{
			Minecraft.getMinecraft().player.sendMessage(new TextComponentString(ChatFormatting.RED + I18n.format("worldhandler.permission.refused")));
			Minecraft.getMinecraft().player.sendMessage(new TextComponentString(ChatFormatting.RED + I18n.format("worldhandler.permission.refused.change", I18n.format("gui.worldhandler.config.key.settings.permission_query"))));
		}
		else
		{
			if(BlockHelper.isFocusedBlockEqualTo(Blocks.STANDING_SIGN) || BlockHelper.isFocusedBlockEqualTo(Blocks.WALL_SIGN))
			{
				Minecraft.getMinecraft().displayGuiScreen(new GuiWorldHandlerContainer(Contents.SIGN_EDITOR));
			}
			else if(BlockHelper.isFocusedBlockEqualTo(Blocks.NOTEBLOCK))
			{
				Minecraft.getMinecraft().displayGuiScreen(new GuiWorldHandlerContainer(Contents.NOTE_EDITOR));
			}
			else
			{
				Minecraft.getMinecraft().displayGuiScreen(new GuiWorldHandlerContainer(Contents.MAIN));
			}
		}
	}
}
