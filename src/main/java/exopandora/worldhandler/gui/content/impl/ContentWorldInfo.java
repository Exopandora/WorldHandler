package exopandora.worldhandler.gui.content.impl;

import java.util.function.Function;

import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiTextFieldTooltip;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

public class ContentWorldInfo extends Content
{
	private Page page = Page.START;
	
	private GuiTextFieldTooltip posXField;
	private GuiTextFieldTooltip posYField;
	private GuiTextFieldTooltip posZField;
	
	private GuiTextFieldTooltip worldField;
	private GuiTextFieldTooltip seedField;
	
	private GuiTextFieldTooltip totalTimeField;
	private GuiTextFieldTooltip currentTimeField;
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		Level level = ContentWorldInfo.getSidedWorld();
		IntegratedServer server = Minecraft.getInstance().getSingleplayerServer();
		
		this.posXField = new GuiTextFieldTooltip(x + 118, y + 12, 114, 20);
		this.posXField.setValue(I18n.get("gui.worldhandler.world_info.start.spawn") + " X: " + ContentWorldInfo.format(level, object -> object.getLevelData().getXSpawn()));
		
		this.posYField = new GuiTextFieldTooltip(x + 118, y + 36, 114, 20);
		this.posYField.setValue(I18n.get("gui.worldhandler.world_info.start.spawn") + " Y: " + ContentWorldInfo.format(level, object -> object.getLevelData().getYSpawn()));
		
		this.posZField = new GuiTextFieldTooltip(x + 118, y + 60, 114, 20);
		this.posZField.setValue(I18n.get("gui.worldhandler.world_info.start.spawn") + " Z: " + ContentWorldInfo.format(level, object -> object.getLevelData().getZSpawn()));
		
		this.worldField = new GuiTextFieldTooltip(x + 118, y + 12, 114, 20);
		this.worldField.setValue(I18n.get("gui.worldhandler.world_info.world.name") + ": " + ContentWorldInfo.format(server, object -> object.getWorldData().getLevelName()));
		
		this.seedField = new GuiTextFieldTooltip(x + 118, y + 36, 114, 20);
		this.seedField.setValue(I18n.get("gui.worldhandler.world_info.world.seed") + ": " + ContentWorldInfo.format(server, object -> object.overworld().getSeed()));
		this.seedField.setFilter(string -> string.equals(this.seedField.getValue()));
		this.seedField.moveCursorToStart();
		
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
		
		container.add(new GuiButtonBase(x, y + 96, 114, 20, new TranslatableComponent("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, new TranslatableComponent("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(start = new GuiButtonBase(x, y + 12, 114, 20, new TranslatableComponent("gui.worldhandler.world_info.start"), () ->
		{
			this.page = Page.START;
			container.init();
		}));
		container.add(world = new GuiButtonBase(x, y + 36, 114, 20, new TranslatableComponent("gui.worldhandler.world_info.world"), () ->
		{
			this.page = Page.WORLD;
			container.init();
		}));
		container.add(stats = new GuiButtonBase(x, y + 60, 114, 20, new TranslatableComponent("gui.worldhandler.world_info.statistics"), () ->
		{
			this.page = Page.STATS;
			container.init();
		}));
		
		if(Page.START.equals(this.page))
		{
			start.active = false;
		}
		else if(Page.WORLD.equals(this.page))
		{
			GuiButtonBase seed;
			IntegratedServer server = Minecraft.getInstance().getSingleplayerServer();
			
			world.active = false;
			container.add(seed = new GuiButtonBase(x + 118, y + 60, 114, 20, new TranslatableComponent("gui.worldhandler.world_info.world.copy_seed"), () ->
			{
				Minecraft.getInstance().keyboardHandler.setClipboard(String.valueOf(server.overworld().getSeed()));
			}));
			
			seed.active = server != null;
		}
		else if(Page.STATS.equals(this.page))
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
	public void drawScreen(PoseStack matrix, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(Page.START.equals(this.page))
		{
			this.posXField.renderButton(matrix, mouseX, mouseY, partialTicks);
			this.posYField.renderButton(matrix, mouseX, mouseY, partialTicks);
			this.posZField.renderButton(matrix, mouseX, mouseY, partialTicks);
		}
		else if(Page.WORLD.equals(this.page))
		{
			this.worldField.renderButton(matrix, mouseX, mouseY, partialTicks);
			this.seedField.renderButton(matrix, mouseX, mouseY, partialTicks);
		}
		else if(Page.STATS.equals(this.page))
		{
			this.totalTimeField.renderButton(matrix, mouseX, mouseY, partialTicks);
			this.currentTimeField.renderButton(matrix, mouseX, mouseY, partialTicks);
		}
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
		return new TranslatableComponent("gui.worldhandler.title.world.world");
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return new TranslatableComponent("gui.worldhandler.tab.world.world");
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
