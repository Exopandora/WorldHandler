package exopandora.worldhandler.gui.content.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.base.Predicates;

import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderCustomItem;
import exopandora.worldhandler.builder.impl.abstr.EnumAttributes;
import exopandora.worldhandler.builder.impl.abstr.EnumAttributes.Applyable;
import exopandora.worldhandler.config.ConfigSliders;
import exopandora.worldhandler.gui.button.GuiButtonList;
import exopandora.worldhandler.gui.button.GuiButtonWorldHandler;
import exopandora.worldhandler.gui.button.GuiSlider;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.button.responder.AttributeResponder;
import exopandora.worldhandler.gui.button.responder.SimpleResponder;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.content.element.impl.ElementColorMenu;
import exopandora.worldhandler.gui.content.element.impl.ElementPageList;
import exopandora.worldhandler.gui.content.element.logic.ILogicPageList;
import exopandora.worldhandler.helper.ResourceHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ContentCustomItem extends Content
{
	private GuiTextFieldTooltip itemField;
	private GuiTextFieldTooltip itemLore1Field;
	private GuiTextFieldTooltip itemLore2Field;
	
	private final BuilderCustomItem builderCutomItem = new BuilderCustomItem();
	
	private int startPage;
	
	private String selectedPage = "start";
	private String item;
	
	private GuiButtonList colorButton;
	
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
			
			if(ammount > ConfigSliders.getMaxItemAttributes())
			{
				this.builderCutomItem.setAttribute(attribute, ConfigSliders.getMaxItemAttributes());
			}
		}
		
		for(Enchantment enchantment : this.builderCutomItem.getEnchantments())
		{
			short level = this.builderCutomItem.getEnchantmentLevel(enchantment);
			
			if(level > ConfigSliders.getMaxItemEnchantment())
			{
				this.builderCutomItem.setEnchantment(enchantment, (short) ConfigSliders.getMaxItemEnchantment());
			}
		}
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.itemField = new GuiTextFieldTooltip(x + 118, y, 114, 20, I18n.format("gui.worldhandler.items.custom_item.start.item_id"));
		this.itemField.setValidator(Predicates.<String>notNull());
		this.itemField.setText(this.item);
		
		this.itemLore1Field = new GuiTextFieldTooltip(x + 118, y + 24, 114, 20, I18n.format("gui.worldhandler.items.custom_item.start.lore_1"));
		this.itemLore1Field.setValidator(Predicates.<String>notNull());
		this.itemLore1Field.setText(this.builderCutomItem.getLore1());
		
		this.itemLore2Field = new GuiTextFieldTooltip(x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.items.custom_item.start.lore_2"));
		this.itemLore2Field.setValidator(Predicates.<String>notNull());
		this.itemLore2Field.setText(this.builderCutomItem.getLore2());
		
		if(this.selectedPage.equals("start"))
		{
			if(this.startPage == 1)
			{
				ElementColorMenu colors = new ElementColorMenu(this, x, y, "gui.worldhandler.items.custom_item.start.custom_name", this.builderCutomItem.getName(), new int[] {10, 11, 12, 13, 14, 15});
				container.add(colors);
			}
		}
		else if(this.selectedPage.equals("enchant"))
		{
			ElementPageList<ResourceLocation, String> enchantments = new ElementPageList<ResourceLocation, String>(x + 118, y, new ArrayList<ResourceLocation>(Enchantment.REGISTRY.getKeys()), null, 114, 20, 3, this, new int[] {10, 11, 12}, new ILogicPageList<ResourceLocation, String>()
			{
				@Override
				public String translate(ResourceLocation key)
				{
					return I18n.format(Enchantment.REGISTRY.getObject(key).getName());
				}
				
				@Override
				public String getRegistryName(ResourceLocation key)
				{
					return key.toString();
				}
				
				@Override
				public void onClick(ResourceLocation clicked)
				{
					
				}
				
				@Override
				public void onRegister(int id, int x, int y, int width, int height, String display, String registry, boolean enabled, ResourceLocation value, Container container)
				{
					container.add(new GuiSlider<ResourceLocation>(Contents.CUSTOM_ITEM, container, value, x, y, width, height, display, 0, ConfigSliders.getMaxItemEnchantment(), 0, new SimpleResponder<ResourceLocation>(response ->
					{
						builderCutomItem.setEnchantment(Enchantment.REGISTRY.getObject(value), response.shortValue());
					})));
				}
				
				@Override
				public ResourceLocation getObject(String object)
				{
					if(object != null)
					{
						return new ResourceLocation(object.toString());
					}
					
					return null;
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
			ElementPageList<EnumAttributes, Object> attributes = new ElementPageList<EnumAttributes, Object>(x + 118, y, this.attributes, null,  114, 20, 3, this, new int[] {13, 14, 15}, new ILogicPageList<EnumAttributes, Object>()
			{
				@Override
				public String translate(EnumAttributes key)
				{
					return I18n.format("attribute.name." + key.getAttribute());
				}
				
				@Override
				public void onClick(EnumAttributes clicked)
				{
					
				}
				
				@Override
				public String getRegistryName(EnumAttributes key)
				{
					return key.getAttribute();
				}
				
				@Override
				public void onRegister(int id, int x, int y, int width, int height, String display, String registry, boolean enabled, EnumAttributes value, Container container)
				{
					container.add(new GuiSlider<EnumAttributes>(Contents.CUSTOM_ITEM, container, value, x, y, width, height, display, -ConfigSliders.getMaxItemAttributes(), ConfigSliders.getMaxItemAttributes(), 0, new AttributeResponder(response ->
					{
						builderCutomItem.setAttribute(value, response);
					})));
				}
				
				@Override
				public EnumAttributes getObject(Object object)
				{
					return null;
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
		GuiButtonWorldHandler button3;
		GuiButtonWorldHandler button4;
		GuiButtonWorldHandler button5;
		GuiButtonWorldHandler button6;
		GuiButtonWorldHandler button7;
		GuiButtonWorldHandler button8;
		GuiButtonWorldHandler button9;
		GuiButtonWorldHandler button10;
		GuiButtonWorldHandler button11;
		
		container.add(new GuiButtonWorldHandler(0, x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back")));
		container.add(new GuiButtonWorldHandler(1, x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame")));
		
		container.add(button3 = new GuiButtonWorldHandler(3, x, y, 114, 20, I18n.format("gui.worldhandler.items.custom_item.start")));
		container.add(button4 = new GuiButtonWorldHandler(4, x, y + 24, 114, 20, I18n.format("gui.worldhandler.items.custom_item.enchantment")));
		container.add(button5 = new GuiButtonWorldHandler(5, x, y + 48, 114, 20, I18n.format("gui.worldhandler.items.custom_item.attributes")));
		
		if(this.selectedPage.equals("start"))
		{
			button3.enabled = false;
			
			container.add(button7 = new GuiButtonWorldHandler(6, x + 118, y + 72, 56, 20, "<"));
			container.add(button8 = new GuiButtonWorldHandler(7, x + 118 + 60, y + 72, 55, 20, ">"));
			
			button7.enabled = this.startPage != 0;
			button8.enabled = this.startPage != 1;
		}
		else if(this.selectedPage.equals("enchant"))
		{
			button4.enabled = false;
		}
		else if(this.selectedPage.equals("attributes"))
		{
			button5.enabled = false;
		}
		
		if(!this.builderCutomItem.needsCommandBlock() && !this.builderCutomItem.getName().isSpecial())
		{
			container.add(button6 = new GuiButtonWorldHandler(9, x, y + 72, 114, 20, I18n.format("gui.worldhandler.items.custom_item.custom_item")));
		}
		else
		{
			container.add(button6 = new GuiButtonWorldHandler(9, x, y + 72, 114, 20, I18n.format("gui.worldhandler.actions.place_command_block")));
		}
		
		button6.enabled = ResourceHelper.isRegisteredItem(this.item);
	}
	
	@Override
	public void actionPerformed(Container container, GuiButton button)
	{
		switch(button.id)
		{
			case 3:
				this.selectedPage = "start";
				container.initGui();
				break;
			case 4:
				this.selectedPage = "enchant";
				container.initGui();
				break;
			case 5:
				this.selectedPage = "attributes";
				container.initGui();
				break;
			case 6:
				this.startPage--;
				container.initGui();
				break;
			case 7:
				this.startPage++;
				container.initGui();
				break;
			case 9:
				WorldHandler.sendCommand(this.builderCutomItem, this.builderCutomItem.getName().isSpecial());
				break;
			default:
				break;
		}
	}
	
	@Override
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(this.selectedPage.equals("start"))
		{
			if(this.startPage == 0)
			{
				this.itemField.drawTextBox();
				this.itemLore1Field.drawTextBox();
				this.itemLore2Field.drawTextBox();
			}
		}
	}
	
	@Override
	public void keyTyped(Container container, char charTyped, int keyCode)
	{
		if(this.itemField.textboxKeyTyped(charTyped, keyCode))
		{
			this.item = this.itemField.getText();
			this.builderCutomItem.setItem(this.item);
			container.initButtons();
		}
		
		if(this.itemLore1Field.textboxKeyTyped(charTyped, keyCode))
		{
			this.builderCutomItem.setLore1(this.itemLore1Field.getText());
			container.initButtons();
		}
		
		if(this.itemLore2Field.textboxKeyTyped(charTyped, keyCode))
		{
			this.builderCutomItem.setLore2(this.itemLore2Field.getText());
			container.initButtons();
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		if(this.selectedPage.equals("start"))
		{
			if(this.startPage == 0)
			{
				this.itemField.mouseClicked(mouseX, mouseY, mouseButton);
				this.itemLore1Field.mouseClicked(mouseX, mouseY, mouseButton);
				this.itemLore2Field.mouseClicked(mouseX, mouseY, mouseButton);
			}
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
