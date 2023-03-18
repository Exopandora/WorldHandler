package exopandora.worldhandler.gui.content.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Predicates;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;

import exopandora.worldhandler.builder.impl.GameRuleCommandBuilder;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.widget.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.widget.menu.impl.ILogicPageList;
import exopandora.worldhandler.gui.widget.menu.impl.MenuPageList;
import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.GameRuleTypeVisitor;

public class ContentGamerules extends Content
{
	private GuiTextFieldTooltip valueField;
	
	private boolean booleanValue;
	private String value;
	
	private final GameRuleCommandBuilder builderGamerule = new GameRuleCommandBuilder();
	private final CommandPreview preview = new CommandPreview(this.builderGamerule, GameRuleCommandBuilder.Label.GAMERULE_VALUE);
	
	@Override
	public CommandPreview getCommandPreview()
	{
		return this.preview;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.valueField = new GuiTextFieldTooltip(x + 118, y + 24, 114, 20, Component.translatable("gui.worldhandler.generic.value"));
		this.valueField.setFilter(Predicates.notNull());
		this.valueField.setValue(this.value);
		this.valueField.moveCursorToEnd();
		this.valueField.setResponder(text ->
		{
			this.value = text;
			this.builderGamerule.value().set(text);
		});
		
		Map<String, ArgumentType<?>> map = new HashMap<String, ArgumentType<?>>();
		
		GameRules.visitGameRuleTypes(new GameRuleTypeVisitor()
		{
			@Override
			public <T extends GameRules.Value<T>> void visit(GameRules.Key<T> rule, GameRules.Type<T> type)
			{
				map.put(rule.getId(), type.createArgument(null).getType());
			}
		});
		
		MenuPageList<String> rules = new MenuPageList<String>(x, y, new ArrayList<String>(map.keySet()), 114, 20, 3, container, new ILogicPageList<String>()
		{
			@Override
			public MutableComponent translate(String rule)
			{
				return Component.translatable("gamerule." + rule);
			}
			
			@Override
			public MutableComponent toTooltip(String rule)
			{
				return Component.literal(rule);
			}
			
			@Override
			public void onClick(String rule)
			{
				ContentGamerules.this.builderGamerule.rule().set(rule);
				ContentGamerules.this.booleanValue = map.get(rule) instanceof BoolArgumentType;
				
				if(ContentGamerules.this.booleanValue)
				{
					ContentGamerules.this.builderGamerule.value().set(null);
				}
				else
				{
					ContentGamerules.this.builderGamerule.value().set(ContentGamerules.this.value);
				}
				
				container.initButtons();
			}
			
			@Override
			public GuiButtonBase onRegister(int x, int y, int width, int height, MutableComponent text, String item, ActionHandler actionHandler)
			{
				return new GuiButtonTooltip(x, y, width, height, text, this.toTooltip(item), actionHandler);
			}
			
			@Override
			public String getId()
			{
				return "gamerules";
			}
		});
		
		container.addMenu(rules);
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.addRenderableWidget(new GuiButtonBase(x, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.addRenderableWidget(new GuiButtonBase(x + 118, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		if(this.booleanValue)
		{
			container.addRenderableWidget(new GuiButtonBase(x + 118, y + 24, 114, 20, Component.translatable("gui.worldhandler.generic.enable"), () ->
			{
				this.setGameRule(container.getPlayer(), String.valueOf(true));
			}));
			container.addRenderableWidget(new GuiButtonBase(x + 118, y + 48, 114, 20, Component.translatable("gui.worldhandler.generic.disable"), () ->
			{
				this.setGameRule(container.getPlayer(), String.valueOf(false));
			}));
		}
		else
		{
			container.addRenderableWidget(this.valueField);
			container.addRenderableWidget(new GuiButtonBase(x + 118, y + 48, 114, 20, Component.translatable("gui.worldhandler.actions.perform"), () ->
			{
				this.setGameRule(container.getPlayer(), this.value);
			}));
		}
	}
	
	private void setGameRule(String player, String value)
	{
		GameRuleCommandBuilder builder = new GameRuleCommandBuilder();
		builder.rule().set(this.builderGamerule.rule().get());
		builder.value().set(value);
		CommandHelper.sendCommand(player, builder, GameRuleCommandBuilder.Label.GAMERULE_VALUE);
	}
	
	@Override
	public void tick(Container container)
	{
		if(!this.booleanValue)
		{
			this.valueField.tick();
		}
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.WORLD;
	}
	
	@Override
	public MutableComponent getTitle()
	{
		return Component.translatable("gui.worldhandler.title.world.gamerules");
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return Component.translatable("gui.worldhandler.tab.world.gamerules");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.GAMERULES;
	}
}
