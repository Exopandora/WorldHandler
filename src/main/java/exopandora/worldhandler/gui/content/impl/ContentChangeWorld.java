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
			Minecraft.getInstance().displayGuiScreen(new WorldSelectionScreen(new DummyScreen(() -> ContentChangeWorld.reconnect(connection))));
		}));
		
		container.add(new GuiButtonBase(x + 116 / 2, y + 48, 232 / 2, 20, new TranslationTextComponent("gui.worldhandler.change_world.multiplayer"), () ->
		{
			IConnection connection = ContentChangeWorld.disconnect();
			DummyScreen dummy = new DummyScreen(() -> ContentChangeWorld.reconnect(connection));
			
			if(Minecraft.getInstance().gameSettings.skipMultiplayerWarning)
			{
				Minecraft.getInstance().displayGuiScreen(new MultiplayerScreen(dummy));
			}
			else
			{
				Minecraft.getInstance().displayGuiScreen(new MultiplayerWarningScreen(dummy));
			}
		}));
	}
	
	private static IConnection disconnect()
	{
		boolean isIntegrated = Minecraft.getInstance().isIntegratedServerRunning();
		boolean isRealms = Minecraft.getInstance().isConnectedToRealms();
		ServerData data = Minecraft.getInstance().getCurrentServerData();
		
		if(isIntegrated)
		{
			IntegratedServer integrated = Minecraft.getInstance().getIntegratedServer();
			String folder = integrated.anvilConverterForAnvilFile.getSaveName();
			DimensionGeneratorSettings dimensionGeneratorSettings = integrated.getServerConfiguration().getDimensionGeneratorSettings();
			WorldSettings worldSettings = integrated.getServerConfiguration().getWorldSettings();
			
			Minecraft.getInstance().world.sendQuittingDisconnectingPacket();
			Minecraft.getInstance().unloadWorld(new DirtMessageScreen(new TranslationTextComponent("menu.savingLevel")));
			
			return new IntegratedConnection(folder, worldSettings, dimensionGeneratorSettings);
		}
		
		if(Minecraft.getInstance().world != null)
		{
			Minecraft.getInstance().world.sendQuittingDisconnectingPacket();
			Minecraft.getInstance().unloadWorld();
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
			realmsbridge.func_231394_a_(new MainMenuScreen());
		}
		else if(connection instanceof IntegratedConnection)
		{
			IntegratedConnection integrated = (IntegratedConnection) connection;
			Minecraft.getInstance().createWorld(integrated.getFolder(), integrated.getWorldSettings(), DynamicRegistries.func_239770_b_(), integrated.getDimensionGeneratorSettings());
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
	public IFormattableTextComponent getTitle()
	{
		return new TranslationTextComponent("gui.worldhandler.title.change_world");
	}
}
