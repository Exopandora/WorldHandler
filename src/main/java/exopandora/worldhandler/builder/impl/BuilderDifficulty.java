package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.types.Type;

public class BuilderDifficulty extends CommandBuilder
{
	public BuilderDifficulty()
	{
		
	}
	
	public BuilderDifficulty(EnumDifficulty difficulty)
	{
		this.setDifficulty(difficulty);
	}
	
	public void setDifficulty(EnumDifficulty difficulty)
	{
		this.setNode(0, difficulty.toString());
	}
	
	@Override
	public String getCommandName()
	{
		return "difficulty";
	}

	@Override
	public final Syntax getSyntax()
	{
		Syntax syntax = new Syntax();
		
		syntax.addRequired("peaceful|easy|normal|hard", Type.STRING);
		
		return syntax;
	}
	
	public static enum EnumDifficulty
	{
		PEACEFUL,
		EASY,
		NORMAL,
		HARD;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
}
