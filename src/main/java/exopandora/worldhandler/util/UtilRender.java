package exopandora.worldhandler.util;

import exopandora.worldhandler.config.ConfigSkin;
import exopandora.worldhandler.format.TextFormatting;
import exopandora.worldhandler.helper.ResourceHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UtilRender
{
    public static void drawWatchIntoGui(Gui gui, int width, int height, long worldTicks, boolean smooth)
    {
        float hour = TextFormatting.getHour(worldTicks);
        float minute = TextFormatting.getMinute(worldTicks);
        
        if(smooth)
        {
        	hour = (worldTicks + 6000) / 1000F;
        	minute = (float) ((worldTicks + 6000F - Math.floor(hour) * 1000) * 6 / 100);
        }
        
        float rotationHour = (360 / 12) * (hour >= 12 ? (hour - 12) : hour) - 180F;
        float rotationMinute = (360 / 60) * minute - 180F;
        
	    GlStateManager.pushMatrix();
        GlStateManager.translate(width + 5, height + 5, 0F);
        GlStateManager.scale(0.25F, 0.25F, 0.25F);
        
        GlStateManager.rotate(rotationHour, 0F, 0F, 1F);
	    Gui.drawRect(-1, -1, 1, 11, 0xFF383838);
        GlStateManager.rotate(-rotationHour, 0F, 0F, 1F);
        
        GlStateManager.rotate(rotationMinute, 0F, 0F, 1F);
        Gui.drawRect(-1, -1, 1, 15, 0xFF6F6F6F);
        GlStateManager.rotate(-rotationMinute, 0F, 0F, 1F);
        
	    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	    GlStateManager.popMatrix();
	    
	    GlStateManager.color((float) ConfigSkin.getButtonRed() / 255, (float) ConfigSkin.getButtonGreen() / 255, (float) ConfigSkin.getButtonBlue() / 255, 1.0F);
	    Minecraft.getMinecraft().renderEngine.bindTexture(ResourceHelper.getIconTexture());
	    
	    gui.drawTexturedModalRect(width + 0, height, 48, 0, 10, 10);
	    
	    GlStateManager.pushMatrix();
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        Gui.drawRect((width + 5) * 2 - 1, (height + 4) * 2 + 1, (width + 6) * 2 - 1, (height + 5) * 2 + 1, 0xFF000000);
	    GlStateManager.popMatrix();
    }
}
