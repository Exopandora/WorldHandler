package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.types.Type;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
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
		if(difficulty != null)
		{
			this.setNode(0, difficulty.toString());
		}
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
	
	@Deprecated
	@OnlyIn(Dist.CLIENT)
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
