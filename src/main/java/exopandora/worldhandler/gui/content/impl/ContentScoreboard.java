package exopandora.worldhandler.gui.content.impl;

import exopandora.worldhandler.builder.impl.ScoreboardCommandBuilder;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.util.ScoreboardHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public abstract class ContentScoreboard extends Content
{
	protected static final ScoreboardHelper HELPER = new ScoreboardHelper();
	protected static final ScoreboardCommandBuilder BUILDER = new ScoreboardCommandBuilder();
	
	static
	{
		BUILDER.score().set(0);
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.SCOREBOARD;
	}
	
	@Override
	public MutableComponent getTitle()
	{
		return Component.translatable("gui.worldhandler.title.scoreboard");
	}
}
