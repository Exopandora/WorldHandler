package exopandora.worldhandler.gui.content.impl;

import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.impl.abstr.ContentChild;
import exopandora.worldhandler.helper.ActionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ConnectingScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.MultiplayerScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.WorldSelectionScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ContentChangeWorld extends ContentChild
{
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(new GuiButtonBase(x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(new GuiButtonBase(x + 116 / 2, y + 24, 232 / 2, 20, I18n.format("gui.worldhandler.change_world.singleplayer"), () ->
		{
			Minecraft.getInstance().displayGuiScreen(new WorldSelectionScreen(container));
		}));
		container.add(new GuiButtonBase(x + 116 / 2, y + 48, 232 / 2, 20, I18n.format("gui.worldhandler.change_world.multiplayer"), () ->
		{
			ServerData server = Minecraft.getInstance().getCurrentServerData();
			
			if(server != null)
			{
				Minecraft.getInstance().world.sendQuittingDisconnectingPacket();
				Minecraft.getInstance().loadWorld(null);
				
				Minecraft.getInstance().displayGuiScreen(new MultiplayerScreen(new Screen(new StringTextComponent(""))
				{
					@Override
					public void init()
					{
						Minecraft.getInstance().displayGuiScreen(new ConnectingScreen(new MainMenuScreen(), Minecraft.getInstance(), server));
						Minecraft.getInstance().mouseHelper.grabMouse();
					}
				}));
			}
			else
			{
				String worldName = Minecraft.getInstance().getIntegratedServer().getWorldName();
				String folderName = Minecraft.getInstance().getIntegratedServer().getFolderName();
				
				Minecraft.getInstance().world.sendQuittingDisconnectingPacket();
				Minecraft.getInstance().loadWorld(null);
				
				Minecraft.getInstance().displayGuiScreen(new MultiplayerScreen(new Screen(new StringTextComponent(""))
				{
					@Override
					public void init()
					{
						Minecraft.getInstance().launchIntegratedServer(folderName, worldName, null);
						Minecraft.getInstance().displayGuiScreen(null);
						Minecraft.getInstance().mouseHelper.grabMouse();
					}
				}));
			}
		}));
	}
	
	@Override
	public String getTitle()
	{
		return I18n.format("gui.worldhandler.title.change_world");
	}
}
