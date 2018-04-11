package exopandora.worldhandler.gui.content.impl;

import exopandora.worldhandler.gui.button.GuiButtonWorldHandler;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.impl.abstr.ContentChild;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ContentChangeWorld extends ContentChild
{
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(new GuiButtonWorldHandler(0, x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back")));
		container.add(new GuiButtonWorldHandler(1, x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame")));
		
		container.add(new GuiButtonWorldHandler(2, x + 116 / 2, y + 24, 232 / 2, 20, I18n.format("gui.worldhandler.change_world.singleplayer")));
		container.add(new GuiButtonWorldHandler(3, x + 116 / 2, y + 48, 232 / 2, 20, I18n.format("gui.worldhandler.change_world.multiplayer")));
	}
	
	@Override
	public void actionPerformed(Container container, GuiButton button)
	{
		switch(button.id)
		{
			case 2:
				Minecraft.getMinecraft().displayGuiScreen(new GuiWorldSelection(container));
				break;
			case 3:
				ServerData server = Minecraft.getMinecraft().getCurrentServerData();
				
				Minecraft.getMinecraft().world.sendQuittingDisconnectingPacket();
				Minecraft.getMinecraft().loadWorld((WorldClient)null);
				
				Minecraft.getMinecraft().displayGuiScreen(new GuiMultiplayer(new GuiScreen()
				{
					@Override
					public void initGui()
					{
						FMLClientHandler.instance().connectToServer(new GuiMultiplayer(new GuiMainMenu()), server);
						Minecraft.getMinecraft().displayGuiScreen((GuiScreen) null);
						Minecraft.getMinecraft().setIngameFocus();
					}
				}));
				break;
			default:
				break;
		}
	}
	
	@Override
	public String getTitle()
	{
		return I18n.format("gui.worldhandler.title.change_world");
	}
}
