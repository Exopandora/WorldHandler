package exopandora.worldhandler.gui.content.impl;

import java.util.function.Function;

import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.helper.ActionHelper;
import exopandora.worldhandler.util.TextFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
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
		World world = this.getWorld();
		
		this.posXField = new GuiTextFieldTooltip(x + 118, y + 12, 114, 20);
		this.posXField.setText(I18n.format("gui.worldhandler.world_info.start.spawn") + " X: " + this.getWorldInfo(WorldInfo::getSpawnX, world));
		
		this.posYField = new GuiTextFieldTooltip(x + 118, y + 36, 114, 20);
		this.posYField.setText(I18n.format("gui.worldhandler.world_info.start.spawn") + " Y: " + this.getWorldInfo(WorldInfo::getSpawnY, world));
		
		this.posZField = new GuiTextFieldTooltip(x + 118, y + 60, 114, 20);
		this.posZField.setText(I18n.format("gui.worldhandler.world_info.start.spawn") + " Z: " + this.getWorldInfo(WorldInfo::getSpawnZ, world));
		
		this.worldField = new GuiTextFieldTooltip(x + 118, y, 114, 20);
		this.worldField.setText(I18n.format("gui.worldhandler.world_info.world.name") + ": " + this.getWorldInfo(WorldInfo::getWorldName, world));
		
		this.terrainField = new GuiTextFieldTooltip(x + 118, y + 24, 114, 20);
		this.terrainField.setText(I18n.format("gui.worldhandler.world_info.world.world_type") + ": " + this.getWorldInfo(info -> I18n.format(info.getGenerator().getTranslationKey()), world));
		
		this.seedField = new GuiTextFieldTooltip(x + 118, y + 48, 114, 20);
		this.seedField.setText(I18n.format("gui.worldhandler.world_info.world.seed") + ": " + this.getSeed(world));
		this.seedField.setValidator(string -> string.equals(this.seedField.getText()));
		this.seedField.setCursorPositionZero();
		
		this.currentTimeField = new GuiTextFieldTooltip(x + 118, y + 24, 114, 20);
		this.updateCurrentTime();
		
		this.totalTimeField = new GuiTextFieldTooltip(x + 118, y + 48, 114, 20);
		this.updateTotalTime();
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonBase start;
		GuiButtonBase world;
		GuiButtonBase stats;
		
		container.add(new GuiButtonBase(x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(start = new GuiButtonBase(x, y + 12, 114, 20, I18n.format("gui.worldhandler.world_info.start"), () ->
		{
			this.selectedMain = "start";
			container.init();
		}));
		container.add(world = new GuiButtonBase(x, y + 36, 114, 20, I18n.format("gui.worldhandler.world_info.world"), () ->
		{
			this.selectedMain = "world";
			container.init();
		}));
		container.add(stats = new GuiButtonBase(x, y + 60, 114, 20, I18n.format("gui.worldhandler.world_info.statistics"), () ->
		{
			this.selectedMain = "stats";
			container.init();
		}));
		
		if(this.selectedMain.equals("start"))
		{
			start.active = false;
		}
		else if(this.selectedMain.equals("world"))
		{
			GuiButtonBase seed;
			
			world.active = false;
			container.add(seed = new GuiButtonBase(x + 118, y + 72, 114, 20, I18n.format("gui.worldhandler.world_info.world.copy_seed"), () ->
			{
				Minecraft.getInstance().keyboardListener.setClipboardString(this.getSeed(this.getWorld()));
			}));
			
			seed.active = Minecraft.getInstance().getIntegratedServer() != null;
		}
		else if(this.selectedMain.equals("stats"))
		{
			stats.active = false;
		}
	}
	
	@Override
	public void tick(Container container)
	{
		this.updateCurrentTime();
		this.updateTotalTime();
		this.seedField.tick();
	}
	
	@Override
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(this.selectedMain.equals("start"))
		{
			this.posXField.renderButton(mouseX, mouseY, partialTicks);
			this.posYField.renderButton(mouseX, mouseY, partialTicks);
			this.posZField.renderButton(mouseX, mouseY, partialTicks);
		}
		else if(this.selectedMain.equals("world"))
		{
			this.worldField.renderButton(mouseX, mouseY, partialTicks);
			this.terrainField.renderButton(mouseX, mouseY, partialTicks);
			this.seedField.renderButton(mouseX, mouseY, partialTicks);
		}
		else if(this.selectedMain.equals("stats"))
		{
			this.totalTimeField.renderButton(mouseX, mouseY, partialTicks);
			this.currentTimeField.renderButton(mouseX, mouseY, partialTicks);
		}
	}
	
	private void updateCurrentTime()
	{
		this.currentTimeField.setText(I18n.format("gui.worldhandler.world_info.statistics.world_time") + ": " + TextFormatting.formatWorldTime(Minecraft.getInstance().world.getWorldInfo().getDayTime()));
	}
	
	private void updateTotalTime()
	{
		this.totalTimeField.setText(I18n.format("gui.worldhandler.world_info.statistics.played") + ": " + TextFormatting.getTotalTimePlayed(Minecraft.getInstance().world.getWorldInfo().getGameTime()));
	}
	
	private <T> String getWorldInfo(Function<WorldInfo, T> function, World world)
	{
		if(world != null)
		{
			return String.valueOf(function.apply(world.getWorldInfo()));
		}
		
		return I18n.format("gui.worldhandler.world_info.n_a");
	}
	
	private World getWorld()
	{
		if(Minecraft.getInstance().getIntegratedServer() != null)
		{
			return Minecraft.getInstance().getIntegratedServer().getWorld(DimensionType.OVERWORLD);
		}
		
		return Minecraft.getInstance().world;
	}
	
	private String getSeed(World world)
	{
		return Minecraft.getInstance().getIntegratedServer() != null ? String.valueOf(world.getWorldInfo().getSeed()) : I18n.format("gui.worldhandler.world_info.n_a");
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
