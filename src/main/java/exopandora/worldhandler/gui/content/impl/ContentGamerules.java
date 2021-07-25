package exopandora.worldhandler.gui.content.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Predicates;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderGamerule;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.menu.impl.ILogicPageList;
import exopandora.worldhandler.gui.menu.impl.MenuPageList;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.widget.button.GuiTextFieldTooltip;
import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.GameRuleTypeVisitor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ContentGamerules extends Content
{
	private GuiTextFieldTooltip valueField;
	
	private boolean booleanValue;
	private String value;
	
	private final BuilderGamerule builderGamerule = new BuilderGamerule();
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		return this.builderGamerule;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.valueField = new GuiTextFieldTooltip(x + 118, y + 24, 114, 20, new TranslatableComponent("gui.worldhandler.generic.value"));
		this.valueField.setFilter(Predicates.notNull());
		this.valueField.setValue(this.value);
		this.valueField.moveCursorToEnd();
		this.valueField.setResponder(text ->
		{
			this.value = text;
			this.builderGamerule.setValue(this.value);
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
			public MutableComponent translate(String item)
			{
				return new TranslatableComponent("gamerule." + item);
			}
			
			@Override
			public MutableComponent toTooltip(String item)
			{
				return new TextComponent(item);
			}
			
			@Override
			public void onClick(String item)
			{
				ContentGamerules.this.builderGamerule.setRule(item);
				ContentGamerules.this.booleanValue = map.get(item) instanceof BoolArgumentType;
				
				if(ContentGamerules.this.booleanValue)
				{
					ContentGamerules.this.builderGamerule.setValue(null);
				}
				else
				{
					ContentGamerules.this.builderGamerule.setValue(ContentGamerules.this.value);
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
		
		container.add(rules);
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(new GuiButtonBase(x, y + 96, 114, 20, new TranslatableComponent("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, new TranslatableComponent("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		if(this.booleanValue)
		{
			container.add(new GuiButtonBase(x + 118, y + 24, 114, 20, new TranslatableComponent("gui.worldhandler.generic.enable"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderGamerule.build(String.valueOf(true)));
			}));
			container.add(new GuiButtonBase(x + 118, y + 48, 114, 20, new TranslatableComponent("gui.worldhandler.generic.disable"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderGamerule.build(String.valueOf(false)));
			}));
		}
		else
		{
			container.add(this.valueField);
			container.add(new GuiButtonBase(x + 118, y + 48, 114, 20, new TranslatableComponent("gui.worldhandler.actions.perform"), () ->
			{
				CommandHelper.sendCommand(container.getPlayer(), this.builderGamerule);
			}));
		}
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
	public void drawScreen(PoseStack matrix, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(!this.booleanValue)
		{
			this.valueField.renderButton(matrix, mouseX, mouseY, partialTicks);
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
		return new TranslatableComponent("gui.worldhandler.title.world.gamerules");
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return new TranslatableComponent("gui.worldhandler.tab.world.gamerules");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.GAMERULES;
	}
}
