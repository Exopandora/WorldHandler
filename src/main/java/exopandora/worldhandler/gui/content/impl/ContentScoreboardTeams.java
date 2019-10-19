package exopandora.worldhandler.gui.content.impl;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Predicates;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderTeams;
import exopandora.worldhandler.builder.impl.BuilderTeams.EnumMode;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.content.element.impl.ElementMultiButtonList;
import exopandora.worldhandler.gui.content.impl.abstr.ContentScoreboard;
import exopandora.worldhandler.gui.logic.ILogicClickList;
import exopandora.worldhandler.helper.ActionHelper;
import exopandora.worldhandler.helper.CommandHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ContentScoreboardTeams extends ContentScoreboard
{
	private GuiTextFieldTooltip teamField;
	
	private String team;
	private String selectedTeam = "add";
	
	private final BuilderTeams builderTeams = new BuilderTeams();
	
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
		this.teamField.setResponder(text ->
		{
			this.team = text;
			this.builderTeams.setTeam(this.team);
			container.initButtons();
		});
		
		if(this.selectedTeam.equals("option"))
		{
			ElementMultiButtonList options = new ElementMultiButtonList(x + 118, y + 24, HELPER.getOptions(), 2, new ILogicClickList()
			{
				@Override
				public String translate(String key, int depth)
				{
					if(depth == 0)
					{
						return I18n.format("gui.worldhandler.scoreboard.team.options." + key);
					}
					else if(depth == 1)
					{
						if(Arrays.stream(TextFormatting.values()).map(TextFormatting::getFriendlyName).anyMatch(Predicates.equalTo(key)))
						{
							return I18n.format("gui.worldhandler.color." + key);
						}
						
						return I18n.format("gui.worldhandler.scoreboard.team.suboption." + key);
					}
					
					return key;
				}
				
				@Override
				public String buildEventKey(List<String> keys, int depth)
				{
					return ILogicClickList.super.buildTranslationKey(keys, depth);
				}
				
				@Override
				public void onClick(String key, int depth)
				{
					if(depth == 0)
					{
						ContentScoreboardTeams.this.builderTeams.setRule(key);
					}
					else if(depth == 1)
					{
						ContentScoreboardTeams.this.builderTeams.setValue(key);
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
		GuiButtonBase button1;
		GuiButtonBase button2;
		GuiButtonBase button3;
		GuiButtonBase button4;
		
		container.add(new GuiButtonBase(x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(button1 = new GuiButtonBase(x, y, 114, 20, I18n.format("gui.worldhandler.scoreboard.team.create"), () ->
		{
			this.selectedTeam = "add";
			container.init();
		}));
		container.add(button2 = new GuiButtonBase(x, y + 24, 114, 20, I18n.format("gui.worldhandler.scoreboard.team.join") + " / " + I18n.format("gui.worldhandler.scoreboard.team.leave"), () ->
		{
			this.selectedTeam = "join|leave";
			container.init();
		}));
		container.add(button3 = new GuiButtonBase(x, y + 48, 114, 20, I18n.format("gui.worldhandler.scoreboard.team.remove") + " / " + I18n.format("gui.worldhandler.scoreboard.team.empty"), () ->
		{
			this.selectedTeam = "remove|empty";
			container.init();
		}));
		container.add(button4 = new GuiButtonBase(x, y + 72, 114, 20, I18n.format("gui.worldhandler.scoreboard.team.options"), () ->
		{
			this.selectedTeam = "option";
			container.init();
		}));
		
		button1.active = !this.selectedTeam.equals("add");
		button2.active = !this.selectedTeam.equals("join|leave");
		button3.active = !this.selectedTeam.equals("remove|empty");
		button4.active = !this.selectedTeam.equals("option");
		
		this.builderTeams.setMode(this.selectedTeam);
		
		boolean enabled = this.team != null && this.team.length() > 0;
		
		if(this.selectedTeam.equals("add"))
		{
			this.builderTeams.setTeam(this.team);
		}
		else if(this.selectedTeam.equals("join|leave"))
		{
			this.builderTeams.setPlayer(container.getPlayer());
			
			container.add(button1 = new GuiButtonBase(x + 118, y + 36, 114, 20, I18n.format("gui.worldhandler.scoreboard.team.join"), () ->
			{
				CommandHelper.sendCommand(this.builderTeams.getBuilderForMode(EnumMode.JOIN));
				container.initButtons();
			}));
			container.add(new GuiButtonBase(x + 118, y + 60, 114, 20, I18n.format("gui.worldhandler.scoreboard.team.leave"), () ->
			{
				CommandHelper.sendCommand(this.builderTeams.getBuilderForMode(EnumMode.LEAVE));
				container.initButtons();
			}));
			
			button1.active = enabled;
		}
		else if(this.selectedTeam.equals("remove|empty"))
		{
			container.add(button1 = new GuiButtonBase(x + 118, y + 36, 114, 20, I18n.format("gui.worldhandler.scoreboard.team.remove"), () ->
			{
				CommandHelper.sendCommand(this.builderTeams.getBuilderForMode(EnumMode.REMOVE));
				container.initButtons();
			}));
			container.add(button2 = new GuiButtonBase(x + 118, y + 60, 114, 20, I18n.format("gui.worldhandler.scoreboard.team.empty"), () ->
			{
				CommandHelper.sendCommand(this.builderTeams.getBuilderForMode(EnumMode.EMPTY));
				container.initButtons();
			}));
			
			button1.active = enabled;
			button2.active = enabled;
		}
		
		if(!this.selectedTeam.equals("join|leave") && !this.selectedTeam.equals("remove|empty"))
		{
			int yOffset = this.selectedTeam.equals("option") ? 24 : 0;
			
			container.add(button1 = new GuiButtonBase(x + 118, y + 48 + yOffset, 114, 20, I18n.format("gui.worldhandler.actions.perform"), () ->
			{
				CommandHelper.sendCommand(this.builderTeams);
				container.initButtons();
			}));
			button1.active = enabled;
		}
		
		container.add(this.teamField);
	}
	
	@Override
	public void tick(Container container)
	{
		this.teamField.tick();
	}
	
	@Override
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		this.teamField.renderButton(mouseX, mouseY, partialTicks);
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
