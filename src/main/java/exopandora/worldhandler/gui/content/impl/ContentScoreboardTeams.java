package exopandora.worldhandler.gui.content.impl;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Predicates;
import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderTeams;
import exopandora.worldhandler.builder.impl.BuilderTeams.EnumMode;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.menu.impl.ILogicButtonList;
import exopandora.worldhandler.gui.menu.impl.MenuButtonList;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiTextFieldTooltip;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class ContentScoreboardTeams extends ContentScoreboard
{
	private GuiTextFieldTooltip teamField;
	
	private String team;
	private Page page = Page.ADD;
	
	private final BuilderTeams builderTeams = new BuilderTeams();
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		return this.builderTeams;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.teamField = new GuiTextFieldTooltip(x + 118, y + this.page.getShift(), 114, 20, new TranslatableComponent("gui.worldhandler.scoreboard.team.team"));
		this.teamField.setFilter(Predicates.notNull());
		this.teamField.setValue(this.team);
		this.teamField.setResponder(text ->
		{
			this.team = text;
			this.builderTeams.setTeam(this.team);
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
						return new TranslatableComponent("gui.worldhandler.scoreboard.team.options." + key);
					}
					else if(depth == 1)
					{
						if(Arrays.stream(ChatFormatting.values()).map(ChatFormatting::getName).anyMatch(Predicates.equalTo(key)))
						{
							return new TranslatableComponent("gui.worldhandler.color." + key);
						}
						
						return new TranslatableComponent("gui.worldhandler.scoreboard.team.suboption." + key);
					}
					
					return new TextComponent(key);
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
		
		container.add(new GuiButtonBase(x, y + 96, 114, 20, new TranslatableComponent("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, new TranslatableComponent("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(button1 = new GuiButtonBase(x, y, 114, 20, new TranslatableComponent("gui.worldhandler.scoreboard.team.create"), () ->
		{
			this.page = Page.ADD;
			container.init();
		}));
		container.add(button2 = new GuiButtonBase(x, y + 24, 114, 20, new TextComponent(I18n.get("gui.worldhandler.scoreboard.team.join") + " / " + I18n.get("gui.worldhandler.scoreboard.team.leave")), () ->
		{
			this.page = Page.JOIN_OR_LEAVE;
			container.init();
		}));
		container.add(button3 = new GuiButtonBase(x, y + 48, 114, 20, new TextComponent(I18n.get("gui.worldhandler.scoreboard.team.remove") + " / " + I18n.get("gui.worldhandler.scoreboard.team.empty")), () ->
		{
			this.page = Page.REMOVE_OR_EMPTY;
			container.init();
		}));
		container.add(button4 = new GuiButtonBase(x, y + 72, 114, 20, new TranslatableComponent("gui.worldhandler.scoreboard.team.options"), () ->
		{
			this.page = Page.OPTION;
			container.init();
		}));
		
		button1.active = !Page.ADD.equals(this.page);
		button2.active = !Page.JOIN_OR_LEAVE.equals(this.page);
		button3.active = !Page.REMOVE_OR_EMPTY.equals(this.page);
		button4.active = !Page.OPTION.equals(this.page);
		
		this.builderTeams.setMode(this.page.getMode());
		
		boolean enabled = this.team != null && this.team.length() > 0;
		
		if(Page.ADD.equals(this.page))
		{
			this.builderTeams.setTeam(this.team);
		}
		else if(Page.JOIN_OR_LEAVE.equals(this.page))
		{
			this.builderTeams.setPlayer(container.getPlayer());
			
			container.add(button1 = new GuiButtonBase(x + 118, y + 36, 114, 20, new TranslatableComponent("gui.worldhandler.scoreboard.team.join"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderTeams.build(EnumMode.JOIN));
				container.initButtons();
			}));
			container.add(new GuiButtonBase(x + 118, y + 60, 114, 20, new TranslatableComponent("gui.worldhandler.scoreboard.team.leave"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderTeams.build(EnumMode.LEAVE));
				container.initButtons();
			}));
			
			button1.active = enabled;
		}
		else if(Page.REMOVE_OR_EMPTY.equals(this.page))
		{
			container.add(button1 = new GuiButtonBase(x + 118, y + 36, 114, 20, new TranslatableComponent("gui.worldhandler.scoreboard.team.remove"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderTeams.build(EnumMode.REMOVE));
				container.initButtons();
			}));
			container.add(button2 = new GuiButtonBase(x + 118, y + 60, 114, 20, new TranslatableComponent("gui.worldhandler.scoreboard.team.empty"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderTeams.build(EnumMode.EMPTY));
				container.initButtons();
			}));
			
			button1.active = enabled;
			button2.active = enabled;
		}
		
		if(Page.ADD.equals(this.page) || Page.OPTION.equals(this.page))
		{
			container.add(button1 = new GuiButtonBase(x + 118, y + 72 - this.page.getShift(), 114, 20, new TranslatableComponent("gui.worldhandler.actions.perform"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderTeams);
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
	public void drawScreen(PoseStack matrix, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		this.teamField.renderButton(matrix, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return new TranslatableComponent("gui.worldhandler.tab.scoreboard.teams");
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
			this.builderTeams.setPlayer(username);
		}
	}
	
	public static enum Page
	{
		ADD(EnumMode.ADD, 24),
		JOIN_OR_LEAVE(EnumMode.JOIN_OR_LEAVE, 12),
		REMOVE_OR_EMPTY(EnumMode.REMOVE_OR_EMPTY, 12),
		OPTION(EnumMode.MODIFY, 0);
		
		private final EnumMode mode;
		private final int shift;
		
		private Page(EnumMode mode, int shift)
		{
			this.shift = shift;
			this.mode = mode;
		}
		
		public EnumMode getMode()
		{
			return this.mode;
		}
		
		public int getShift()
		{
			return this.shift;
		}
	}
}
