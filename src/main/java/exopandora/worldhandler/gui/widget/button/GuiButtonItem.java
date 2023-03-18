package exopandora.worldhandler.gui.widget.button;

import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

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
	public void renderWidget(PoseStack matrix, int mouseX, int mouseY, float partialTicks)
	{
		super.renderBackground(matrix, mouseX, mouseY, partialTicks);
		Minecraft.getInstance().getItemRenderer().renderGuiItem(matrix, this.stack, this.getX() + this.width / 2 - 8, this.getY() + 2);
	}
}
