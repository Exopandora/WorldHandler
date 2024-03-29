package exopandora.worldhandler.gui.content.impl;

import com.mojang.blaze3d.systems.RenderSystem;

import exopandora.worldhandler.builder.impl.ClearInventoryCommandBuilder;
import exopandora.worldhandler.builder.impl.KillCommandBuilder;
import exopandora.worldhandler.builder.impl.SetSpawnCommandBuilder;
import exopandora.worldhandler.builder.impl.SetWorldSpawnCommandBuilder;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiHintTextField;
import exopandora.worldhandler.util.ActionHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

public class ContentPlayer extends Content
{
	private Page page = Page.START;
	
	private GuiHintTextField posXField;
	private GuiHintTextField posYField;
	private GuiHintTextField posZField;
	
	private GuiHintTextField scoreField;
	private GuiHintTextField coinsField;
	private GuiHintTextField xpField;
	
	private final SetWorldSpawnCommandBuilder builderSetWorldSpawn = new SetWorldSpawnCommandBuilder();
	private final SetSpawnCommandBuilder builderSpawnpoint = new SetSpawnCommandBuilder();
	private final KillCommandBuilder builderKill = new KillCommandBuilder();
	private final ClearInventoryCommandBuilder builderClear = new ClearInventoryCommandBuilder();
	private final CommandPreview preview = new CommandPreview()
			.add(this.builderSetWorldSpawn, SetWorldSpawnCommandBuilder.Label.SET_WORLD_SPAWN)
			.add(this.builderSpawnpoint, SetSpawnCommandBuilder.Label.SPAWNPOINT)
			.add(this.builderKill, KillCommandBuilder.Label.KILL)
			.add(this.builderClear, ClearInventoryCommandBuilder.Label.CLEAR);
	
	@Override
	public CommandPreview getCommandPreview()
	{
		if(Page.MISC.equals(this.page))
		{
			return this.preview;
		}
		
		return null;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.posXField = new GuiHintTextField(x + 118, y, 114, 20);
		this.posYField = new GuiHintTextField(x + 118, y + 24, 114, 20);
		this.posZField = new GuiHintTextField(x + 118, y + 48, 114, 20);
		this.scoreField = new GuiHintTextField(x + 118, y + 12, 114, 20);
		this.coinsField = new GuiHintTextField(x + 118, y + 36, 114, 20);
		this.xpField = new GuiHintTextField(x + 118, y + 60, 114, 20);
		
		this.tick(container);
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonBase button1;
		GuiButtonBase button2;
		GuiButtonBase button3;
		GuiButtonBase button4;
		
		container.addRenderableWidget(new GuiButtonBase(x, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.addRenderableWidget(new GuiButtonBase(x + 118, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.addRenderableWidget(button1 = new GuiButtonBase(x, y, 114, 20, Component.translatable("gui.worldhandler.entities.player.start"), () ->
		{
			this.page = Page.START;
			container.init();
		}));
		container.addRenderableWidget(button2 = new GuiButtonBase(x, y + 24, 114, 20, Component.translatable("gui.worldhandler.entities.player.score"), () ->
		{
			this.page = Page.SCORE;
			container.init();
		}));
		container.addRenderableWidget(button3 = new GuiButtonBase(x, y + 48, 114, 20, Component.translatable("gui.worldhandler.entities.player.position"), () ->
		{
			this.page = Page.POSITION;
			container.init();
		}));
		container.addRenderableWidget(button4 = new GuiButtonBase(x, y + 72, 114, 20, Component.translatable("gui.worldhandler.entities.player.miscellaneous"), () ->
		{
			this.page = Page.MISC;
			container.init();
		}));
		
		if(Page.START.equals(this.page))
		{
			button1.active = false;
		}
		else if(Page.SCORE.equals(this.page))
		{
			button2.active = false;
			
			container.addRenderableWidget(this.scoreField);
			container.addRenderableWidget(this.xpField);
			container.addRenderableWidget(this.coinsField);
		}
		else if(Page.POSITION.equals(this.page))
		{
			button3.active = false;
			
			container.addRenderableWidget(this.posXField);
			container.addRenderableWidget(this.posYField);
			container.addRenderableWidget(this.posZField);
			
			container.addRenderableWidget(new GuiButtonBase(x + 118, y + 72, 114, 20, Component.translatable("gui.worldhandler.entities.player.position.copy_position"), () ->
			{
				Player player = Minecraft.getInstance().player;
				
				if(player != null)
				{
					BlockPos position = player.blockPosition();
					Minecraft.getInstance().keyboardHandler.setClipboard(position.getX() + " " + position.getY() + " " + position.getZ());
				}
			}));
		}
		else if(Page.MISC.equals(this.page))
		{
			button4.active = false;
			
			container.addRenderableWidget(new GuiButtonBase(x + 118, y, 114, 20, Component.translatable("gui.worldhandler.entities.player.miscellaneous.set_spawn").withStyle(ChatFormatting.RED), () ->
			{
				ActionHelper.open(Contents.CONTINUE.withBuilder(this.builderSpawnpoint, SetSpawnCommandBuilder.Label.SPAWNPOINT));
			}));
			container.addRenderableWidget(new GuiButtonBase(x + 118, y + 24, 114, 20, Component.translatable("gui.worldhandler.entities.player.miscellaneous.set_global_spawn").withStyle(ChatFormatting.RED), () ->
			{
				ActionHelper.open(Contents.CONTINUE.withBuilder(this.builderSetWorldSpawn, SetWorldSpawnCommandBuilder.Label.SET_WORLD_SPAWN));
			}));
			container.addRenderableWidget(new GuiButtonBase(x + 118, y + 48, 114, 20, Component.translatable("gui.worldhandler.entities.player.miscellaneous.kill").withStyle(ChatFormatting.RED), () ->
			{
				ActionHelper.open(Contents.CONTINUE.withBuilder(this.builderKill, KillCommandBuilder.Label.KILL));
			}));
			container.addRenderableWidget(new GuiButtonBase(x + 118, y + 72, 114, 20, Component.translatable("gui.worldhandler.entities.player.miscellaneous.clear_inventory").withStyle(ChatFormatting.RED), () ->
			{
				ActionHelper.open(Contents.CONTINUE.withBuilder(this.builderClear, ClearInventoryCommandBuilder.Label.CLEAR));
			}));
		}
	}
	
	@Override
	public void tick(Container container)
	{
		Player player = Minecraft.getInstance().player;
		
		if(player != null)
		{
			BlockPos position = player.blockPosition();
			
			this.posXField.setValue("X: " + position.getX());
			this.posYField.setValue("Y: " + position.getY());
			this.posZField.setValue("Z: " + position.getZ());
			this.scoreField.setValue(I18n.get("gui.worldhandler.entities.player.score") + ": " + player.getScore());
			this.coinsField.setValue(I18n.get("gui.worldhandler.entities.player.score.experience") + ": " + player.experienceLevel + "L");
			this.xpField.setValue(I18n.get("gui.worldhandler.entities.player.score.experience_coins") + ": " + player.totalExperience);
		}
	}
	
	@Override
	public void drawScreen(GuiGraphics guiGraphics, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(Page.START.equals(this.page) && Minecraft.getInstance().player != null)
		{
			Minecraft minecraft = Minecraft.getInstance();
			int xPos = x + 175;
			int yPos = y + 82;
			int playerNameWidth = minecraft.font.width(minecraft.player.getName()) / 2;
			
			guiGraphics.fill(container.width / 2 - playerNameWidth - 1 + 59, yPos - 74, container.width / 2 + playerNameWidth + 1 + 59, yPos - 65, 0x3F000000);
			guiGraphics.drawString(minecraft.font, minecraft.player.getName(), container.width / 2 - playerNameWidth + 59, yPos - 73, 0xE0E0E0);
			
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, xPos, yPos, 30, xPos - mouseX, yPos - mouseY - 44, 0.0625F, (float) mouseX, (float) mouseY, minecraft.player);
			RenderSystem.defaultBlendFunc();
		}
	}
	
	@Override
	public void onPlayerNameChanged(String username)
	{
		this.builderSpawnpoint.targets().setTarget(username);
		this.builderKill.targets().setTarget(username);
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.PLAYER;
	}
	
	@Override
	public MutableComponent getTitle()
	{
		return Component.translatable("gui.worldhandler.title.player.player");
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return Component.translatable("gui.worldhandler.tab.player.player");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.PLAYER;
	}
	
	public static enum Page
	{
		START,
		SCORE,
		POSITION,
		MISC;
	}
}
