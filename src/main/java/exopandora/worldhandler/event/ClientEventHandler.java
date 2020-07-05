package exopandora.worldhandler.event;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.render.CustomRenderType;
import exopandora.worldhandler.util.BlockHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.command.CommandSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
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
		if(Config.getSettings().highlightBlocks() && Minecraft.getInstance().world != null && Minecraft.getInstance().getRenderManager().info != null)
		{
			Vector3d projected = Minecraft.getInstance().getRenderManager().info.getProjectedView();
			
			double minX = Math.min(BlockHelper.getPos1().getX(), BlockHelper.getPos2().getX());
			double minY = Math.min(BlockHelper.getPos1().getY(), BlockHelper.getPos2().getY());
			double minZ = Math.min(BlockHelper.getPos1().getZ(), BlockHelper.getPos2().getZ());
			
			double maxX = Math.max(BlockHelper.getPos1().getX(), BlockHelper.getPos2().getX()) + 1;
			double maxY = Math.max(BlockHelper.getPos1().getY(), BlockHelper.getPos2().getY()) + 1;
			double maxZ = Math.max(BlockHelper.getPos1().getZ(), BlockHelper.getPos2().getZ()) + 1;
			
			AxisAlignedBB aabb = new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
			
			if(aabb.getCenter().distanceTo(projected) < 96)
			{
				MatrixStack matrix = event.getMatrixStack();
				matrix.push();
				matrix.translate(-projected.getX(), -projected.getY(), -projected.getZ());
				
				IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
				IVertexBuilder builder = buffer.getBuffer(CustomRenderType.LINES2);
				
				WorldRenderer.drawBoundingBox(matrix, builder, minX, minY, minZ, maxX, maxY, maxZ, 0.9F, 0.9F, 0.9F, 1.0F, 0.5F, 0.5F, 0.5F);
				
				buffer.finish(CustomRenderType.LINES2);
				matrix.pop();
			}
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
