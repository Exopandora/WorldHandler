package exopandora.worldhandler.gui.button;

import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiButtonItem extends GuiButtonBase
{
    private final ItemStack stack;
	
    public GuiButtonItem(int x, int y, int width, int height, Item item, ActionHandler actionHandler)
    {
    	this(x, y, width, height, new ItemStack(item), actionHandler);
    }
    
    public GuiButtonItem(int x, int y, int width, int height, ItemStack stack, ActionHandler actionHandler)
    {
    	super(x, y, width, height, stack.getTextComponent().getString(), actionHandler);
    	this.stack = stack;
    }
    
    @Override
    public void renderButton(int mouseX, int mouseY, float partialTicks)
    {
    	super.renderBg(Minecraft.getInstance(), mouseX, mouseY);
		Minecraft.getInstance().getItemRenderer().renderItemIntoGUI(this.stack, this.x + this.width / 2 - 8, this.y + 2);
    }
}
