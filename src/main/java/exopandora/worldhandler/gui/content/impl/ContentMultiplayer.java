package exopandora.worldhandler.gui.content.impl;

import com.google.common.base.Predicates;
import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.builder.impl.BanCommandBuilder;
import exopandora.worldhandler.builder.impl.DeOpCommandBuilder;
import exopandora.worldhandler.builder.impl.KickCommandBuilder;
import exopandora.worldhandler.builder.impl.OpCommandBuilder;
import exopandora.worldhandler.builder.impl.PardonCommandBuilder;
import exopandora.worldhandler.builder.impl.SaveAllCommandBuilder;
import exopandora.worldhandler.builder.impl.SaveOffCommandBuilder;
import exopandora.worldhandler.builder.impl.SaveOnCommandBuilder;
import exopandora.worldhandler.builder.impl.StopCommandBuilder;
import exopandora.worldhandler.builder.impl.WhitelistCommandBuilder;
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
	
	private final KickCommandBuilder builderKick = new KickCommandBuilder();
	private final BanCommandBuilder builderBan = new BanCommandBuilder();
	private final PardonCommandBuilder builderPardon = new PardonCommandBuilder();
	private final OpCommandBuilder builderOp = new OpCommandBuilder();
	private final DeOpCommandBuilder builderDeop = new DeOpCommandBuilder();
	private final SaveAllCommandBuilder builderSaveAll = new SaveAllCommandBuilder();
	private final SaveOnCommandBuilder builderSaveOn = new SaveOnCommandBuilder();
	private final SaveOffCommandBuilder builderSaveOff = new SaveOffCommandBuilder();
	private final StopCommandBuilder builderStop = new StopCommandBuilder();
	private final WhitelistCommandBuilder builderWhitelist = new WhitelistCommandBuilder();
	
	private final CommandPreview previewKickBan = new CommandPreview()
			.add(this.builderKick, KickCommandBuilder.Label.KICK)
			.add(this.builderBan, BanCommandBuilder.Label.BAN);
	private final CommandPreview previewPardon = new CommandPreview(this.builderPardon, PardonCommandBuilder.Label.PARDON);
	private final CommandPreview previewPermissions = new CommandPreview()
			.add(this.builderOp, OpCommandBuilder.Label.OP)
			.add(this.builderDeop, DeOpCommandBuilder.Label.DEOP);
	private final CommandPreview previewRuntime = new CommandPreview()
			.add(this.builderSaveAll, SaveAllCommandBuilder.Label.SAVE_ALL)
			.add(this.builderSaveOn, SaveOnCommandBuilder.Label.SAVE_ON)
			.add(this.builderSaveOff, SaveOffCommandBuilder.Label.SAVE_OFF)
			.add(this.builderStop, StopCommandBuilder.Label.STOP);
	private final CommandPreview previewWhitelist = new CommandPreview(this.builderWhitelist, null);
	
	@Override
	public CommandPreview getCommandPreview()
	{
		if(Page.KICK_AND_BAN.equals(this.page))
		{
			return this.previewKickBan;
		}
		else if(Page.PARDON.equals(this.page))
		{
			return this.previewPardon;
		}
		else if(Page.PERMISSIONS.equals(this.page))
		{
			return this.previewPermissions;
		}
		else if(Page.RUNTIME.equals(this.page))
		{
			return this.previewRuntime;
		}
		else if(Page.WHITELIST.equals(this.page))
		{
			return this.previewWhitelist;
		}
		
		return null;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.playerField = new GuiTextFieldTooltip(x + 118, y + this.page.getShift(), 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.username"));
		this.playerField.setFilter(Predicates.notNull());
		this.playerField.setFocus(false);
		this.playerField.setValue(this.builderKick.targets().getTarget());
		this.playerField.setMaxLength(16);
		this.playerField.setResponder(text ->
		{
			this.setPlayer(this.playerField.getValue());
			container.initButtons();
		});
		
		this.reasonField = new GuiTextFieldTooltip(x + 118, y + 24 + this.page.getShift(), 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.kick_ban.reason"));
		this.reasonField.setFilter(Predicates.notNull());
		this.reasonField.setFocus(false);
		this.reasonField.setValue(this.builderKick.reason().get());
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
			container.add(button6 = new GuiButtonTooltip(x + 118, y + 48, 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.kick"), new TextComponent(this.builderKick.toCommand(KickCommandBuilder.Label.KICK_REASON, false)), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderKick, KickCommandBuilder.Label.KICK_REASON);
			}));
			container.add(button7 = new GuiButtonTooltip(x + 118, y + 72, 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.ban"), new TextComponent(this.builderBan.toCommand(BanCommandBuilder.Label.BAN_REASON, false)), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderBan, BanCommandBuilder.Label.BAN_REASON);
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
			container.add(button6 = new GuiButtonTooltip(x + 118, y + 48, 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.pardon"), new TextComponent(this.builderPardon.toCommand(PardonCommandBuilder.Label.PARDON, false)), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderPardon, PardonCommandBuilder.Label.PARDON);
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
			container.add(button6 = new GuiButtonTooltip(x + 118, y + 24 + 12, 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.permissions.give"), new TextComponent(this.builderOp.toCommand(OpCommandBuilder.Label.OP, false)), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderOp, OpCommandBuilder.Label.OP);
			}));
			container.add(button7 = new GuiButtonTooltip(x + 118, y + 48 + 12, 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.permissions.take"), new TextComponent(this.builderDeop.toCommand(DeOpCommandBuilder.Label.DEOP, false)), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderDeop, DeOpCommandBuilder.Label.DEOP);
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
			container.add(new GuiButtonTooltip(x + 118, y, 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.runtime.save_world"), new TextComponent(this.builderSaveAll.toCommand(SaveAllCommandBuilder.Label.SAVE_ALL, false)), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderSaveAll, SaveAllCommandBuilder.Label.SAVE_ALL);
			}));
			container.add(new GuiButtonTooltip(x + 118, y + 24, 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.runtime.autosave", new TranslatableComponent("gui.worldhandler.generic.on")), new TextComponent(this.builderSaveOn.toCommand(SaveOnCommandBuilder.Label.SAVE_ON, false)), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderSaveOn, SaveOnCommandBuilder.Label.SAVE_ON);
			}));
			container.add(new GuiButtonTooltip(x + 118, y + 48, 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.runtime.autosave", new TranslatableComponent("gui.worldhandler.generic.off")).withStyle(ChatFormatting.RED), new TextComponent(this.builderSaveOff.toCommand(SaveOffCommandBuilder.Label.SAVE_OFF, false)), () ->
			{
				ActionHelper.open(Contents.CONTINUE.withBuilder(this.builderSaveOff, SaveOffCommandBuilder.Label.SAVE_OFF));
			}));
			container.add(new GuiButtonTooltip(x + 118, y + 72, 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.runtime.stop_server").withStyle(ChatFormatting.RED), new TextComponent(this.builderStop.toCommand(StopCommandBuilder.Label.STOP, false)), () ->
			{
				ActionHelper.open(Contents.CONTINUE.withBuilder(this.builderStop, StopCommandBuilder.Label.STOP));
			}));
			
			button4.active = false;
		}
		else if(Page.WHITELIST.equals(this.page))
		{
			container.add(this.playerField);
			container.add(button6 = new GuiButtonBase(x + 118, y + 24, 44, 20, new TranslatableComponent("gui.worldhandler.multiplayer.whitelist.add"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderWhitelist, WhitelistCommandBuilder.Label.ADD);
			}));
			container.add(button7 = new GuiButtonBase(x + 118 + 47, y + 24, 44, 20, new TranslatableComponent("gui.worldhandler.multiplayer.whitelist.remove"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderWhitelist, WhitelistCommandBuilder.Label.REMOVE);
			}));
			
			container.add(new GuiButtonBase(x + 118, y + 48, 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.whitelist.whitelist", new TranslatableComponent("gui.worldhandler.generic.on")), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderWhitelist, WhitelistCommandBuilder.Label.ON);
			}));
			container.add(new GuiButtonBase(x + 118, y + 72, 114, 20, new TranslatableComponent("gui.worldhandler.multiplayer.whitelist.whitelist", new TranslatableComponent("gui.worldhandler.generic.off")), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderWhitelist, WhitelistCommandBuilder.Label.OFF);
			}));
			
			container.add(new GuiButtonIcon(x + 232 - 20, y + 24, 20, 20, EnumIcon.RELOAD, new TranslatableComponent("gui.worldhandler.multiplayer.whitelist.reload"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderWhitelist, WhitelistCommandBuilder.Label.RELOAD);
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
		this.builderBan.targets().setTarget(player);
		this.builderKick.targets().setTarget(player);
		this.builderPardon.targets().setTarget(player);
		this.builderOp.targets().setTarget(player);
		this.builderDeop.targets().setTarget(player);
		this.builderWhitelist.targets().setTarget(player);
	}
	
	private void setReason(String reason)
	{
		this.builderBan.reason().set(reason);
		this.builderKick.reason().set(reason);
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
