package exopandora.worldhandler.gui.content.impl;

import com.google.common.base.Predicates;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderMultiCommand;
import exopandora.worldhandler.builder.impl.BuilderScoreboardPlayers;
import exopandora.worldhandler.builder.impl.BuilderScoreboardPlayers.EnumMode;
import exopandora.worldhandler.builder.impl.BuilderTag;
import exopandora.worldhandler.builder.impl.BuilderTrigger;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.button.GuiSlider;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.logic.LogicSliderSimple;
import exopandora.worldhandler.helper.ActionHelper;
import exopandora.worldhandler.helper.CommandHelper;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ContentScoreboardPlayers extends ContentScoreboard
{
	private GuiTextFieldTooltip objectField;
	private GuiTextFieldTooltip tagField;
	
	private final BuilderScoreboardPlayers builderPlayers = new BuilderScoreboardPlayers();
	private final BuilderTag builderTag = new BuilderTag();
	private final BuilderTrigger builderTrigger = new BuilderTrigger();
	private final BuilderMultiCommand builderTriggerMulti = new BuilderMultiCommand(this.builderTrigger, this.builderPlayers);
	
	private Page page = Page.ADD_SET_REMOVE;
	
	private String tag;
	
	private GuiButtonBase addButton;
	private GuiButtonBase removeButton;
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		if(Page.ADD_SET_REMOVE.equals(this.page))
		{
			return this.builderPlayers;
		}
		else if(Page.TAG.equals(this.page))
		{
			return this.builderTag;
		}
		else if(Page.ENABLE.equals(this.page))
		{
			return this.builderTriggerMulti;
		}
		
		return null;
	}
	
	@Override
	public void init(Container container)
	{
		if(this.builderPlayers.getPoints() > Config.getSliders().getMaxPlayerPoints())
		{
			this.builderPlayers.setPoints((int) Config.getSliders().getMaxPlayerPoints());
		}
		
		if(this.builderTrigger.getValue() > Config.getSliders().getMaxTriggerValue())
		{
			this.builderTrigger.setValue((int) Config.getSliders().getMaxTriggerValue());
		}
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.objectField = new GuiTextFieldTooltip(x + 118, y, 114, 20, I18n.format("gui.worldhandler.scoreboard.objectives.objective"));
		this.objectField.setValidator(Predicates.notNull());
		this.objectField.setText(ContentScoreboard.getObjective());
		this.objectField.setResponder(text ->
		{
			ContentScoreboard.setObjective(text);
			this.builderPlayers.setObjective(ContentScoreboard.getObjective());
			this.builderTrigger.setObjective(ContentScoreboard.getObjective());
			container.initButtons();
		});
		
		this.tagField = new GuiTextFieldTooltip(x + 118, y + 12, 114, 20, I18n.format("gui.worldhandler.scoreboard.players.tag"));
		this.tagField.setValidator(string -> string != null && !string.contains(" "));
		this.tagField.setText(this.tag);
		this.tagField.setResponder(text ->
		{
			this.tag = text;
			this.builderTag.setName(this.tag);
			container.initButtons();
		});
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonBase button1;
		GuiButtonBase button2;
		GuiButtonBase button3;
		
		container.add(new GuiButtonBase(x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(button1 = new GuiButtonBase(x, y + 12, 114, 20, I18n.format("gui.worldhandler.scoreboard.players.points"), () ->
		{
			this.page = Page.ADD_SET_REMOVE;
			container.init();
		}));
		container.add(button2 = new GuiButtonBase(x, y + 36, 114, 20, I18n.format("gui.worldhandler.scoreboard.players.tag"), () ->
		{
			this.page = Page.TAG;
			container.init();
		}));
		container.add(button3 = new GuiButtonBase(x, y + 60, 114, 20, I18n.format("gui.worldhandler.scoreboard.players.trigger"), () ->
		{
			this.page = Page.ENABLE;
			container.init();
		}));
		
		button1.active = !Page.ADD_SET_REMOVE.equals(this.page);
		button2.active = !Page.TAG.equals(this.page);
		button3.active = !Page.ENABLE.equals(this.page);
		
		boolean enabled = ContentScoreboard.isObjectiveValid();
		this.builderPlayers.setMode(this.page.getMode());
		
		if(Page.ADD_SET_REMOVE.equals(this.page))
		{
			container.add(new GuiSlider(x + 118, y + 24, 114, 20, 0, Config.getSliders().getMaxPlayerPoints(), 0, container, new LogicSliderSimple("points", I18n.format("gui.worldhandler.scoreboard.players.points"), value ->
			{
				this.builderPlayers.setPoints(value);
			})));
			container.add(this.addButton = new GuiButtonBase(x + 118, y + 48, 56, 20, I18n.format("gui.worldhandler.actions.add"), () ->
			{
				CommandHelper.sendCommand(this.builderPlayers.getBuilderForPoints(EnumMode.ADD));
				container.init();
			}));
			container.add(this.removeButton = new GuiButtonBase(x + 118 + 114 / 2 + 1, y + 48, 56, 20, I18n.format("gui.worldhandler.actions.remove"), () ->
			{
				CommandHelper.sendCommand(this.builderPlayers.getBuilderForPoints(EnumMode.REMOVE));
				container.init();
			}));
			container.add(button1 = new GuiButtonTooltip(x + 118, y + 72, 114, 20, I18n.format("gui.worldhandler.actions.reset"), I18n.format("gui.worldhandler.actions.set_to_0"), () ->
			{
				CommandHelper.sendCommand(this.builderPlayers.getBuilderForPoints(EnumMode.SET, 0));
				container.init();
			}));
			
			boolean points = enabled && this.builderPlayers.getPoints() > 0;
			
			this.addButton.active = points;
			this.removeButton.active = points;
			button1.active = enabled;
		}
		else if(Page.TAG.equals(this.page))
		{
			container.add(button1 = new GuiButtonBase(x + 118, y + 36, 114, 20, I18n.format("gui.worldhandler.actions.add"), () ->
			{
				CommandHelper.sendCommand(this.builderTag.getBuilderForMode(BuilderTag.EnumMode.ADD));
				container.init();
			}));
			container.add(button2 = new GuiButtonBase(x + 118, y + 60, 114, 20, I18n.format("gui.worldhandler.actions.remove"), () ->
			{
				CommandHelper.sendCommand(this.builderTag.getBuilderForMode(BuilderTag.EnumMode.REMOVE));
				container.init();
			}));
			
			boolean tag = this.tag != null && !this.tag.isEmpty();
			
			button1.active = tag;
			button2.active = tag;
		}
		else if(Page.ENABLE.equals(this.page))
		{
			container.add(new GuiSlider(x + 118, y + 24, 114, 20, 0, Config.getSliders().getMaxTriggerValue(), 0, container, new LogicSliderSimple("enable", I18n.format("gui.worldhandler.generic.value"), value ->
			{
				this.builderTrigger.setValue(value.intValue());
			})));
			container.add(this.addButton = new GuiButtonBase(x + 118, y + 48, 56, 20, I18n.format("gui.worldhandler.actions.add"), () ->
			{
				CommandHelper.sendCommand(this.builderTrigger.getBuilderForMode(BuilderTrigger.EnumMode.ADD));
				container.init();
			}));
			container.add(this.removeButton = new GuiButtonBase(x + 118 + 114 / 2 + 1, y + 48, 56, 20, I18n.format("gui.worldhandler.actions.set"), () ->
			{
				CommandHelper.sendCommand(this.builderTrigger.getBuilderForMode(BuilderTrigger.EnumMode.SET));
				container.init();
			}));
			container.add(button1 = new GuiButtonBase(x + 118, y + 72, 114, 20, I18n.format("gui.worldhandler.generic.enable"), () ->
			{
				CommandHelper.sendCommand(this.builderPlayers.getBuilderForEnable());
				container.init();
			}));
			
			this.addButton.active = enabled && this.builderTrigger.getValue() > 0;
			this.removeButton.active = enabled;
			button1.active = enabled;
		}
		
		if(Page.TAG.equals(this.page))
		{
			container.add(this.tagField);
		}
		else
		{
			container.add(this.objectField);
			this.builderPlayers.setObjective(ContentScoreboard.getObjective());
			this.builderTrigger.setObjective(ContentScoreboard.getObjective());
		}
	}
	
	@Override
	public void tick(Container container)
	{
		if(Page.TAG.equals(this.page))
		{
			this.tagField.tick();
		}
		else
		{
			boolean enabled = ContentScoreboard.isObjectiveValid();
			
			if(Page.ADD_SET_REMOVE.equals(this.page))
			{
				boolean points = enabled && this.builderPlayers.getPoints() > 0;
				
				this.addButton.active = points;
				this.removeButton.active = points;
			}
			else if(Page.ENABLE.equals(this.page))
			{
				this.addButton.active = enabled && this.builderTrigger.getValue() > 0;
				this.removeButton.active = enabled;
			}
			
			this.objectField.tick();
		}
	}
	
	@Override
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(Page.TAG.equals(this.page))
		{
			this.tagField.renderButton(mouseX, mouseY, partialTicks);
		}
		else
		{
			this.objectField.renderButton(mouseX, mouseY, partialTicks);
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
		this.builderTag.setPlayer(username);
	}
	
	@OnlyIn(Dist.CLIENT)
	public static enum Page
	{
		ADD_SET_REMOVE("add|set|remove"),
		TAG("tag"),
		ENABLE("enable");
		
		private final String mode;
		
		private Page(String mode)
		{
			this.mode = mode;
		}
		
		public String getMode()
		{
			return this.mode;
		}
	}
}
