package exopandora.worldhandler.gui.content.impl;

import com.google.common.base.Predicates;
import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderGeneric;
import exopandora.worldhandler.builder.impl.BuilderMultiCommand;
import exopandora.worldhandler.builder.impl.BuilderPlayer;
import exopandora.worldhandler.builder.impl.BuilderPlayerReason;
import exopandora.worldhandler.builder.impl.BuilderWhitelist;
import exopandora.worldhandler.builder.impl.BuilderWhitelist.EnumMode;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.widget.button.EnumIcon;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiButtonIcon;
import exopandora.worldhandler.gui.widget.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.widget.button.GuiTextFieldTooltip;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class ContentMultiplayer extends Content
{
	private GuiTextFieldTooltip playerField;
	private GuiTextFieldTooltip reasonField;
	
	private Page page = Page.KICK_AND_BAN;
	
	private final BuilderPlayerReason builderKick = new BuilderPlayerReason("kick");
	private final BuilderPlayerReason builderBan = new BuilderPlayerReason("ban");
	private final BuilderPlayer builderPardon = new BuilderPlayer("pardon");
	private final BuilderPlayer builderOp = new BuilderPlayer("op");
	private final BuilderPlayer builderDeop = new BuilderPlayer("deop");
	private final BuilderGeneric builderSaveAll = new BuilderGeneric("save-all");
	private final BuilderGeneric builderSaveOn = new BuilderGeneric("save-on");
	private final BuilderGeneric builderSaveOff = new BuilderGeneric("save-off");
	private final BuilderGeneric builderStop = new BuilderGeneric("stop");
	private final BuilderWhitelist builderWhitelist = new BuilderWhitelist();
	
	private final BuilderMultiCommand builderKickBan = new BuilderMultiCommand(this.builderKick, this.builderBan);
	private final BuilderMultiCommand builderPermissions = new BuilderMultiCommand(this.builderOp, this.builderDeop);
	private final BuilderMultiCommand builderRuntime = new BuilderMultiCommand(this.builderSaveAll, this.builderSaveOn, this.builderSaveOff, this.builderStop);
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		if(Page.KICK_AND_BAN.equals(this.page))
		{
			return this.builderKickBan;
		}
		else if(Page.PARDON.equals(this.page))
		{
			return this.builderPardon;
		}
		else if(Page.PERMISSIONS.equals(this.page))
		{
			return this.builderPermissions;
		}
		else if(Page.RUNTIME.equals(this.page))
		{
			return this.builderRuntime;
		}
		else if(Page.WHITELIST.equals(this.page))
		{
			return this.builderWhitelist;
		}
		
		return null;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.playerField = new GuiTextFieldTooltip(x + 118, y + this.page.getShift(), 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.username"));
		this.playerField.setFilter(Predicates.notNull());
		this.playerField.setFocus(false);
		this.playerField.setValue(this.builderKick.getPlayer());
		this.playerField.setMaxLength(16);
		this.playerField.setResponder(text ->
		{
			this.setPlayer(this.playerField.getValue());
			container.initButtons();
		});
		
		this.reasonField = new GuiTextFieldTooltip(x + 118, y + 24 + this.page.getShift(), 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.kick_ban.reason"));
		this.reasonField.setFilter(Predicates.notNull());
		this.reasonField.setFocus(false);
		this.reasonField.setValue(this.builderKick.getReason());
		this.reasonField.setResponder(text ->
		{
			this.setReason(this.reasonField.getValue());
			container.initButtons();
		});
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonBase button1;
		GuiButtonBase button2;
		GuiButtonBase button3;
		GuiButtonBase button4;
		GuiButtonBase button5;
		GuiButtonBase button6;
		GuiButtonBase button7;
		
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, new TranslatableComponent("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(button1 = new GuiButtonBase(x, y, 114, 20, new TextComponent(I18n.get("gui.worldhandler.multiplayer.kick") + " / " + I18n.get("gui.worldhandler.multiplayer.ban")), () ->
		{
			this.page = Page.KICK_AND_BAN;
			container.init();
		}));
		container.add(button2 = new GuiButtonBase(x, y + 24, 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.pardon"), () ->
		{
			this.page = Page.PARDON;
			container.init();
		}));
		container.add(button3 = new GuiButtonBase(x, y + 48, 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.permissions"), () ->
		{
			this.page = Page.PERMISSIONS;
			container.init();
		}));
		container.add(button4 = new GuiButtonBase(x, y + 72, 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.runtime"), () ->
		{
			this.page = Page.RUNTIME;
			container.init();
		}));
		container.add(button5 = new GuiButtonBase(x, y + 96, 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.whitelist"), () ->
		{
			this.page = Page.WHITELIST;
			container.init();
		}));
		
		if(Page.KICK_AND_BAN.equals(this.page))
		{
			container.add(this.playerField);
			container.add(this.reasonField);
			container.add(button6 = new GuiButtonTooltip(x + 118, y + 48, 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.kick"), new TextComponent(this.builderKick.toActualCommand()), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderKick);
			}));
			container.add(button7 = new GuiButtonTooltip(x + 118, y + 72, 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.ban"), new TextComponent(this.builderBan.toActualCommand()), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderBan);
			}));
			
			if(this.playerField.getValue().isEmpty())
			{
				button6.active = false;
				button7.active = false;
			}
			
			button1.active = false;
		}
		else if(Page.PARDON.equals(this.page))
		{
			container.add(this.playerField);
			container.add(button6 = new GuiButtonTooltip(x + 118, y + 48, 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.pardon"), new TextComponent(this.builderPardon.toActualCommand()), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderPardon);
			}));
			
			if(this.playerField.getValue().isEmpty())
			{
				button6.active = false;
			}
			
			button2.active = false;
		}
		else if(Page.PERMISSIONS.equals(this.page))
		{
			container.add(this.playerField);
			container.add(button6 = new GuiButtonTooltip(x + 118, y + 24 + 12, 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.permissions.give"), new TextComponent(this.builderOp.toActualCommand()), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderOp);
			}));
			container.add(button7 = new GuiButtonTooltip(x + 118, y + 48 + 12, 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.permissions.take"), new TextComponent(this.builderDeop.toActualCommand()), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderDeop);
			}));
			
			if(this.playerField.getValue().isEmpty())
			{
				button6.active = false;
				button7.active = false;
			}
			
			button3.active = false;
		}
		else if(Page.RUNTIME.equals(this.page))
		{
			container.add(new GuiButtonTooltip(x + 118, y, 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.runtime.save_world"), new TextComponent(this.builderSaveAll.toActualCommand()), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderSaveAll);
			}));
			container.add(new GuiButtonTooltip(x + 118, y + 24, 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.runtime.autosave", new TranslatableComponent("gui.worldhandler.generic.on")), new TextComponent(this.builderSaveOn.toActualCommand()), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderSaveOn);
			}));
			container.add(new GuiButtonTooltip(x + 118, y + 48, 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.runtime.autosave", new TranslatableComponent("gui.worldhandler.generic.off")).withStyle(ChatFormatting.RED), new TextComponent(this.builderSaveOff.toActualCommand()), () ->
			{
				ActionHelper.open(Contents.CONTINUE.withBuilder(this.builderSaveOff));
			}));
			container.add(new GuiButtonTooltip(x + 118, y + 72, 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.runtime.stop_server").withStyle(ChatFormatting.RED), new TextComponent(this.builderStop.toActualCommand()), () ->
			{
				ActionHelper.open(Contents.CONTINUE.withBuilder(this.builderStop));
			}));
			
			button4.active = false;
		}
		else if(Page.WHITELIST.equals(this.page))
		{
			container.add(this.playerField);
			container.add(button6 = new GuiButtonBase(x + 118, y + 24, 44, 20, new TranslatableComponent("gui.worldhandler.multiplayer.whitelist.add"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderWhitelist.build(EnumMode.ADD));
			}));
			container.add(button7 = new GuiButtonBase(x + 118 + 47, y + 24, 44, 20, new TranslatableComponent("gui.worldhandler.multiplayer.whitelist.remove"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderWhitelist.build(EnumMode.REMOVE));
			}));
			
			container.add(new GuiButtonBase(x + 118, y + 48, 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.whitelist.whitelist", new TranslatableComponent("gui.worldhandler.generic.on")), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderWhitelist.build(EnumMode.ON));
			}));
			container.add(new GuiButtonBase(x + 118, y + 72, 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.whitelist.whitelist", new TranslatableComponent("gui.worldhandler.generic.off")), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderWhitelist.build(EnumMode.OFF));
			}));
			
			container.add(new GuiButtonIcon(x + 232 - 20, y + 24, 20, 20, EnumIcon.RELOAD, new TranslatableComponent("gui.worldhandler.multiplayer.whitelist.reload"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderWhitelist.build(EnumMode.RELOAD));
			}));
			
			if(this.playerField.getValue().isEmpty())
			{
				button6.active = false;
				button7.active = false;
			}
			
			button5.active = false;
		}
	}
	
	@Override
	public void tick(Container container)
	{
		if(Page.KICK_AND_BAN.equals(this.page))
		{
			this.reasonField.tick();
		}
		
		if(!Page.RUNTIME.equals(this.page))
		{
			this.playerField.tick();
		}
	}
	
	@Override
	public void drawScreen(PoseStack matrix, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(Page.KICK_AND_BAN.equals(this.page))
		{
			this.reasonField.renderButton(matrix, mouseX, mouseY, partialTicks);
		}
		
		if(!Page.RUNTIME.equals(this.page))
		{
			this.playerField.renderButton(matrix, mouseX, mouseY, partialTicks);
		}
	}
	
	private void setPlayer(String player)
	{
		this.builderBan.setPlayer(player);
		this.builderKick.setPlayer(player);
		
		this.builderPardon.setPlayer(player);
		this.builderOp.setPlayer(player);
		this.builderDeop.setPlayer(player);
		
		this.builderWhitelist.setPlayer(player);
	}
	
	private void setReason(String reason)
	{
		this.builderBan.setReason(reason);
		this.builderKick.setReason(reason);
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.MAIN;
	}
	
	@Override
	public MutableComponent getTitle()
	{
		return new TranslatableComponent("gui.worldhandler.title.multiplayer");
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return new TranslatableComponent("gui.worldhandler.tab.multiplayer");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.MULTIPLAYER;
	}
	
	@Override
	public Content getBackContent()
	{
		return null;
	}
	
	public static enum Page
	{
		KICK_AND_BAN(0),
		PARDON(24),
		PERMISSIONS(14),
		RUNTIME(0),
		WHITELIST(0);
		
		private final int shift;
		
		private Page(int shift)
		{
			this.shift = shift;
		}
		
		public int getShift()
		{
			return this.shift;
		}
	}
}
