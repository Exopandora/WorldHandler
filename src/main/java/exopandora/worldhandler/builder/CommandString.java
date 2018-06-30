package exopandora.worldhandler.builder;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CommandString
{
	private final StringBuilder command = new StringBuilder("/");
	
	public CommandString(String name)
	{
		this.command.append(name);
	}
	
	public CommandString(String name, String... arguments)
	{
		this(name);
		this.append(arguments);
	}
	
	public void append(String argument)
	{
		if(argument != null && !argument.isEmpty())
		{
			this.command.append(" " + argument);
		}
		else
		{
			this.command.append(" " + ChatFormatting.RED + "[error]" + ChatFormatting.RESET);
		}
	}
	
	public void append(String... arguments)
	{
		if(arguments != null)
		{
			for(String argument : arguments)
			{
				this.append(argument);
			}
		}
	}
	
	@Override
	public String toString()
	{
		return this.command.toString();
	}
}
