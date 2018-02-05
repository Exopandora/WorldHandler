package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.ICommandBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BuilderGeneric implements ICommandBuilder
{
	private final String command;
	private final String[] arguments;
	
	public BuilderGeneric(String command, String... arguments)
	{
		this.command = command;
		this.arguments = arguments;
	}
	
	@Override
	public String toCommand()
	{
		return "/" + this.command + " " + String.join(" ", this.arguments);
	}
	
	public String toActualCommand()
	{
		return this.toCommand();
	}
}
