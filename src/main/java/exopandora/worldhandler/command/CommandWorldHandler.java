package exopandora.worldhandler.command;

import java.util.Collections;
import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.builder.impl.BuilderWorldHandler;
import exopandora.worldhandler.event.EventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.versioning.ComparableVersion;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CommandWorldHandler extends CommandBase
{
	@Override
	public String getName()
	{
		return "worldhandler";
	}
	
	@Override
	public int getRequiredPermissionLevel()
	{
		return 0;
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if(args.length > 0)
		{
			if(args[0].equalsIgnoreCase("help"))
			{
				this.printHelp(sender);
			}
			else if(args[0].equalsIgnoreCase("display"))
			{
				new Thread(() -> Minecraft.getMinecraft().addScheduledTask(EventHandler::displayGui)).start();
			}
			else if(args[0].equalsIgnoreCase("version"))
			{
				sender.sendMessage(new TextComponentString("Installed: $mcversion-$version"));
				ComparableVersion target = ForgeVersion.getResult(Loader.instance().getIndexedModList().get(Main.MODID)).target;
				sender.sendMessage(new TextComponentString("Latest: $mcversion-" + (target != null ? target : "$version")));
			}
			else
			{
				throw new WrongUsageException(this.getUsage(sender));
			}
		}
		else if(args.length == 0)
		{
			this.printHelp(sender);
		}
		else
		{
			throw new WrongUsageException(this.getUsage(sender));
		}
	}
	
	private void printHelp(ICommandSender player)
	{
		player.sendMessage(new TextComponentString(ChatFormatting.DARK_GREEN + "--- Showing help page 1 of 1 (/worldhandler help) ---"));
		player.sendMessage(new TextComponentString("/worldhandler help"));
		player.sendMessage(new TextComponentString("/worldhandler display"));
		player.sendMessage(new TextComponentString("/worldhandler version"));
		player.sendMessage(new TextComponentString(ChatFormatting.GREEN + "Tip: Press '" + WorldHandler.KEY_WORLD_HANDLER.getDisplayName() + "' to open the World Handler"));
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
	{
		if(args.length == 1)
		{
			return this.getListOfStringsMatchingLastWord(args, new String[]{"help", "display", "version"});
		}
		
		return Collections.<String>emptyList();
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return new BuilderWorldHandler().toCommand();
	}
}