package exopandora.worldhandler.gui.content.impl;

import java.util.ArrayList;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderEnchantment;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.button.GuiSlider;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.element.impl.ElementPageList;
import exopandora.worldhandler.gui.logic.ILogicPageList;
import exopandora.worldhandler.gui.logic.LogicSliderSimple;
import exopandora.worldhandler.helper.ActionHelper;
import exopandora.worldhandler.helper.CommandHelper;
import exopandora.worldhandler.util.ActionHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
public class ContentEnchantment extends Content
{
	private final BuilderEnchantment builderEnchantment = new BuilderEnchantment();
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		return this.builderEnchantment;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		ElementPageList<Enchantment> enchantments = new ElementPageList<Enchantment>(x, y, new ArrayList<Enchantment>(ForgeRegistries.ENCHANTMENTS.getValues()), 114, 20, 3, container, new ILogicPageList<Enchantment>()
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
				ContentEnchantment.this.builderEnchantment.setEnchantment(item);
				ContentEnchantment.this.builderEnchantment.setLevel(1);
				container.initButtons();
			}
			
			@Override
			public GuiButtonBase onRegister(int x, int y, int width, int height, String text, Enchantment item, ActionHandler actionHandler)
			{
				return new GuiButtonTooltip(x, y, width, height, text, this.toTooltip(item), actionHandler);
			}
			
			@Override
			public String getId()
			{
				return "enchantments";
			}
		});
		
		container.add(enchantments);
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(new GuiButtonBase(x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(new GuiSlider(x + 118, y + 24, 114, 20, 1, ForgeRegistries.ENCHANTMENTS.getValue(this.builderEnchantment.getEnchantment()).getMaxLevel(), 1, container, new LogicSliderSimple("enchantment", I18n.format("gui.worldhandler.items.enchantment.level"), value ->
		{
			this.builderEnchantment.setLevel(value.intValue());
		})));
		
		container.add(new GuiButtonBase(x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.items.enchantment.enchant"), () ->
		{
			CommandHelper.sendCommand(this.builderEnchantment);
		}));
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.ITEMS;
	}
	
	@Override
	public String getTitle()
	{
		return I18n.format("gui.worldhandler.title.items.enchantment");
	}
	
	@Override
	public String getTabTitle()
	{
		return I18n.format("gui.worldhandler.tab.items.enchantment");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.ENCHANTMENT;
	}
	
	@Override
	public void onPlayerNameChanged(String username)
	{
		this.builderEnchantment.setPlayer(username);
	}
}
