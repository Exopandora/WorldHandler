package exopandora.worldhandler.gui;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DummyScreen extends Screen
{
	private final Runnable runnable;
	
	public DummyScreen(Runnable runnable)
	{
		super(TextComponent.EMPTY);
		this.runnable = runnable;
	}
	
	@Override
	protected void init()
	{
		this.runnable.run();
	}
}
