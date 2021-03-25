package exopandora.worldhandler.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;

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
		super(x, y, width, height, stack.getHoverName(), actionHandler);
		this.stack = stack;
	}
	
	@Override
	public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partialTicks)
	{
		super.renderBg(matrix, Minecraft.getInstance(), mouseX, mouseY);
		Minecraft.getInstance().getItemRenderer().renderGuiItem(this.stack, this.x + this.width / 2 - 8, this.y + 2);
	}
}
