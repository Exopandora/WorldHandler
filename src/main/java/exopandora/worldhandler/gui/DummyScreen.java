package exopandora.worldhandler.gui;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class DummyScreen extends Screen
{
	private final Runnable runnable;
	
	public DummyScreen(Runnable runnable)
	{
		super(Component.empty());
		this.runnable = runnable;
	}
	
	@Override
	protected void init()
	{
		this.runnable.run();
	}
}
