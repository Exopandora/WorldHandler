package exopandora.worldhandler.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiButtonItem extends GuiButtonWorldHandler
{
    private final ItemStack item;
	
    public GuiButtonItem(int id, int x, int y, int width, int height, Item item)
    {
    	this(id, x, y, width, height, new ItemStack(item));
    }
    
    public GuiButtonItem(int id, int x, int y, int width, int height, ItemStack item)
    {
    	super(id, x, y, width, height, null);
    	this.item = item;
    }
    
    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY, float partialTicks)
    {
    	if(this.visible)
    	{
            super.drawBackground(minecraft, mouseX, mouseY);
            
            GlStateManager.enableRescaleNormal();
    		RenderHelper.enableGUIStandardItemLighting();
            
    		Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(this.item, this.x + this.width / 2 - 8, this.y + 2);
            
    		RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
    		GlStateManager.disableBlend();
            
			this.isActive = true;
    	}
    }
}
