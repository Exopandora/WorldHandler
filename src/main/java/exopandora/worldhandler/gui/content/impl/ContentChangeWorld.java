package exopandora.worldhandler.gui.content.impl;

import com.mojang.realmsclient.RealmsMainScreen;

import exopandora.worldhandler.gui.DummyScreen;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.IConnection;
import exopandora.worldhandler.util.IConnection.DedicatedConnection;
import exopandora.worldhandler.util.IConnection.IntegratedConnection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ContentChangeWorld extends ContentChild
{
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.addRenderableWidget(new GuiButtonBase(x, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.addRenderableWidget(new GuiButtonBase(x + 118, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.addRenderableWidget(new GuiButtonBase(x + 116 / 2, y + 24, 232 / 2, 20, Component.translatable("gui.worldhandler.change_world.singleplayer"), () ->
		{
			IConnection connection = ContentChangeWorld.disconnect();
			Minecraft.getInstance().setScreen(new SelectWorldScreen(new DummyScreen(() -> ContentChangeWorld.reconnect(connection))));
		}));
		
		container.addRenderableWidget(new GuiButtonBase(x + 116 / 2, y + 48, 232 / 2, 20, Component.translatable("gui.worldhandler.change_world.multiplayer"), () ->
		{
			IConnection connection = ContentChangeWorld.disconnect();
			DummyScreen dummy = new DummyScreen(() -> ContentChangeWorld.reconnect(connection));
			
			if(Minecraft.getInstance().options.skipMultiplayerWarning)
			{
				Minecraft.getInstance().setScreen(new JoinMultiplayerScreen(dummy));
			}
			else
			{
				Minecraft.getInstance().setScreen(new SafetyScreen(dummy));
			}
		}));
	}
	
	private static IConnection disconnect()
	{
		Minecraft minecraft = Minecraft.getInstance();
		boolean isIntegrated = minecraft.isLocalServer();
		ServerData serverData = minecraft.getCurrentServer();
		
		minecraft.level.disconnect();
		
		if(isIntegrated)
		{
			String folder = minecraft.getSingleplayerServer().storageSource.getLevelId();
			minecraft.disconnect(new GenericDirtMessageScreen(Component.translatable("menu.savingLevel")));
			return new IntegratedConnection(folder);
		}
		else
		{
			minecraft.disconnect();
		}
		
		if(serverData.isRealm())
		{
			return null;
		}
		
		return new DedicatedConnection(serverData);
	}
	
	private static void reconnect(IConnection connection)
	{
		if(connection == null)
		{
			Minecraft.getInstance().setScreen(new RealmsMainScreen(new TitleScreen()));
		}
		else if(connection instanceof IntegratedConnection integrated)
		{
			Minecraft.getInstance().createWorldOpenFlows().loadLevel(new TitleScreen(), integrated.getFolder());
			Minecraft.getInstance().mouseHandler.grabMouse();
		}
		else if(connection instanceof DedicatedConnection dedicated)
		{
			ServerData data = dedicated.getData();
			ConnectScreen.startConnecting(new TitleScreen(), Minecraft.getInstance(), ServerAddress.parseString(data.ip), data, false);
		}
	}
	
	@Override
	public MutableComponent getTitle()
	{
		return Component.translatable("gui.worldhandler.title.change_world");
	}
}
