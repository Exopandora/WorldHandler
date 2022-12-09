package exopandora.worldhandler.gui.content.impl;

import java.util.ArrayList;

import exopandora.worldhandler.builder.impl.EnchantCommandBuilder;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.widget.button.GuiSlider;
import exopandora.worldhandler.gui.widget.button.LogicSliderSimple;
import exopandora.worldhandler.gui.widget.menu.impl.ILogicPageList;
import exopandora.worldhandler.gui.widget.menu.impl.MenuPageList;
import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;

public class ContentEnchantment extends Content
{
	private final EnchantCommandBuilder builderEnchantment = new EnchantCommandBuilder();
	private final CommandPreview preview = new CommandPreview(this.builderEnchantment, EnchantCommandBuilder.Label.ENCHANT_LEVEL);
	
	@Override
	public CommandPreview getCommandPreview()
	{
		return this.preview;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		MenuPageList<Enchantment> enchantments = new MenuPageList<Enchantment>(x, y, new ArrayList<Enchantment>(ForgeRegistries.ENCHANTMENTS.getValues()), 114, 20, 3, container, new ILogicPageList<Enchantment>()
		{
			@Override
			public MutableComponent translate(Enchantment enchantment)
			{
				return Component.translatable(enchantment.getDescriptionId());
			}
			
			@Override
			public MutableComponent toTooltip(Enchantment enchantment)
			{
				return Component.literal(ForgeRegistries.ENCHANTMENTS.getKey(enchantment).toString());
			}
			
			@Override
			public void onClick(Enchantment enchantment)
			{
				ContentEnchantment.this.builderEnchantment.enchantment().set(enchantment);
				ContentEnchantment.this.builderEnchantment.level().set(1);
				container.initButtons();
			}
			
			@Override
			public GuiButtonBase onRegister(int x, int y, int width, int height, MutableComponent text, Enchantment item, ActionHandler actionHandler)
			{
				return new GuiButtonTooltip(x, y, width, height, text, this.toTooltip(item), actionHandler);
			}
			
			@Override
			public String getId()
			{
				return "enchantments";
			}
		});
		
		container.addMenu(enchantments);
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.addRenderableWidget(new GuiButtonBase(x, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.addRenderableWidget(new GuiButtonBase(x + 118, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.addRenderableWidget(new GuiSlider(x + 118, y + 24, 114, 20, 1, this.builderEnchantment.enchantment().getEnchantment().getMaxLevel(), 1, container, new LogicSliderSimple("enchantment", Component.translatable("gui.worldhandler.items.enchantment.level"), value ->
		{
			this.builderEnchantment.level().set(value.intValue());
		})));
		
		container.addRenderableWidget(new GuiButtonBase(x + 118, y + 48, 114, 20, Component.translatable("gui.worldhandler.items.enchantment.enchant"), () ->
		{
			CommandHelper.sendCommand(container.getPlayer(), this.builderEnchantment, EnchantCommandBuilder.Label.ENCHANT_LEVEL);
		}));
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.ITEMS;
	}
	
	@Override
	public MutableComponent getTitle()
	{
		return Component.translatable("gui.worldhandler.title.items.enchantment");
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return Component.translatable("gui.worldhandler.tab.items.enchantment");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.ENCHANTMENT;
	}
	
	@Override
	public void onPlayerNameChanged(String username)
	{
		this.builderEnchantment.target().setTarget(username);
	}
}
