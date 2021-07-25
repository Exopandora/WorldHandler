package exopandora.worldhandler.gui.content;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.widget.IWidget;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IContent extends IWidget
{
	Category getCategory();
	
	MutableComponent getTitle();
	MutableComponent getTabTitle();
	
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
