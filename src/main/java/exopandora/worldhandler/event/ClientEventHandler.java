package exopandora.worldhandler.event;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
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
//		if(Config.getSettings().highlightBlocks() && Minecraft.getInstance().world != null && Minecraft.getInstance().getRenderManager().info != null)
//		{
//			final double constant = 0.0020000000949949026D;
//			Vec3d projected = Minecraft.getInstance().getRenderManager().info.getProjectedView();
//			
//			double minX = Math.min(BlockHelper.getPos1().getX(), BlockHelper.getPos2().getX()) - constant - projected.getX();
//			double minY = Math.min(BlockHelper.getPos1().getY(), BlockHelper.getPos2().getY()) - constant - projected.getY();
//			double minZ = Math.min(BlockHelper.getPos1().getZ(), BlockHelper.getPos2().getZ()) - constant - projected.getZ();
//			
//			double maxX = Math.max(BlockHelper.getPos1().getX(), BlockHelper.getPos2().getX()) + constant - projected.getX() + 1;
//			double maxY = Math.max(BlockHelper.getPos1().getY(), BlockHelper.getPos2().getY()) + constant - projected.getY() + 1;
//			double maxZ = Math.max(BlockHelper.getPos1().getZ(), BlockHelper.getPos2().getZ()) + constant - projected.getZ() + 1;
//			
//			IVertexBuilder builder = Minecraft.getInstance().func_228019_au_().func_228487_b_().getBuffer(RenderType.func_228659_m_());
//			WorldRenderer.func_228427_a_(event.getMatrixStack(), builder, minX, minY, minZ, maxX, maxY, maxZ, 1.0F, 0.5F, 0.5F, 1.0F);
//			
//			RenderSystem.pushMatrix();
//			RenderSystem.disableAlphaTest();
//			RenderSystem.enableBlend();
//			RenderSystem.defaultBlendFunc();
//			RenderSystem.lineWidth(2.0F);
//			RenderSystem.disableTexture();
//			RenderSystem.depthMask(false);
//			
//			Tessellator tesselator = Tessellator.getInstance();
//		    BufferBuilder buffer = tesselator.getBuffer();
//		    
//			final int alpha = 255;
//			final int color1 = 223;
//			final int color2 = 127;
//			
//			RenderSystem.lineWidth(2.0F);
//			buffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
//			buffer.func_225582_a_(minX, minY, minZ).func_225586_a_(color1, color1, color1, 0).endVertex();
//			buffer.func_225582_a_(minX, minY, minZ).func_225586_a_(color1, color1, color1, alpha).endVertex();
//			buffer.func_225582_a_(maxX, minY, minZ).func_225586_a_(color1, color2, color2, alpha).endVertex();
//			buffer.func_225582_a_(maxX, minY, maxZ).func_225586_a_(color1, color1, color1, alpha).endVertex();
//			buffer.func_225582_a_(minX, minY, maxZ).func_225586_a_(color1, color1, color1, alpha).endVertex();
//			buffer.func_225582_a_(minX, minY, minZ).func_225586_a_(color2, color2, color1, alpha).endVertex();
//			buffer.func_225582_a_(minX, maxY, minZ).func_225586_a_(color2, color1, color2, alpha).endVertex();
//			buffer.func_225582_a_(maxX, maxY, minZ).func_225586_a_(color1, color1, color1, alpha).endVertex();
//			buffer.func_225582_a_(maxX, maxY, maxZ).func_225586_a_(color1, color1, color1, alpha).endVertex();
//			buffer.func_225582_a_(minX, maxY, maxZ).func_225586_a_(color1, color1, color1, alpha).endVertex();
//			buffer.func_225582_a_(minX, maxY, minZ).func_225586_a_(color1, color1, color1, alpha).endVertex();
//			buffer.func_225582_a_(minX, maxY, maxZ).func_225586_a_(color1, color1, color1, alpha).endVertex();
//			buffer.func_225582_a_(minX, minY, maxZ).func_225586_a_(color1, color1, color1, alpha).endVertex();
//			buffer.func_225582_a_(maxX, minY, maxZ).func_225586_a_(color1, color1, color1, alpha).endVertex();
//			buffer.func_225582_a_(maxX, maxY, maxZ).func_225586_a_(color1, color1, color1, alpha).endVertex();
//			buffer.func_225582_a_(maxX, maxY, minZ).func_225586_a_(color1, color1, color1, alpha).endVertex();
//			buffer.func_225582_a_(maxX, minY, minZ).func_225586_a_(color1, color1, color1, alpha).endVertex();
//			buffer.func_225582_a_(maxX, minY, minZ).func_225586_a_(color1, color1, color1, 0).endVertex();
//			tesselator.draw();
//			
//			RenderSystem.lineWidth(1.0F);
//			RenderSystem.depthMask(true);
//			RenderSystem.enableTexture();
//			RenderSystem.disableBlend();
//			RenderSystem.enableAlphaTest();
//			RenderSystem.popMatrix();
//		}
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
					Minecraft.getInstance().ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
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
