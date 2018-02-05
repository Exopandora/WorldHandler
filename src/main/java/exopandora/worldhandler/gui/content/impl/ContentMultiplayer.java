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
import exopandora.worldhandler.gui.button.EnumTooltip;
import exopandora.worldhandler.gui.button.GuiButtonWorldHandler;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.container.impl.GuiWorldHandlerContainer;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.main.WorldHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
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
		
		this.reasonField = new GuiTextFieldTooltip(x + 118, y + 24 + this.shiftDown, 114, 20, I18n.format("gui.worldhandler.multiplayer.kick_ban.reason"));
		this.reasonField.setValidator(Predicates.notNull());
		this.reasonField.setFocused(false);
		this.reasonField.setText(this.builderKick.getReason());
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonWorldHandler button3;
		GuiButtonWorldHandler button4;
		GuiButtonWorldHandler button5;
		GuiButtonWorldHandler button6;
		GuiButtonWorldHandler button7;
		GuiButtonWorldHandler button8;
		GuiButtonWorldHandler button9;
		
		container.add(new GuiButtonWorldHandler(1, x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame")));
		
		container.add(button3 = new GuiButtonWorldHandler(3, x, y, 114, 20, I18n.format("gui.worldhandler.multiplayer.kick") + " / " + I18n.format("gui.worldhandler.multiplayer.ban")));
		container.add(button4 = new GuiButtonWorldHandler(4, x, y + 24, 114, 20, I18n.format("gui.worldhandler.multiplayer.pardon")));
		container.add(button5 = new GuiButtonWorldHandler(5, x, y + 48, 114, 20, I18n.format("gui.worldhandler.multiplayer.permissions")));
		container.add(button6 = new GuiButtonWorldHandler(6, x, y + 72, 114, 20, I18n.format("gui.worldhandler.multiplayer.runtime")));
		container.add(button7 = new GuiButtonWorldHandler(7, x, y + 96, 114, 20, I18n.format("gui.worldhandler.multiplayer.whitelist")));
		
		if(this.selected.equals("kickBan"))
		{
			container.add(button8 = new GuiButtonWorldHandler(8, x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.multiplayer.kick"), this.builderKick.toActualCommand(), EnumTooltip.TOP_RIGHT));
			container.add(button9 = new GuiButtonWorldHandler(9, x + 118, y + 72, 114, 20, I18n.format("gui.worldhandler.multiplayer.ban"), this.builderBan.toActualCommand(), EnumTooltip.TOP_RIGHT));
			
			if(this.playerField.getText().isEmpty())
			{
				button8.enabled = false;
				button9.enabled = false;
			}
			
			button3.enabled = false;
		}
		else if(this.selected.equals("pardon"))
		{
			container.add(button8 = new GuiButtonWorldHandler(10, x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.multiplayer.pardon"), this.builderPardon.toActualCommand(), EnumTooltip.TOP_RIGHT));
			
			if(this.playerField.getText().isEmpty())
			{
				button8.enabled = false;
			}
			
			button4.enabled = false;
		}
		else if(this.selected.equals("permissions"))
		{
			container.add(button8 = new GuiButtonWorldHandler(11, x + 118, y + 24 + 12, 114, 20, I18n.format("gui.worldhandler.multiplayer.permissions.give"), this.builderOp.toActualCommand(), EnumTooltip.TOP_RIGHT));
			container.add(button9 = new GuiButtonWorldHandler(12, x + 118, y + 48 + 12, 114, 20, I18n.format("gui.worldhandler.multiplayer.permissions.take"), this.builderDeop.toActualCommand(), EnumTooltip.TOP_RIGHT));
			
			if(this.playerField.getText().isEmpty())
			{
				button8.enabled = false;
				button9.enabled = false;
			}
			
			button5.enabled = false;
		}
		else if(this.selected.equals("runtime"))
		{
			container.add(new GuiButtonWorldHandler(13, x + 118, y, 114, 20, I18n.format("gui.worldhandler.multiplayer.runtime.save_world"), this.builderSaveAll.toActualCommand(), EnumTooltip.TOP_RIGHT));
			container.add(new GuiButtonWorldHandler(14, x + 118, y + 24, 114, 20, I18n.format("gui.worldhandler.multiplayer.runtime.autosave", I18n.format("gui.worldhandler.generic.on"), this.builderSaveOn.toActualCommand(), EnumTooltip.TOP_RIGHT)));
			container.add(new GuiButtonWorldHandler(15, x + 118, y + 48, 114, 20, TextFormatting.RED + I18n.format("gui.worldhandler.multiplayer.runtime.autosave", I18n.format("gui.worldhandler.generic.off"), this.builderSaveOff.toActualCommand(), EnumTooltip.TOP_RIGHT)));
			container.add(new GuiButtonWorldHandler(16, x + 118, y + 72, 114, 20, TextFormatting.RED + I18n.format("gui.worldhandler.multiplayer.runtime.stop_server"), this.builderStop.toActualCommand(), EnumTooltip.TOP_RIGHT));
			
			button6.enabled = false;
		}
		else if(this.selected.equals("whitelist"))
		{
			container.add(button8 = new GuiButtonWorldHandler(17, x + 118, y + 24, 44, 20, I18n.format("gui.worldhandler.multiplayer.whitelist.add")));
			container.add(button9 = new GuiButtonWorldHandler(18, x + 118 + 47, y + 24, 44, 20, I18n.format("gui.worldhandler.multiplayer.whitelist.remove")));
			
			container.add(new GuiButtonWorldHandler(19, x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.multiplayer.whitelist.whitelist", I18n.format("gui.worldhandler.generic.on"))));
			container.add(new GuiButtonWorldHandler(20, x + 118, y + 72, 114, 20, I18n.format("gui.worldhandler.multiplayer.whitelist.whitelist", I18n.format("gui.worldhandler.generic.off"))));
			
			container.add(new GuiButtonWorldHandler(21, x + 232 - 20, y + 24, 20, 20, null, I18n.format("gui.worldhandler.multiplayer.whitelist.reload"), EnumTooltip.TOP_RIGHT, EnumIcon.RELOAD));
			
			if(this.playerField.getText().isEmpty())
			{
				button8.enabled = false;
				button9.enabled = false;
			}
			
			button7.enabled = false;
		}
	}
	
	@Override
	public void actionPerformed(Container container, GuiButton button)
	{
		switch(button.id)
		{
			case 3:
				this.selected = "kickBan";
				this.shiftDown = 0;
				container.initGui();
				break;
			case 4:
				this.selected = "pardon";
				this.shiftDown = 24;
				container.initGui();
				break;
			case 5:
				this.selected = "permissions";
				this.shiftDown = 12;
				container.initGui();
				break;
			case 6:
				this.selected = "runtime";
				this.shiftDown = 0;
				container.initGui();
				break;
			case 7:
				this.selected = "whitelist";
				this.shiftDown = 0;
				container.initGui();
				break;
			case 8:
				WorldHandler.sendCommand(this.builderKick);
				break;
			case 9:
				WorldHandler.sendCommand(this.builderBan);
				break;
			case 10:
				WorldHandler.sendCommand(this.builderPardon);
				break;
			case 11:
				WorldHandler.sendCommand(this.builderOp);
				break;
			case 12:
				WorldHandler.sendCommand(this.builderDeop);
				break;
			case 13:
				WorldHandler.sendCommand(this.builderSaveAll);
				break;
			case 14:
				WorldHandler.sendCommand(this.builderSaveOn);
				break;
			case 15:
				Minecraft.getMinecraft().displayGuiScreen(new GuiWorldHandlerContainer(Contents.CONTINUE.withBuilder(this.builderSaveOff).withParent(Contents.MULTIPLAYER)));
				break;
			case 16:
				Minecraft.getMinecraft().displayGuiScreen(new GuiWorldHandlerContainer(Contents.CONTINUE.withBuilder(this.builderStop).withParent(Contents.MULTIPLAYER)));
				break;
			case 17:
				WorldHandler.sendCommand(this.builderWhitelist.getBuilder(EnumMode.ADD));
				break;
			case 18:
				WorldHandler.sendCommand(this.builderWhitelist.getBuilder(EnumMode.REMOVE));
				break;
			case 19:
				WorldHandler.sendCommand(this.builderWhitelist.getBuilder(EnumMode.ON));
				break;
			case 20:
				WorldHandler.sendCommand(this.builderWhitelist.getBuilder(EnumMode.OFF));
				break;
			case 21:
				WorldHandler.sendCommand(this.builderWhitelist.getBuilder(EnumMode.RELOAD));
				break;
			default:
				break;
		}
	}
	
	@Override
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(this.selected.equals("kickBan"))
		{
			this.reasonField.drawTextBox();
		}
		
		if(!this.selected.equals("runtime"))
		{
			this.playerField.drawTextBox();
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
	public String[] getHeadline()
	{
		return new String[]{I18n.format("gui.worldhandler.generic.browse"), I18n.format("gui.worldhandler.generic.options")};
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.MULTIPLAYER;
	}
	
	@Override
	public void keyTyped(Container container, char typedChar, int keyCode)
	{
		if(this.playerField.textboxKeyTyped(typedChar, keyCode))
		{
			this.setPlayer(this.playerField.getText());
			container.initButtons();
		}
		
		if(this.reasonField.textboxKeyTyped(typedChar, keyCode))
		{
			this.setReason(this.reasonField.getText());
			container.initButtons();
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		this.playerField.mouseClicked(mouseX, mouseY, mouseButton);
		this.reasonField.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	public Content getBackContent()
	{
		return null;
	}
}
