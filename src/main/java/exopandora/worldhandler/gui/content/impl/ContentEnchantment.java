package exopandora.worldhandler.gui.content.impl;

import java.util.ArrayList;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderEnchantment;
import exopandora.worldhandler.gui.button.GuiButtonBase;
import exopandora.worldhandler.gui.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.button.GuiSlider;
import exopandora.worldhandler.gui.button.LogicSliderSimple;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.menu.impl.ILogicPageList;
import exopandora.worldhandler.gui.menu.impl.MenuPageList;
import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
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
		MenuPageList<Enchantment> enchantments = new MenuPageList<Enchantment>(x, y, new ArrayList<Enchantment>(ForgeRegistries.ENCHANTMENTS.getValues()), 114, 20, 3, container, new ILogicPageList<Enchantment>()
		{
			@Override
			public IFormattableTextComponent translate(Enchantment item)
			{
				return new TranslationTextComponent(item.getName());
			}
			
			@Override
			public IFormattableTextComponent toTooltip(Enchantment item)
			{
				return new StringTextComponent(item.getRegistryName().toString());
			}
			
			@Override
			public void onClick(Enchantment item)
			{
				ContentEnchantment.this.builderEnchantment.setEnchantment(item);
				ContentEnchantment.this.builderEnchantment.setLevel(1);
				container.initButtons();
			}
			
			@Override
			public GuiButtonBase onRegister(int x, int y, int width, int height, IFormattableTextComponent text, Enchantment item, ActionHandler actionHandler)
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
		container.add(new GuiButtonBase(x, y + 96, 114, 20, new TranslationTextComponent("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, new TranslationTextComponent("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(new GuiSlider(x + 118, y + 24, 114, 20, 1, ForgeRegistries.ENCHANTMENTS.getValue(this.builderEnchantment.getEnchantment()).getMaxLevel(), 1, container, new LogicSliderSimple("enchantment", new TranslationTextComponent("gui.worldhandler.items.enchantment.level"), value ->
		{
			this.builderEnchantment.setLevel(value.intValue());
		})));
		
		container.add(new GuiButtonBase(x + 118, y + 48, 114, 20, new TranslationTextComponent("gui.worldhandler.items.enchantment.enchant"), () ->
		{
			CommandHelper.sendCommand(container.getPlayer(), this.builderEnchantment);
		}));
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.ITEMS;
	}
	
	@Override
	public IFormattableTextComponent getTitle()
	{
		return new TranslationTextComponent("gui.worldhandler.title.items.enchantment");
	}
	
	@Override
	public IFormattableTextComponent getTabTitle()
	{
		return new TranslationTextComponent("gui.worldhandler.tab.items.enchantment");
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
