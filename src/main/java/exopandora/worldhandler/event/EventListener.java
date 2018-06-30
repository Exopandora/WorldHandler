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
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EventListener
{
	private final FakeCommandHandler commandHandler = new FakeCommandHandler();
	private final BiomeIndicator biomeIndicator = new BiomeIndicator();
	
	@SubscribeEvent
	public void renderWorldLastEvent(RenderWorldLastEvent event)
	{
		if(ConfigSettings.isHighlightBlocksEnabled() && Minecraft.getMinecraft().world != null)
		{
			GlStateManager.pushMatrix();
			GlStateManager.disableAlpha();
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.glLineWidth(2.0F);
			GlStateManager.disableTexture2D();
			GlStateManager.depthMask(false);
			
			final double constant = 0.0020000000949949026D;
			EntityPlayer player = Minecraft.getMinecraft().player;
			
			double playerX = player.lastTickPosX + (player.posX - player.lastTickPosX) * Minecraft.getMinecraft().getRenderPartialTicks();
			double playerY = player.lastTickPosY + (player.posY - player.lastTickPosY) * Minecraft.getMinecraft().getRenderPartialTicks();
			double playerZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * Minecraft.getMinecraft().getRenderPartialTicks();
			
			double minX = Math.min(BlockHelper.getPos1().getX(), BlockHelper.getPos2().getX()) - constant - playerX;
			double minY = Math.min(BlockHelper.getPos1().getY(), BlockHelper.getPos2().getY()) - constant - playerY;
			double minZ = Math.min(BlockHelper.getPos1().getZ(), BlockHelper.getPos2().getZ()) - constant - playerZ;
			
			double maxX = Math.max(BlockHelper.getPos1().getX(), BlockHelper.getPos2().getX()) + constant - playerX + 1;
			double maxY = Math.max(BlockHelper.getPos1().getY(), BlockHelper.getPos2().getY()) + constant - playerY + 1;
			double maxZ = Math.max(BlockHelper.getPos1().getZ(), BlockHelper.getPos2().getZ()) + constant - playerZ + 1;
			
			final float opacity = 0.2F;
			
			for(int x = 1; x < maxX - minX; x++)
			{
				RenderGlobal.drawBoundingBox(minX, minY, minZ, minX + x + 2 * constant, maxY, maxZ, 0.0F, 0.0F, 0.0F, opacity);
			}
			
			for(int y = 1; y < maxY - minY; y++)
			{
				RenderGlobal.drawBoundingBox(minX, minY, minZ, maxX, minY + y + 2 * constant, maxZ, 0.0F, 0.0F, 0.0F, opacity);
			}
			
			for(int z = 1; z < maxZ - minZ; z++)
			{
				RenderGlobal.drawBoundingBox(minX, minY, minZ, maxX, maxY, minZ + z + 2 * constant, 0.0F, 0.0F, 0.0F, opacity);
			}
			
			RenderGlobal.renderFilledBox(minX, minY, minZ, maxX, maxY, maxZ, 0.0F, 0.0F, 0.0F, opacity / 2);
			RenderGlobal.renderFilledBox(maxX, maxY, maxZ, minX, minY, minZ, 0.0F, 0.0F, 0.0F, opacity / 2);
			
			GlStateManager.depthMask(true);
			GlStateManager.enableTexture2D();
			GlStateManager.disableBlend();
			GlStateManager.enableAlpha();
			GlStateManager.popMatrix();
		}
	}
	
	@SubscribeEvent
	public void clientTickEvent(ClientTickEvent event)
	{
		if(Minecraft.getMinecraft().inGameHasFocus && event.phase.equals(Phase.START))
		{
			if(ConfigSettings.isBiomeIndicatorEnabled())
			{
				this.biomeIndicator.tick();
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
