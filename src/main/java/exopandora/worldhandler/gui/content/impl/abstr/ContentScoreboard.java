package exopandora.worldhandler.gui.content.impl.abstr;

import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.helper.ScoreboardHelper;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class ContentScoreboard extends Content
{
	protected static final ScoreboardHelper HELPER = new ScoreboardHelper();
	protected static String objective;
	
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
	
	@Override
	public String[] getHeadline()
	{
		return new String[] {I18n.format("gui.worldhandler.generic.browse"), I18n.format("gui.worldhandler.generic.options")};
	}
}
