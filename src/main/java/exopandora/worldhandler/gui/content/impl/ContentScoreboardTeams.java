package exopandora.worldhandler.gui.content.impl;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Predicates;

import exopandora.worldhandler.builder.impl.TeamCommandBuilder;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.widget.menu.impl.ILogicButtonList;
import exopandora.worldhandler.gui.widget.menu.impl.MenuButtonList;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ContentScoreboardTeams extends ContentScoreboard
{
	private final TeamCommandBuilder builderTeams = new TeamCommandBuilder();
	private final CommandPreview previewAdd = new CommandPreview(this.builderTeams, TeamCommandBuilder.Label.ADD_DISPLAYNAME);
	private final CommandPreview previewJoinOrLeave = new CommandPreview()
			.add(this.builderTeams, TeamCommandBuilder.Label.JOIN)
			.add(this.builderTeams, TeamCommandBuilder.Label.LEAVE);
	private final CommandPreview previewRemoveOrEmpty = new CommandPreview()
			.add(this.builderTeams, TeamCommandBuilder.Label.REMOVE)
			.add(this.builderTeams, TeamCommandBuilder.Label.EMPTY);
	private final CommandPreview previewModify = new CommandPreview(this.builderTeams, TeamCommandBuilder.Label.MODIFY);
	
	private GuiTextFieldTooltip teamField;
	private Page page = Page.ADD;
	
	@Override
	public CommandPreview getCommandPreview()
	{
		switch(this.page)
		{
			case ADD:
				return this.previewAdd;
			case JOIN_OR_LEAVE:
				return this.previewJoinOrLeave;
			case REMOVE_OR_EMPTY:
				return this.previewRemoveOrEmpty;
			case OPTION:
				return this.previewModify;
			default:
				return null;
		}
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.teamField = new GuiTextFieldTooltip(x + 118, y + this.page.getShift(), 114, 20, Component.translatable("gui.worldhandler.scoreboard.team.team"));
		this.teamField.setFilter(Predicates.notNull());
		this.teamField.setValue(this.builderTeams.team().get());
		this.teamField.setResponder(text ->
		{
			this.builderTeams.team().set(text);
			this.builderTeams.displayName().deserialize(text);
			container.initButtons();
		});
		
		if(Page.OPTION.equals(this.page))
		{
			MenuButtonList options = new MenuButtonList(x + 118, y + 24, HELPER.getOptions(), 2, new ILogicButtonList()
			{
				@Override
				public MutableComponent translate(String key, int depth)
				{
					if(depth == 0)
					{
						return Component.translatable("gui.worldhandler.scoreboard.team.options." + key);
					}
					else if(depth == 1)
					{
						if(Arrays.stream(ChatFormatting.values()).map(ChatFormatting::getName).anyMatch(Predicates.equalTo(key)))
						{
							return Component.translatable("gui.worldhandler.color." + key);
						}
						
						return Component.translatable("gui.worldhandler.scoreboard.team.suboption." + key);
					}
					
					return Component.literal(key);
				}
				
				@Override
				public String buildEventKey(List<String> keys, int depth)
				{
					return ILogicButtonList.super.buildTranslationKey(keys, depth);
				}
				
				@Override
				public void onClick(String key, int depth)
				{
					if(depth == 0)
					{
						ContentScoreboardTeams.this.builderTeams.option().set(key);
					}
					else if(depth == 1)
					{
						ContentScoreboardTeams.this.builderTeams.value().set(key);
					}
				}
				
				@Override
				public String getId()
				{
					return "options";
				}
			});
			
			container.addMenu(options);
		}
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
		
		container.addRenderableWidget(button1 = new GuiButtonBase(x, y, 114, 20, Component.translatable("gui.worldhandler.scoreboard.team.create"), () ->
		{
			this.page = Page.ADD;
			container.init();
		}));
		container.addRenderableWidget(button2 = new GuiButtonBase(x, y + 24, 114, 20, Component.literal(I18n.get("gui.worldhandler.scoreboard.team.join") + " / " + I18n.get("gui.worldhandler.scoreboard.team.leave")), () ->
		{
			this.page = Page.JOIN_OR_LEAVE;
			container.init();
		}));
		container.addRenderableWidget(button3 = new GuiButtonBase(x, y + 48, 114, 20, Component.literal(I18n.get("gui.worldhandler.scoreboard.team.remove") + " / " + I18n.get("gui.worldhandler.scoreboard.team.empty")), () ->
		{
			this.page = Page.REMOVE_OR_EMPTY;
			container.init();
		}));
		container.addRenderableWidget(button4 = new GuiButtonBase(x, y + 72, 114, 20, Component.translatable("gui.worldhandler.scoreboard.team.options"), () ->
		{
			this.page = Page.OPTION;
			container.init();
		}));
		
		button1.active = !Page.ADD.equals(this.page);
		button2.active = !Page.JOIN_OR_LEAVE.equals(this.page);
		button3.active = !Page.REMOVE_OR_EMPTY.equals(this.page);
		button4.active = !Page.OPTION.equals(this.page);
		
		boolean enabled = this.builderTeams.team().get() != null && !this.builderTeams.team().get().isEmpty();
		
		if(Page.JOIN_OR_LEAVE.equals(this.page))
		{
			container.addRenderableWidget(button1 = new GuiButtonBase(x + 118, y + 36, 114, 20, Component.translatable("gui.worldhandler.scoreboard.team.join"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderTeams, TeamCommandBuilder.Label.JOIN);
				container.initButtons();
			}));
			container.addRenderableWidget(new GuiButtonBase(x + 118, y + 60, 114, 20, Component.translatable("gui.worldhandler.scoreboard.team.leave"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderTeams, TeamCommandBuilder.Label.LEAVE);
				container.initButtons();
			}));
			
			button1.active = enabled;
		}
		else if(Page.REMOVE_OR_EMPTY.equals(this.page))
		{
			container.addRenderableWidget(button1 = new GuiButtonBase(x + 118, y + 36, 114, 20, Component.translatable("gui.worldhandler.scoreboard.team.remove"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderTeams, TeamCommandBuilder.Label.REMOVE);
				container.initButtons();
			}));
			container.addRenderableWidget(button2 = new GuiButtonBase(x + 118, y + 60, 114, 20, Component.translatable("gui.worldhandler.scoreboard.team.empty"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderTeams, TeamCommandBuilder.Label.EMPTY);
				container.initButtons();
			}));
			
			button1.active = enabled;
			button2.active = enabled;
		}
		
		if(Page.ADD.equals(this.page) || Page.OPTION.equals(this.page))
		{
			container.addRenderableWidget(button1 = new GuiButtonBase(x + 118, y + 72 - this.page.getShift(), 114, 20, Component.translatable("gui.worldhandler.actions.perform"), () ->
			{
				if(Page.ADD.equals(this.page))
				{
					CommandHelper.sendCommand(container.getPlayer(), this.builderTeams, TeamCommandBuilder.Label.ADD_DISPLAYNAME);
				}
				else if(Page.OPTION.equals(this.page))
				{
					CommandHelper.sendCommand(container.getPlayer(), this.builderTeams, TeamCommandBuilder.Label.MODIFY);
				}
				container.initButtons();
			}));
			button1.active = enabled;
		}
		
		container.addRenderableWidget(this.teamField);
	}
	
	@Override
	public void tick(Container container)
	{
		this.teamField.tick();
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return Component.translatable("gui.worldhandler.tab.scoreboard.teams");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.SCOREBOARD_TEAMS;
	}
	
	@Override
	public void onPlayerNameChanged(String username)
	{
		if(Page.JOIN_OR_LEAVE.equals(this.page))
		{
			this.builderTeams.members().setTarget(username);
		}
	}
	
	public static enum Page
	{
		ADD(24),
		JOIN_OR_LEAVE(12),
		REMOVE_OR_EMPTY(12),
		OPTION(0);
		
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
