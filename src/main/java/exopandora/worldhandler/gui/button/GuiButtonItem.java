package exopandora.worldhandler.gui.button;

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
		super(x, y, width, height, stack.getTextComponent(), actionHandler);
		this.stack = stack;
	}
	
	@Override
	public void func_230431_b_(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) //renderButton
	{
		super.func_230441_a_(matrix, Minecraft.getInstance(), mouseX, mouseY); //renderBg
		Minecraft.getInstance().getItemRenderer().renderItemIntoGUI(this.stack, this.field_230690_l_ + this.field_230688_j_ / 2 - 8, this.field_230691_m_ + 2);
	}
}
