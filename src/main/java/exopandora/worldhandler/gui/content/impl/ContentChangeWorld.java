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
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;

public class ContentChangeWorld extends ContentChild
{
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(new GuiButtonBase(x, y + 96, 114, 20, new TranslatableComponent("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, new TranslatableComponent("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(new GuiButtonBase(x + 116 / 2, y + 24, 232 / 2, 20, new TranslatableComponent("gui.worldhandler.change_world.singleplayer"), () ->
		{
			IConnection connection = ContentChangeWorld.disconnect();
			Minecraft.getInstance().setScreen(new SelectWorldScreen(new DummyScreen(() -> ContentChangeWorld.reconnect(connection))));
		}));
		
		container.add(new GuiButtonBase(x + 116 / 2, y + 48, 232 / 2, 20, new TranslatableComponent("gui.worldhandler.change_world.multiplayer"), () ->
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
		boolean isIntegrated = Minecraft.getInstance().isLocalServer();
		boolean isRealms = Minecraft.getInstance().isConnectedToRealms();
		ServerData data = Minecraft.getInstance().getCurrentServer();
		
		if(isIntegrated)
		{
			IntegratedServer integrated = Minecraft.getInstance().getSingleplayerServer();
			String folder = integrated.storageSource.getLevelId();
			WorldGenSettings worldGenSettings = integrated.getWorldData().worldGenSettings();
			LevelSettings levelSettings = integrated.getWorldData().getLevelSettings();
			
			Minecraft.getInstance().level.disconnect();
			Minecraft.getInstance().clearLevel(new GenericDirtMessageScreen(new TranslatableComponent("menu.savingLevel")));
			
			return new IntegratedConnection(folder, levelSettings, worldGenSettings);
		}
		
		if(Minecraft.getInstance().level != null)
		{
			Minecraft.getInstance().level.disconnect();
			Minecraft.getInstance().clearLevel();
		}
		
		if(isRealms)
		{
			return null;
		}
		
		return new DedicatedConnection(data);
	}
	
	private static void reconnect(IConnection connection)
	{
		if(connection == null)
		{
			Minecraft.getInstance().setScreen(new RealmsMainScreen(new TitleScreen()));
		}
		else if(connection instanceof IntegratedConnection)
		{
			IntegratedConnection integrated = (IntegratedConnection) connection;
			Minecraft.getInstance().createLevel(integrated.getFolder(), integrated.getWorldSettings(), RegistryAccess.builtin(), integrated.getWorldGenSettings());
			Minecraft.getInstance().mouseHandler.grabMouse();
		}
		else if(connection instanceof DedicatedConnection)
		{
			DedicatedConnection dedicated = (DedicatedConnection) connection;
			ServerData data = dedicated.getData();
			ConnectScreen.startConnecting(new TitleScreen(), Minecraft.getInstance(), ServerAddress.parseString(data.ip), data);
		}
	}
	
	@Override
	public MutableComponent getTitle()
	{
		return new TranslatableComponent("gui.worldhandler.title.change_world");
	}
}
