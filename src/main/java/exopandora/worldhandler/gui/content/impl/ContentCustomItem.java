package exopandora.worldhandler.gui.content.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.base.Predicates;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderCustomItem;
import exopandora.worldhandler.builder.impl.abstr.EnumAttributes;
import exopandora.worldhandler.builder.impl.abstr.EnumAttributes.Applyable;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiSlider;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.content.element.impl.ElementColorMenu;
import exopandora.worldhandler.gui.content.element.impl.ElementPageList;
import exopandora.worldhandler.gui.logic.ILogicPageList;
import exopandora.worldhandler.gui.logic.LogicSliderAttribute;
import exopandora.worldhandler.gui.logic.LogicSliderSimple;
import exopandora.worldhandler.helper.ActionHelper;
import exopandora.worldhandler.helper.CommandHelper;
import exopandora.worldhandler.helper.ResourceHelper;
import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
public class ContentCustomItem extends Content
{
	private GuiTextFieldTooltip itemField;
	private GuiTextFieldTooltip itemLore1Field;
	private GuiTextFieldTooltip itemLore2Field;
	
	private final BuilderCustomItem builderCutomItem = new BuilderCustomItem();
	
	private int startPage;
	
	private String selectedPage = "start";
	private String item;
	
	private final List<EnumAttributes> attributes = Stream.concat(EnumAttributes.getAttributesFor(Applyable.BOTH).stream(), EnumAttributes.getAttributesFor(Applyable.PLAYER).stream()).collect(Collectors.toList());
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		return this.builderCutomItem;
	}
	
	@Override
	public void init(Container container)
	{
		for(EnumAttributes attribute : this.builderCutomItem.getAttributes())
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
		this.itemField = new GuiTextFieldTooltip(x + 118, y, 114, 20, I18n.format("gui.worldhandler.items.custom_item.start.item_id"));
		this.itemField.setValidator(Predicates.<String>notNull());
		this.itemField.setText(this.item);
		this.itemField.setTextAcceptHandler((id, text) ->
		{
			this.item = text;
			this.builderCutomItem.setItem(this.item);
			container.initButtons();
		});
		
		this.itemLore1Field = new GuiTextFieldTooltip(x + 118, y + 24, 114, 20, I18n.format("gui.worldhandler.items.custom_item.start.lore_1"));
		this.itemLore1Field.setValidator(Predicates.<String>notNull());
		this.itemLore1Field.setText(this.builderCutomItem.getLore1());
		this.itemLore1Field.setTextAcceptHandler((id, text) ->
		{
			this.builderCutomItem.setLore1(text);
			container.initButtons();
		});
		
		this.itemLore2Field = new GuiTextFieldTooltip(x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.items.custom_item.start.lore_2"));
		this.itemLore2Field.setValidator(Predicates.<String>notNull());
		this.itemLore2Field.setText(this.builderCutomItem.getLore2());
		this.itemLore2Field.setTextAcceptHandler((id, text) ->
		{
			this.builderCutomItem.setLore2(text);
			container.initButtons();
		});
		
		if(this.selectedPage.equals("start"))
		{
			if(this.startPage == 1)
			{
				container.add(new ElementColorMenu(x, y, "gui.worldhandler.items.custom_item.start.custom_name", this.builderCutomItem.getName()));
			}
		}
		else if(this.selectedPage.equals("enchant"))
		{
			ElementPageList<Enchantment> enchantments = new ElementPageList<Enchantment>(x + 118, y, new ArrayList<Enchantment>(ForgeRegistries.ENCHANTMENTS.getValues()), 114, 20, 3, container, new ILogicPageList<Enchantment>()
			{
				@Override
				public String translate(Enchantment item)
				{
					return I18n.format(item.getName());
				}
				
				@Override
				public String toTooltip(Enchantment item)
				{
					return item.getRegistryName().toString();
				}
				
				@Override
				public void onClick(Enchantment item)
				{
					
				}
				
				@Override
				public GuiButtonBase onRegister(int x, int y, int width, int height, String text, Enchantment item, ActionHandler actionHandler)
				{
					return new GuiSlider(x, y, width, height, 0, Config.getSliders().getMaxItemEnchantment(), 0, container, new LogicSliderSimple(this.toTooltip(item), text, value ->
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
		else if(this.selectedPage.equals("attributes"))
		{
			ElementPageList<EnumAttributes> attributes = new ElementPageList<EnumAttributes>(x + 118, y, this.attributes, 114, 20, 3, container, new ILogicPageList<EnumAttributes>()
			{
				@Override
				public String translate(EnumAttributes item)
				{
					return item.getTranslation();
				}
				
				@Override
				public String toTooltip(EnumAttributes item)
				{
					return item.getAttribute();
				}
				
				@Override
				public void onClick(EnumAttributes item)
				{
					
				}
				
				@Override
				public GuiButtonBase onRegister(int x, int y, int width, int height, String text, EnumAttributes item, ActionHandler actionHandler)
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
		
		container.add(new GuiButtonBase(x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(button1 = new GuiButtonBase(x, y, 114, 20, I18n.format("gui.worldhandler.items.custom_item.start"), () ->
		{
			this.selectedPage = "start";
			container.initGui();
		}));
		container.add(button2 = new GuiButtonBase(x, y + 24, 114, 20, I18n.format("gui.worldhandler.items.custom_item.enchantment"), () ->
		{
			this.selectedPage = "enchant";
			container.initGui();
		}));
		container.add(button3 = new GuiButtonBase(x, y + 48, 114, 20, I18n.format("gui.worldhandler.items.custom_item.attributes"), () ->
		{
			this.selectedPage = "attributes";
			container.initGui();
		}));
		
		if(this.selectedPage.equals("start"))
		{
			button1.enabled = false;
			
			container.add(button5 = new GuiButtonBase(x + 118, y + 72, 56, 20, "<", () ->
			{
				this.startPage--;
				container.initGui();
			}));
			container.add(button6 = new GuiButtonBase(x + 118 + 60, y + 72, 55, 20, ">", () ->
			{
				this.startPage++;
				container.initGui();
			}));
			
			if(this.startPage == 0)
			{
				button5.enabled = false;
				container.add(this.itemField);
				container.add(this.itemLore1Field);
				container.add(this.itemLore2Field);
			}
			else if(this.startPage == 1)
			{
				button6.enabled = false;
			}
		}
		else if(this.selectedPage.equals("enchant"))
		{
			button2.enabled = false;
		}
		else if(this.selectedPage.equals("attributes"))
		{
			button3.enabled = false;
		}
		
		if(!this.builderCutomItem.needsCommandBlock() && !this.builderCutomItem.getName().isSpecial())
		{
			container.add(button4 = new GuiButtonBase(x, y + 72, 114, 20, I18n.format("gui.worldhandler.items.custom_item.custom_item"), this::send));
		}
		else
		{
			container.add(button4 = new GuiButtonBase(x, y + 72, 114, 20, I18n.format("gui.worldhandler.actions.place_command_block"), this::send));
		}
		
		button4.enabled = ResourceHelper.isRegistered(ResourceHelper.stringToResourceLocation(this.item), ForgeRegistries.ITEMS);
	}
	
	private void send()
	{
		CommandHelper.sendCommand(this.builderCutomItem, this.builderCutomItem.getName().isSpecial());
	}
	
	@Override
	public void tick(Container container)
	{
		if(this.selectedPage.equals("start") && this.startPage == 0)
		{
			this.itemField.tick();
			this.itemLore1Field.tick();
			this.itemLore2Field.tick();
		}
	}
	
	@Override
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(this.selectedPage.equals("start") && this.startPage == 0)
		{
			this.itemField.drawTextField(mouseX, mouseY, partialTicks);
			this.itemLore1Field.drawTextField(mouseX, mouseY, partialTicks);
			this.itemLore2Field.drawTextField(mouseX, mouseY, partialTicks);
		}
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.ITEMS;
	}
	
	@Override
	public String getTitle()
	{
		return I18n.format("gui.worldhandler.title.items.custom_item");
	}
	
	@Override
	public String getTabTitle()
	{
		return I18n.format("gui.worldhandler.tab.items.custom_item");
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
}
