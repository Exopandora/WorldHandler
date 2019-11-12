package exopandora.worldhandler.gui.content.impl;

import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.util.ScoreboardHelper;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ContentScoreboard extends Content
{
	protected static final ScoreboardHelper HELPER = new ScoreboardHelper();
	private static String objective;
	
	protected static boolean isObjectiveValid()
	{
		return ContentScoreboard.objective != null && ContentScoreboard.objective.length() > 0;
	}
	
	protected static void setObjective(String objective)
	{
		ContentScoreboard.objective = objective;
	}
	
	protected static String getObjective()
	{
		return ContentScoreboard.objective;
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.SCOREBOARD;
	}
	
	@Override
	public String getTitle()
	{
		return I18n.format("gui.worldhandler.title.scoreboard");
	}
}
