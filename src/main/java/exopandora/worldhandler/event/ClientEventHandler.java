package exopandora.worldhandler.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.util.BlockHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEventHandler
{
	@SubscribeEvent
	public static void renderWorldLastEvent(RenderWorldLastEvent event)
	{
		if(Config.getSettings().highlightBlocks() && Minecraft.getInstance().level != null && Minecraft.getInstance().getEntityRenderDispatcher().camera != null)
		{
			Vec3 projected = Minecraft.getInstance().getEntityRenderDispatcher().camera.getPosition();
			
			double minX = Math.min(BlockHelper.getPos1().getX(), BlockHelper.getPos2().getX());
			double minY = Math.min(BlockHelper.getPos1().getY(), BlockHelper.getPos2().getY());
			double minZ = Math.min(BlockHelper.getPos1().getZ(), BlockHelper.getPos2().getZ());
			
			double maxX = Math.max(BlockHelper.getPos1().getX(), BlockHelper.getPos2().getX()) + 1;
			double maxY = Math.max(BlockHelper.getPos1().getY(), BlockHelper.getPos2().getY()) + 1;
			double maxZ = Math.max(BlockHelper.getPos1().getZ(), BlockHelper.getPos2().getZ()) + 1;
			
			AABB aabb = new AABB(minX, minY, minZ, maxX, maxY, maxZ);
			
			if(aabb.getCenter().distanceTo(projected) < 96)
			{
				PoseStack matrix = event.getMatrixStack();
				matrix.pushPose();
				matrix.translate(-projected.x(), -projected.y(), -projected.z());
				
				BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
				VertexConsumer builder = buffer.getBuffer(RenderType.lines());
				
				LevelRenderer.renderLineBox(matrix, builder, minX, minY, minZ, maxX, maxY, maxZ, 0.9F, 0.9F, 0.9F, 1.0F, 0.5F, 0.5F, 0.5F);
				
				buffer.endBatch(RenderType.lines());
				buffer.endBatch();
				
//				try
//				{
//					Field transparencyChain = WorldRenderer.class.getDeclaredField("transparencyChain");
//					transparencyChain.setAccessible(true);
//					ShaderGroup shader = (ShaderGroup) transparencyChain.get(event.getContext());
//					
//					if(shader != null)
//					{
//						Field ITEM_ENTITY_TARGET = RenderState.class.getDeclaredField("ITEM_ENTITY_TARGET");
//						ITEM_ENTITY_TARGET.setAccessible(true);
//						RenderState.TargetState target = (RenderState.TargetState) ITEM_ENTITY_TARGET.get(null);
//						target.setupRenderState();
//						event.getContext().getItemEntityTarget().framebufferClear(Minecraft.IS_RUNNING_ON_MAC);
//						event.getContext().getItemEntityTarget().copyDepthFrom(Minecraft.getInstance().getFramebuffer());
//				        Minecraft.getInstance().getFramebuffer().bindFramebuffer(false);
//						target.clearRenderState();
//					}
//				}
//				catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
//				{
//					e.printStackTrace();
//				}
				
				matrix.popPose();
			}
		}
	}
	
	@SubscribeEvent
	public static void clientChatEvent(ClientChatEvent event)
	{
		if(!Minecraft.getInstance().hasSingleplayerServer() && Minecraft.getInstance().player != null)
		{
			CommandDispatcher<CommandSourceStack> dispatcher = new CommandDispatcher<CommandSourceStack>();
			CommandHelper.registerCommands(dispatcher);
			
			StringReader command = new StringReader(event.getMessage());
			command.skip();
			
			ParseResults<CommandSourceStack> result = dispatcher.parse(command, Minecraft.getInstance().player.createCommandSourceStack());
			
			if(result.getContext().getCommand() != null)
			{
				try
				{
					dispatcher.execute(result);
					Minecraft.getInstance().gui.getChat().addRecentChat(event.getMessage());
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
