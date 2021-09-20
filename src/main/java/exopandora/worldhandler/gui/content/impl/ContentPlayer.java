package exopandora.worldhandler.gui.content.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderGeneric;
import exopandora.worldhandler.builder.impl.BuilderMultiCommand;
import exopandora.worldhandler.builder.impl.BuilderPlayer;
import exopandora.worldhandler.builder.impl.BuilderSpawnpoint;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiTextFieldTooltip;
import exopandora.worldhandler.util.ActionHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;

public class ContentPlayer extends Content
{
	private Page page = Page.START;
	
	private GuiTextFieldTooltip posXField;
	private GuiTextFieldTooltip posYField;
	private GuiTextFieldTooltip posZField;
	
	private GuiTextFieldTooltip scoreField;
	private GuiTextFieldTooltip coinsField;
	private GuiTextFieldTooltip xpField;
	
	private final BuilderGeneric builderSetworldspawn = new BuilderGeneric("setworldspawn");
	private final BuilderSpawnpoint builderSpawnpoint = new BuilderSpawnpoint();
	private final BuilderPlayer builderKill = new BuilderPlayer("kill");
	private final BuilderGeneric builderClear = new BuilderGeneric("clear");
	
	private final BuilderMultiCommand builderMiscellaneous = new BuilderMultiCommand(this.builderSetworldspawn, this.builderSpawnpoint, this.builderKill, this.builderClear);
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		if(Page.MISC.equals(this.page))
		{
			return this.builderMiscellaneous;
		}
		
		return null;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.posXField = new GuiTextFieldTooltip(x + 118, y, 114, 20);
		this.posYField = new GuiTextFieldTooltip(x + 118, y + 24, 114, 20);
		this.posZField = new GuiTextFieldTooltip(x + 118, y + 48, 114, 20);
		this.scoreField = new GuiTextFieldTooltip(x + 118, y + 12, 114, 20);
		this.coinsField = new GuiTextFieldTooltip(x + 118, y + 36, 114, 20);
		this.xpField = new GuiTextFieldTooltip(x + 118, y + 60, 114, 20);
		
		this.tick(container);
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonBase button1;
		GuiButtonBase button2;
		GuiButtonBase button3;
		GuiButtonBase button4;
		
		container.add(new GuiButtonBase(x, y + 96, 114, 20, new TranslatableComponent("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, new TranslatableComponent("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(button1 = new GuiButtonBase(x, y, 114, 20, new TranslatableComponent("gui.worldhandler.entities.player.start"), () ->
		{
			this.page = Page.START;
			container.init();
		}));
		container.add(button2 = new GuiButtonBase(x, y + 24, 114, 20, new TranslatableComponent("gui.worldhandler.entities.player.score"), () ->
		{
			this.page = Page.SCORE;
			container.init();
		}));
		container.add(button3 = new GuiButtonBase(x, y + 48, 114, 20, new TranslatableComponent("gui.worldhandler.entities.player.position"), () ->
		{
			this.page = Page.POSITION;
			container.init();
		}));
		container.add(button4 = new GuiButtonBase(x, y + 72, 114, 20, new TranslatableComponent("gui.worldhandler.entities.player.miscellaneous"), () ->
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
		}
		else if(Page.POSITION.equals(this.page))
		{
			button3.active = false;
			
			container.add(new GuiButtonBase(x + 118, y + 72, 114, 20, new TranslatableComponent("gui.worldhandler.entities.player.position.copy_position"), () ->
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
			
			container.add(new GuiButtonBase(x + 118, y, 114, 20, new TranslatableComponent("gui.worldhandler.entities.player.miscellaneous.set_spawn").withStyle(ChatFormatting.RED), () ->
			{
				ActionHelper.open(Contents.CONTINUE.withBuilder(this.builderSpawnpoint));
			}));
			container.add(new GuiButtonBase(x + 118, y + 24, 114, 20, new TranslatableComponent("gui.worldhandler.entities.player.miscellaneous.set_global_spawn").withStyle(ChatFormatting.RED), () ->
			{
				ActionHelper.open(Contents.CONTINUE.withBuilder(this.builderSetworldspawn));
			}));
			container.add(new GuiButtonBase(x + 118, y + 48, 114, 20, new TranslatableComponent("gui.worldhandler.entities.player.miscellaneous.kill").withStyle(ChatFormatting.RED), () ->
			{
				ActionHelper.open(Contents.CONTINUE.withBuilder(this.builderKill));
			}));
			container.add(new GuiButtonBase(x + 118, y + 72, 114, 20, new TranslatableComponent("gui.worldhandler.entities.player.miscellaneous.clear_inventory").withStyle(ChatFormatting.RED), () ->
			{
				ActionHelper.open(Contents.CONTINUE.withBuilder(this.builderClear));
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
	public void drawScreen(PoseStack matrix, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(Page.START.equals(this.page) && Minecraft.getInstance().player != null)
		{
			int xPos = x + 175;
			int yPos = y + 82;
			int playerNameWidth = Minecraft.getInstance().font.width(Minecraft.getInstance().player.getName()) / 2;
			
			GuiComponent.fill(matrix, container.width / 2 - playerNameWidth - 1 + 59, yPos - 74, container.width / 2 + playerNameWidth + 1 + 59, yPos - 65, 0x3F000000);
			Minecraft.getInstance().font.draw(matrix, Minecraft.getInstance().player.getName(), container.width / 2 - playerNameWidth + 59, yPos - 73, 0xE0E0E0);
			
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			InventoryScreen.renderEntityInInventory(xPos, yPos, 30, xPos - mouseX, yPos - mouseY - 44, Minecraft.getInstance().player);
			RenderSystem.defaultBlendFunc();
		}
		else if(Page.SCORE.equals(this.page))
		{
			this.scoreField.renderButton(matrix, mouseX, mouseY, partialTicks);
			this.xpField.renderButton(matrix, mouseX, mouseY, partialTicks);
			this.coinsField.renderButton(matrix, mouseX, mouseY, partialTicks);
		}
		else if(Page.POSITION.equals(this.page))
		{
			this.posXField.renderButton(matrix, mouseX, mouseY, partialTicks);
			this.posYField.renderButton(matrix, mouseX, mouseY, partialTicks);
			this.posZField.renderButton(matrix, mouseX, mouseY, partialTicks);
		}
	}
	
	@Override
	public void onPlayerNameChanged(String username)
	{
		this.builderSpawnpoint.setPlayer(username);
		this.builderKill.setPlayer(username);
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.PLAYER;
	}
	
	@Override
	public MutableComponent getTitle()
	{
		return new TranslatableComponent("gui.worldhandler.title.player.player");
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return new TranslatableComponent("gui.worldhandler.tab.player.player");
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
