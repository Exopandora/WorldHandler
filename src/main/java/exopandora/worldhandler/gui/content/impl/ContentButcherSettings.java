package exopandora.worldhandler.gui.content.impl;

import java.util.List;
import java.util.stream.Collectors;

import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.menu.impl.ILogicPageList;
import exopandora.worldhandler.gui.menu.impl.MenuPageList;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiButtonTooltip;
import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.ActionHelper;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

public class ContentButcherSettings extends ContentChild
{
	private ResourceLocation entity;
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		List<EntityType<?>> list = ForgeRegistries.ENTITIES.getValues().stream().filter(EntityType::canSummon).collect(Collectors.toList());
		
		MenuPageList<EntityType<?>> entities = new MenuPageList<EntityType<?>>(x, y, list, 114, 20, 3, container, new ILogicPageList<EntityType<?>>()
		{
			@Override
			public MutableComponent translate(EntityType<?> item)
			{
				return new TranslatableComponent(item.getDescriptionId());
			}
			
			@Override
			public MutableComponent toTooltip(EntityType<?> item)
			{
				return new TextComponent(item.getRegistryName().toString());
			}
			
			@Override
			public void onClick(EntityType<?> item)
			{
				ContentButcherSettings.this.entity = item.getRegistryName();
				container.initButtons();
			}
			
			@Override
			public GuiButtonBase onRegister(int x, int y, int width, int height, MutableComponent text, EntityType<?> item, ActionHandler actionHandler)
			{
				return new GuiButtonTooltip(x, y, width, height, text, this.toTooltip(item), actionHandler);
			}
			
			@Override
			public String getId()
			{
				return "entities";
			}
		});
		
		container.add(entities);
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonBase button1;
		GuiButtonBase button2;
		
		container.add(new GuiButtonBase(x, y + 96, 114, 20, new TranslatableComponent("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, new TranslatableComponent("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(button1 = new GuiButtonBase(x + 118, y + 24, 114, 20, new TranslatableComponent("gui.worldhandler.generic.enable"), () ->
		{
			Config.CLIENT.getButcher().addEntity(this.entity);
			container.initButtons();
		}));
		container.add(button2 = new GuiButtonBase(x + 118, y + 48, 114, 20, new TranslatableComponent("gui.worldhandler.generic.disable"), () ->
		{
			Config.CLIENT.getButcher().removeEntity(this.entity);
			container.initButtons();
		}));
		
		boolean contains = Config.CLIENT.getButcher().containsEntity(this.entity);
		
		button1.active = !contains;
		button2.active = contains;
	}
}
