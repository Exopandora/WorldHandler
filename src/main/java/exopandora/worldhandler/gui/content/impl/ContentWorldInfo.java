package exopandora.worldhandler.gui.content.impl;

import java.util.function.Function;

import com.mojang.blaze3d.matrix.MatrixStack;

import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
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
		World world = ContentWorldInfo.getSidedWorld();
		IntegratedServer server = Minecraft.getInstance().getIntegratedServer();
		
		this.posXField = new GuiTextFieldTooltip(x + 118, y + 12, 114, 20);
		this.posXField.setText(I18n.format("gui.worldhandler.world_info.start.spawn") + " X: " + ContentWorldInfo.format(world, object -> object.getWorldInfo().getSpawnX()));
		
		this.posYField = new GuiTextFieldTooltip(x + 118, y + 36, 114, 20);
		this.posYField.setText(I18n.format("gui.worldhandler.world_info.start.spawn") + " Y: " + ContentWorldInfo.format(world, object -> object.getWorldInfo().getSpawnY()));
		
		this.posZField = new GuiTextFieldTooltip(x + 118, y + 60, 114, 20);
		this.posZField.setText(I18n.format("gui.worldhandler.world_info.start.spawn") + " Z: " + ContentWorldInfo.format(world, object -> object.getWorldInfo().getSpawnZ()));
		
		this.worldField = new GuiTextFieldTooltip(x + 118, y + 12, 114, 20);
		this.worldField.setText(I18n.format("gui.worldhandler.world_info.world.name") + ": " + ContentWorldInfo.format(server, object -> object.func_240793_aU_().getWorldName()));
		
		this.seedField = new GuiTextFieldTooltip(x + 118, y + 36, 114, 20);
		this.seedField.setText(I18n.format("gui.worldhandler.world_info.world.seed") + ": " + ContentWorldInfo.format(server, object -> object.func_241755_D_().getSeed()));
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
		
		container.add(new GuiButtonBase(x, y + 96, 114, 20, new TranslationTextComponent("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, new TranslationTextComponent("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(start = new GuiButtonBase(x, y + 12, 114, 20, new TranslationTextComponent("gui.worldhandler.world_info.start"), () ->
		{
			this.page = Page.START;
			container.init();
		}));
		container.add(world = new GuiButtonBase(x, y + 36, 114, 20, new TranslationTextComponent("gui.worldhandler.world_info.world"), () ->
		{
			this.page = Page.WORLD;
			container.init();
		}));
		container.add(stats = new GuiButtonBase(x, y + 60, 114, 20, new TranslationTextComponent("gui.worldhandler.world_info.statistics"), () ->
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
			IntegratedServer server = Minecraft.getInstance().getIntegratedServer();
			
			world.active = false;
			container.add(seed = new GuiButtonBase(x + 118, y + 60, 114, 20, new TranslationTextComponent("gui.worldhandler.world_info.world.copy_seed"), () ->
			{
				Minecraft.getInstance().keyboardListener.setClipboardString(String.valueOf(server.func_241755_D_().getSeed()));
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
	public void drawScreen(MatrixStack matrix, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
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
		this.currentTimeField.setText(I18n.format("gui.worldhandler.world_info.statistics.world_time") + ": " + TextUtils.formatWorldTime(Minecraft.getInstance().world.getWorldInfo().getDayTime()));
	}
	
	private void updateTotalTime()
	{
		this.totalTimeField.setText(I18n.format("gui.worldhandler.world_info.statistics.played") + ": " + TextUtils.formatTotalTime(Minecraft.getInstance().world.getWorldInfo().getGameTime()));
	}
	
	private static <T> String format(T object, Function<T, Object> function)
	{
		if(object != null)
		{
			return String.valueOf(function.apply(object));
		}
		
		return I18n.format("gui.worldhandler.world_info.n_a");
	}
	
	private static World getSidedWorld()
	{
		if(Minecraft.getInstance().isSingleplayer())
		{
			return Minecraft.getInstance().getIntegratedServer().func_241755_D_();
		}
		
		return Minecraft.getInstance().world;
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.WORLD;
	}
	
	@Override
	public IFormattableTextComponent getTitle()
	{
		return new TranslationTextComponent("gui.worldhandler.title.world.world");
	}
	
	@Override
	public IFormattableTextComponent getTabTitle()
	{
		return new TranslationTextComponent("gui.worldhandler.tab.world.world");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.WORLD_INFO;
	}
	
	@OnlyIn(Dist.CLIENT)
	public static enum Page
	{
		START,
		WORLD,
		STATS;
	}
}
