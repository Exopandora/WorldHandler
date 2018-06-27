package exopandora.worldhandler.gui.content.impl;

import java.util.Arrays;

import com.google.common.base.Predicates;

import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderScoreboardTeams;
import exopandora.worldhandler.builder.impl.BuilderScoreboardTeams.EnumMode;
import exopandora.worldhandler.format.EnumColor;
import exopandora.worldhandler.gui.button.GuiButtonWorldHandler;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.content.element.impl.ElementClickList;
import exopandora.worldhandler.gui.content.element.logic.ILogicClickList;
import exopandora.worldhandler.gui.content.impl.abstr.ContentScoreboard;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ContentScoreboardTeams extends ContentScoreboard
{
	private GuiTextFieldTooltip teamField;
	
	private String team;
	private String selectedTeam = "add";
	
	private final BuilderScoreboardTeams builderTeams = new BuilderScoreboardTeams();
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		return this.builderTeams;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.teamField = new GuiTextFieldTooltip(x + 118, y + (this.selectedTeam.equals("option") ? 0 : (this.selectedTeam.equals("add") ? 24 : 12)), 114, 20, I18n.format("gui.worldhandler.scoreboard.team.team"));
		this.teamField.setValidator(Predicates.notNull());
		this.teamField.setText(this.team);
		
		if(this.selectedTeam.equals("option"))
		{
			ElementClickList options = new ElementClickList(x + 118, y + 24, HELPER.getOptions(), new int[] {6, 7}, this, new ILogicClickList()
			{
				@Override
				public String translate(String... keys)
				{
					if(keys.length > 1)
					{
						if(Arrays.stream(EnumColor.values()).map(EnumColor::getFormat).anyMatch(Predicates.equalTo(keys[1])))
						{
							return I18n.format("gui.worldhandler.color." + keys[1]);
						}
						
						return I18n.format("gui.worldhandler.scoreboard.team.suboption." + keys[1]);
					}
					else
					{
						return I18n.format("gui.worldhandler.scoreboard.team.options." + keys[0]);
					}
				}
				
				@Override
				public void consumeKey(String... keys)
				{
					if(keys.length > 1)
					{
						builderTeams.setValue(keys[1]);
					}
					else
					{
						builderTeams.setRule(keys[0]);
					}
				}
				
				@Override
				public String getId()
				{
					return "options";
				}
			});
			
			container.add(options);
		}
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonWorldHandler button3;
		GuiButtonWorldHandler button4;
		GuiButtonWorldHandler button5;
		GuiButtonWorldHandler button6;
		
		container.add(new GuiButtonWorldHandler(0, x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back")));
		container.add(new GuiButtonWorldHandler(1, x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame")));
		
		container.add(button3 = new GuiButtonWorldHandler(2, x, y, 114, 20, I18n.format("gui.worldhandler.scoreboard.team.create")));
		container.add(button4 = new GuiButtonWorldHandler(3, x, y + 24, 114, 20, I18n.format("gui.worldhandler.scoreboard.team.join") + " / " + I18n.format("gui.worldhandler.scoreboard.team.leave")));
		container.add(button5 = new GuiButtonWorldHandler(4, x, y + 48, 114, 20, I18n.format("gui.worldhandler.scoreboard.team.remove") + " / " + I18n.format("gui.worldhandler.scoreboard.team.empty")));
		container.add(button6 = new GuiButtonWorldHandler(5, x, y + 72, 114, 20, I18n.format("gui.worldhandler.scoreboard.team.options")));
		
		button3.enabled = !this.selectedTeam.equals("add");
		button4.enabled = !this.selectedTeam.equals("join|leave");
		button5.enabled = !this.selectedTeam.equals("remove|empty");
		button6.enabled = !this.selectedTeam.equals("option");
		
		this.builderTeams.setMode(this.selectedTeam);
		
		boolean enabled = this.team != null && this.team.length() > 0;
		
		if(this.selectedTeam.equals("add"))
		{
			this.builderTeams.setTeam(this.team);
		}
		else if(this.selectedTeam.equals("join|leave"))
		{
			this.builderTeams.setPlayer(container.getPlayer());
			
			container.add(button3 = new GuiButtonWorldHandler(9, x + 118, y + 36, 114, 20, I18n.format("gui.worldhandler.scoreboard.team.join")));
			container.add(new GuiButtonWorldHandler(10, x + 118, y + 60, 114, 20, I18n.format("gui.worldhandler.scoreboard.team.leave")));
			
			button3.enabled = enabled;
		}
		else if(this.selectedTeam.equals("remove|empty"))
		{
			container.add(button3 = new GuiButtonWorldHandler(11, x + 118, y + 36, 114, 20, I18n.format("gui.worldhandler.scoreboard.team.remove")));
			container.add(button4 = new GuiButtonWorldHandler(12, x + 118, y + 60, 114, 20, I18n.format("gui.worldhandler.scoreboard.team.empty")));
			
			button3.enabled = enabled;
			button4.enabled = enabled;
		}
		
		if(!this.selectedTeam.equals("join|leave") && !this.selectedTeam.equals("remove|empty"))
		{
			int yOffset = this.selectedTeam.equals("option") ? 24 : 0;
			
			container.add(button3 = new GuiButtonWorldHandler(8, x + 118, y + 48 + yOffset, 114, 20, I18n.format("gui.worldhandler.actions.perform")));
			button3.enabled = enabled;
		}
	}
	
	@Override
	public void actionPerformed(Container container, GuiButton button)
	{
		switch(button.id)
		{
			case 2:
				this.selectedTeam = "add";
				container.initGui();
				break;
			case 3:
				this.selectedTeam = "join|leave";
				container.initGui();
				break;
			case 4:
				this.selectedTeam = "remove|empty";
				container.initGui();
				break;
			case 5:
				this.selectedTeam = "option";
				container.initGui();
				break;
			case 8:
				WorldHandler.sendCommand(this.builderTeams);
				container.initButtons();
				break;
			case 9:
				WorldHandler.sendCommand(this.builderTeams.getBuilderForMode(EnumMode.JOIN));
				container.initButtons();
				break;
			case 10:
				WorldHandler.sendCommand(this.builderTeams.getBuilderForMode(EnumMode.LEAVE));
				container.initButtons();
				break;
			case 11:
				WorldHandler.sendCommand(this.builderTeams.getBuilderForMode(EnumMode.REMOVE));
				container.initButtons();
				break;
			case 12:
				WorldHandler.sendCommand(this.builderTeams.getBuilderForMode(EnumMode.EMPTY));
				container.initButtons();
				break;
			default:
				break;
		}
	}
	
	@Override
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		this.teamField.drawTextBox();
	}
	
	@Override
	public void keyTyped(Container container, char typedChar, int keyCode)
	{
		if(this.teamField.textboxKeyTyped(typedChar, keyCode))
		{
			this.team = this.teamField.getText();
			this.builderTeams.setTeam(this.team);
			container.initButtons();
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		this.teamField.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	public String getTabTitle()
	{
		return I18n.format("gui.worldhandler.tab.scoreboard.teams");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.SCOREBOARD_TEAMS;
	}
	
	@Override
	public void onPlayerNameChanged(String username)
	{
		if(this.selectedTeam.equals("join|leave"))
		{
			this.builderTeams.setPlayer(username);
		}
	}
}
