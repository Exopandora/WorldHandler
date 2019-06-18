package exopandora.worldhandler.builder;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
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
			this.command.append(" " + TextFormatting.RED + "[error]" + TextFormatting.RESET);
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
