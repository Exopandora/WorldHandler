package exopandora.worldhandler.gui.content.impl;

import java.util.function.Function;

import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiHintTextField;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.Level;

public class ContentWorldInfo extends Content
{
	private Page page = Page.START;
	
	private GuiHintTextField posXField;
	private GuiHintTextField posYField;
	private GuiHintTextField posZField;
	
	private GuiHintTextField worldField;
	private GuiHintTextField seedField;
	
	private GuiHintTextField totalTimeField;
	private GuiHintTextField currentTimeField;
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		Level level = ContentWorldInfo.getSidedWorld();
		IntegratedServer server = Minecraft.getInstance().getSingleplayerServer();
		
		this.posXField = new GuiHintTextField(x + 118, y + 12, 114, 20);
		this.posXField.setValue(I18n.get("gui.worldhandler.world_info.start.spawn") + " X: " + ContentWorldInfo.format(level, object -> object.getLevelData().getXSpawn()));
		
		this.posYField = new GuiHintTextField(x + 118, y + 36, 114, 20);
		this.posYField.setValue(I18n.get("gui.worldhandler.world_info.start.spawn") + " Y: " + ContentWorldInfo.format(level, object -> object.getLevelData().getYSpawn()));
		
		this.posZField = new GuiHintTextField(x + 118, y + 60, 114, 20);
		this.posZField.setValue(I18n.get("gui.worldhandler.world_info.start.spawn") + " Z: " + ContentWorldInfo.format(level, object -> object.getLevelData().getZSpawn()));
		
		this.worldField = new GuiHintTextField(x + 118, y + 12, 114, 20);
		this.worldField.setValue(I18n.get("gui.worldhandler.world_info.world.name") + ": " + ContentWorldInfo.format(server, object -> object.getWorldData().getLevelName()));
		
		this.seedField = new GuiHintTextField(x + 118, y + 36, 114, 20);
		this.seedField.setValue(I18n.get("gui.worldhandler.world_info.world.seed") + ": " + ContentWorldInfo.format(server, object -> object.overworld().getSeed()));
		this.seedField.setFilter(string -> string.equals(this.seedField.getValue()));
		this.seedField.moveCursorToStart(false);
		
		this.currentTimeField = new GuiHintTextField(x + 118, y + 24, 114, 20);
		this.updateCurrentTime();
		
		this.totalTimeField = new GuiHintTextField(x + 118, y + 48, 114, 20);
		this.updateTotalTime();
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonBase start;
		GuiButtonBase world;
		GuiButtonBase stats;
		
		container.addRenderableWidget(new GuiButtonBase(x, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.addRenderableWidget(new GuiButtonBase(x + 118, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.addRenderableWidget(start = new GuiButtonBase(x, y + 12, 114, 20, Component.translatable("gui.worldhandler.world_info.start"), () ->
		{
			this.page = Page.START;
			container.init();
		}));
		container.addRenderableWidget(world = new GuiButtonBase(x, y + 36, 114, 20, Component.translatable("gui.worldhandler.world_info.world"), () ->
		{
			this.page = Page.WORLD;
			container.init();
		}));
		container.addRenderableWidget(stats = new GuiButtonBase(x, y + 60, 114, 20, Component.translatable("gui.worldhandler.world_info.statistics"), () ->
		{
			this.page = Page.STATS;
			container.init();
		}));
		
		if(Page.START.equals(this.page))
		{
			start.active = false;
			container.addRenderableWidget(this.posXField);
			container.addRenderableWidget(this.posYField);
			container.addRenderableWidget(this.posZField);
		}
		else if(Page.WORLD.equals(this.page))
		{
			GuiButtonBase seed;
			IntegratedServer server = Minecraft.getInstance().getSingleplayerServer();
			
			world.active = false;
			container.addRenderableWidget(seed = new GuiButtonBase(x + 118, y + 60, 114, 20, Component.translatable("gui.worldhandler.world_info.world.copy_seed"), () ->
			{
				Minecraft.getInstance().keyboardHandler.setClipboard(String.valueOf(server.overworld().getSeed()));
			}));
			
			seed.active = server != null;
			
			container.addRenderableWidget(this.worldField);
			container.addRenderableWidget(this.seedField);
		}
		else if(Page.STATS.equals(this.page))
		{
			stats.active = false;
			
			container.addRenderableWidget(this.totalTimeField);
			container.addRenderableWidget(this.currentTimeField);
		}
	}
	
	@Override
	public void tick(Container container)
	{
		this.updateCurrentTime();
		this.updateTotalTime();
	}
	
	private void updateCurrentTime()
	{
		Level level = Minecraft.getInstance().level;
		
		if(level != null)
		{
			this.currentTimeField.setValue(I18n.get("gui.worldhandler.world_info.statistics.world_time") + ": " + TextUtils.formatWorldTime(level.getLevelData().getDayTime()));
		}
	}
	
	private void updateTotalTime()
	{
		Level level = Minecraft.getInstance().level;
		
		if(level != null)
		{
			this.totalTimeField.setValue(I18n.get("gui.worldhandler.world_info.statistics.played") + ": " + TextUtils.formatTotalTime(level.getLevelData().getGameTime()));
		}
	}
	
	private static <T> String format(T object, Function<T, Object> function)
	{
		if(object != null)
		{
			return String.valueOf(function.apply(object));
		}
		
		return I18n.get("gui.worldhandler.world_info.n_a");
	}
	
	private static Level getSidedWorld()
	{
		if(Minecraft.getInstance().isLocalServer())
		{
			return Minecraft.getInstance().getSingleplayerServer().overworld();
		}
		
		return Minecraft.getInstance().level;
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.WORLD;
	}
	
	@Override
	public MutableComponent getTitle()
	{
		return Component.translatable("gui.worldhandler.title.world.world");
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return Component.translatable("gui.worldhandler.tab.world.world");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.WORLD_INFO;
	}
	
	public static enum Page
	{
		START,
		WORLD,
		STATS;
	}
}
