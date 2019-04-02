package exopandora.worldhandler.gui.content.impl;

import com.google.common.base.Predicates;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderGeneric;
import exopandora.worldhandler.builder.impl.BuilderMultiCommand;
import exopandora.worldhandler.builder.impl.BuilderPlayer;
import exopandora.worldhandler.builder.impl.BuilderPlayerReason;
import exopandora.worldhandler.builder.impl.BuilderWhitelist;
import exopandora.worldhandler.builder.impl.BuilderWhitelist.EnumMode;
import exopandora.worldhandler.gui.button.EnumIcon;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiButtonIcon;
import exopandora.worldhandler.gui.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.container.impl.GuiWorldHandler;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.helper.ActionHelper;
import exopandora.worldhandler.helper.CommandHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ContentMultiplayer extends Content
{
	private GuiTextFieldTooltip playerField;
	private GuiTextFieldTooltip reasonField;
	
	private int shiftDown = 0;
	
	private String selected = "kickBan";
	
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
		if(this.selected.equals("kickBan"))
		{
			return this.builderKickBan;
		}
		else if(this.selected.equals("pardon"))
		{
			return this.builderPardon;
		}
		else if(this.selected.equals("permissions"))
		{
			return this.builderPermissions;
		}
		else if(this.selected.equals("runtime"))
		{
			return this.builderRuntime;
		}
		else if(this.selected.equals("whitelist"))
		{
			return this.builderWhitelist;
		}
		
		return null;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.playerField = new GuiTextFieldTooltip(x + 118, y + this.shiftDown, 114, 20, I18n.format("gui.worldhandler.multiplayer.username"));
		this.playerField.setValidator(Predicates.notNull());
		this.playerField.setFocused(false);
		this.playerField.setText(this.builderKick.getPlayer());
		this.playerField.setMaxStringLength(16);
		this.playerField.setTextAcceptHandler((id, text) ->
		{
			this.setPlayer(this.playerField.getText());
			container.initButtons();
		});
		
		this.reasonField = new GuiTextFieldTooltip(x + 118, y + 24 + this.shiftDown, 114, 20, I18n.format("gui.worldhandler.multiplayer.kick_ban.reason"));
		this.reasonField.setValidator(Predicates.notNull());
		this.reasonField.setFocused(false);
		this.reasonField.setText(this.builderKick.getReason());
		this.reasonField.setTextAcceptHandler((id, text) ->
		{
			this.setReason(this.reasonField.getText());
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
		
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(button1 = new GuiButtonBase(x, y, 114, 20, I18n.format("gui.worldhandler.multiplayer.kick") + " / " + I18n.format("gui.worldhandler.multiplayer.ban"), () ->
		{
			this.selected = "kickBan";
			this.shiftDown = 0;
			container.initGui();
		}));
		container.add(button2 = new GuiButtonBase(x, y + 24, 114, 20, I18n.format("gui.worldhandler.multiplayer.pardon"), () ->
		{
			this.selected = "pardon";
			this.shiftDown = 24;
			container.initGui();
		}));
		container.add(button3 = new GuiButtonBase(x, y + 48, 114, 20, I18n.format("gui.worldhandler.multiplayer.permissions"), () ->
		{
			this.selected = "permissions";
			this.shiftDown = 12;
			container.initGui();
		}));
		container.add(button4 = new GuiButtonBase(x, y + 72, 114, 20, I18n.format("gui.worldhandler.multiplayer.runtime"), () ->
		{
			this.selected = "runtime";
			this.shiftDown = 0;
			container.initGui();
		}));
		container.add(button5 = new GuiButtonBase(x, y + 96, 114, 20, I18n.format("gui.worldhandler.multiplayer.whitelist"), () ->
		{
			this.selected = "whitelist";
			this.shiftDown = 0;
			container.initGui();
		}));
		
		if(this.selected.equals("kickBan"))
		{
			container.add(this.playerField);
			container.add(this.reasonField);
			container.add(button6 = new GuiButtonTooltip(x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.multiplayer.kick"), this.builderKick.toActualCommand(), () ->
			{
				CommandHelper.sendCommand(this.builderKick);
			}));
			container.add(button7 = new GuiButtonTooltip(x + 118, y + 72, 114, 20, I18n.format("gui.worldhandler.multiplayer.ban"), this.builderBan.toActualCommand(), () ->
			{
				CommandHelper.sendCommand(this.builderBan);
			}));
			
			if(this.playerField.getText().isEmpty())
			{
				button6.enabled = false;
				button7.enabled = false;
			}
			
			button1.enabled = false;
		}
		else if(this.selected.equals("pardon"))
		{
			container.add(this.playerField);
			container.add(button6 = new GuiButtonTooltip(x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.multiplayer.pardon"), this.builderPardon.toActualCommand(), () ->
			{
				CommandHelper.sendCommand(this.builderPardon);
			}));
			
			if(this.playerField.getText().isEmpty())
			{
				button6.enabled = false;
			}
			
			button2.enabled = false;
		}
		else if(this.selected.equals("permissions"))
		{
			container.add(this.playerField);
			container.add(button6 = new GuiButtonTooltip(x + 118, y + 24 + 12, 114, 20, I18n.format("gui.worldhandler.multiplayer.permissions.give"), this.builderOp.toActualCommand(), () ->
			{
				CommandHelper.sendCommand(this.builderOp);
			}));
			container.add(button7 = new GuiButtonTooltip(x + 118, y + 48 + 12, 114, 20, I18n.format("gui.worldhandler.multiplayer.permissions.take"), this.builderDeop.toActualCommand(), () ->
			{
				CommandHelper.sendCommand(this.builderDeop);
			}));
			
			if(this.playerField.getText().isEmpty())
			{
				button6.enabled = false;
				button7.enabled = false;
			}
			
			button3.enabled = false;
		}
		else if(this.selected.equals("runtime"))
		{
			container.add(new GuiButtonTooltip(x + 118, y, 114, 20, I18n.format("gui.worldhandler.multiplayer.runtime.save_world"), this.builderSaveAll.toActualCommand(), () ->
			{
				CommandHelper.sendCommand(this.builderSaveAll);
			}));
			container.add(new GuiButtonTooltip(x + 118, y + 24, 114, 20, I18n.format("gui.worldhandler.multiplayer.runtime.autosave", I18n.format("gui.worldhandler.generic.on")), this.builderSaveOn.toActualCommand(), () ->
			{
				CommandHelper.sendCommand(this.builderSaveOn);
			}));
			container.add(new GuiButtonTooltip(x + 118, y + 48, 114, 20, TextFormatting.RED + I18n.format("gui.worldhandler.multiplayer.runtime.autosave", I18n.format("gui.worldhandler.generic.off")), this.builderSaveOff.toActualCommand(), () ->
			{
				Minecraft.getInstance().displayGuiScreen(new GuiWorldHandler(Contents.CONTINUE.withBuilder(this.builderSaveOff).withParent(Contents.MULTIPLAYER)));
			}));
			container.add(new GuiButtonTooltip(x + 118, y + 72, 114, 20, TextFormatting.RED + I18n.format("gui.worldhandler.multiplayer.runtime.stop_server"), this.builderStop.toActualCommand(), () ->
			{
				Minecraft.getInstance().displayGuiScreen(new GuiWorldHandler(Contents.CONTINUE.withBuilder(this.builderStop).withParent(Contents.MULTIPLAYER)));
			}));
			
			button4.enabled = false;
		}
		else if(this.selected.equals("whitelist"))
		{
			container.add(this.playerField);
			container.add(button6 = new GuiButtonBase(x + 118, y + 24, 44, 20, I18n.format("gui.worldhandler.multiplayer.whitelist.add"), () ->
			{
				CommandHelper.sendCommand(this.builderWhitelist.getBuilder(EnumMode.ADD));
			}));
			container.add(button7 = new GuiButtonBase(x + 118 + 47, y + 24, 44, 20, I18n.format("gui.worldhandler.multiplayer.whitelist.remove"), () ->
			{
				CommandHelper.sendCommand(this.builderWhitelist.getBuilder(EnumMode.REMOVE));
			}));
			
			container.add(new GuiButtonBase(x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.multiplayer.whitelist.whitelist", I18n.format("gui.worldhandler.generic.on")), () ->
			{
				CommandHelper.sendCommand(this.builderWhitelist.getBuilder(EnumMode.ON));
			}));
			container.add(new GuiButtonBase(x + 118, y + 72, 114, 20, I18n.format("gui.worldhandler.multiplayer.whitelist.whitelist", I18n.format("gui.worldhandler.generic.off")), () ->
			{
				CommandHelper.sendCommand(this.builderWhitelist.getBuilder(EnumMode.OFF));
			}));
			
			container.add(new GuiButtonIcon(x + 232 - 20, y + 24, 20, 20, EnumIcon.RELOAD, I18n.format("gui.worldhandler.multiplayer.whitelist.reload"), () ->
			{
				CommandHelper.sendCommand(this.builderWhitelist.getBuilder(EnumMode.RELOAD));
			}));
			
			if(this.playerField.getText().isEmpty())
			{
				button6.enabled = false;
				button7.enabled = false;
			}
			
			button5.enabled = false;
		}
	}
	
	@Override
	public void tick(Container container)
	{
		if(this.selected.equals("kickBan"))
		{
			this.reasonField.tick();
		}
		
		if(!this.selected.equals("runtime"))
		{
			this.playerField.tick();
		}
	}
	
	@Override
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(this.selected.equals("kickBan"))
		{
			this.reasonField.drawTextField(mouseX, mouseY, partialTicks);
		}
		
		if(!this.selected.equals("runtime"))
		{
			this.playerField.drawTextField(mouseX, mouseY, partialTicks);
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
	public String getTitle()
	{
		return I18n.format("gui.worldhandler.title.multiplayer");
	}
	
	@Override
	public String getTabTitle()
	{
		return I18n.format("gui.worldhandler.tab.multiplayer");
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
}
