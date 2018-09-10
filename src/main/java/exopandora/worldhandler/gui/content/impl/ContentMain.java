package exopandora.worldhandler.gui.content.impl;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.config.ConfigSettings;
import exopandora.worldhandler.gui.button.EnumIcon;
import exopandora.worldhandler.gui.button.EnumTooltip;
import exopandora.worldhandler.gui.button.GuiButtonWorldHandler;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.config.GuiConfigWorldHandler;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.container.impl.GuiWorldHandlerContainer;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ContentMain extends Content
{
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(new GuiButtonWorldHandler(-1, x, y, 22, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.time", I18n.format("gui.worldhandler.shortcuts.tooltip.time.dawn", ConfigSettings.getDawn())), EnumTooltip.TOP_RIGHT, EnumIcon.TIME_DAWN));
		container.add(new GuiButtonWorldHandler(-2, x + 26, y, 22, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.time", I18n.format("gui.worldhandler.shortcuts.tooltip.time.noon", ConfigSettings.getNoon())), EnumTooltip.TOP_RIGHT, EnumIcon.TIME_NOON));
		container.add(new GuiButtonWorldHandler(-3, x + 26 * 2, y, 22, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.time", I18n.format("gui.worldhandler.shortcuts.tooltip.time.sunset", ConfigSettings.getSunset())), EnumTooltip.TOP_RIGHT, EnumIcon.TIME_SUNSET));
		container.add(new GuiButtonWorldHandler(-4, x + 26 * 3, y, 23, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.time", I18n.format("gui.worldhandler.shortcuts.tooltip.time.midnight", ConfigSettings.getMidnight())), EnumTooltip.TOP_RIGHT, EnumIcon.TIME_MIDNIGHT));
		container.add(new GuiButtonWorldHandler(-8, x + 26 * 4, y, 24, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty", I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty.peaceful")), EnumTooltip.TOP_RIGHT, EnumIcon.DIFFICULTY_PEACEFUL));
		container.add(new GuiButtonWorldHandler(-9, x + 26 * 5 + 2, y, 23, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty", I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty.easy")), EnumTooltip.TOP_RIGHT, EnumIcon.DIFFICULTY_EASY));
		container.add(new GuiButtonWorldHandler(-10, x + 26 * 6 + 2, y, 22, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty", I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty.normal")), EnumTooltip.TOP_RIGHT, EnumIcon.DIFFICULTY_NORMAL));
		container.add(new GuiButtonWorldHandler(-11, x + 26 * 7 + 2, y, 22, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty", I18n.format("gui.worldhandler.shortcuts.tooltip.difficulty.hard")), EnumTooltip.TOP_RIGHT, EnumIcon.DIFFICULTY_HARD));
		container.add(new GuiButtonWorldHandler(10, x + 26 * 8 + 2, y, 22, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.settings"), EnumTooltip.TOP_RIGHT, EnumIcon.SETTINGS));
		
		container.add(new GuiButtonWorldHandler(-5, x, y + 24, 22, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.weather", I18n.format("gui.worldhandler.shortcuts.tooltip.weather.clear")), EnumTooltip.TOP_RIGHT, EnumIcon.WEATHER_SUN));
		container.add(new GuiButtonWorldHandler(-6, x + 26, y + 24, 22, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.weather", I18n.format("gui.worldhandler.shortcuts.tooltip.weather.rainy")), EnumTooltip.TOP_RIGHT, EnumIcon.WEATHER_RAIN));
		container.add(new GuiButtonWorldHandler(-7, x + 26 * 2, y + 24, 22, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.weather", I18n.format("gui.worldhandler.shortcuts.tooltip.weather.thunder")), EnumTooltip.TOP_RIGHT, EnumIcon.WEATHER_STORM));
		container.add(new GuiButtonWorldHandler(11, x + 26 * 3, y + 24, 23, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.butcher"), EnumTooltip.TOP_RIGHT, EnumIcon.BUTCHER));
		container.add(new GuiButtonWorldHandler(12, x + 26 * 4, y + 24, 24, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.potions"), EnumTooltip.TOP_RIGHT, EnumIcon.POTION));
		container.add(new GuiButtonWorldHandler(-12, x + 26 * 5 + 2, y + 24, 23, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode", I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode.survival")), EnumTooltip.TOP_RIGHT, EnumIcon.GAMEMODE_SURVIVAL));
		container.add(new GuiButtonWorldHandler(-13, x + 26 * 6 + 2, y + 24, 22, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode", I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode.creative")), EnumTooltip.TOP_RIGHT, EnumIcon.GAMEMODE_CREATIVE));
		container.add(new GuiButtonWorldHandler(-14, x + 26 * 7 + 2, y + 24, 22, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode", I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode.adventure")), EnumTooltip.TOP_RIGHT, EnumIcon.GAMEMODE_ADVENTURE));
		container.add(new GuiButtonWorldHandler(-15, x + 26 * 8 + 2, y + 24, 22, 20, null, I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode", I18n.format("gui.worldhandler.shortcuts.tooltip.gamemode.spectator")), EnumTooltip.TOP_RIGHT, EnumIcon.GAMEMODE_SPECTATOR));		
		
		container.add(new GuiButtonWorldHandler(2, x, y + 48, 74, 20, I18n.format("gui.worldhandler.items")));
		container.add(new GuiButtonWorldHandler(3, x + 78, y + 48, 76, 20, I18n.format("gui.worldhandler.blocks")));
		container.add(new GuiButtonWorldHandler(9, x + 158, y + 48, 74, 20, I18n.format("gui.worldhandler.entities")));
		
		container.add(new GuiButtonWorldHandler(7, x, y + 72, 74, 20, I18n.format("gui.worldhandler.world")));
		container.add(new GuiButtonWorldHandler(8, x + 78, y + 72, 76, 20, I18n.format("gui.worldhandler.player")));
		container.add(new GuiButtonWorldHandler(4, x + 158, y + 72, 74, 20, I18n.format("gui.worldhandler.scoreboard")));
		
		container.add(new GuiButtonWorldHandler(6, x, y + 96, 74, 20, I18n.format("gui.worldhandler.change_world")));
		container.add(new GuiButtonWorldHandler(5, x + 78, y + 96, 76, 20, I18n.format("gui.worldhandler.resourcepack")));
		container.add(new GuiButtonWorldHandler(1, x + 158, y + 96, 74, 20, I18n.format("gui.worldhandler.generic.backToGame")));
	}
	
	@Override
	public void actionPerformed(Container container, GuiButton button) throws Exception
	{
		switch(button.id)
		{
			case 2:
				Minecraft.getMinecraft().displayGuiScreen(new GuiWorldHandlerContainer(Contents.CUSTOM_ITEM));
				break;
			case 3:
				Minecraft.getMinecraft().displayGuiScreen(new GuiWorldHandlerContainer(Contents.EDIT_BLOCKS));
				break;
			case 4:
				Minecraft.getMinecraft().displayGuiScreen(new GuiWorldHandlerContainer(Contents.SCOREBOARD_OBJECTIVES));
				break;
			case 5:
				Minecraft.getMinecraft().gameSettings.saveOptions();
				Minecraft.getMinecraft().displayGuiScreen(new GuiScreenResourcePacks(container));
				break;
			case 6:
				Minecraft.getMinecraft().displayGuiScreen(new GuiWorldHandlerContainer(Contents.CHANGE_WORLD.withParent(Contents.MAIN)));
				break;
			case 7:
				Minecraft.getMinecraft().displayGuiScreen(new GuiWorldHandlerContainer(Contents.WORLD_INFO));
				break;
			case 8:
				Minecraft.getMinecraft().displayGuiScreen(new GuiWorldHandlerContainer(Contents.PLAYER));
				break;
			case 9:
				Minecraft.getMinecraft().displayGuiScreen(new GuiWorldHandlerContainer(Contents.SUMMON));
				break;
			case 10:
				Minecraft.getMinecraft().displayGuiScreen(new GuiConfigWorldHandler(container, ConfigSettings.CATEGORY));
				break;
			case 11:
				Minecraft.getMinecraft().displayGuiScreen(new GuiWorldHandlerContainer(Contents.BUTCHER.withParent(Contents.MAIN)));
				break;
			case 12:
				Minecraft.getMinecraft().displayGuiScreen(new GuiWorldHandlerContainer(Contents.POTIONS.withParent(Contents.MAIN)));
				break;
			default:
				break;
		}
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.MAIN;
	}
	
	@Override
	public String getTitle()
	{
		return Main.NAME;
	}
	
	@Override
	public String getTabTitle()
	{
		return Main.NAME;
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.MAIN;
	}

	@Override
	public Content getBackContent()
	{
		return null;
	}
}
