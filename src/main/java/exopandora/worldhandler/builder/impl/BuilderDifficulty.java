package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.types.ArgumentType;
import net.minecraft.world.Difficulty;

public class BuilderDifficulty extends CommandBuilder
{
	public BuilderDifficulty()
	{
		super();
	}
	
	public BuilderDifficulty(Difficulty difficulty)
	{
		this.setDifficulty(difficulty);
	}
	
	public void setDifficulty(Difficulty difficulty)
	{
		if(difficulty != null)
		{
			this.setNode(0, difficulty.getKey());
		}
	}
	
	@Override
	public String getCommandName()
	{
		return "difficulty";
	}
	
	@Override
	public final CommandSyntax getSyntax()
	{
		CommandSyntax syntax = new CommandSyntax();
		
		syntax.addRequired("peaceful|easy|normal|hard", ArgumentType.STRING);
		
		return syntax;
	}
}
