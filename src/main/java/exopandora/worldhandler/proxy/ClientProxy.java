package exopandora.worldhandler.proxy;

import exopandora.worldhandler.event.ClientEventHandler;
import exopandora.worldhandler.event.KeyHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy
{
	@Override
	public void setup()
	{
		MinecraftForge.EVENT_BUS.addListener(KeyHandler::keyInputEvent);
		MinecraftForge.EVENT_BUS.addListener(ClientEventHandler::renderWorldLastEvent);
		MinecraftForge.EVENT_BUS.addListener(ClientEventHandler::clientChatEvent);
	}
}
