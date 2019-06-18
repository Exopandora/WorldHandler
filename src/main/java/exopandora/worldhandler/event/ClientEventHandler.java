package exopandora.worldhandler.event;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.helper.BlockHelper;
import exopandora.worldhandler.helper.CommandHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.command.CommandSource;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class ClientEventHandler
{
	@SubscribeEvent
	public static void renderWorldLastEvent(RenderWorldLastEvent event)
	{
		if(Config.getSettings().highlightBlocks() && Minecraft.getInstance().world != null && Minecraft.getInstance().getRenderManager().field_217783_c != null)
		{
			GlStateManager.pushMatrix();
			GlStateManager.disableAlphaTest();
			GlStateManager.enableBlend();
			GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.lineWidth(2.0F);
			GlStateManager.disableTexture();
			GlStateManager.depthMask(false);
			
			final double constant = 0.0020000000949949026D;
			Vec3d projected = Minecraft.getInstance().getRenderManager().field_217783_c.func_216785_c();
			
			double minX = Math.min(BlockHelper.getPos1().getX(), BlockHelper.getPos2().getX()) - constant - projected.x;
			double minY = Math.min(BlockHelper.getPos1().getY(), BlockHelper.getPos2().getY()) - constant - projected.y;
			double minZ = Math.min(BlockHelper.getPos1().getZ(), BlockHelper.getPos2().getZ()) - constant - projected.z;
			
			double maxX = Math.max(BlockHelper.getPos1().getX(), BlockHelper.getPos2().getX()) + constant - projected.x + 1;
			double maxY = Math.max(BlockHelper.getPos1().getY(), BlockHelper.getPos2().getY()) + constant - projected.y + 1;
			double maxZ = Math.max(BlockHelper.getPos1().getZ(), BlockHelper.getPos2().getZ()) + constant - projected.z + 1;
			
			Tessellator tesselator = Tessellator.getInstance();
		    BufferBuilder buffer = tesselator.getBuffer();
		    
			final int alpha = 255;
			final int color1 = 223;
			final int color2 = 127;
			
			GlStateManager.lineWidth(2.0F);
			buffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
			buffer.pos(minX, minY, minZ).color((float) color1, (float) color1, (float) color1, 0.0F).endVertex();
			buffer.pos(minX, minY, minZ).color(color1, color1, color1, alpha).endVertex();
			buffer.pos(maxX, minY, minZ).color(color1, color2, color2, alpha).endVertex();
			buffer.pos(maxX, minY, maxZ).color(color1, color1, color1, alpha).endVertex();
			buffer.pos(minX, minY, maxZ).color(color1, color1, color1, alpha).endVertex();
			buffer.pos(minX, minY, minZ).color(color2, color2, color1, alpha).endVertex();
			buffer.pos(minX, maxY, minZ).color(color2, color1, color2, alpha).endVertex();
			buffer.pos(maxX, maxY, minZ).color(color1, color1, color1, alpha).endVertex();
			buffer.pos(maxX, maxY, maxZ).color(color1, color1, color1, alpha).endVertex();
			buffer.pos(minX, maxY, maxZ).color(color1, color1, color1, alpha).endVertex();
			buffer.pos(minX, maxY, minZ).color(color1, color1, color1, alpha).endVertex();
			buffer.pos(minX, maxY, maxZ).color(color1, color1, color1, alpha).endVertex();
			buffer.pos(minX, minY, maxZ).color(color1, color1, color1, alpha).endVertex();
			buffer.pos(maxX, minY, maxZ).color(color1, color1, color1, alpha).endVertex();
			buffer.pos(maxX, maxY, maxZ).color(color1, color1, color1, alpha).endVertex();
			buffer.pos(maxX, maxY, minZ).color(color1, color1, color1, alpha).endVertex();
			buffer.pos(maxX, minY, minZ).color(color1, color1, color1, alpha).endVertex();
			buffer.pos(maxX, minY, minZ).color((float) color1, (float) color1, (float) color1, 0.0F).endVertex();
			tesselator.draw();
			GlStateManager.lineWidth(1.0F);
			
			GlStateManager.depthMask(true);
			GlStateManager.enableTexture();
			GlStateManager.disableBlend();
			GlStateManager.enableAlphaTest();
			GlStateManager.popMatrix();
		}
	}
	
	@SubscribeEvent
	public static void clientChatEvent(ClientChatEvent event)
	{
		if(!Minecraft.getInstance().isSingleplayer())
		{
			CommandDispatcher<CommandSource> dispatcher = new CommandDispatcher<CommandSource>();
			CommandHelper.registerCommands(dispatcher);
			
			StringReader command = new StringReader(event.getMessage());
			command.skip();
			
			ParseResults<CommandSource> result = dispatcher.parse(command, Minecraft.getInstance().player.getCommandSource());
			
			if(result.getContext().getCommand() != null)
			{
				try
				{
					dispatcher.execute(result);
				}
				catch(CommandSyntaxException e)
				{
					e.printStackTrace();
				}
				
				if(event.isCancelable())
				{
					event.setCanceled(true);
				}
			}
		}
	}
}
