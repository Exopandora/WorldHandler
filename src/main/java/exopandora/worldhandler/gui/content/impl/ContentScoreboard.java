package exopandora.worldhandler.gui.content.impl;

import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.util.ScoreboardHelper;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;

public abstract class ContentScoreboard extends Content
{
	protected static final ScoreboardHelper HELPER = new ScoreboardHelper();
	private static String objective;
	
	protected boolean isObjectiveValid()
	{
		return ContentScoreboard.objective != null && ContentScoreboard.objective.length() > 0;
	}
	
	protected void setObjective(String objective)
	{
		ContentScoreboard.objective = objective;
	}
	
	protected String getObjective()
	{
		return ContentScoreboard.objective;
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.SCOREBOARD;
	}
	
	@Override
	public MutableComponent getTitle()
	{
		return new TranslatableComponent("gui.worldhandler.title.scoreboard");
	}
}
