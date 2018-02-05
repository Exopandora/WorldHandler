package exopandora.worldhandler.builder.impl;

import java.util.Arrays;

import exopandora.worldhandler.builder.ICommandBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BuilderMultiCommand implements ICommandBuilder
{
	private final ICommandBuilder[] builders;
	
	public BuilderMultiCommand(ICommandBuilder... builder)
	{
		this.builders = builder;
	}
	
	@Override
	public String toCommand()
	{
		return String.join(" | ", Arrays.stream(this.builders).map(ICommandBuilder::toCommand).toArray(String[]::new));
	}
}
