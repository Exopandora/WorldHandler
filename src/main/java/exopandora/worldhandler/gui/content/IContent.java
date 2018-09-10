package exopandora.worldhandler.gui.content;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IContent
{
	default void init(Container container)
	{
		
	}
	
	default void initGui(Container container, int x, int y)
	{
		
	}
	
	void initButtons(Container container, int x, int y);
	
	default void updateScreen(Container container)
	{
		
	}
	
	default void actionPerformed(Container container, GuiButton button) throws Exception
	{
		
	}
	
	default void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		
	}
	
	default void keyTyped(Container container, char typedChar, int keyCode)
	{
		
	}
	
	default void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		
	}
	
	default void onPlayerNameChanged(String username)
	{
		
	}
	
	Category getCategory();
	
	String getTitle();
	String getTabTitle();
	
	Content getActiveContent();
	
	@Nullable
	default Content getBackContent()
	{
		return Contents.MAIN;
	}
	
	@Nullable
	default ICommandBuilder getCommandBuilder()
	{
		return null;
	}
	
	@Nullable
	default String[] getHeadline()
	{
		return null;
	}
	
	default void onGuiClosed()
	{
		
	}
}
