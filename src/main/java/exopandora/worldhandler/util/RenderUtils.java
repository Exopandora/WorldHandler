package exopandora.worldhandler.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import exopandora.worldhandler.config.Config;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class RenderUtils
{
	public static final double EPS = 0.0020000000949949026D;
	
	public static void drawWatchIntoGui(GuiGraphics guiGraphics, int width, int height, long worldTicks, boolean smooth)
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
		
		PoseStack poseStack = guiGraphics.pose();
		poseStack.pushPose();
		poseStack.translate(width + 5, height + 5, 0F);
		poseStack.scale(0.25F, 0.25F, 0.25F);
		
		poseStack.mulPose(Axis.ZP.rotationDegrees(rotationHour));
		guiGraphics.fill(-1, -1, 1, 11, 0xFF383838);
		poseStack.mulPose(Axis.ZN.rotationDegrees(rotationHour));
		
		poseStack.mulPose(Axis.ZP.rotationDegrees(rotationMinute));
		guiGraphics.fill(-1, -1, 1, 15, 0xFF6F6F6F);
		poseStack.mulPose(Axis.ZN.rotationDegrees(rotationMinute));
		
		poseStack.popPose();
		
		RenderUtils.colorDefaultButton();
		
		guiGraphics.blit(ResourceHelper.iconTexture(), width + 0, height, 48, 0, 10, 10);
		
		poseStack.pushPose();
		poseStack.scale(0.5F, 0.5F, 0.5F);
		guiGraphics.fill((width + 5) * 2 - 1, (height + 4) * 2 + 1, (width + 6) * 2 - 1, (height + 5) * 2 + 1, 0xFF000000);
		poseStack.popPose();
	}
	
	public static void colorDefaultButton()
	{
		float r = Config.getSkin().getButtonRed();
		float g = Config.getSkin().getButtonGreen();
		float b = Config.getSkin().getButtonBlue();
		float a = Config.getSkin().getButtonAlpha();
		
		RenderSystem.setShaderColor(r, g, b, a);
	}
	
	public static void colorDefaultBackground()
	{
		RenderUtils.colorDefaultBackground(1.0F);
	}
	
	public static void colorDefaultBackground(double alpha)
	{
		float r = Config.getSkin().getBackgroundRed();
		float g = Config.getSkin().getBackgroundGreen();
		float b = Config.getSkin().getBackgroundBlue();
		float a = (float) alpha * Config.getSkin().getBackgroundAlpha();
		
		RenderSystem.setShaderColor(r, g, b, a);
	}
	
	public static void colorDarkBackground()
	{
		float r = Config.getSkin().getBackgroundRed();
		float g = Config.getSkin().getBackgroundGreen();
		float b = Config.getSkin().getBackgroundBlue();
		float a = Config.getSkin().getBackgroundAlpha();
		
		RenderSystem.setShaderColor(Math.max(0, r - 0.3F), Math.max(0, g - 0.3F), Math.max(0, b - 0.3F), a);
	}
	
	public static void drawTexturedTriangleBL(GuiGraphics guiGraphics, ResourceLocation texture, int x, int y, int textureX, int textureY, int size)
	{
		for(int i = 0; i < size; i++)
		{
			guiGraphics.blit(texture, x, y + i, textureX, textureY + i, i + 1, 1);
		}
	}
	
	public static void drawTexturedTriangleBR(GuiGraphics guiGraphics, ResourceLocation texture, int x, int y, int textureX, int textureY, int size)
	{
		for(int i = 0; i < size; i++)
		{
			guiGraphics.blit(texture, x + size - i - 1, y + i, textureX + size - i - 1, textureY + i, i + 1, 1);
		}
	}
	
	public static void drawTexturedTriangleTL(GuiGraphics guiGraphics, ResourceLocation texture, int x, int y, int textureX, int textureY, int size)
	{
		for(int i = 0; i < size; i++)
		{
			guiGraphics.blit(texture, x, y + i, textureX, textureY, size - i, 1);
		}
	}
	
	public static void drawTexturedTriangleTR(GuiGraphics guiGraphics, ResourceLocation texture, int x, int y, int textureX, int textureY, int size)
	{
		for(int i = 0; i < size; i++)
		{
			guiGraphics.blit(texture, x + i, y + i, textureX + i, textureY, size - i, 1);
		}
	}
	
	public static void drawTexturedWedgeGradientTR(GuiGraphics guiGraphics, ResourceLocation texture, int x, int y, int textureX, int textureY, int width, int height)
	{
		RenderSystem.enableBlend();
		
		for(int i = 0; i < height; i++)
		{
			double w = (height - i) / (double) (height + 1);
			int z = width - (int) (w * width);
			
			RenderUtils.colorDefaultBackground(w);
			guiGraphics.blit(texture, x + z, y + i, textureX + z, textureY + i, width - z, 1);
		}
		
		RenderSystem.disableBlend();
	}
	
	public static void drawTexturedWedgeGradientTL(GuiGraphics guiGraphics, ResourceLocation texture, int x, int y, int textureX, int textureY, int width, int height)
	{
		RenderSystem.enableBlend();
		
		for(int i = 0; i < height; i++)
		{
			double w = (height - i) / (double) (height + 1);
			int z = (int) (w * width);
			
			RenderUtils.colorDefaultBackground(w);
			guiGraphics.blit(texture, x, y + i, textureX, textureY + i, z, 1);
		}
		
		RenderSystem.disableBlend();
	}
}
