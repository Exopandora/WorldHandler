package exopandora.worldhandler.gui.content.impl;

import com.google.common.base.Predicates;

import exopandora.worldhandler.builder.impl.ScoreboardCommandBuilder;
import exopandora.worldhandler.builder.impl.TagCommandBuilder;
import exopandora.worldhandler.builder.impl.TriggerCommandBuilder;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.widget.button.GuiSlider;
import exopandora.worldhandler.gui.widget.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.widget.button.LogicSliderSimple;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ContentScoreboardPlayers extends ContentScoreboard
{
	private final TagCommandBuilder builderTag = new TagCommandBuilder();
	private final TriggerCommandBuilder builderTrigger = new TriggerCommandBuilder();
	private final CommandPreview previewPlayers = new CommandPreview(BUILDER, ScoreboardCommandBuilder.Label.PLAYERS);
	private final CommandPreview previewTag = new CommandPreview(this.builderTag, null);
	private final CommandPreview previewTrigger = new CommandPreview()
			.add(this.builderTrigger, null)
			.add(BUILDER, ScoreboardCommandBuilder.Label.PLAYERS_ENABLE_OBJECTIVE);
	
	private Page page = Page.ADD_SET_REMOVE;
	
	private GuiButtonBase addButton;
	private GuiButtonBase removeButton;
	private GuiTextFieldTooltip objectField;
	private GuiTextFieldTooltip tagField;
	
	public ContentScoreboardPlayers()
	{
		this.builderTrigger.value().set(0);
	}
	
	@Override
	public CommandPreview getCommandPreview()
	{
		switch(this.page)
		{
			case ADD_SET_REMOVE:
				return this.previewPlayers;
			case ENABLE:
				return this.previewTrigger;
			case TAG:
				return this.previewTag;
			default:
				return null;
		}
	}
	
	@Override
	public void init(Container container)
	{
		if(BUILDER.score().get() > Config.getSliders().getMaxPlayerPoints())
		{
			BUILDER.score().set((int) Config.getSliders().getMaxPlayerPoints());
		}
		
		if(this.builderTrigger.value().get() > Config.getSliders().getMaxTriggerValue())
		{
			this.builderTrigger.value().set((int) Config.getSliders().getMaxTriggerValue());
		}
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.objectField = new GuiTextFieldTooltip(x + 118, y, 114, 20, Component.translatable("gui.worldhandler.scoreboard.objectives.objective"));
		this.objectField.setFilter(Predicates.notNull());
		this.objectField.setResponder(text ->
		{
			BUILDER.objective().set(text);
			BUILDER.displayName().deserialize(text);
			this.builderTrigger.objective().set(text);
			container.initButtons();
		});
		this.objectField.setValue(BUILDER.objective().get());
		
		this.tagField = new GuiTextFieldTooltip(x + 118, y + 12, 114, 20, Component.translatable("gui.worldhandler.scoreboard.players.tag"));
		this.tagField.setFilter(string -> string != null && !string.contains(" "));
		this.tagField.setValue(this.builderTag.name().get());
		this.tagField.setResponder(text ->
		{
			this.builderTag.name().set(text);
			container.initButtons();
		});
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonBase button1;
		GuiButtonBase button2;
		GuiButtonBase button3;
		
		container.addRenderableWidget(new GuiButtonBase(x, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.addRenderableWidget(new GuiButtonBase(x + 118, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.addRenderableWidget(button1 = new GuiButtonBase(x, y + 12, 114, 20, Component.translatable("gui.worldhandler.scoreboard.players.points"), () ->
		{
			this.page = Page.ADD_SET_REMOVE;
			container.init();
		}));
		container.addRenderableWidget(button2 = new GuiButtonBase(x, y + 36, 114, 20, Component.translatable("gui.worldhandler.scoreboard.players.tag"), () ->
		{
			this.page = Page.TAG;
			container.init();
		}));
		container.addRenderableWidget(button3 = new GuiButtonBase(x, y + 60, 114, 20, Component.translatable("gui.worldhandler.scoreboard.players.trigger"), () ->
		{
			this.page = Page.ENABLE;
			container.init();
		}));
		
		button1.active = !Page.ADD_SET_REMOVE.equals(this.page);
		button2.active = !Page.TAG.equals(this.page);
		button3.active = !Page.ENABLE.equals(this.page);
		
		boolean enabled = BUILDER.objective().get() != null && !BUILDER.objective().get().isEmpty();
		
		if(Page.ADD_SET_REMOVE.equals(this.page))
		{
			container.addRenderableWidget(new GuiSlider(x + 118, y + 24, 114, 20, 0, Config.getSliders().getMaxPlayerPoints(), 0, container, new LogicSliderSimple("points", Component.translatable("gui.worldhandler.scoreboard.players.points"), value ->
			{
				BUILDER.score().set(value);
			})));
			container.addRenderableWidget(this.addButton = new GuiButtonBase(x + 118, y + 48, 56, 20, Component.translatable("gui.worldhandler.actions.add"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), BUILDER, ScoreboardCommandBuilder.Label.PLAYERS_ADD_SCORE);
				container.init();
			}));
			container.addRenderableWidget(this.removeButton = new GuiButtonBase(x + 118 + 114 / 2 + 1, y + 48, 56, 20, Component.translatable("gui.worldhandler.actions.remove"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), BUILDER, ScoreboardCommandBuilder.Label.PLAYERS_REMOVE_SCORE);
				container.init();
			}));
			container.addRenderableWidget(button1 = new GuiButtonTooltip(x + 118, y + 72, 114, 20, Component.translatable("gui.worldhandler.actions.reset"), Component.translatable("gui.worldhandler.actions.set_to_0"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), BUILDER, ScoreboardCommandBuilder.Label.PLAYERS_RESET_SCORE);
				container.init();
			}));
			
			boolean score = enabled && BUILDER.score().get() > 0;
			
			this.addButton.active = score;
			this.removeButton.active = score;
			button1.active = enabled;
		}
		else if(Page.TAG.equals(this.page))
		{
			container.addRenderableWidget(button1 = new GuiButtonBase(x + 118, y + 36, 114, 20, Component.translatable("gui.worldhandler.actions.add"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderTag, TagCommandBuilder.Label.ADD);
				container.init();
			}));
			container.addRenderableWidget(button2 = new GuiButtonBase(x + 118, y + 60, 114, 20, Component.translatable("gui.worldhandler.actions.remove"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderTag, TagCommandBuilder.Label.REMOVE);
				container.init();
			}));
			
			boolean tag = this.builderTag.name().get() != null && !this.builderTag.name().get().isEmpty();
			
			button1.active = tag;
			button2.active = tag;
		}
		else if(Page.ENABLE.equals(this.page))
		{
			container.addRenderableWidget(new GuiSlider(x + 118, y + 24, 114, 20, 0, Config.getSliders().getMaxTriggerValue(), 0, container, new LogicSliderSimple("enable", Component.translatable("gui.worldhandler.generic.value"), value ->
			{
				this.builderTrigger.value().set(value);
			})));
			container.addRenderableWidget(this.addButton = new GuiButtonBase(x + 118, y + 48, 56, 20, Component.translatable("gui.worldhandler.actions.add"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderTrigger, TriggerCommandBuilder.Label.ADD);
				container.init();
			}));
			container.addRenderableWidget(this.removeButton = new GuiButtonBase(x + 118 + 114 / 2 + 1, y + 48, 56, 20, Component.translatable("gui.worldhandler.actions.set"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderTrigger, TriggerCommandBuilder.Label.SET);
				container.init();
			}));
			container.addRenderableWidget(button1 = new GuiButtonBase(x + 118, y + 72, 114, 20, Component.translatable("gui.worldhandler.generic.enable"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), BUILDER, ScoreboardCommandBuilder.Label.PLAYERS_ENABLE_OBJECTIVE);
				container.init();
			}));
			
			this.addButton.active = enabled && this.builderTrigger.value().get() > 0;
			this.removeButton.active = enabled;
			button1.active = enabled;
		}
		
		if(Page.TAG.equals(this.page))
		{
			container.addRenderableWidget(this.tagField);
		}
		else
		{
			container.addRenderableWidget(this.objectField);
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
			boolean enabled = BUILDER.objective().get() != null && !BUILDER.objective().get().isEmpty();
			
			if(Page.ADD_SET_REMOVE.equals(this.page))
			{
				boolean points = enabled && BUILDER.score().get() > 0;
				
				this.addButton.active = points;
				this.removeButton.active = points;
			}
			else if(Page.ENABLE.equals(this.page))
			{
				this.addButton.active = enabled && this.builderTrigger.value().get() > 0;
				this.removeButton.active = enabled;
			}
			
			this.objectField.tick();
		}
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return Component.translatable("gui.worldhandler.tab.scoreboard.players");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.SCOREBOARD_PLAYERS;
	}
	
	@Override
	public void onPlayerNameChanged(String username)
	{
		BUILDER.targets().setTarget(username);
		this.builderTag.targets().setTarget(username);
	}
	
	public static enum Page
	{
		ADD_SET_REMOVE,
		TAG,
		ENABLE;
	}
}
