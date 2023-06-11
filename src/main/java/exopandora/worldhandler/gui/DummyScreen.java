package exopandora.worldhandler.gui;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class DummyScreen extends Screen
{
	private final Runnable onInit;
	
	public DummyScreen(Runnable onInit)
	{
		super(Component.empty());
		this.onInit = onInit;
	}
	
	@Override
	protected void init()
	{
		this.onInit.run();
	}
}
