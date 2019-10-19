package exopandora.worldhandler.gui.content.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Predicates;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderGamerule;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.content.element.impl.ElementPageList;
import exopandora.worldhandler.gui.logic.ILogicPageList;
import exopandora.worldhandler.helper.ActionHelper;
import exopandora.worldhandler.helper.CommandHelper;
import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.IRuleEntryVisitor;
import net.minecraft.world.GameRules.RuleKey;
import net.minecraft.world.GameRules.RuleType;
import net.minecraft.world.GameRules.RuleValue;
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
		this.valueField = new GuiTextFieldTooltip(x + 118, y + 24, 114, 20, I18n.format("gui.worldhandler.generic.value"));
		this.valueField.setValidator(Predicates.notNull());
		this.valueField.setText(this.value);
		this.valueField.setCursorPositionEnd();
		this.valueField.setResponder(text ->
		{
			this.value = text;
			this.builderGamerule.setValue(this.value);
		});
		
		Map<String, ArgumentType<?>> map = new HashMap<String, ArgumentType<?>>();
		
		GameRules.func_223590_a(new IRuleEntryVisitor()
		{
			@Override
			public <T extends RuleValue<T>> void func_223481_a(RuleKey<T> rule, RuleType<T> type)
			{
				map.put(rule.func_223576_a(), type.func_223581_a(null).getType());
			}
		});
		
		ElementPageList<String> rules = new ElementPageList<String>(x, y, new ArrayList<String>(map.keySet()), 114, 20, 3, container, new ILogicPageList<String>()
		{
			@Override
			public String translate(String item)
			{
				return I18n.format("gui.worldhandler.gamerules.rule." + item);
			}
			
			@Override
			public String toTooltip(String item)
			{
				return item;
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
			public GuiButtonBase onRegister(int x, int y, int width, int height, String text, String item, ActionHandler actionHandler)
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
		container.add(new GuiButtonBase(x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		if(this.booleanValue)
		{
			container.add(new GuiButtonBase(x + 118, y + 24, 114, 20, I18n.format("gui.worldhandler.generic.enable"), () ->
			{
				CommandHelper.sendCommand(this.builderGamerule.getBuilderForValue(String.valueOf(true)));
			}));
			container.add(new GuiButtonBase(x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.generic.disable"), () ->
			{
				CommandHelper.sendCommand(this.builderGamerule.getBuilderForValue(String.valueOf(false)));
			}));
		}
		else
		{
			container.add(this.valueField);
			container.add(new GuiButtonBase(x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.actions.perform"), () ->
			{
				CommandHelper.sendCommand(this.builderGamerule);
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
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(!this.booleanValue)
		{
			this.valueField.renderButton(mouseX, mouseY, partialTicks);
		}
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.WORLD;
	}
	
	@Override
	public String getTitle()
	{
		return I18n.format("gui.worldhandler.title.world.gamerules");
	}
	
	@Override
	public String getTabTitle()
	{
		return I18n.format("gui.worldhandler.tab.world.gamerules");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.GAMERULES;
	}
}
