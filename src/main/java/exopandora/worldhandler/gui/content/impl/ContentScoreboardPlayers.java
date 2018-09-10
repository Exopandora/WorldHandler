package exopandora.worldhandler.gui.content.impl;

import com.google.common.base.Predicate;

import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderScoreboardPlayers;
import exopandora.worldhandler.builder.impl.BuilderScoreboardPlayers.EnumPoints;
import exopandora.worldhandler.builder.impl.BuilderScoreboardPlayers.EnumTag;
import exopandora.worldhandler.config.ConfigSliders;
import exopandora.worldhandler.gui.button.EnumTooltip;
import exopandora.worldhandler.gui.button.GuiButtonWorldHandler;
import exopandora.worldhandler.gui.button.GuiSlider;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.button.responder.SimpleResponder;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.content.impl.abstr.ContentScoreboard;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ContentScoreboardPlayers extends ContentScoreboard
{
	private GuiTextFieldTooltip objectField;
	private GuiTextFieldTooltip tagField;
	
	private final BuilderScoreboardPlayers builderPlayers = new BuilderScoreboardPlayers();
	private final Predicate<String> nonNullNoSpace = string -> string != null && !string.matches("(.* .*)+");
	
	private String selectedPlayer = "add|set|remove";
	private String tag;
	
	private GuiButtonWorldHandler addButton;
	private GuiButtonWorldHandler removeButton;
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		return this.builderPlayers;
	}
	
	@Override
	public void init(Container container)
	{
		if(this.builderPlayers.getPoints() > ConfigSliders.getMaxPlayerPoints())
		{
			this.builderPlayers.setPoints((int) ConfigSliders.getMaxPlayerPoints());
		}
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.objectField = new GuiTextFieldTooltip(x + 118, y + (this.selectedPlayer.equals("enable") ? 24 : 0), 114, 20, I18n.format("gui.worldhandler.scoreboard.objectives.objective"));
		this.objectField.setValidator(this.nonNullNoSpace);
		this.objectField.setText(this.objective);
		
		this.tagField = new GuiTextFieldTooltip(x + 118, y + 12, 114, 20, I18n.format("gui.worldhandler.scoreboard.players.tag"));
		this.tagField.setValidator(this.nonNullNoSpace);
		this.tagField.setText(this.tag);
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonWorldHandler button3;
		GuiButtonWorldHandler button4;
		GuiButtonWorldHandler button5;
		
		container.add(new GuiButtonWorldHandler(0, x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back")));
		container.add(new GuiButtonWorldHandler(1, x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame")));
		
		container.add(button3 = new GuiButtonWorldHandler(2, x, y + 12, 114, 20, I18n.format("gui.worldhandler.scoreboard.players.points")));
		container.add(button4 = new GuiButtonWorldHandler(3, x, y + 36, 114, 20, I18n.format("gui.worldhandler.scoreboard.players.tag")));
		container.add(button5 = new GuiButtonWorldHandler(4, x, y + 60, 114, 20, I18n.format("gui.worldhandler.scoreboard.players.trigger")));
		
		button3.enabled = !this.selectedPlayer.equals("add|set|remove");
		button4.enabled = !this.selectedPlayer.equals("tag");
		button5.enabled = !this.selectedPlayer.equals("enable");
		
		this.builderPlayers.setMode(this.selectedPlayer);
		
		boolean objective = this.builderPlayers.getObjective() != null && !this.builderPlayers.getObjective().isEmpty();
		
		if(this.selectedPlayer.equals("add|set|remove"))
		{
			container.add(new GuiSlider<String>(this, container, "points", x + 118, y + 24, 114, 20, I18n.format("gui.worldhandler.scoreboard.players.points"), 0, ConfigSliders.getMaxPlayerPoints(), 0, new SimpleResponder<String>(value ->
			{
				this.builderPlayers.setPoints(value);
			})));
			
			container.add(this.addButton = new GuiButtonWorldHandler(5, x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.actions.add")));
			container.add(this.removeButton = new GuiButtonWorldHandler(6, x + 118, y + 72, 56, 20, I18n.format("gui.worldhandler.actions.remove")));
			container.add(button5 = new GuiButtonWorldHandler(7, x + 118 + 114 / 2 + 1, y + 72, 56, 20, I18n.format("gui.worldhandler.actions.reset"), I18n.format("gui.worldhandler.actions.set_to_0"), EnumTooltip.TOP_RIGHT));
			
			boolean enabled = objective && this.builderPlayers.getPoints() > 0;
			
			this.addButton.enabled = enabled;
			this.removeButton.enabled = enabled;
			button5.enabled = objective;
		}
		else if(this.selectedPlayer.equals("tag"))
		{
			container.add(button3 = new GuiButtonWorldHandler(8, x + 118, y + 36, 114, 20, I18n.format("gui.worldhandler.actions.add")));
			container.add(button4 = new GuiButtonWorldHandler(9, x + 118, y + 60, 114, 20, I18n.format("gui.worldhandler.actions.remove")));
			
			boolean enabled = this.tag != null && !this.tag.isEmpty();
			
			button3.enabled = enabled;
			button4.enabled = enabled;
		}
		else if(this.selectedPlayer.equals("enable"))
		{
			container.add(button3 = new GuiButtonWorldHandler(10, x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.generic.enable")));
			
			button3.enabled = objective;
		}
		
		if(this.selectedPlayer.equals("tag"))
		{
			this.builderPlayers.setTag(this.tag);
		}
		else
		{
			this.builderPlayers.setObjective(this.objective);
		}
	}
	
	@Override
	public void updateScreen(Container container)
	{
		if(this.selectedPlayer.equals("add|set|remove"))
		{
			boolean objective = this.builderPlayers.getObjective() != null && !this.builderPlayers.getObjective().isEmpty();
			boolean enabled = objective && this.builderPlayers.getPoints() > 0;
			
			this.addButton.enabled = enabled;
			this.removeButton.enabled = enabled;
		}
	}
	
	@Override
	public void actionPerformed(Container container, GuiButton button) throws Exception
	{
		switch(button.id)
		{
			case 2:
				this.selectedPlayer = "add|set|remove";
				container.initGui();
				break;
			case 3:
				this.selectedPlayer = "tag";
				container.initGui();
				break;
			case 4:
				this.selectedPlayer = "enable";
				container.initGui();
				break;
			case 5:
				WorldHandler.sendCommand(this.builderPlayers.getBuilderForPoints(EnumPoints.ADD));
				container.initGui();
				break;
			case 6:
				WorldHandler.sendCommand(this.builderPlayers.getBuilderForPoints(EnumPoints.REMOVE));
				container.initGui();
				break;
			case 7:
				WorldHandler.sendCommand(this.builderPlayers.getBuilderForPoints(EnumPoints.SET, 0));
				container.initGui();
				break;
			case 8:
				WorldHandler.sendCommand(this.builderPlayers.getBuilderForTag(EnumTag.ADD));
				container.initGui();
				break;
			case 9:
				WorldHandler.sendCommand(this.builderPlayers.getBuilderForTag(EnumTag.REMOVE));
				container.initGui();
				break;
			case 10:
				WorldHandler.sendCommand(this.builderPlayers);
				container.initGui();
				break;
			default:
				break;
		}
	}
	
	@Override
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(this.selectedPlayer.equals("tag"))
		{
			this.tagField.drawTextBox();
		}
		else
		{
			this.objectField.drawTextBox();
		}
	}
	
	@Override
	public void keyTyped(Container container, char typedChar, int keyCode)
	{
		if(this.objectField.textboxKeyTyped(typedChar, keyCode))
		{
			this.objective = this.objectField.getText();
			this.builderPlayers.setObjective(this.objective);
			container.initButtons();
		}
		
		if(this.tagField.textboxKeyTyped(typedChar, keyCode))
		{
			this.tag = this.tagField.getText();
			this.builderPlayers.setTag(this.tag);
			container.initButtons();
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		if(this.selectedPlayer.equals("tag"))
		{
			this.tagField.mouseClicked(mouseX, mouseY, mouseButton);
		}
		else
		{
			this.objectField.mouseClicked(mouseX, mouseY, mouseButton);
		}
	}
	
	@Override
	public String getTabTitle()
	{
		return I18n.format("gui.worldhandler.tab.scoreboard.players");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.SCOREBOARD_PLAYERS;
	}
	
	@Override
	public void onPlayerNameChanged(String username)
	{
		this.builderPlayers.setPlayer(username);
	}
}
