package exopandora.worldhandler.gui.content.impl;

import java.util.ArrayList;

import com.google.common.base.Predicates;
import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.component.impl.ComponentAttribute;
import exopandora.worldhandler.builder.impl.BuilderCustomItem;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.menu.impl.ILogicPageList;
import exopandora.worldhandler.gui.menu.impl.MenuColorField;
import exopandora.worldhandler.gui.menu.impl.MenuPageList;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiSlider;
import exopandora.worldhandler.gui.widget.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.widget.button.LogicSliderAttribute;
import exopandora.worldhandler.gui.widget.button.LogicSliderSimple;
import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.CommandHelper;
import exopandora.worldhandler.util.ResourceHelper;
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
	
	private final BuilderCustomItem builderCutomItem = new BuilderCustomItem();
	
	private int startPage;
	
	private Page page = Page.START;
	private String item;
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		return this.builderCutomItem;
	}
	
	@Override
	public void init(Container container)
	{
		for(Attribute attribute : this.builderCutomItem.getAttributes())
		{
			double ammount = this.builderCutomItem.getAttributeAmmount(attribute);
			
			if(ammount > Config.getSliders().getMaxItemAttributes())
			{
				this.builderCutomItem.setAttribute(attribute, Config.getSliders().getMaxItemAttributes());
			}
		}
		
		for(Enchantment enchantment : this.builderCutomItem.getEnchantments())
		{
			short level = this.builderCutomItem.getEnchantmentLevel(enchantment);
			
			if(level > Config.getSliders().getMaxItemEnchantment())
			{
				this.builderCutomItem.setEnchantment(enchantment, (short) Config.getSliders().getMaxItemEnchantment());
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
			this.builderCutomItem.setItem(this.item);
			container.initButtons();
		});
		
		this.itemLore1Field = new GuiTextFieldTooltip(x + 118, y + 24, 114, 20, new TranslatableComponent("gui.worldhandler.items.custom_item.start.lore_1"));
		this.itemLore1Field.setFilter(Predicates.<String>notNull());
		this.itemLore1Field.setText(this.builderCutomItem.getLore1());
		this.itemLore1Field.setResponder(text ->
		{
			this.builderCutomItem.setLore1(new TextComponent(text));
			container.initButtons();
		});
		
		this.itemLore2Field = new GuiTextFieldTooltip(x + 118, y + 48, 114, 20, new TranslatableComponent("gui.worldhandler.items.custom_item.start.lore_2"));
		this.itemLore2Field.setFilter(Predicates.<String>notNull());
		this.itemLore2Field.setText(this.builderCutomItem.getLore2());
		this.itemLore2Field.setResponder(text ->
		{
			this.builderCutomItem.setLore2(new TextComponent(text));
			container.initButtons();
		});
		
		if(Page.START.equals(this.page))
		{
			if(this.startPage == 1)
			{
				container.add(new MenuColorField(x, y, "gui.worldhandler.items.custom_item.start.custom_name", this.builderCutomItem.getName()));
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
						ContentCustomItem.this.builderCutomItem.setEnchantment(item, value.shortValue());
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
			MenuPageList<Attribute> attributes = new MenuPageList<Attribute>(x + 118, y, ComponentAttribute.ATTRIBUTES, 114, 20, 3, container, new ILogicPageList<Attribute>()
			{
				@Override
				public MutableComponent translate(Attribute item)
				{
					return new TranslatableComponent(item.getDescriptionId());
				}
				
				@Override
				public MutableComponent toTooltip(Attribute item)
				{
					return new TextComponent(item.getRegistryName().toString());
				}
				
				@Override
				public void onClick(Attribute item)
				{
					
				}
				
				@Override
				public GuiButtonBase onRegister(int x, int y, int width, int height, MutableComponent text, Attribute item, ActionHandler actionHandler)
				{
					return new GuiSlider(x, y, width, height, -Config.getSliders().getMaxItemAttributes(), Config.getSliders().getMaxItemEnchantment(), 0, container, new LogicSliderAttribute(item, text, value ->
					{
						ContentCustomItem.this.builderCutomItem.setAttribute(item, value);
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
		
		if(!this.builderCutomItem.needsCommandBlock() && !this.builderCutomItem.getName().isSpecial())
		{
			container.add(button4 = new GuiButtonBase(x, y + 72, 114, 20, new TranslatableComponent("gui.worldhandler.items.custom_item.custom_item"), () -> this.send(container.getPlayer())));
		}
		else
		{
			container.add(button4 = new GuiButtonBase(x, y + 72, 114, 20, new TranslatableComponent("gui.worldhandler.actions.place_command_block"), () -> this.send(container.getPlayer())));
		}
		
		button4.active = ResourceHelper.isRegistered(ResourceHelper.stringToResourceLocation(this.item), ForgeRegistries.ITEMS);
	}
	
	private void send(String player)
	{
		CommandHelper.sendCommand(player, this.builderCutomItem, this.builderCutomItem.getName().isSpecial());
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
		this.builderCutomItem.setPlayer(username);
	}
	
	public static enum Page
	{
		START,
		ENCHANT,
		ATTRIBUTES;
	}
}
