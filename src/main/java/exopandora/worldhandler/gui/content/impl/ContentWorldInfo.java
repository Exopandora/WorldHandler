package exopandora.worldhandler.gui.content.impl;

import java.util.function.Function;

import exopandora.worldhandler.format.TextFormatting;
import exopandora.worldhandler.gui.button.GuiButtonWorldHandler;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ContentWorldInfo extends Content
{
	private String selectedMain = "start";
	
	private GuiTextFieldTooltip posXField;
	private GuiTextFieldTooltip posYField;
	private GuiTextFieldTooltip posZField;
	
	private GuiTextFieldTooltip worldField;
	private GuiTextFieldTooltip seedField;
	private GuiTextFieldTooltip terrainField;
	
	private GuiTextFieldTooltip totalTimeField;
	private GuiTextFieldTooltip currentTimeField;
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		World world = Minecraft.getMinecraft().world;
		
		if(Minecraft.getMinecraft().getIntegratedServer() != null)
		{
			world = Minecraft.getMinecraft().getIntegratedServer().getEntityWorld();
		}
		
		this.posXField = new GuiTextFieldTooltip(x + 118, y + 12, 114, 20);
		this.posXField.setText(I18n.format("gui.worldhandler.world_info.start.spawn") + " X: " + this.getWorldInfo(WorldInfo::getSpawnX, world));
		
		this.posYField = new GuiTextFieldTooltip(x + 118, y + 36, 114, 20);
		this.posYField.setText(I18n.format("gui.worldhandler.world_info.start.spawn") + " Y: " + this.getWorldInfo(WorldInfo::getSpawnY, world));
		
		this.posZField = new GuiTextFieldTooltip(x + 118, y + 60, 114, 20);
		this.posZField.setText(I18n.format("gui.worldhandler.world_info.start.spawn") + " Z: " + this.getWorldInfo(WorldInfo::getSpawnZ, world));
		
		this.worldField = new GuiTextFieldTooltip(x + 118, y + 12, 114, 20);
		this.worldField.setText(I18n.format("gui.worldhandler.world_info.world.name") + ": " + this.getWorldInfo(WorldInfo::getWorldName, world));
		
		this.terrainField = new GuiTextFieldTooltip(x + 118, y + 36, 114, 20);
		this.terrainField.setText(I18n.format("gui.worldhandler.world_info.world.world_type") + ": " + this.getWorldInfo(info -> I18n.format(info.getTerrainType().getTranslationKey()), world));
		
		this.seedField = new GuiTextFieldTooltip(x + 118, y + 60, 114, 20);
		this.seedField.setText(I18n.format("gui.worldhandler.world_info.world.seed") + ": " + (Minecraft.getMinecraft().getIntegratedServer() != null ? world.getWorldInfo().getSeed() : I18n.format("gui.worldhandler.world_info.n_a")));
		this.seedField.setValidator(string -> string.equals(this.seedField.getText()));
		this.seedField.setCursorPositionZero();
		this.updateCursorPosition();
		
		this.currentTimeField = new GuiTextFieldTooltip(x + 118, y + 24, 114, 20);
		this.updateCurrentTime();
		
		this.totalTimeField = new GuiTextFieldTooltip(x + 118, y + 48, 114, 20);
		this.updateTotalTime();
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonWorldHandler start;
		GuiButtonWorldHandler world;
		GuiButtonWorldHandler stats;
		
		container.add(new GuiButtonWorldHandler(0, x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back")));
		container.add(new GuiButtonWorldHandler(1, x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame")));
		
		container.add(start = new GuiButtonWorldHandler(2, x, y + 12, 114, 20, I18n.format("gui.worldhandler.world_info.start")));
		container.add(world = new GuiButtonWorldHandler(3, x, y + 36, 114, 20, I18n.format("gui.worldhandler.world_info.world")));
		container.add(stats = new GuiButtonWorldHandler(4, x, y + 60, 114, 20, I18n.format("gui.worldhandler.world_info.statistics")));
		
		if(this.selectedMain.equals("start"))
		{
			start.enabled = false;
		}
		else if(this.selectedMain.equals("world"))
		{
			world.enabled = false;
		}
		else if(this.selectedMain.equals("stats"))
		{
			stats.enabled = false;
		}
	}
	
	@Override
	public void updateScreen(Container container)
	{
		this.updateCurrentTime();
		this.updateTotalTime();
	}
	
	@Override
	public void actionPerformed(Container container, GuiButton button) throws Exception
	{
		switch(button.id)
		{
			case 2:
				this.selectedMain = "start";
				container.initGui();
				break;
			case 3:
				this.selectedMain = "world";
				container.initGui();
				break;
			case 4:
				this.selectedMain = "stats";
				container.initGui();
				break;
			default:
				break;
		}
	}
	
	@Override
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(this.selectedMain.equals("start"))
		{
			this.posXField.drawTextBox();
			this.posYField.drawTextBox();
			this.posZField.drawTextBox();
		}
		else if(this.selectedMain.equals("world"))
		{
			this.worldField.drawTextBox();
			this.terrainField.drawTextBox();
			this.seedField.drawTextBox();
		}
		else if(this.selectedMain.equals("stats"))
		{
			this.totalTimeField.drawTextBox();
			this.currentTimeField.drawTextBox();
		}
	}
	
	private void updateCurrentTime()
	{
		this.currentTimeField.setText(I18n.format("gui.worldhandler.world_info.statistics.world_time") + ": " + TextFormatting.getWorldTime(Minecraft.getMinecraft().world.getWorldInfo().getWorldTime()));
	}
	
	private void updateTotalTime()
	{
		this.totalTimeField.setText(I18n.format("gui.worldhandler.world_info.statistics.played") + ": " + TextFormatting.getTotalTimePlayed(Minecraft.getMinecraft().world.getWorldInfo().getWorldTotalTime()));
	}
	
	@Override
	public void keyTyped(Container container, char typedChar, int keyCode)
	{
		if(this.selectedMain.equals("world"))
		{
			this.seedField.textboxKeyTyped(typedChar, keyCode);
			this.updateCursorPosition();
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		if(this.selectedMain.equals("world"))
		{
			this.seedField.mouseClicked(mouseX, mouseY, mouseButton);
			this.updateCursorPosition();
		}
	}
	
	private <T> String getWorldInfo(Function<WorldInfo, T> function, World world)
	{
		if(world != null)
		{
			return String.valueOf(function.apply(world.getWorldInfo()));
		}
		
		return I18n.format("gui.worldhandler.world_info.n_a");
	}
	
	private void updateCursorPosition()
	{
		int length = I18n.format("gui.worldhandler.world_info.start.spawn").length();
		
		if(this.seedField.getCursorPosition() < length + 2)
		{
			this.seedField.setCursorPosition(length + 1);
		}
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.WORLD;
	}
	
	@Override
	public String getTitle()
	{
		return I18n.format("gui.worldhandler.title.world.world");
	}
	
	@Override
	public String getTabTitle()
	{
		return I18n.format("gui.worldhandler.tab.world.world");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.WORLD_INFO;
	}
}
