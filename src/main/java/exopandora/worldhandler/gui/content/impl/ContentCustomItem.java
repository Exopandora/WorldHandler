package exopandora.worldhandler.gui.content.impl;

import java.util.ArrayList;

import com.google.common.base.Predicates;
import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.builder.argument.tag.AbstractAttributeTag;
import exopandora.worldhandler.builder.argument.tag.AttributeModifiersTag;
import exopandora.worldhandler.builder.argument.tag.DisplayTag;
import exopandora.worldhandler.builder.argument.tag.EnchantmentsTag;
import exopandora.worldhandler.builder.impl.GiveCommandBuilder;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiSlider;
import exopandora.worldhandler.gui.widget.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.widget.button.LogicSliderAttribute;
import exopandora.worldhandler.gui.widget.button.LogicSliderSimple;
import exopandora.worldhandler.gui.widget.menu.impl.ILogicPageList;
import exopandora.worldhandler.gui.widget.menu.impl.MenuColorField;
import exopandora.worldhandler.gui.widget.menu.impl.MenuPageList;
import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.CommandHelper;
import exopandora.worldhandler.util.TextUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;

public class ContentCustomItem extends Content
{
	private GuiTextFieldTooltip itemField;
	private GuiTextFieldTooltip itemLore1Field;
	private GuiTextFieldTooltip itemLore2Field;
	
	private final GiveCommandBuilder builderCutomItem = new GiveCommandBuilder();
	private final AttributeModifiersTag attributes = new AttributeModifiersTag();
	private final DisplayTag display = new DisplayTag();
	private final EnchantmentsTag enchantments = new EnchantmentsTag();
	private final CommandPreview preview = new CommandPreview(this.builderCutomItem, GiveCommandBuilder.Label.GIVE);
	
	private int startPage;
	private Page page = Page.START;
	private String item;
	
	public ContentCustomItem()
	{
		this.builderCutomItem.item().addTagProvider(this.attributes);
		this.builderCutomItem.item().addTagProvider(this.display);
		this.builderCutomItem.item().addTagProvider(this.enchantments);
	}
	
	@Override
	public CommandPreview getCommandPreview()
	{
		return this.preview;
	}
	
	@Override
	public void init(Container container)
	{
		for(Attribute attribute : this.attributes.getAttributes())
		{
			double value = this.attributes.get(attribute);
			
			if(value > Config.getSliders().getMaxItemAttributes())
			{
				this.attributes.set(attribute, Config.getSliders().getMaxItemAttributes());
			}
		}
		
		for(Enchantment enchantment : this.enchantments.getEnchantments())
		{
			short level = this.enchantments.get(enchantment);
			
			if(level > Config.getSliders().getMaxItemEnchantment())
			{
				this.enchantments.set(enchantment, (short) Config.getSliders().getMaxItemEnchantment());
			}
		}
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.itemField = new GuiTextFieldTooltip(x + 118, y, 114, 20, new TranslatableComponent("gui.worldhandler.items.custom_item.start.item_id"));
		this.itemField.setFilter(Predicates.<String>notNull());
		this.itemField.setValue(this.item);
		this.itemField.setResponder(text ->
		{
			this.item = text;
			this.builderCutomItem.item().deserialize(this.item.replace(' ', '_'));
			container.initButtons();
		});
		
		this.itemLore1Field = new GuiTextFieldTooltip(x + 118, y + 24, 114, 20, new TranslatableComponent("gui.worldhandler.items.custom_item.start.lore_1"));
		this.itemLore1Field.setFilter(Predicates.<String>notNull());
		this.itemLore1Field.setText(this.display.getLore1());
		this.itemLore1Field.setResponder(text ->
		{
			this.display.setLore1(new TextComponent(text));
			container.initButtons();
		});
		
		this.itemLore2Field = new GuiTextFieldTooltip(x + 118, y + 48, 114, 20, new TranslatableComponent("gui.worldhandler.items.custom_item.start.lore_2"));
		this.itemLore2Field.setFilter(Predicates.<String>notNull());
		this.itemLore2Field.setText(this.display.getLore2());
		this.itemLore2Field.setResponder(text ->
		{
			this.display.setLore2(new TextComponent(text));
			container.initButtons();
		});
		
		if(Page.START.equals(this.page))
		{
			if(this.startPage == 1)
			{
				container.add(new MenuColorField(x, y, "gui.worldhandler.items.custom_item.start.custom_name", this.display.getName()));
			}
		}
		else if(Page.ENCHANT.equals(this.page))
		{
			MenuPageList<Enchantment> enchantments = new MenuPageList<Enchantment>(x + 118, y, new ArrayList<Enchantment>(ForgeRegistries.ENCHANTMENTS.getValues()), 114, 20, 3, container, new ILogicPageList<Enchantment>()
			{
				@Override
				public MutableComponent translate(Enchantment item)
				{
					return new TranslatableComponent(item.getDescriptionId());
				}
				
				@Override
				public MutableComponent toTooltip(Enchantment item)
				{
					return new TextComponent(item.getRegistryName().toString());
				}
				
				@Override
				public void onClick(Enchantment item)
				{
					
				}
				
				@Override
				public GuiButtonBase onRegister(int x, int y, int width, int height, MutableComponent text, Enchantment item, ActionHandler actionHandler)
				{
					return new GuiSlider(x, y, width, height, 0, Config.getSliders().getMaxItemEnchantment(), 0, container, new LogicSliderSimple(item.getRegistryName().toString(), text, value ->
					{
						ContentCustomItem.this.enchantments.set(item, value.shortValue());
					}));
				}
				
				@Override
				public boolean doDisable()
				{
					return false;
				}
				
				@Override
				public String getId()
				{
					return "enchantments";
				}
			});
			container.add(enchantments);
		}
		else if(Page.ATTRIBUTES.equals(this.page))
		{
			MenuPageList<Attribute> attributes = new MenuPageList<Attribute>(x + 118, y, AbstractAttributeTag.ATTRIBUTES, 114, 20, 3, container, new ILogicPageList<Attribute>()
			{
				@Override
				public MutableComponent translate(Attribute attribute)
				{
					return new TranslatableComponent(attribute.getDescriptionId());
				}
				
				@Override
				public MutableComponent toTooltip(Attribute attribute)
				{
					return new TextComponent(attribute.getRegistryName().toString());
				}
				
				@Override
				public void onClick(Attribute attribute)
				{
					
				}
				
				@Override
				public GuiButtonBase onRegister(int x, int y, int width, int height, MutableComponent text, Attribute attribute, ActionHandler actionHandler)
				{
					return new GuiSlider(x, y, width, height, -Config.getSliders().getMaxItemAttributes(), Config.getSliders().getMaxItemEnchantment(), 0, container, new LogicSliderAttribute(attribute, text, value ->
					{
						ContentCustomItem.this.attributes.set(attribute, value);
					}));
				}
				
				@Override
				public boolean doDisable()
				{
					return false;
				}
				
				@Override
				public String getId()
				{
					return "attributes";
				}
			});
			
			container.add(attributes);
		}
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonBase button1;
		GuiButtonBase button2;
		GuiButtonBase button3;
		GuiButtonBase button4;
		GuiButtonBase button5;
		GuiButtonBase button6;
		
		container.add(new GuiButtonBase(x, y + 96, 114, 20, new TranslatableComponent("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, new TranslatableComponent("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(button1 = new GuiButtonBase(x, y, 114, 20, new TranslatableComponent("gui.worldhandler.items.custom_item.start"), () ->
		{
			this.page = Page.START;
			container.init();
		}));
		container.add(button2 = new GuiButtonBase(x, y + 24, 114, 20, new TranslatableComponent("gui.worldhandler.items.custom_item.enchantment"), () ->
		{
			this.page = Page.ENCHANT;
			container.init();
		}));
		container.add(button3 = new GuiButtonBase(x, y + 48, 114, 20, new TranslatableComponent("gui.worldhandler.items.custom_item.attributes"), () ->
		{
			this.page = Page.ATTRIBUTES;
			container.init();
		}));
		
		if(Page.START.equals(this.page))
		{
			button1.active = false;
			
			container.add(button5 = new GuiButtonBase(x + 118, y + 72, 56, 20, TextUtils.ARROW_LEFT, () ->
			{
				this.startPage--;
				container.init();
			}));
			container.add(button6 = new GuiButtonBase(x + 118 + 60, y + 72, 55, 20, TextUtils.ARROW_RIGHT, () ->
			{
				this.startPage++;
				container.init();
			}));
			
			if(this.startPage == 0)
			{
				button5.active = false;
				container.add(this.itemField);
				container.add(this.itemLore1Field);
				container.add(this.itemLore2Field);
			}
			else if(this.startPage == 1)
			{
				button6.active = false;
			}
		}
		else if(Page.ENCHANT.equals(this.page))
		{
			button2.active = false;
		}
		else if(Page.ATTRIBUTES.equals(this.page))
		{
			button3.active = false;
		}
		
		if(!this.builderCutomItem.needsCommandBlock(GiveCommandBuilder.Label.GIVE, false) && !this.display.getName().isSpecial())
		{
			container.add(button4 = new GuiButtonBase(x, y + 72, 114, 20, new TranslatableComponent("gui.worldhandler.items.custom_item.custom_item"), () -> this.giveItem(container.getPlayer())));
		}
		else
		{
			container.add(button4 = new GuiButtonBase(x, y + 72, 114, 20, new TranslatableComponent("gui.worldhandler.actions.place_command_block"), () -> this.giveItem(container.getPlayer())));
		}
		
		button4.active = this.builderCutomItem.item().hasValue();
	}
	
	private void giveItem(String player)
	{
		CommandHelper.sendCommand(player, this.builderCutomItem, GiveCommandBuilder.Label.GIVE, this.display.getName().isSpecial());
	}
	
	@Override
	public void tick(Container container)
	{
		if(Page.START.equals(this.page) && this.startPage == 0)
		{
			this.itemField.tick();
			this.itemLore1Field.tick();
			this.itemLore2Field.tick();
		}
	}
	
	@Override
	public void drawScreen(PoseStack matrix, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(Page.START.equals(this.page) && this.startPage == 0)
		{
			this.itemField.renderButton(matrix, mouseX, mouseY, partialTicks);
			this.itemLore1Field.renderButton(matrix, mouseX, mouseY, partialTicks);
			this.itemLore2Field.renderButton(matrix, mouseX, mouseY, partialTicks);
		}
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.ITEMS;
	}
	
	@Override
	public MutableComponent getTitle()
	{
		return new TranslatableComponent("gui.worldhandler.title.items.custom_item");
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return new TranslatableComponent("gui.worldhandler.tab.items.custom_item");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.CUSTOM_ITEM;
	}
	
	@Override
	public void onPlayerNameChanged(String username)
	{
		this.builderCutomItem.targets().setTarget(username);
	}
	
	public static enum Page
	{
		START,
		ENCHANT,
		ATTRIBUTES;
	}
}
