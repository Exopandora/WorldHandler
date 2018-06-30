package exopandora.worldhandler.gui.config;

import java.util.ArrayList;
import java.util.List;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.config.ConfigButcher;
import exopandora.worldhandler.config.ConfigSettings;
import exopandora.worldhandler.config.ConfigSkin;
import exopandora.worldhandler.config.ConfigSliders;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiConfigWorldHandler extends GuiConfig
{
	public GuiConfigWorldHandler(GuiScreen parentScreen, List<IConfigElement> configElements)
	{
		super(parentScreen, configElements, Main.MODID, false, false, Main.NAME);
	}
	
	public GuiConfigWorldHandler(GuiScreen parentScreen, ConfigCategory category)
	{
		this(parentScreen, new ConfigElement(category).getChildElements());
	}
	
	public GuiConfigWorldHandler(GuiScreen parentScreen, String category)
	{
		this(parentScreen, WorldHandler.CONFIG.getCategory(category));
	}
	
	public GuiConfigWorldHandler(GuiScreen parentScreen)
	{
		this(parentScreen, getConfigElements());
	}
	
	private static List<IConfigElement> getConfigElements()
	{
		List<IConfigElement> list = new ArrayList();
    	
		list.add(new DummyCategoryElement(I18n.format("gui.worldhandler.config.category.settings"), "gui.worldhandler.config", new ConfigElement(WorldHandler.CONFIG.getCategory(ConfigSettings.CATEGORY)).getChildElements()));
		list.add(new DummyCategoryElement(I18n.format("gui.worldhandler.config.category.skin"), "gui.worldhandler.config", new ConfigElement(WorldHandler.CONFIG.getCategory(ConfigSkin.CATEGORY)).getChildElements()));
		list.add(new DummyCategoryElement(I18n.format("gui.worldhandler.config.category.butcher"), "gui.worldhandler.config", new ConfigElement(WorldHandler.CONFIG.getCategory(ConfigButcher.CATEGORY)).getChildElements()));
		list.add(new DummyCategoryElement(I18n.format("gui.worldhandler.config.category.sliders"), "gui.worldhandler.config", new ConfigElement(WorldHandler.CONFIG.getCategory(ConfigSliders.CATEGORY)).getChildElements()));
		
		return list;
	}
}
