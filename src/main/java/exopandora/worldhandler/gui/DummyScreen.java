package exopandora.worldhandler.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DummyScreen extends Screen
{
	private final Runnable runnable;
	
	public DummyScreen(Runnable runnable)
	{
		super(new StringTextComponent(""));
		this.runnable = runnable;
	}
	
	@Override
	protected void func_231160_c_()
	{
		this.runnable.run();
	}
}
