package exopandora.worldhandler.command;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FakeCommandHandler extends CommandHandler
{
	@Override
	protected MinecraftServer getServer()
	{
		return null;
	}
	
	private void fakeCommand(ICommand command, ClientChatEvent event)
	{
		Minecraft.getMinecraft().ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
		this.tryExecute(Minecraft.getMinecraft().player, dropFirstString(event.getMessage().split(" ")), command, event.getMessage());
		
		if(event != null && event.isCancelable())
		{
			event.setCanceled(true);
		}
	}
	
	private static String[] dropFirstString(String[] input)
	{
		String[] string = new String[input.length - 1];
		System.arraycopy(input, 1, string, 0, input.length - 1);
		return string;
	}
	
	public void tryCommand(ICommand command, ClientChatEvent event)
	{
		if(event.getMessage().startsWith("/" + command.getName()) || event.getMessage().startsWith("/" + command.getName() + " "))
		{
			this.fakeCommand(command, event);
		}
	}
}
