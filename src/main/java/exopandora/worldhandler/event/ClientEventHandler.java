package exopandora.worldhandler.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.BlockHelper;
import exopandora.worldhandler.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEventHandler
{
	public static boolean openGui;
	
	@SubscribeEvent
	public static void renderLevelLastEvent(RenderLevelLastEvent event)
	{
		if(Config.getSettings().highlightBlocks() && Minecraft.getInstance().level != null && Minecraft.getInstance().getEntityRenderDispatcher().camera != null)
		{
			Vec3 projected = Minecraft.getInstance().getEntityRenderDispatcher().camera.getPosition();
			
			double minX = Math.min(BlockHelper.pos1().getX(), BlockHelper.pos2().getX()) - RenderUtils.EPS;
			double minY = Math.min(BlockHelper.pos1().getY(), BlockHelper.pos2().getY()) - RenderUtils.EPS;
			double minZ = Math.min(BlockHelper.pos1().getZ(), BlockHelper.pos2().getZ()) - RenderUtils.EPS;
			
			double maxX = Math.max(BlockHelper.pos1().getX(), BlockHelper.pos2().getX()) + 1 + RenderUtils.EPS;
			double maxY = Math.max(BlockHelper.pos1().getY(), BlockHelper.pos2().getY()) + 1 + RenderUtils.EPS;
			double maxZ = Math.max(BlockHelper.pos1().getZ(), BlockHelper.pos2().getZ()) + 1 + RenderUtils.EPS;
			
			AABB aabb = new AABB(minX, minY, minZ, maxX, maxY, maxZ);
			
			if(aabb.getCenter().distanceTo(projected) < 96)
			{
				PoseStack matrix = event.getPoseStack();
				matrix.pushPose();
				matrix.translate(-projected.x(), -projected.y(), -projected.z());
				
				BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
				VertexConsumer builder = buffer.getBuffer(RenderType.lines());
				
				LevelRenderer.renderLineBox(matrix, builder, minX, minY, minZ, maxX, maxY, maxZ, 0.9F, 0.9F, 0.9F, 1.0F, 0.5F, 0.5F, 0.5F);
				
				buffer.endBatch(RenderType.lines());
				buffer.endBatch();
				
				matrix.popPose();
			}
		}
	}
	
	@SubscribeEvent
	public static void clientTickEvent(ClientTickEvent event)
	{
		if(TickEvent.Phase.START.equals(event.phase) && ClientEventHandler.openGui)
		{
			ClientEventHandler.openGui = false;
			ActionHelper.displayGui();
		}
	}
}
