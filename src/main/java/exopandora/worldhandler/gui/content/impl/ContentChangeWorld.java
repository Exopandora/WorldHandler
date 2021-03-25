package exopandora.worldhandler.gui.content.impl;

import exopandora.worldhandler.gui.DummyScreen;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.IConnection;
import exopandora.worldhandler.util.IConnection.DedicatedConnection;
import exopandora.worldhandler.util.IConnection.IntegratedConnection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ConnectingScreen;
import net.minecraft.client.gui.screen.DirtMessageScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.MultiplayerScreen;
import net.minecraft.client.gui.screen.MultiplayerWarningScreen;
import net.minecraft.client.gui.screen.WorldSelectionScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.realms.RealmsBridgeScreen;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ContentChangeWorld extends ContentChild
{
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(new GuiButtonBase(x, y + 96, 114, 20, new TranslationTextComponent("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, new TranslationTextComponent("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(new GuiButtonBase(x + 116 / 2, y + 24, 232 / 2, 20, new TranslationTextComponent("gui.worldhandler.change_world.singleplayer"), () ->
		{
			IConnection connection = ContentChangeWorld.disconnect();
			Minecraft.getInstance().setScreen(new WorldSelectionScreen(new DummyScreen(() -> ContentChangeWorld.reconnect(connection))));
		}));
		
		container.add(new GuiButtonBase(x + 116 / 2, y + 48, 232 / 2, 20, new TranslationTextComponent("gui.worldhandler.change_world.multiplayer"), () ->
		{
			IConnection connection = ContentChangeWorld.disconnect();
			DummyScreen dummy = new DummyScreen(() -> ContentChangeWorld.reconnect(connection));
			
			if(Minecraft.getInstance().options.skipMultiplayerWarning)
			{
				Minecraft.getInstance().setScreen(new MultiplayerScreen(dummy));
			}
			else
			{
				Minecraft.getInstance().setScreen(new MultiplayerWarningScreen(dummy));
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
			DimensionGeneratorSettings dimensionGeneratorSettings = integrated.getWorldData().worldGenSettings();
			WorldSettings worldSettings = integrated.getWorldData().getLevelSettings();
			
			Minecraft.getInstance().level.disconnect();
			Minecraft.getInstance().clearLevel(new DirtMessageScreen(new TranslationTextComponent("menu.savingLevel")));
			
			return new IntegratedConnection(folder, worldSettings, dimensionGeneratorSettings);
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
			RealmsBridgeScreen realmsbridge = new RealmsBridgeScreen();
			realmsbridge.switchToRealms(new MainMenuScreen());
		}
		else if(connection instanceof IntegratedConnection)
		{
			IntegratedConnection integrated = (IntegratedConnection) connection;
			Minecraft.getInstance().createLevel(integrated.getFolder(), integrated.getWorldSettings(), DynamicRegistries.builtin(), integrated.getDimensionGeneratorSettings());
			Minecraft.getInstance().mouseHandler.grabMouse();
		}
		else if(connection instanceof DedicatedConnection)
		{
			DedicatedConnection dedicated = (DedicatedConnection) connection;
			Minecraft.getInstance().setScreen(new ConnectingScreen(new MainMenuScreen(), Minecraft.getInstance(), dedicated.getData()));
			Minecraft.getInstance().mouseHandler.grabMouse();
		}
	}
	
	@Override
	public IFormattableTextComponent getTitle()
	{
		return new TranslationTextComponent("gui.worldhandler.title.change_world");
	}
}
