package exopandora.worldhandler.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;

import exopandora.worldhandler.gui.container.Container;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IWidget extends IGuiEventListener
{
	default void init(Container container)
	{
		
	}
	
	default void initGui(Container container, int x, int y)
	{
		
	}
	
	default void initButtons(Container container, int x, int y)
	{
		
	}
	
	default void tick(Container container)
	{
		
	}
	
	default void drawScreen(MatrixStack matrix, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		
	}
	
	default void onPlayerNameChanged(String username)
	{
		
	}
}
