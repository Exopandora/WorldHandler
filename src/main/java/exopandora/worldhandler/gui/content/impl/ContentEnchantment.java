package exopandora.worldhandler.gui.content.impl;

import java.util.ArrayList;

import exopandora.worldhandler.WorldHandler;
import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderEnchantment;
import exopandora.worldhandler.builder.types.Type;
import exopandora.worldhandler.gui.button.EnumTooltip;
import exopandora.worldhandler.gui.button.GuiButtonWorldHandler;
import exopandora.worldhandler.gui.button.GuiSlider;
import exopandora.worldhandler.gui.button.responder.SimpleResponder;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.content.element.impl.ElementPageList;
import exopandora.worldhandler.gui.content.element.logic.ILogicPageList;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
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
		ElementPageList<ResourceLocation, String> enchantments = new ElementPageList<ResourceLocation, String>(x, y, new ArrayList<ResourceLocation>(Enchantment.REGISTRY.getKeys()), null, 114, 20, 3, this, new int[] {3, 4, 5}, new ILogicPageList<ResourceLocation, String>()
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
				builderEnchantment.setEnchantment(clicked);
				builderEnchantment.setLevel(1);
			}
			
			@Override
			public void onRegister(int id, int x, int y, int width, int height, String display, String registry, boolean enabled, ResourceLocation value, Container container)
			{
				GuiButtonWorldHandler button = new GuiButtonWorldHandler(id, x, y, width, height, display, registry, EnumTooltip.TOP_RIGHT);
				button.enabled = enabled;
				container.add(button);
			}
			
			@Override
			public ResourceLocation convert(String object)
			{
				return Type.parseResourceLocation(object);
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
		container.add(new GuiButtonWorldHandler(0, x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back")));
		container.add(new GuiButtonWorldHandler(1, x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame")));
		
		container.add(new GuiSlider<String>(this, container, "enchantment", x + 118, y + 24, 114, 20, I18n.format("gui.worldhandler.items.enchantment.level"), 1, Enchantment.REGISTRY.getObject(this.builderEnchantment.getEnchantment()).getMaxLevel(), 1, new SimpleResponder<String>(value ->
		{
			this.builderEnchantment.setLevel(value.intValue());
		})));
		
		container.add(new GuiButtonWorldHandler(2, x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.items.enchantment.enchant")));
	}
	
	@Override
	public void actionPerformed(Container container, GuiButton button)
	{
		switch(button.id)
		{
			case 2:
				WorldHandler.sendCommand(this.builderEnchantment);
				container.initGui();
				break;
			default:
				break;
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
