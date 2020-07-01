package exopandora.worldhandler.gui.content;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IContent extends IGuiEventListener
{
	default void init(Container container)
	{
		
	}
	
	default void initGui(Container container, int x, int y)
	{
		
	}
	
	void initButtons(Container container, int x, int y);
	
	default void tick(Container container)
	{
		
	}
	
	default void drawScreen(MatrixStack matrix, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		
	}
	
	default void onPlayerNameChanged(String username)
	{
		
	}
	
	Category getCategory();
	
	IFormattableTextComponent getTitle();
	IFormattableTextComponent getTabTitle();
	
	Content getActiveContent();
	
	@Nonnull
	default Content getBackContent()
	{
		return Contents.MAIN;
	}
	
	@Nullable
	default ICommandBuilder getCommandBuilder()
	{
		return null;
	}
	
	default void onGuiClosed()
	{
		
	}
}
