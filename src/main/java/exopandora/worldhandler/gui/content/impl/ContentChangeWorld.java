package exopandora.worldhandler.gui.content.impl;

import exopandora.worldhandler.gui.DummyScreen;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.Connection;
import exopandora.worldhandler.util.Connection.DedicatedConnection;
import exopandora.worldhandler.util.Connection.IntegratedConnection;
import exopandora.worldhandler.util.Connection.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ConnectingScreen;
import net.minecraft.client.gui.screen.DirtMessageScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.MultiplayerScreen;
import net.minecraft.client.gui.screen.WorldSelectionScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.text.TranslationTextComponent;
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
			Connection connection = ContentChangeWorld.disconnect();
			Minecraft.getInstance().displayGuiScreen(new WorldSelectionScreen(new DummyScreen(() -> ContentChangeWorld.reconnect(connection))));
		}));
		
		container.add(new GuiButtonBase(x + 116 / 2, y + 48, 232 / 2, 20, I18n.format("gui.worldhandler.change_world.multiplayer"), () ->
		{
			Connection connection = ContentChangeWorld.disconnect();
			Minecraft.getInstance().displayGuiScreen(new MultiplayerScreen(new DummyScreen(() -> ContentChangeWorld.reconnect(connection))));
		}));
	}
	
	private static Connection disconnect()
	{
		boolean isIntegrated = Minecraft.getInstance().isIntegratedServerRunning();
		boolean isRealms = Minecraft.getInstance().isConnectedToRealms();
		ServerData data = Minecraft.getInstance().getCurrentServerData();
		
		if(isIntegrated)
		{
			String worldName = Minecraft.getInstance().getIntegratedServer().getWorldName();
			String folderName = Minecraft.getInstance().getIntegratedServer().getFolderName();
			
			Minecraft.getInstance().world.sendQuittingDisconnectingPacket();
			Minecraft.getInstance().unloadWorld(new DirtMessageScreen(new TranslationTextComponent("menu.savingLevel")));
			
			return new IntegratedConnection(Type.INTEGRATED, worldName, folderName);
		}
		
		Minecraft.getInstance().world.sendQuittingDisconnectingPacket();
		Minecraft.getInstance().unloadWorld();
		
		if(isRealms)
		{
			return null;
		}
		
		return new DedicatedConnection(Type.DEDICATED, data);
	}
	
	private static void reconnect(Connection connection)
	{
		if(connection == null)
		{
			RealmsBridge realmsbridge = new RealmsBridge();
			realmsbridge.switchToRealms(new MainMenuScreen());
		}
		else if(connection instanceof IntegratedConnection)
		{
			IntegratedConnection integrated = (IntegratedConnection) connection;
			Minecraft.getInstance().launchIntegratedServer(integrated.getFolderName(), integrated.getWorldName(), null);
			Minecraft.getInstance().mouseHelper.grabMouse();
		}
		else if(connection instanceof DedicatedConnection)
		{
			DedicatedConnection dedicated = (DedicatedConnection) connection;
			Minecraft.getInstance().displayGuiScreen(new ConnectingScreen(new MainMenuScreen(), Minecraft.getInstance(), dedicated.getData()));
			Minecraft.getInstance().mouseHelper.grabMouse();
		}
	}
	
	@Override
	public String getTitle()
	{
		return I18n.format("gui.worldhandler.title.change_world");
	}
}
