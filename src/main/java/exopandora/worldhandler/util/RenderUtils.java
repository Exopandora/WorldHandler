package exopandora.worldhandler.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import exopandora.worldhandler.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderUtils
{
	public static void drawWatchIntoGui(MatrixStack matrix, AbstractGui gui, int width, int height, long worldTicks, boolean smooth)
	{
		float hour = TextUtils.toHour(worldTicks);
		float minute = TextUtils.toMinute(worldTicks);
		
		if(smooth)
		{
			hour = (worldTicks + 6000) / 1000F;
			minute = (float) ((worldTicks + 6000F - Math.floor(hour) * 1000) * 6 / 100);
		}
		
		float rotationHour = (360 / 12) * (hour >= 12 ? (hour - 12) : hour) - 180F;
		float rotationMinute = (360 / 60) * minute - 180F;
		
		matrix.push();
		matrix.translate(width + 5, height + 5, 0F);
		matrix.scale(0.25F, 0.25F, 0.25F);
		
		matrix.rotate(Vector3f.ZP.rotationDegrees(rotationHour));
		AbstractGui.fill(matrix, -1, -1, 1, 11, 0xFF383838);
		matrix.rotate(Vector3f.ZN.rotationDegrees(rotationHour));
		
		matrix.rotate(Vector3f.ZP.rotationDegrees(rotationMinute));
		AbstractGui.fill(matrix, -1, -1, 1, 15, 0xFF6F6F6F);
		matrix.rotate(Vector3f.ZN.rotationDegrees(rotationMinute));
		
		matrix.pop();
		
		RenderUtils.color(Config.getSkin().getButtonRedF(), Config.getSkin().getButtonGreenF(), Config.getSkin().getButtonBlueF(), Config.getSkin().getButtonAlphaF());
		Minecraft.getInstance().getTextureManager().bindTexture(ResourceHelper.getIconTexture());
		
		gui.blit(matrix, width + 0, height, 48, 0, 10, 10);
		
		matrix.push();
		matrix.scale(0.5F, 0.5F, 0.5F);
		AbstractGui.fill(matrix, (width + 5) * 2 - 1, (height + 4) * 2 + 1, (width + 6) * 2 - 1, (height + 5) * 2 + 1, 0xFF000000);
		matrix.pop();
	}
	
	public static void renderItemIntoGUI(MatrixStack matrix, ItemStack stack, int x, int y)
	{
		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
		TextureManager textureManager = Minecraft.getInstance().getTextureManager();
		IBakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getItemModelWithOverrides(stack, (World) null, (LivingEntity) null);
		
		matrix.push();
		
		textureManager.bindTexture(PlayerContainer.LOCATION_BLOCKS_TEXTURE);
		textureManager.getTexture(PlayerContainer.LOCATION_BLOCKS_TEXTURE).setBlurMipmapDirect(false, false);
		
		RenderUtils.enableRescaleNormal();
		RenderUtils.enableAlphaTest();
		RenderSystem.defaultAlphaFunc();
		RenderSystem.enableBlend();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		RenderUtils.color(1.0F, 1.0F, 1.0F, 1.0F);
		
		matrix.translate((float) x, (float) y, 100.0F + itemRenderer.zLevel);
		matrix.translate(8.0F, 8.0F, 0.0F);
		matrix.scale(1.0F, -1.0F, 1.0F);
		matrix.scale(16.0F, 16.0F, 16.0F);
		
		IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
		boolean flag = !bakedmodel.func_230044_c_();
		
		if(flag)
		{
			RenderHelper.setupGuiFlatDiffuseLighting();
		}
		
		itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.GUI, false, matrix, buffer, 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);
		buffer.finish();
		
		RenderSystem.enableDepthTest();
		
		if(flag)
		{
			RenderHelper.setupGui3DDiffuseLighting();
		}
		
		RenderUtils.disableAlphaTest();
		RenderUtils.disableRescaleNormal();
		
		matrix.pop();
	}
	
	@SuppressWarnings("deprecation")
	public static void color(float r, float g, float b, float a)
	{
		RenderSystem.color4f(r, g, b, a);
	}
	
	@SuppressWarnings("deprecation")
	public static void color(float r, float g, float b)
	{
		RenderSystem.color3f(r, g, b);
	}
	
	@SuppressWarnings("deprecation")
	public static void enableAlphaTest()
	{
		RenderSystem.enableAlphaTest();
	}
	
	@SuppressWarnings("deprecation")
	public static void disableAlphaTest()
	{
		RenderSystem.disableAlphaTest();
	}
	
	@SuppressWarnings("deprecation")
	public static void enableRescaleNormal()
	{
		RenderSystem.enableRescaleNormal();
	}
	
	@SuppressWarnings("deprecation")
	public static void disableRescaleNormal()
	{
		RenderSystem.disableRescaleNormal();
	}
	
	@SuppressWarnings("deprecation")
	public static void disableLighting()
	{
		RenderSystem.disableLighting();
	}
}
